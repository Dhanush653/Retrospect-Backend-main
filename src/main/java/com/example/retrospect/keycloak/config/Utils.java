package com.example.retrospect.keycloak.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public class Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true).findAndRegisterModules();

    /**
     *
     * getSource-This method returns the username for the loggedin user
     *
     * @return String
     */
    public static String getSource() {
        String principal = null;
        try {

            if (SecurityContextHolder.getContext().getAuthentication() == null
                    || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
                LOGGER.warn("Could not get the logged in user, due to automated/async job ");
                return "admin";
            }
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Jwt) {
                Jwt customUserData = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Map<String, Object> claim = (Map<String, Object>) customUserData.getClaims();
                if (claim != null)
                    principal = (String) claim.get("email");
            } else {
                principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }
            return principal;
        } catch (Exception e) {
            LOGGER.warn("Could not get the logged in user, due to automated/async job.");
            return "admin";
        }
    }

    /**
     * convertObjectToJson- This method is used to convert the object To JSON
     *
     * @param object
     * @return
     */
    public static <T> String convertObjectToJson(T object){
        String json = null;
        try {
            json = objectMapper.writer().writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error("convertObjectToJson :", e);
            throw new RuntimeException("convertObjectToJson cased exception in utils: " + e);
        }
        return json;
    }
}
