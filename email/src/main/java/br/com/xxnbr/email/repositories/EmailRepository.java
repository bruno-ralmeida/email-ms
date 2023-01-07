package br.com.xxnbr.email.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.xxnbr.email.models.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, Long> {

}
