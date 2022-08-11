package com.lottus.sfbservice.credentials.persistence.repository;

import com.lottus.sfbservice.credentials.domain.model.Formalities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FormalitiesRepository extends
        JpaRepository<Formalities, Long>, JpaSpecificationExecutor<Formalities> {
}
