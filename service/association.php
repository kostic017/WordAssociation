<?php
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

$stmt = prepare($mysqli,
    "SELECT assoc.* "
    . "FROM association assoc "
    . "WHERE language_code=? "
);

$stmt->bind_param("s", $_GET["language"]);
$stmt->execute();
$result = $stmt->get_result();

$associations = [
    "EASY" => [],
    "MEDIUM" => [],
    "HARD" => []
];

while ($association = $result->fetch_object()) {
    $stmt1 = prepare($mysqli,
        "SELECT alt.symbol_value, alt.alternative "
        . "FROM alternative_solution alt "
        . "WHERE alt.association_id=? "
    );

    $association->solution_a = [$association->solution_a];
    $association->solution_b = [$association->solution_b];
    $association->solution_c = [$association->solution_c];
    $association->solution_d = [$association->solution_d];
    $association->solution = [$association->solution];

    $stmt1->bind_param("i", $association->id);
    $stmt1->execute();
    $result1 = $stmt1->get_result();

    while ($alt_solution = $result1->fetch_object()) {
        switch ($alt_solution->symbol_value) {
            case "A":
                $association->solution_a[] = $alt_solution->alternative;
                break;
            case "B":
                $association->solution_b[] = $alt_solution->alternative;
                break;
            case "C":
                $association->solution_c[] = $alt_solution->alternative;
                break;
            case "D":
                $association->solution_d[] = $alt_solution->alternative;
                break;
            case "F":
                $association->solution[] = $alt_solution->alternative;
                break;
        }
    }

    $associations[$association->difficulty_value][] = $association;
}

$result->close();
$stmt->close();
$mysqli->close();

http_response_code(200);
echo json_encode($associations, JSON_UNESCAPED_UNICODE);

function prepare($mysqli, $sql) {
    $stmt = $mysqli->prepare($sql);

    if (!$stmt) {
        http_response_code(500);
        die($mysqli->error);
    }

    return $stmt;
}
