package com.lottus.sfbservice.credentials.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "FORMALITIES")
public class Formalities {

    @Id
    @Column(name = "formality_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "formalityNumber", nullable = false)
    private String formalityNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private String cost;

    @Column(name = "status")
    private String status;

    @Column(name = "acronym")
    private String acronym;
}
