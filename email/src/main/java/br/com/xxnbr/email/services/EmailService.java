package br.com.xxnbr.email.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.xxnbr.email.models.EmailModel;
import br.com.xxnbr.email.repositories.EmailRepository;

@Service
public class EmailService {

  @Autowired
  private EmailRepository repository;

  public void sendEmail(EmailModel email) {
    repository.save(email);
  }
}
