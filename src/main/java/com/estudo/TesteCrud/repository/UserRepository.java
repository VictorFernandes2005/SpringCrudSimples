package com.estudo.TesteCrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.TesteCrud.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long>{
    UserModel findByUsername(String username);
}
