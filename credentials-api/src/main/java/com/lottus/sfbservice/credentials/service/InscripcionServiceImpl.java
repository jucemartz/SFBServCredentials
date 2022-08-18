package com.lottus.sfbservice.credentials.service;

import com.lottus.sfbservice.credentials.common.ApplicationHelper;
import com.lottus.sfbservice.credentials.config.ApplicationConfiguration;
import com.lottus.sfbservice.credentials.contracts.CostDetailContract;
import com.lottus.sfbservice.credentials.contracts.FormalitiesContract;
import com.lottus.sfbservice.credentials.domain.model.Formalities;
import com.lottus.sfbservice.credentials.mappers.FormalitiesMapper;
import com.lottus.sfbservice.credentials.mappers.TransactionMapper;
import com.lottus.sfbservice.credentials.persistence.repository.FormalitiesRepository;

import com.lottus.virtualcampus.banner.domain.model.TransactionType;
import com.lottus.virtualcampus.banner.domain.repository.StudentAcademicInformationRepository;
import com.lottus.virtualcampus.banner.domain.repository.TransactionDetailsRepository;
import com.lottus.virtualcampus.banner.domain.repository.TransactionTypeRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    @Autowired
    private ApplicationConfiguration appConfig;

    @Autowired
    private StudentAcademicInformationRepository studentAcademicInformationRepository;

    @Autowired
    private FormalitiesRepository formalitiesRepository;

    @Autowired
    private FormalitiesMapper formalitiesMapper;

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private ApplicationHelper appHelper;


    @Override
    public List<FormalitiesContract> getFormalities(String school) {
        try {
            studentAcademicInformationRepository.getContextoBanner(school);
        } catch (Exception e) {
            System.out.println(school + " -1 error");
        }
        List<Formalities> result = Collections.emptyList();

        result = formalitiesRepository.findAll();

        return formalitiesMapper.mapToContract(result);
    }

    @Override
    public List<CostDetailContract> getCosts(List<String> detailCodes, String school) {
        try {
            studentAcademicInformationRepository.getContextoBanner(school);
        } catch (Exception e) {
            System.out.println(school + " -1 error");
        }
        List<CostDetailContract> contracts = new ArrayList<CostDetailContract>();
        for (String detailCode:detailCodes) {

            CostDetailContract contract = new CostDetailContract();
            TransactionType transactionType = transactionTypeRepository
                    .findTransactionType(detailCode).orElse(new TransactionType());
            contract.setCodeDetail(detailCode);
            if (transactionType != null && transactionType.getCost() != null) {
                contract.setCost(transactionType.getCost());
            } else {
                contract.setCost(BigDecimal.valueOf(-1));
            }
            contracts.add(contract);
        }
        return contracts;
    }
}
