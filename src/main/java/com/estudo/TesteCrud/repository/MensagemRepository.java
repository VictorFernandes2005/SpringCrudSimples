package com.estudo.TesteCrud.repository;

import org.springframework.data.repository.CrudRepository;

import com.estudo.TesteCrud.model.MensagemModel;

public interface MensagemRepository extends CrudRepository<MensagemModel,Long>{
}
