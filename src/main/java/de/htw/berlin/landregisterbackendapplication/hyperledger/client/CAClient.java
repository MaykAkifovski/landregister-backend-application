package de.htw.berlin.landregisterbackendapplication.hyperledger.client;

import de.htw.berlin.landregisterbackendapplication.hyperledger.user.UserContext;
import de.htw.berlin.landregisterbackendapplication.hyperledger.util.Util;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Level;

public class CAClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CAClient.class);

    @Autowired
    private Util util;

    private String caUrl;
    private Properties caProperties;

    private HFCAClient instance;

    private UserContext adminContext;

    public CAClient(String caUrl, Properties caProperties) throws IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, CryptoException, NoSuchMethodException, MalformedURLException, ClassNotFoundException {
        this.caUrl = caUrl;
        this.caProperties = caProperties;
        init();
    }

    private void init() throws MalformedURLException, IllegalAccessException, InstantiationException, ClassNotFoundException, CryptoException, InvalidArgumentException, NoSuchMethodException, InvocationTargetException {
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        instance = HFCAClient.createNewInstance(caUrl, caProperties);
        instance.setCryptoSuite(cryptoSuite);
    }

    public void setAdminUserContext(UserContext adminUserContext) {
        this.adminContext = adminUserContext;
    }

    public UserContext enrollAdminUser(String username, String password) throws EnrollmentException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException {
        Enrollment adminEnrollment = instance.enroll(username, password);
        adminContext.setEnrollment(adminEnrollment);
        LOGGER.info("CA - {} Enrolled Admin.", caUrl);
        return adminContext;

    }

    public String registerUser(String username, String organization) throws Exception {
        RegistrationRequest rr = new RegistrationRequest(username, organization);
        String enrollmentSecret = instance.register(rr, adminContext);
        LOGGER.info("CA - {} Registered User - {}", caUrl, username);
        return enrollmentSecret;
    }

    public UserContext enrollUser(UserContext user, String secret) throws EnrollmentException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException {
        Enrollment enrollment = instance.enroll(user.getName(), secret);
        user.setEnrollment(enrollment);
        LOGGER.info("CA - {} Enrolled User - {}", caUrl, user.getName());
        return user;
    }
}
