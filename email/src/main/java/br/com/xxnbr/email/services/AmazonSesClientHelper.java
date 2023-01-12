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
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

import br.com.xxnbr.email.models.EmailModel;

@Service
public class AmazonSesClientHelper {

  @Value("${aws.access_key}")
  private String accessKey;

  @Value("${aws.secret_key}")
  private String secretKey;

  public SendEmailResult sendEmail(EmailModel email) throws Exception {
    SendEmailRequest request = new SendEmailRequest()
        .withDestination(
            new Destination().withToAddresses(email.getEmailTo()))
        .withMessage(new Message()
            .withBody(new Body()
                .withText(new Content()
                    .withCharset("UTF-8").withData(email.getText())))
            .withSubject(new Content()
                .withCharset("UTF-8").withData(email.getSubject())))
        .withSource(email.getEmailFrom());
    return getAmazonSimpleEmailService().sendEmail(request);
  }

  private AmazonSimpleEmailService getAmazonSimpleEmailService() {
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