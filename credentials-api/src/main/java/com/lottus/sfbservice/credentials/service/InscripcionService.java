package com.lottus.sfbservice.credentials.service;

import com.lottus.sfbservice.credentials.contracts.CostDetailContract;
import com.lottus.sfbservice.credentials.contracts.FormalitiesContract;
import com.lottus.sfbservice.credentials.exception.ServiceException;
import java.util.List;

public interface InscripcionService {

    /**
     * Gets the formalities.
     *
     * @param school    The school of the student.
     * @return The List of {@link FormalitiesContract} information.
     */
    List<FormalitiesContract> getFormalities(String school);

    /**
     * get cost for detail codes.
     *
     * @param detailCodes the detail code registred in banner.
     * @param school    The school of the student.
     * @return List of cost.
     * @throws ServiceException in following scenarios
     *      <p>When transaction could not get from database</p>
     */
    List<CostDetailContract> getCosts(List<String> detailCodes, String school);

}
