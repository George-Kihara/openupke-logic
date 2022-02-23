package com.labs.openupke.controller

import com.labs.openupke.ResultCallback
import com.labs.openupke.model.Patient
import com.labs.openupke.model.StandardResponse
import com.labs.openupke.model.StandardResponsePayload
import com.labs.openupke.service.PatientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.logging.Logger

@RestController
@RequestMapping("/api/patient")
class PatientController (@Autowired val patientService: PatientService) {

    @GetMapping
    fun index() : ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }

    @PostMapping
    @RequestMapping("/add")
    fun addPatient(@RequestBody patient : Patient,
                   @RequestHeader("SKEY") skey: String?) : Future<StandardResponse>? {
        val response = CompletableFuture<StandardResponse>()
        if (verifyToken(skey).get() == 200) {
            patientService.addPatient(patient)
            response.complete(StandardResponse())
        } else {
            response.complete(
                    StandardResponse(
                    501,
                    "SKEY is missing"
            ))
        }
        return response
    }

    private fun verifyToken(skey: String?) : Future<Int> {
        val response = CompletableFuture<Int>()
        var status = 200
        if (skey == null ||
                skey == "") {
            status = 0
        }
        response.complete(status)
        return response
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