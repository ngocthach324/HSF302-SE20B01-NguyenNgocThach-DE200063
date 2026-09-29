package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByStudentsIsEmpty();
}
