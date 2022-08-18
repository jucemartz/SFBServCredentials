package com.lottus.sfbservice.credentials.mappers;

import static com.lottus.sfbservice.credentials.common.utils.DateUtils.dateToString;
import static java.util.Objects.nonNull;

import com.lottus.sfbservice.credentials.constants.InscripcionConstants;
import com.lottus.sfbservice.credentials.contracts.TransactionContract;
import com.lottus.virtualcampus.banner.domain.model.TransactionDetails;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    /**
     * Map a PaymentConcept domain list to a PaymentConceptContract list.
     *
     * @param domainObjects The PaymentConcept domain list.
     * @return The PaymentConceptContract list.
     */
    public List<TransactionContract> mapToContract(List<TransactionDetails> domainObjects) {
        return domainObjects.parallelStream().map(this::mapToContract).collect(Collectors.toList());
    }

    /**
     * Map a PaymentConcept domain object to a PaymentConceptContract object.
     *
     * @param domainObject The PaymentConcept domain object.
     * @return The PaymentConceptContract object.
     */
    public TransactionContract mapToContract(TransactionDetails domainObject) {
        String dueDate = nonNull(domainObject.getEffectiveDate())
                ? dateToString(domainObject.getEffectiveDate()) : null;

        return TransactionContract.builder()
                .withTransactionNumber(domainObject.getId().getTransactionNumber())
                .withBalance(domainObject.getBalance())
                .withConceptCode(domainObject.getCode())
                .withConceptDescription(domainObject.getTransactionType().getDescription())
                .withDueDate(dueDate)
                .withPeriodCode(domainObject.getAcademicPeriod().getId().getPeriodCode())
                .withPeriodDescription(domainObject.getAcademicPeriod().getDescription())
                .withCanBeCancelled(canBeCancelled(domainObject))
                .build();
    }

    private boolean canBeCancelled(TransactionDetails transactionDetails) {
        return !InscripcionConstants.NON_CANCELLABLE_CATEGORIES
                        .contains(transactionDetails.getTransactionType().getCategory());
    }

}
