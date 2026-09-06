package com.example.payment.config;

/*
 * AwsSecretsService
 *
 * Retrieves the RDS credentials from AWS Secrets Manager.
 *
 * The AWS SDK automatically uses the default credentials provider chain.
 * On EC2, that allows it to use temporary credentials from the IAM role
 * attached to the EC2 instance instead of hardcoded AWS access keys.
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@Service
public class AwsSecretsService {

   private final ObjectMapper objectMapper;

    public AwsSecretsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public RdsCredentials getRdsCredentials(String secretName) {

        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .build()) {

            var request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();

            var response = client.getSecretValue(request);

            return objectMapper.readValue(
                    response.secretString(),
                    RdsCredentials.class
            );

        } catch (JsonProcessingException exception) {
            throw new IllegalStateException(
                    "Unable to parse RDS credentials from Secrets Manager",
                    exception
            );
        }
    }
}
