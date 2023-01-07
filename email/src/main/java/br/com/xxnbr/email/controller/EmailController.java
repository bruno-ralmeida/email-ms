package br.com.xxnbr.email.controller;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.xxnbr.email.dto.EmailDTO;
import br.com.xxnbr.email.models.EmailModel;
import br.com.xxnbr.email.services.EmailService;

@RestController
public class EmailController {

  @Autowired
  private EmailService service;

  @PostMapping("/sending-email")
  public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDTO emailDTO) {
    EmailModel emailModel = new EmailModel();
    BeanUtils.copyProperties(emailDTO, emailModel);

    service.sendEmail(emailModel);
    return new ResponseEntity<EmailModel>(emailModel, HttpStatus.CREATED);
  }
}
