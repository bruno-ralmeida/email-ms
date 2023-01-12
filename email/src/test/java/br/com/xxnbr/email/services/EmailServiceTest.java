package br.com.xxnbr.email.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.simpleemail.model.MessageRejectedException;

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
        .uuid(UUID.randomUUID())
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
  public void deveLancarExececaoAoEnviarEmail() throws Exception {

    final var email = EmailModel.builder()
        .uuid(UUID.randomUUID())
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
