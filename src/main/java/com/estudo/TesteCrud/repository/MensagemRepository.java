package com.estudo.TesteCrud.repository;

import org.springframework.data.repository.CrudRepository;

import com.estudo.TesteCrud.model.MensagemModel;
import com.estudo.TesteCrud.model.UserModel;


public interface MensagemRepository extends CrudRepository<MensagemModel,String>{
    Iterable<MensagemModel> findByUser(UserModel user);
}
