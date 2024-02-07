package com.lottus.sfbservice.credentials.mappers;

import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_CN;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_DISPLAY_NAME;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_EXT_10;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_GIVEN_NAME;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_HOME_PHONE;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_MAIL;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_MAIL_NICK_NAME;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_MOBILE;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_PROFILE_ID;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_PROXY_ADDRESSES;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_SAM_ACCOUNT_NAME;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_SN;

import com.lottus.sfbservice.credentials.dto.UserTypeDto;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import org.springframework.ldap.core.AttributesMapper;

public class UserTypeMapper implements AttributesMapper {

    /**
     * Mapping the Attributes LDAP.
     */
    public Object mapFromAttributes(Attributes attributes) throws NamingException {
        UserTypeDto user = new UserTypeDto();

        if (attributes.get(ATTR_CN) != null) {
            user.setCn((String) attributes.get(ATTR_CN).get());
        }
        if (attributes.get(ATTR_DISPLAY_NAME) != null) {
            user.setDisplayName((String) attributes.get(ATTR_DISPLAY_NAME).get());
        }
        if (attributes.get(ATTR_EXT_10) != null) {
            user.setEmailPerson((String) attributes.get(ATTR_EXT_10).get());
        }
        if (attributes.get(ATTR_GIVEN_NAME) != null) {
            user.setGivenName((String) attributes.get(ATTR_GIVEN_NAME).get());
        }
        if (attributes.get(ATTR_HOME_PHONE) != null) {
            user.setHomePhone((String) attributes.get(ATTR_HOME_PHONE).get());
        }
        if (attributes.get(ATTR_MAIL) != null) {
            user.setMail((String) attributes.get(ATTR_MAIL).get());
        }
        if (attributes.get(ATTR_MAIL_NICK_NAME) != null) {
            user.setMailNickName((String) attributes.get(ATTR_MAIL_NICK_NAME).get());
        } else {
            user.setMailNickName((String) attributes.get(ATTR_CN).get());
        }
        if (attributes.get(ATTR_MOBILE) != null) {
            user.setMobile((String) attributes.get(ATTR_MOBILE).get());
        }
        if (attributes.get(ATTR_PROFILE_ID) != null) {
            user.setProfileID((String) attributes.get(ATTR_PROFILE_ID).get());
        }
        if (attributes.get(ATTR_PROXY_ADDRESSES) != null) {
            user.setProxyAddresses((String) attributes.get(ATTR_PROXY_ADDRESSES).get());
        }
        if (attributes.get(ATTR_SAM_ACCOUNT_NAME) != null) {
            user.setStudentID((String) attributes.get(ATTR_SAM_ACCOUNT_NAME).get());
        }
        if (attributes.get(ATTR_SN) != null) {
            user.setSn((String) attributes.get(ATTR_SN).get());
        }
        return user;
    }
}
