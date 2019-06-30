package de.htw.berlin.landregisterbackendapplication.services;

import de.htw.berlin.landregisterbackendapplication.hyperledger.client.CAClient;
import de.htw.berlin.landregisterbackendapplication.hyperledger.config.Config;
import de.htw.berlin.landregisterbackendapplication.hyperledger.user.UserContext;
import de.htw.berlin.landregisterbackendapplication.hyperledger.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

import static de.htw.berlin.landregisterbackendapplication.hyperledger.config.Config.*;


@Component
public class UserRegistrationEnrollmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationEnrollmentService.class);

    private UserContext adminContext;
    private UserContext userContext;

    public HttpStatus registerEnrollUser() {
        try {
            CAClient caClient = new CAClient(CA_ORG1_URL, null);
            // Enroll Admin to Org1MSP
            UserContext _adminContext = new UserContext();
            _adminContext.setName(ADMIN);
            _adminContext.setAffiliation(Config.ORG1);
            _adminContext.setMspId(Config.ORG1_MSP);
            caClient.setAdminUserContext(_adminContext);
            adminContext = caClient.enrollAdminUser(ADMIN, ADMIN_PASSWORD);

            // Register and Enroll user to Org1MSP
            UserContext _userContext = new UserContext();
            String name = "user" + System.currentTimeMillis();
            _userContext.setName(name);
            _userContext.setRoles(Collections.singleton("client"));
            _userContext.setAffiliation(AFFILIATION);
            _userContext.setMspId(Config.ORG1_MSP);

            String eSecret = caClient.registerUser(name, Config.ORG1);

            userContext = caClient.enrollUser(_userContext, eSecret);

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public UserContext getAdminContext() {
        return adminContext;
    }

    public UserContext getUserContext() {
        return userContext;
    }
}
