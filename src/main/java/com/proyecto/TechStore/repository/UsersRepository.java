package com.proyecto.TechStore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.TechStore.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
        Optional<Users> findByName(String name);
}
