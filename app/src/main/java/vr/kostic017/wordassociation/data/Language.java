package vr.kostic017.wordassociation.data;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Language {
    SR("sr", "Srpski"),
    EN("en", "English");

    private String code;
    private String name;

    Language(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private static Map<String, Language> valuesMap;

    static {
        valuesMap = new HashMap<>();
        for (Language language : Language.values()) {
            valuesMap.put(language.code, language);
        }
    }

    @JsonCreator
    public static Language fromCode(String code) {
        return valuesMap.get(code);
    }

    @JsonValue
    public String getCode() {
        return this.code;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
