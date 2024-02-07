package com.lottus.sfbservice.credentials.service;

import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_CN;
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
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_SN;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.ATTR_UPN;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.BASE_DN;
import static com.lottus.sfbservice.credentials.constants.LdapConstants.USER_EMAIL_DOMAIN;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_107;
import static com.lottus.sfbservice.credentials.enums.ApplicationErrorCode.ERROR_502;
import static com.lottus.sfbservice.credentials.exception.HandledException.ErrorType.INTERNAL_ERROR;

import com.lottus.sfbservice.credentials.common.GDSHelper;
import com.lottus.sfbservice.credentials.config.ApplicationConfiguration;
import com.lottus.sfbservice.credentials.contracts.request.GetPersonCredentialsRequest;
import com.lottus.sfbservice.credentials.dto.UserTypeDto;
import com.lottus.sfbservice.credentials.exception.ServiceException;
import com.lottus.virtualcampus.banner.domain.dto.UserDto;
import com.lottus.virtualcampus.banner.domain.repository.TransactionDetailsRepository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.Name;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.stereotype.Service;

@Service
public class UpdateADServiceImpl implements UpdateADService {

    InputStream inputStream;

    @Autowired
    private ApplicationConfiguration appConfig;

    @Autowired
    private TransactionDetailsRepository repository;

    private final Logger logger = LoggerFactory.getLogger(UpdateADServiceImpl.class);

    @Override
    public List<String> updateUser(GetPersonCredentialsRequest personRequest) throws Exception {
        try {
            repository.contexto(personRequest.getData().getSchool());
        } catch (Exception e) {
            logger.error(personRequest.getData().getSchool() + " -1 error");
        }
        Properties prop = new Properties();
        String propFileName = "application.properties";
        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        prop.load(inputStream);
        final String context = prop.getProperty("java.naming.ldap.context.factory");
        final String url = prop.getProperty("java.naming.ldap.provider.url");
        final String authentication = prop.getProperty("java.naming.ldap.security.authentication");
        final String principal = prop.getProperty("java.naming.ldap.security.principal");
        final String credentials = prop.getProperty("java.naming.ldap.security.credentials");

        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, context);
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_AUTHENTICATION, authentication);
        env.put(Context.SECURITY_PRINCIPAL, principal);
        env.put(Context.SECURITY_CREDENTIALS, credentials);
        DirContext ctx = new InitialDirContext(env);
        List<String> ldapValues = new ArrayList<>();

        try {
            UserDto userDto =
                    repository.findUser(personRequest.getData().getStudentId()).orElseThrow(() ->
                            new ServiceException(INTERNAL_ERROR, ERROR_502.getErrorId(),
                                    appConfig.getErrorMessage(ERROR_502)));
            final String fiName = userDto.getFirstName();
            final String laName = userDto.getLastName();
            final String email = userDto.getEmail();
            final String phone = userDto.getPhone();
            final String profileId = userDto.getProfileId();

            ActiveDirectoryServiceImpl ldapClient = new ActiveDirectoryServiceImpl();
            List<UserTypeDto> existingUsers = ldapClient.searchAllUsers(profileId, ATTR_PROFILE_ID);
            if (existingUsers == null || existingUsers.size() == 0) {
                throw new Exception("No se encontro ningun usuario con este profileId [" + profileId + "]");
            } else if (existingUsers.size() > 1) {
                throw new Exception("Se encontraron multiples registros de este profileId [" + profileId + "]");
            } else {
                final UserTypeDto existingUser = existingUsers.get(0);
                ModificationItem[] mods = new ModificationItem[11];
                String firstName = GDSHelper.validateChars(fiName);
                String lastName = GDSHelper.validateChars(laName);
                String firName = GDSHelper.validateWord(firstName);
                String lasName = GDSHelper.validateWord(lastName);

                if (StringUtils.isNotEmpty(firName)) {
                    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute(ATTR_GIVEN_NAME, StringUtils.capitalize(firName)));
                    logger.info("UPDATE ATTR_GIVEN_NAME");
                }
                if (StringUtils.isNotEmpty(lasName)) {
                    mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute(ATTR_SN, StringUtils.capitalize(lasName)));
                    logger.info("UPDATE ATTR_SN");
                }

                String newDisplayName = GDSHelper.getLegalName(firName, lasName);
                mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute(ATTR_DISPLAY_NAME, newDisplayName));
                logger.info("UPDATE ATTR_DISPLAY_NAME");
                mods[3] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute(ATTR_EXT_10, email));
                logger.info("UPDATE ATTR_EXT_10");
                mods[4] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute(ATTR_EXT_13, DateFormatUtils.format(new Date(), "MMddyy")));
                logger.info("UPDATE ATTR_EXT_13");
                mods[5] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute(ATTR_HOME_PHONE, phone));
                logger.info("UPDATE ATTR_HOME_PHONE");
                mods[6] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                        new BasicAttribute(ATTR_MOBILE, phone));
                logger.info("UPDATE ATTR_MOBILE");

                String userName = ldapClient.computeUserName(firName, lasName);
                String oldNickName = GDSHelper.valNickName(existingUser.getMailNickName());
                String newNickName = GDSHelper.valNickName(userName);
                String nickName;
                String accountMail;
                String proxyEmail;

                if (oldNickName.equals(newNickName)) {
                    nickName = existingUser.getMailNickName();
                    accountMail = existingUser.getMail();
                    proxyEmail = existingUser.getProxyAddresses();
                    mods[7] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute(ATTR_MAIL_NICK_NAME, nickName));
                    logger.info("UPDATE OLD ATTR_MAIL_NICK_NAME");
                    mods[8] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute(ATTR_MAIL, accountMail));
                    logger.info("UPDATE OLD ATTR_MAIL");
                    mods[9] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute(ATTR_UPN, accountMail));
                    logger.info("UPDATE OLD ATTR_UPN");
                    mods[10] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute(ATTR_PROXY_ADDRESSES, proxyEmail));
                    logger.info("UPDATE OLD ATTR_PROXY_ADDRESSES");
                } else {
                    nickName = userName;
                    accountMail = nickName + "@" + USER_EMAIL_DOMAIN;
                    proxyEmail = "SMTP:" + accountMail;
                    mods[7] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute(ATTR_MAIL_NICK_NAME, userName));
                    logger.info("UPDATE ATTR_MAIL_NICK_NAME");
                    mods[8] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute(ATTR_MAIL, accountMail));
                    logger.info("UPDATE ATTR_MAIL");
                    mods[9] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute(ATTR_UPN, accountMail));
                    logger.info("UPDATE ATTR_UPN");
                    mods[10] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute(ATTR_PROXY_ADDRESSES, proxyEmail));
                    logger.info("UPDATE ATTR_PROXY_ADDRESSES");
                }
                Name name = buildDN(existingUser.getCn());
                ctx.modifyAttributes(name, mods);
                ctx.close();
                logger.info("**** UPDATE ALL ATTRIBUTES ****");
                repository.uniEmail(0,personRequest.getData().getStudentId(),accountMail);
                logger.info("updateEmailUni");
                ldapValues.add(nickName);
                ldapValues.add("");
                ldapValues.add(accountMail);

            } 
        } catch (Exception e) {
            logger.error(appConfig.getErrorMessage(ERROR_107));
            new ServiceException(INTERNAL_ERROR, ERROR_107.getErrorId(),
                    appConfig.getErrorMessage(ERROR_107));
        }
        return ldapValues;
    }

    private Name buildDN(String commonName) {
        DistinguishedName distinguishedName = new DistinguishedName(BASE_DN);
        distinguishedName.add(ATTR_CN, commonName);
        logger.info("distinguishedName: " + distinguishedName.toString());
        return distinguishedName;
    }
}
