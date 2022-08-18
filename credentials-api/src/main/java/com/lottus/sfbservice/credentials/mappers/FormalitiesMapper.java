package com.lottus.sfbservice.credentials.mappers;

import com.lottus.sfbservice.credentials.contracts.FormalitiesContract;
import com.lottus.sfbservice.credentials.domain.model.Formalities;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;


@Component
public class FormalitiesMapper {
    /**
     * Map a FormalitiesConcept domain list to a FormalitiesConceptContract list.
     *
     * @param domainObjects The FormalitiesConcept domain list.
     * @return The FormalitiesContract list.
     */
    public List<FormalitiesContract> mapToContract(List<Formalities> domainObjects) {
        return domainObjects.parallelStream().map(this::mapToContract).collect(Collectors.toList());
    }

    /**
     * Map a Formalities domain object to a FormalitiesContract object.
     *
     * @param domainObject The Formalities domain object.
     * @return The FormalitiesContract object.
     */
    public FormalitiesContract mapToContract(Formalities domainObject) {
        return FormalitiesContract.builder()
                .withFormalityNumber(domainObject.getFormalityNumber())
                .withName(domainObject.getName())
                .withCost(domainObject.getCost())
                .withDescription(domainObject.getDescription())
                .withStatus(domainObject.getStatus())
                .withAcronym(domainObject.getAcronym())
                .build();
    }
}
