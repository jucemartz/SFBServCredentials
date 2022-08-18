package com.lottus.sfbservice.credentials.wsclient;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public ConsultaResponse createConsultaResponse() {
        return new ConsultaResponse();
    }

    public CreateUserAd createCreateUserAd() {
        return new CreateUserAd();
    }

    public CreateUserAdResponse createCreateUserAdResponse() {
        return new CreateUserAdResponse();
    }

}
