package com.example.demojavaspringboot.repository;


import com.example.demojavaspringboot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
