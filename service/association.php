<?php
require_once "model/association.php";

define("DB_HOST", "localhost");
define("DB_NAME", "associations");
define("DB_USER", "root");
define("DB_PASS", "");

header('Content-type: application/json; charset=utf-8');

$mysqli = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

if ($mysqli->connect_errno) {
    http_response_code(500);
    die($mysqli->connect_error);
}

$mysqli->set_charset("utf8mb4");

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    http_response_code(405);
    die("Method not Allowed.");
}

if (!isset($_GET["language"])) {
    http_response_code(400);
    die("Parameter language is missing.");
}

$readAssocStmt = prepare($mysqli,
    "SELECT assoc.* "
    . "FROM association assoc "
    . "WHERE language_code=? "
);

$readAssocStmt->bind_param("s", $_GET["language"]);
$readAssocStmt->execute();
$associationsResult = $readAssocStmt->get_result();

$associations = [
    "EASY" => [],
    "MEDIUM" => [],
    "HARD" => []
];

while ($associationRow = $associationsResult->fetch_object()) {

    $association = new Association();
    $association->id = $associationRow->id;
    $association->language = $associationRow->language_code;
    $association->difficulty = $associationRow->difficulty_value;
    $association->a1 = $associationRow->a1;
    $association->a2 = $associationRow->a2;
    $association->a3 = $associationRow->a3;
    $association->a4 = $associationRow->a4;
    $association->solutionsA = [$associationRow->solution_a];
    $association->b1 = $associationRow->b1;
    $association->b2 = $associationRow->b2;
    $association->b3 = $associationRow->b3;
    $association->b4 = $associationRow->b4;
    $association->solutionsB = [$associationRow->solution_b];
    $association->c1 = $associationRow->c1;
    $association->c2 = $associationRow->c2;
    $association->c3 = $associationRow->c3;
    $association->c4 = $associationRow->c4;
    $association->solutionsC = [$associationRow->solution_c];
    $association->d1 = $associationRow->d1;
    $association->d2 = $associationRow->d2;
    $association->d3 = $associationRow->d3;
    $association->d4 = $associationRow->d4;
    $association->solutionsD = [$associationRow->solution_d];
    $association->solutions = [$associationRow->solution];

    $readAltSolStmt = prepare($mysqli,
        "SELECT alt.symbol_value, alt.alternative "
        . "FROM alternative_solution alt "
        . "WHERE alt.association_id=? "
    );

    $readAltSolStmt->bind_param("i", $association->id);
    $readAltSolStmt->execute();
    $altSolutionsResult = $readAltSolStmt->get_result();

    while ($altSolutionRow = $altSolutionsResult->fetch_object()) {
        switch ($altSolutionRow->symbol_value) {
            case "A":
                $association->solutionsA[] = $altSolutionRow->alternative;
                break;
            case "B":
                $association->solutionsB[] = $altSolutionRow->alternative;
                break;
            case "C":
                $association->solutionsC[] = $altSolutionRow->alternative;
                break;
            case "D":
                $association->solutionsD[] = $altSolutionRow->alternative;
                break;
            case "F":
                $association->solutions[] = $altSolutionRow->alternative;
                break;
        }
    }

    $altSolutionsResult->close();
    $readAltSolStmt->close();

    $associations[$association->difficulty][] = $association;

}

$associationsResult->close();
$readAssocStmt->close();
$mysqli->close();

http_response_code(200);

$response = new stdClass();
$response->associations = $associations;
echo json_encode($response, JSON_UNESCAPED_UNICODE);

function prepare($mysqli, $sql) {
    $stmt = $mysqli->prepare($sql);

    if (!$stmt) {
        http_response_code(500);
        die($mysqli->error);
    }

    return $stmt;
}
