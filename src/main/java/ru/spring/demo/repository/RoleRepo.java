package ru.spring.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.demo.model.Role;

public interface RoleRepo extends JpaRepository <Role, Long> {
}
