package com.labs.openupke.controller

import com.labs.openupke.model.Patient
import com.labs.openupke.service.PatientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/patient")
class PatientController (@Autowired val patientService: PatientService) {

    @GetMapping
    fun index() : ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }

    @PostMapping
    @RequestMapping("/add")
    fun addPatient(@RequestBody patient : Patient) : ResponseEntity<String> {
        patientService.addPatient(patient)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping
    @RequestMapping("/update")
    fun updatePatient(@RequestBody patient: Patient) : ResponseEntity<String> {
        patientService.updatePatient(patient)
        return ResponseEntity.ok().build()
    }

}