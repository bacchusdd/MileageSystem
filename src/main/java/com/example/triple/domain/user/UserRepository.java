package com.example.triple.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {

    Users findByUserId(String userId);
}
