package com.lottus.sfbservice.credentials.stub;


import static com.lottus.sfbservice.credentials.stub.CommonTestStub.FAKER;

import com.lottus.virtualcampus.banner.domain.model.AcademicLevel;
import com.lottus.virtualcampus.banner.domain.model.Campus;
import com.lottus.virtualcampus.banner.domain.model.Student;
import com.lottus.virtualcampus.banner.domain.model.StudentAcademicInformation;
import com.lottus.virtualcampus.banner.domain.model.StudentAcademicInformationId;

import java.util.Collections;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentInformationTestStub {


    public static Student prepareStudent() {
        Student student = new Student();
        student.setId(FAKER.random().nextLong(1));
        student.setChangeIndicator("N");
        student.setEnrollmentNumber(FAKER.numerify("?#######"));
        student.setFirstName(FAKER.name().firstName());
        student.setLastName(FAKER.name().lastName());
        student.setStudentAcademicInformation(Collections.singletonList(prepareStudentAcademicInformation()));

        return student;
    }

    public static StudentAcademicInformation prepareStudentAcademicInformation() {
        StudentAcademicInformationId id = new StudentAcademicInformationId();
        id.setPeriodCode(FAKER.letterify("????####"));
        id.setSchoolCode(FAKER.letterify("????"));
        id.setStudentId(FAKER.random().nextLong(1));

        StudentAcademicInformation academicInformation = new StudentAcademicInformation();
        academicInformation.setId(id);
        academicInformation.setCampus(prepareCampus());
        academicInformation.setAcademicLevel(prepareAcademicLevel());

        return academicInformation;
    }

    public static Campus prepareCampus() {
        Campus campus = new Campus();
        campus.setCode(FAKER.bothify("???"));
        campus.setDescription(FAKER.lorem().paragraph());

        return campus;
    }

    public static AcademicLevel prepareAcademicLevel() {
        AcademicLevel academicLevel = new AcademicLevel();
        academicLevel.setCode(FAKER.bothify("???"));
        academicLevel.setDescription(FAKER.lorem().paragraph());

        return academicLevel;
    }
}
