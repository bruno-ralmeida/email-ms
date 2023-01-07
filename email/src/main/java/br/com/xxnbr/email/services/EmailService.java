package br.com.xxnbr.email.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import br.com.xxnbr.email.enums.StatusEmail;
import br.com.xxnbr.email.models.EmailModel;
import br.com.xxnbr.email.repositories.EmailRepository;

@Service
public class EmailService {

  @Autowired
  private EmailRepository repository;

  @Autowired
  private AmazonSesClient sesClient;

  public EmailModel sendEmail(EmailModel email) {
    email.setSendDateTime(LocalDateTime.now());

    try {
      AmazonSimpleEmailServiceClientBuilder.standard()
          .withRegion(Regions.US_EAST_1).build();

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

      sesClient.getAmazonSimpleEmailService().sendEmail(request);
      email.setStatus(StatusEmail.SENT);

    } catch (MailException e) {
      email.setStatus(StatusEmail.ERROR);
    }
    return repository.save(email);
  }
}
