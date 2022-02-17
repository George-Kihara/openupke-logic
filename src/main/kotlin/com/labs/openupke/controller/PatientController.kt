package com.labs.openupke.controller

import com.labs.openupke.model.Patient
import com.labs.openupke.model.StandardResponse
import com.labs.openupke.model.StandardResponsePayload
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
    fun addPatient(@RequestBody patient : Patient) : StandardResponse {
        patientService.addPatient(patient)
        return StandardResponse()
    }

    @PutMapping
    @RequestMapping("/update")
    fun updatePatient(@RequestBody patient: Patient) : StandardResponse {
        patientService.updatePatient(patient)
        return StandardResponse()
    }

    @PostMapping
    @RequestMapping("/getbyname")
    fun getPatient(@RequestBody patient: Patient) : StandardResponsePayload {
        val response = StandardResponsePayload()
        response.payload = patientService.getPatientByName(patient.patientName!!)
        return response
    }

    @PostMapping
    @RequestMapping("/getbyid")
    fun getPatientById(@RequestBody patient: Patient) : StandardResponsePayload {
        val response = StandardResponsePayload()
        response.payload = patientService.getPatientById(patient.id!!)
        return response
    }

    @GetMapping
    @RequestMapping("/getallpatients")
    fun getAllPatients() : StandardResponsePayload {
        val response = StandardResponsePayload()
        response.payload = patientService.getAllPatients()
        return response
    }

}