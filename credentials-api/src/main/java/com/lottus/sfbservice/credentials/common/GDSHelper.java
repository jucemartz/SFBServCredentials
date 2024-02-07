package com.lottus.sfbservice.credentials.common;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class GDSHelper {

    /**
     * Generate a UserName for LDAP.
     *
     * @return idealUserName.
     */
    public static String getIdealUserName(String firstName, String lastName) {
        StringBuilder idealUserName = new StringBuilder();
        idealUserName.append(firstName.trim().substring(0, 1));
        idealUserName.append(".");
        lastName = lastName.replaceAll("\\s","");
        idealUserName.append(lastName);
        return StringUtils.substring(idealUserName.toString(), 0, 20).toLowerCase();
    }

    /**
     * Generate a CommonName for LDAP.
     *
     * @return idealCommonName.
     */
    public static String getIdealCommonName(String firstName, String lastName) {
        StringBuilder commonName = new StringBuilder();
        commonName.append(firstName);
        commonName.append(" ");
        commonName.append(lastName);
        return WordUtils.capitalizeFully(commonName.toString());
    }

    /**
     * Check if CommonName and UserName exist in LDAP.
     *
     * @return idealValue.
     */
    public static String computeSeqValue(final String idValue, final List<String> existingValues,
                                           String seperator) throws Exception {
        List<Integer> templist = new ArrayList<Integer>();
        String suffix;
        String endValue;
        String idealValue = null;

        if (seperator == null) {
            seperator = "";
        }
        if (existingValues == null || existingValues.size() == 0) {
            idealValue = idValue;
            return idealValue;
        } else {
            boolean exactMatchExist = false;
            for (String value : existingValues) {
                endValue = validateChars(value);
                if (idValue.toUpperCase().contains("Ñ") && !endValue.toUpperCase().contains("Ñ")) {
                    idealValue = validateWord(idValue);
                } else {
                    idealValue = idValue;
                }
                if (idealValue.equalsIgnoreCase(endValue.trim())) {
                    exactMatchExist = true;
                } else {
                    suffix = endValue.replaceFirst(idealValue + seperator, "");
                    try {
                        templist.add(Integer.parseInt(suffix));
                    } catch (NumberFormatException e) {
                        throw new Exception("Error: " + e.getMessage());
                    }
                }
            }
            if (exactMatchExist) {
                if (templist.size() > 0) {
                    Collections.sort(templist);
                    return idealValue + seperator + (templist.get(templist.size() - 1) + 1);
                } else {
                    return idealValue + seperator + "2";
                }
            } else {
                return idealValue;
            }
        }
    }

    /**
     * Generate a userPassword for LDAP.
     *
     * @return password.
     */
    public static String getInitialPassword(Integer len) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

    /**
     * Generate a legalName for LDAP.
     *
     * @return legalName.
     */
    public static String getLegalName(String firstName, String lastName) {
        StringBuilder legalName = new StringBuilder();
        legalName.append(firstName);
        legalName.append(" ");
        legalName.append(lastName);
        return WordUtils.capitalizeFully(legalName.toString());
    }

    /**
     * Validates if the names are not null.
     */
    public static void validateNames(String firstName, String lastName) throws Exception {
        if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
            throw new Exception("El nombre y el apellido no deben estar en blanco");
        }
        if (lastName.trim().length() < 2) {
            throw new Exception("El apellido debe tener al menos 2 caracteres");
        }
        int count = validateLastName(lastName);
        if (count == 0) {
            throw new Exception("El usuario solamente tiene un solo apellido, debe tener al menos 2");
        }
    }

    /**
     * Validates if the names contain special characters.
     */
    public static String validateChars(String name) {
        String newName = null;

        if (name.contains("Ñ")) {
            newName = Normalizer.normalize(name.replace('Ñ', '\001'), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "").replaceAll("\\s+", " ").replace('\001', 'Ñ').trim();
            return newName;
        } else if (name.contains("ñ")) {
            newName = Normalizer.normalize(name.replace('ñ', '\001'), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "").replaceAll("\\s+", " ").replace('\001', 'ñ').trim();
            return newName;
        }
        newName = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("\\s+", " ").trim();
        return newName;
    }

    /**
     * Validates if the names contain a "Ñ".
     */
    public static String validateWord(String name) {
        String newName = null;

        if (name.contains("Ñ")) {
            newName = name.replace("Ñ", "N");
            return newName;
        } else if (name.contains("ñ")) {
            newName = name.replace("ñ", "n");
            return newName;
        }
        return name;
    }

    /**
     * Validates if the names contain a consecutive number.
     */
    public static boolean containName(List matchingUser, String idealUser) {
        boolean isNumeric = false;
        for (int i = 0; i < matchingUser.size(); i++) {
            int max = matchingUser.size() - 1;
            String existValue = (String) matchingUser.get(matchingUser.size() - 1);
            String endValue = validateChars(existValue);
            if ((max == 0) && (idealUser.trim().equals(endValue.trim()))) {
                isNumeric = true;
            } else if (idealUser.toUpperCase().contains("Ñ") && !endValue.toUpperCase().contains("Ñ")) {
                isNumeric = true;
            } else {
                Pattern p = Pattern.compile("[0-9]+");
                isNumeric = (endValue != null && p.matcher(endValue).find());
            }
        }
        return isNumeric;
    }

    /**
     * Adjust and correct the names contain common name.
     */
    public static List adjCommonNames(List matchingUser) {
        List<String> endList = new ArrayList<>();

        for (int i = 0; i < matchingUser.size(); i++) {
            String existValue = (String) matchingUser.get(i);
            char[] strDiv = existValue.toCharArray();
            String num = "";
            String str = "";
            for (int j = 0; j < strDiv.length; j++) {
                if (!Character.isDigit(strDiv[j])) {
                    str += strDiv[j];
                }
                if (Character.isDigit(strDiv[j])) {
                    num += strDiv[j];
                }
            }
            String concat = str.trim() + " " + num.trim();
            endList.add(concat.trim());
        }
        return endList;
    }

    /**
     * Validate the LastName of User.
     */
    public static int validateLastName(String lastName) {
        int countSpaces = 0;

        for (int i = 0; i < lastName.length(); i++) {
            if (lastName.charAt(i) == ' ') {
                countSpaces++;
            }
        }
        return countSpaces;
    }

    /**
     * Validate the NickName of User.
     */
    public static String valNickName(String userName) {
        char[] strDiv = userName.toCharArray();
        String str = "";
        for (int j = 0; j < strDiv.length; j++) {
            if (!Character.isDigit(strDiv[j])) {
                str += strDiv[j];
            }
        }
        return str.trim();
    }
}
