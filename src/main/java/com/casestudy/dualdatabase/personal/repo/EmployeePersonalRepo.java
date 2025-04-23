package com.casestudy.dualdatabase.personal.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.dualdatabase.personal.entity.EmployeePersonal;

@Repository
public interface EmployeePersonalRepo extends JpaRepository<EmployeePersonal, Long>{

}
