package br.com.xxnbr.email.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.xxnbr.email.dto.EmailDTO;
import br.com.xxnbr.email.models.EmailModel;
import br.com.xxnbr.email.services.EmailService;

@Component
public class EmailConsumer {

  @Autowired
  EmailService service;

  @RabbitListener(queues = "${spring.rabbitmq.queue}")
  public void listen(@Payload EmailDTO emailDTO) {
    var email = new EmailModel();
    BeanUtils.copyProperties(emailDTO, email);

    service.sendEmail(email);

    System.out.println("EMAIL STATUS: " + email.getStatus().name());
  }

}
