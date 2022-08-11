package com.lottus.sfbservice.credentials.stub;

import com.lottus.virtualcampus.banner.domain.model.AdditionalIdentification;
import com.lottus.virtualcampus.banner.domain.model.AdditionalIdentificationId;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdditionalIdentificationTestStub {

    public static AdditionalIdentification prepareAdditionalIdentification(Long userId, String code, String value) {
        AdditionalIdentificationId id = new AdditionalIdentificationId();
        id.setStudentId(userId);
        id.setCode(code);

        AdditionalIdentification record = new AdditionalIdentification();
        record.setId(id);
        record.setValue(value);

        return record;
    }
}
