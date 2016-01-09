package com.github.zxh0.pbg2.gen;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {

    // fooBar => foo_bar
    public static String toSnakeCase(String fieldName) {
        return Pattern.compile("(?=[A-Z])").splitAsStream(fieldName)
                .map(word -> word.toLowerCase())
                .collect(Collectors.joining("_"));
    }

    // s => "s"
    public static String quote(String s) {
        // todo escape
        return "\"" + s + "\"";
    }

}
