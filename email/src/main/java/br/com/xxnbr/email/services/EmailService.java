package br.com.xxnbr.email.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.xxnbr.email.enums.StatusEmail;
import br.com.xxnbr.email.models.EmailModel;
import br.com.xxnbr.email.repositories.EmailRepository;

@Service
public class EmailService {

  @Autowired
  private EmailRepository repository;

  @Autowired
  private AmazonSesClientHelper awsHelper;

  public EmailModel sendEmail(EmailModel email) {

    email.setSendDateTime(LocalDateTime.now());

    try {
      awsHelper.sendEmail(email);
      email.setStatus(StatusEmail.SENT);

    } catch (Exception e) {
      email.setStatus(StatusEmail.ERROR);
    }
    return repository.save(email);
  }
}
