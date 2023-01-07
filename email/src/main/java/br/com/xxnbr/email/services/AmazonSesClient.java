package br.com.xxnbr.email.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Service
public class AmazonSesClient {

  @Value("${aws.access_key}")
  private String accessKey;

  @Value("${aws.secret_key}")
  private String secretKey;

  @Bean
  public AmazonSimpleEmailService getAmazonSimpleEmailService() {
    return AmazonSimpleEmailServiceClientBuilder.standard()
        .withCredentials(getAwsCredentialProvider())
        .withRegion(Regions.US_EAST_1)
        .build();
  }

  private AWSCredentialsProvider getAwsCredentialProvider() {
    AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    return new AWSStaticCredentialsProvider(awsCredentials);
  }
}