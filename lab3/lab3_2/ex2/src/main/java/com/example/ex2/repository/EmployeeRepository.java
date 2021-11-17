package com.example.ex2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.ex2.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    List<Employee> findByEmailId(String email);
}
