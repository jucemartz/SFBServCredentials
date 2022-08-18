package com.lottus.sfbservice.credentials.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "createUserAdResult"
})
@XmlRootElement(name = "create_user_adResponse")
public class CreateUserAdResponse {

    @XmlElement(name = "create_user_adResult")
    protected ConsultaResponse createUserAdResult;

    public ConsultaResponse getCreateUserAdResult() {
        return createUserAdResult;
    }

    public void setCreateUserAdResult(ConsultaResponse value) {
        this.createUserAdResult = value;
    }

}
