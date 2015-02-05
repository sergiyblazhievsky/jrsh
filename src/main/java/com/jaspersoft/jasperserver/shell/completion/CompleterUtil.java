package com.jaspersoft.jasperserver.shell.completion;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Alexander Krasnyanskiy
 */
public class CompleterUtil {

    /**
     * Returns the difference between input and common strings.
     *
     * @param input  input string
     * @param common the common part
     * @return the difference
     */
    public static String diff(String input, String common) {
        return common.isEmpty() ? "" : common.substring(input.length());
    }


    /**
     * Searches the longest common substring.
     *
     * @param src input
     * @param col collection of candidates
     * @return the longest common substring
     */
    public static String commonSubstring(String src, Collection<String> col) {
        Set<String> set = new TreeSet<>();
        for (String el : col) {
            if (el.startsWith(src)) set.add(el);
        }
        String common = "", small = "", temp = "";
        for (String el : set.toArray(new String[set.size()])) {
            if (small.length() < el.length()) small = el;
        }
        char[] smallStrChars = small.toCharArray();
        for (char c : smallStrChars) {
            temp += c;
            for (String el : set.toArray(new String[set.size()])) {
                if (!el.contains(temp)) {
                    temp = "";
                    break;
                }
            }
            if (!temp.equals("") && temp.length() > common.length()) {
                common = temp;
            }
        }
        return common;
    }


    /**
     * Checks if candidates collection contains the source string.
     *
     * @param src source string
     * @param col the candidates collection
     * @return yes/no
     */
    public static boolean match(String src, Collection<String> col) {
        Set<String> set = new TreeSet<>();
        for (String el : col) {
            if (el.startsWith(src)) set.add(el);
        }
        return set.size() == 1;
    }


    /**
     * Selects matched elements only.
     *
     * @param src match source
     * @param col the candidates collection
     * @return matched candidates
     */
    public static Collection<String> filter(String src, Collection<String> col) {
        Set<String> set = new TreeSet<>();
        for (String el : col) {
            if (el.startsWith(src)) set.add(el);
        }
        return set;
    }
}