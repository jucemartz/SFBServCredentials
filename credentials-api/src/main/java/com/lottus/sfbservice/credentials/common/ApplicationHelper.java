package com.lottus.sfbservice.credentials.common;

import com.lottus.sfbservice.credentials.config.ApplicationConfiguration;
import com.lottus.sfbservice.credentials.constants.ApplicationConstants;
import com.lottus.sfbservice.credentials.contracts.common.response.ApiResponseService;

import com.lottus.virtualcampus.banner.domain.model.base.AuditInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ApplicationHelper {

    @Autowired
    ApplicationConfiguration appConfig;

    /**
     * Generates an ApiResponseService object with global app settings.
     *
     * @return Created {@link ApiResponseService} object.
     */
    public ApiResponseService getApiResponseService() {
        return ApiResponseService.builder().withId(appConfig.getApiId()).withName(appConfig.getApiName())
                .build();
    }


    /**
     * Generates an AuditInfo object with global app settings.
     *
     * @return Created {@link AuditInfo} object.
     */
    public AuditInfo generateAuditInfo() {
        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setModifiedBy(ApplicationConstants.MODIFYING_SYSTEM_ID);
        auditInfo.setDataOrigin(ApplicationConstants.MODIFYING_SYSTEM_ID);
        return auditInfo;
    }
}
