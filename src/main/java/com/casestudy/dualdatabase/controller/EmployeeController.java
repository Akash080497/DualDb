package com.casestudy.dualdatabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.casestudy.dualdatabase.dto.EmployeeRequest;
import com.casestudy.dualdatabase.dto.EmployeeResponse;
import com.casestudy.dualdatabase.job.entity.EmployeeJob;
import com.casestudy.dualdatabase.job.repo.EmployeeJobRepo;
import com.casestudy.dualdatabase.personal.entity.EmployeePersonal;
import com.casestudy.dualdatabase.personal.repo.EmployeePersonalRepo;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeePersonalRepo personalRepo;

    @Autowired
    private EmployeeJobRepo jobRepo;

    @PostMapping("/create")

    public String createEmployee(@RequestBody EmployeeRequest request) {

        EmployeePersonal personal = new EmployeePersonal();

        personal.setName(request.getName());

        personal.setEmail(request.getEmail());

        personal = personalRepo.save(personal);

        EmployeeJob job = new EmployeeJob();

        job.setId(personal.getId()); // use same ID

        job.setDesignation(request.getDesignation());

        job.setSalary(request.getSalary());

        jobRepo.save(job);

        return "Employee created with ID: " + personal.getId();

    }

    @GetMapping("/{id}")

    public EmployeeResponse getEmployee(@PathVariable Long id) {

        EmployeePersonal personal = personalRepo.findById(id).orElseThrow();

        EmployeeJob job = jobRepo.findById(id).orElseThrow();

        return new EmployeeResponse(

                personal.getId(), personal.getName(), personal.getEmail(),

                job.getDesignation(), job.getSalary()

        );

    }

} 