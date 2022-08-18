package com.lottus.sfbservice.credentials.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consulta_response", propOrder = {
    "responseCode",
    "responseMessage"
})
public class ConsultaResponse {

    @XmlElement(name = "response_code")
    protected String responseCode;
    @XmlElement(name = "response_message")
    protected String responseMessage;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String value) {
        this.responseCode = value;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String value) {
        this.responseMessage = value;
    }

}
