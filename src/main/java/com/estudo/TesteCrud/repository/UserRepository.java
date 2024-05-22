package com.estudo.TesteCrud.repository;

import org.springframework.data.repository.CrudRepository;

import com.estudo.TesteCrud.model.UserModel;

public interface UserRepository extends CrudRepository<UserModel,String>{
    UserModel findByUsername(String username);
}
