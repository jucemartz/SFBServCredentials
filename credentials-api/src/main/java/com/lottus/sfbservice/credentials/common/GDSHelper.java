package com.lottus.sfbservice.credentials.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

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
     * Check if users exist in LDAP.
     *
     * @return idealValue.
     */
    public static String computeSequencedValue(final String idealValue, final List<String> existingValues,
                                               String seperator) {
        if (seperator == null) {
            seperator = "";
        }
        if (existingValues == null || existingValues.size() == 0) {
            return idealValue;
        } else {
            boolean exactMatchExist = false;
            List<Integer> templist = new ArrayList<Integer>();
            String suffix = null;
            for (String value : existingValues) {
                if (idealValue.equalsIgnoreCase(value)) {
                    exactMatchExist = true;
                } else {
                    suffix = value.replaceFirst(idealValue + seperator, "");
                    try {
                        templist.add(Integer.parseInt(suffix));
                    } catch (NumberFormatException e) {
                        System.out.println("Error: " + e.getMessage());
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
    public static String getInitialPassword(String lastName) {
        String formattedLastName =
                StringUtils.substring(WordUtils.capitalizeFully(lastName),0,2);
        String formattedDate = DateFormatUtils.format(new Date(), "MMddyy");
        if (formattedLastName != null && formattedLastName.trim().length() < 2) {
            formattedLastName += "z";
        }
        StringBuilder password = new StringBuilder();
        password.append(formattedLastName);
        password.append(formattedDate);
        return password.toString();
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
            throw new Exception("First Name or last Name should not be blank.");
        }
        if (lastName.trim().length() < 2) {
            throw new Exception("Last Name should have atleast 2 characters.");
        }
    }
}
