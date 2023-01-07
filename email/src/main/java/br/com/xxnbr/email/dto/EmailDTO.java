package br.com.xxnbr.email.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailDTO {

  @NotBlank
  private String ownerRef;

  @NotBlank
  @Email
  private String emailFrom;

  @NotBlank
  @Email
  private String emailTo;

  @NotBlank
  private String subject;

  @NotBlank
  private String text;
}
