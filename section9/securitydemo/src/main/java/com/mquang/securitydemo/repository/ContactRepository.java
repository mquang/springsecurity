package com.mquang.securitydemo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mquang.securitydemo.model.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

}
