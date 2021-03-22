package com.stockbit.test.solution;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

    public String findFirstStringInBracket(String str) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(str);
        boolean matchFound = matcher.find();

        if (!matchFound) {
            return "";
        }

        int indexFirstBracketFound = str.indexOf("(");
        int indexClosingBracketFound = str.indexOf(")");
        return str.substring(indexFirstBracketFound + 1, indexClosingBracketFound);

    }

    public String[][] numberFour(String[] strings) {
        List<List<String>> result = new ArrayList<>();
        List<String> grouped = new ArrayList<>();
        Set<String> checker = new HashSet<>();
        for (int i = 0; i < strings.length; i++) {
            if (checker.contains(strings[i])) {
                continue;
            }

            grouped.add(strings[i]);
            checker.add(strings[i]);
            for (int y = i + 1; y < strings.length; y++) {
                if (isAnagram(strings[i], strings[y])) {
                    grouped.add(strings[y]);
                    checker.add(strings[y]);
                }
            }
            result.add(grouped);
            grouped = new ArrayList<>();
        }
        return result.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
    }


    boolean isAnagram(String string1, String string2) {
        if (string1.length() != string2.length()) {
            return false;
        }
        char[] a1 = string1.toCharArray();
        char[] a2 = string2.toCharArray();
        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }
}
