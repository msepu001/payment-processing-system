package com.example.payment.config;

    /*
     * RdsCredentials
     *
     * Represents the username and password stored in AWS Secrets Manager.
     * Jackson converts the JSON secret returned by AWS into this Java record.
     */


    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record RdsCredentials(
            String username,
            String password
    ) {
    }

