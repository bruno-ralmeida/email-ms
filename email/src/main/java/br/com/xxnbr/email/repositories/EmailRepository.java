package br.com.xxnbr.email.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.xxnbr.email.models.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {

}
