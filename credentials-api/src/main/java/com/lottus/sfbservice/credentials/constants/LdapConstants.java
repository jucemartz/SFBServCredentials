package com.lottus.sfbservice.credentials.constants;

public class LdapConstants {

    public static int UF_NORMAL_ACCOUNT = 0x0200;
    public static int UF_PASSWORD_EXPIRED = 0x800000;
    public static String OBJECT_CLASS = "objectclass";
    public static String OBJECT_CLASS_TOP = "top";
    public static String OBJECT_CLASS_USER = "user";
    public static String OBJECT_CLASS_PERSON = "person";
    public static String OBJECT_CLASS_ORG_PERSON = "organizationalPerson";
    public static String ATTR_CN = "cn";
    public static String ATTR_SN = "sn";
    public static String ATTR_GIVEN_NAME = "givenName";
    public static String ATTR_DISPLAY_NAME = "displayName";
    public static String ATTR_DESCRIPTION = "description";
    public static String ATTR_SAM_ACCOUNT_NAME = "sAMAccountName";
    public static String ATTR_UPN = "userPrincipalName";
    public static String ATTR_MAIL = "mail";
    public static String ATTR_MAIL_NICK_NAME = "mailnickName";
    public static String ATTR_USER_ACCOUNT_CONTROL = "userAccountControl";
    public static String ATTR_UNICODE_PWD = "unicodePwd";
    public static String ATTR_PROFILE_ID = "profileID";
    public static String ATTR_EXT_10 = "extensionAttribute10";
    public static String ATTR_EXT_13 = "extensionAttribute13";
    public static String ATTR_AFFILIATION = "affiliation";
    public static String ATTR_PROXY_ADDRESSES = "proxyAddresses";
    public static String ATTR_COMPANY = "company";
    public static String ATTR_HOME_PHONE = "homephone";
    public static String ATTR_MOBILE = "mobile";
    public static String USER_DN = "ou=Academic Accounts,ou=ULA,ou=AM";
    public static String BASE_DN = "OU=Academic Accounts,OU=ULA,OU=AM,OU=Global,DC=ulaalumnos,DC=int";
    public static String USER_EMAIL_DOMAIN = "my.ula.edu.mx";
}
