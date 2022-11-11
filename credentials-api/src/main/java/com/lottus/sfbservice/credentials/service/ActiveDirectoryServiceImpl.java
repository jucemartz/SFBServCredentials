package com.lottus.sfbservice.credentials.service;

import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_AFFILIATION;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_CN;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_COMPANY;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_DESCRIPTION;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_DISPLAY_NAME;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_EXT_10;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_EXT_13;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_GIVEN_NAME;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_HOME_PHONE;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_MAIL;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_MAIL_NICK_NAME;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_MOBILE;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_PROFILE_ID;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_PROXY_ADDRESSES;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_SAM_ACCOUNT_NAME;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_SN;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_UNICODE_PWD;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_UPN;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_USER_ACCOUNT_CONTROL;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.OBJECT_CLASS;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.OBJECT_CLASS_ORG_PERSON;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.OBJECT_CLASS_PERSON;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.OBJECT_CLASS_TOP;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.OBJECT_CLASS_USER;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.UF_NORMAL_ACCOUNT;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.UF_PASSWORD_EXPIRED;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.USER_DN;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.USER_EMAIL_DOMAIN;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_107;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_502;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_503;
import static com.lottus.sfbservice.credentials.exception.HandledException.ErrorType.INTERNAL_ERROR;

import com.lottus.sfbservice.credentials.common.GDSHelper;
import com.lottus.sfbservice.credentials.config.ApplicationConfiguration;
import com.lottus.sfbservice.credentials.contracts.request.GetPersonCredentialsRequest;
import com.lottus.sfbservice.credentials.exception.ServiceException;
import com.lottus.virtualcampus.banner.domain.dto.UserDto;
import com.lottus.virtualcampus.banner.domain.repository.TransactionDetailsRepository;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.stereotype.Service;

@Service
public class ActiveDirectoryServiceImpl implements ActiveDirectoryService {

    ApplicationContext appCtx = new ClassPathXmlApplicationContext("spring-application-context.xml");

    LdapTemplate ldapTemplate = (LdapTemplate) appCtx.getBean("ldapTemplate");

    @Autowired
    private ApplicationConfiguration appConfig;

    @Autowired
    private TransactionDetailsRepository repository;

    private final Logger logger = LoggerFactory.getLogger(ActiveDirectoryServiceImpl.class);

    @Override
    public List<String> createUser(GetPersonCredentialsRequest personRequest) throws Exception {
        try {
            repository.contexto(personRequest.getData().getSchool());
        } catch (Exception e) {
            logger.error(personRequest.getData().getSchool() + " -1 error");
        }
        List<String> ldapValues = new ArrayList<String>();
        UserDto userDto =
                repository.findUser(personRequest.getData().getStudentId()).orElseThrow(() ->
                new ServiceException(INTERNAL_ERROR, ERROR_502.getErrorId(),
                        appConfig.getErrorMessage(ERROR_502)));
        final String fiName = userDto.getFirstName();
        final String laName = userDto.getLastName();
        final String email = userDto.getEmail();
        final String phone = userDto.getPhone();
        final String profileId = userDto.getProfileId();
        List<String> existingUsers =
                findUser(personRequest.getData().getStudentId(), profileId);
        if (existingUsers != null && existingUsers.size() > 0) {
            logger.info("Existen las credenciales en el Directorio Activo...");
            String separator = Pattern.quote("-");
            String[] concat = existingUsers.get(0).split(separator);
            ldapValues.add(concat[0]);
            ldapValues.add("");
            ldapValues.add(concat[1]);
        } else if (existingUsers == null || existingUsers.isEmpty()) {
            try {
                logger.info("Se genera la informacion del usuario en el Directorio Activo...");
                BasicAttribute objectClassAttr = new BasicAttribute(OBJECT_CLASS);
                objectClassAttr.add(OBJECT_CLASS_TOP);
                objectClassAttr.add(OBJECT_CLASS_USER);
                objectClassAttr.add(OBJECT_CLASS_PERSON);
                objectClassAttr.add(OBJECT_CLASS_ORG_PERSON);
                Attributes personAttributes = new BasicAttributes();
                personAttributes.put(objectClassAttr);
                String fnNormalize = Normalizer.normalize(fiName, Normalizer.Form.NFD);
                String firstName =
                        fnNormalize.replaceAll("[^\\p{ASCII}]", "").replaceAll("\\s+", " ").trim();
                personAttributes.put(ATTR_GIVEN_NAME, StringUtils.capitalize(firstName));
                String lnNormalize = Normalizer.normalize(laName, Normalizer.Form.NFD);
                String lastName =
                        lnNormalize.replaceAll("[^\\p{ASCII}]", "").replaceAll("\\s+", " ").trim();
                personAttributes.put(ATTR_SN, StringUtils.capitalize(lastName));
                String displayName = GDSHelper.getLegalName(firstName, lastName);
                personAttributes.put(ATTR_DISPLAY_NAME, displayName);
                String commonName = computeCommonName(firstName, lastName);
                personAttributes.put(ATTR_CN, commonName);
                String userName = computeUserName(firstName, lastName);
                personAttributes.put(ATTR_MAIL_NICK_NAME, userName);
                personAttributes.put(ATTR_DESCRIPTION, "Apollo User Account");
                personAttributes.put(ATTR_EXT_10, email);
                personAttributes.put(ATTR_EXT_13,
                        DateFormatUtils.format(new Date(), "MMddyy"));
                personAttributes.put(ATTR_HOME_PHONE, phone);
                personAttributes.put(ATTR_MOBILE, phone);
                personAttributes.put(ATTR_AFFILIATION,
                        personRequest.getData().getAffiliation().toUpperCase());
                personAttributes.put(ATTR_COMPANY,
                        personRequest.getData().getSchool().toLowerCase());
                personAttributes.put(ATTR_PROFILE_ID, profileId);
                personAttributes.put(ATTR_SAM_ACCOUNT_NAME,
                        personRequest.getData().getStudentId());
                personAttributes.put(ATTR_USER_ACCOUNT_CONTROL,
                        Integer.toString(UF_NORMAL_ACCOUNT + UF_PASSWORD_EXPIRED));
                String mail = userName + "@" + USER_EMAIL_DOMAIN;
                personAttributes.put(ATTR_MAIL, mail);
                personAttributes.put(ATTR_UPN, mail);
                String proxyEmail = "SMTP:" + mail;
                personAttributes.put(ATTR_PROXY_ADDRESSES, proxyEmail);
                String randomPassword = GDSHelper.getInitialPassword(lastName);
                String newQuotedPassword = "\"" + randomPassword + "\"";
                byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
                personAttributes.put(ATTR_UNICODE_PWD, newUnicodePassword);
                Name userDN = buildDN(commonName);
                ldapTemplate.bind(userDN, null, personAttributes);

                Integer pidm = repository.getPIDM(profileId);
                repository.inputsGBEmail(pidm, "UNIV", mail, "A", "N", "SOA_ADMIN",
                            "N", "BANNERADAPTER");
                logger.info("inputsGBEmailStudent");

                ldapValues.add(personRequest.getData().getStudentId());
                ldapValues.add(userName);
                ldapValues.add(randomPassword);
                ldapValues.add(mail);
            } catch (Exception e) {
                logger.error(appConfig.getErrorMessage(ERROR_107));
                new ServiceException(INTERNAL_ERROR, ERROR_107.getErrorId(),
                        appConfig.getErrorMessage(ERROR_107));
            }
        } else {
            logger.error(appConfig.getErrorMessage(ERROR_107));
            new ServiceException(INTERNAL_ERROR, ERROR_503.getErrorId(),
                    appConfig.getErrorMessage(ERROR_503));
        }
        return ldapValues;
    }

    /**
     * Validate if exist the user crendentials in LDAP.
     *
     * @return list.
     */
    public List<String> findUser(String studentId, String profileId)
            throws Exception {
        OrFilter orfilter = new OrFilter();
        if (StringUtils.isNotBlank(studentId)) {
            orfilter.or(new EqualsFilter(ATTR_SAM_ACCOUNT_NAME, studentId));
        }
        if (StringUtils.isNotBlank(profileId)) {
            orfilter.or(new EqualsFilter(ATTR_PROFILE_ID, profileId));
        }
        @SuppressWarnings("unchecked")
        List<String> list = ldapTemplate.search("", orfilter.encode(), new AttributesMapper() {
            public Object mapFromAttributes(Attributes attrs) throws NamingException {
                return attrs.get("mail").get() + "-" + attrs.get("mailNickname").get();
            }
        });
        return list;
    }

    private List<String> getMatchingCommonNames(String idealCommonName) {
        Filter filter = new LikeFilter(ATTR_CN, idealCommonName + "*");
        List matchingCommonNames = ldapTemplate.search("", filter.encode(), new AttributesMapper() {
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return attributes.get(ATTR_CN).get();
            }
        });
        return matchingCommonNames;
    }

    private List<String> getMatchingUserNames(String idealUserName) {
        Filter filter = new LikeFilter(ATTR_MAIL_NICK_NAME, idealUserName + "*");
        List matchingUserNames = ldapTemplate.search("", filter.encode(), new AttributesMapper() {
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return attributes.get(ATTR_MAIL_NICK_NAME).get();
            }
        });
        return matchingUserNames;
    }

    private Name buildDN(String commonName) {
        DistinguishedName distinguishedName = new DistinguishedName(USER_DN);
        distinguishedName.add(ATTR_CN, commonName);
        logger.info(distinguishedName.toString());
        return distinguishedName;
    }

    /**
     * Generate a new CommonName for LDAP.
     *
     * @return newCommonName.
     */
    public String computeCommonName(String firstName, String lastName) throws Exception {
        GDSHelper.validateNames(firstName, lastName);
        String idealCommonName = GDSHelper.getIdealCommonName(firstName, lastName);;
        List<String> matchingCommonNames = getMatchingCommonNames(idealCommonName);
        String newCommonName = GDSHelper.computeSequencedValue(idealCommonName, matchingCommonNames, " ");
        logger.info(newCommonName);
        return newCommonName;
    }

    /**
     * Generate a new UserName for LDAP.
     *
     * @return newUserName.
     */
    public String computeUserName(String firstName, String lastName) throws Exception {
        GDSHelper.validateNames(firstName, lastName);
        String idealUserName = GDSHelper.getIdealUserName(firstName, lastName);
        List<String> matchingUserNames = getMatchingUserNames(idealUserName);
        String newUserName = GDSHelper.computeSequencedValue(idealUserName, matchingUserNames, "");
        logger.info(newUserName);
        return newUserName;
    }
}
