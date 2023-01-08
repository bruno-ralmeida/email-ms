package br.com.xxnbr.email.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

import br.com.xxnbr.email.enums.StatusEmail;
import br.com.xxnbr.email.models.EmailModel;
import br.com.xxnbr.email.repositories.EmailRepository;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

  @InjectMocks
  private EmailService service;

  @Mock
  private EmailRepository repository;

  @Mock
  private AmazonSesClientHelper awsHelper;

  @Test
  public void deveEnviarEmailERetornarStatusSent() {

    final var email = EmailModel.builder()
        .id(1L)
        .emailFrom("teste@teste.com")
        .emailTo("test@To.com")
        .ownerRef("BRA")
        .subject("Olá")
        .text("Olá mundo")
        .build();

    when(repository.save(any(EmailModel.class)))
        .thenReturn(email);

    EmailModel sendEmail = service.sendEmail(email);

    assertEquals(StatusEmail.SENT, sendEmail.getStatus());
  }

  @Test
  public void deveLancarExececaoAoEnviarEmail() {

    final var email = EmailModel.builder()
        .id(1L)
        .emailFrom("teste@teste.com")
        .emailTo("test@To.com")
        .ownerRef("BRA")
        .subject("Olá")
        .text("Olá mundo")
        .build();

    when(awsHelper.sendEmail(any(EmailModel.class)))
        .thenThrow(new MessageRejectedException(""));

    when(repository.save(any(EmailModel.class)))
        .thenReturn(email);

    EmailModel sendEmail = service.sendEmail(email);

    assertEquals(StatusEmail.ERROR, sendEmail.getStatus());
  }
}
