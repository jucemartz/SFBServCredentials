package com.lottus.sfbservice.credentials.controller;

import com.lottus.sfbservice.credentials.config.ApplicationConfiguration;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CrendentialsControllerTest extends SecuredControllerTest {

    @Override
    protected ApplicationConfiguration getAppConfig() {
        return null;
    }
}
