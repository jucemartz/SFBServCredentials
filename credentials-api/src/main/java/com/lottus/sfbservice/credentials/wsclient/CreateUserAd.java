package com.lottus.sfbservice.credentials.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "matricula",
    "tipoUsuario"
})
@XmlRootElement(name = "create_user_ad")
public class CreateUserAd {

    protected String matricula;
    @XmlElement(name = "tipo_usuario")
    protected String tipoUsuario;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String value) {
        this.matricula = value;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String value) {
        this.tipoUsuario = value;
    }

}
