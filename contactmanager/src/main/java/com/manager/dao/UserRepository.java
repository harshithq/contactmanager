package com.manager.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manager.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{

}
