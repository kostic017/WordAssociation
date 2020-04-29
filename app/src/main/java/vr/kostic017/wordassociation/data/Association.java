package vr.kostic017.wordassociation.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Association {

    private String id;

    private Language language;
    private Difficulty difficulty;

    private String a1;
    private String a2;
    private String a3;
    private String a4;

    private String b1;
    private String b2;
    private String b3;
    private String b4;

    private String c1;
    private String c2;
    private String c3;
    private String c4;

    private String d1;
    private String d2;
    private String d3;
    private String d4;

    private List<String> solutionsA;
    private List<String> solutionsB;
    private List<String> solutionsC;
    private List<String> solutionsD;
    private List<String> solutions;

}
