package com.labs.openupke.controller

import com.google.firebase.auth.FirebaseAuth
import com.labs.openupke.model.Patient
import com.labs.openupke.model.StandardResponse
import com.labs.openupke.model.StandardResponsePayload
import com.labs.openupke.service.PatientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

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
        if (patientService.verifyFirebaseToken(skey).get() == 200) {
            patientService.addPatient(patient)
            response.complete(StandardResponse())
        } else {
            response.complete(
                    StandardResponse(
                    501,
                    "Invalid SKEY"
            ))
        }
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

    @GetMapping()
    @RequestMapping("/generatetoken")
    fun generateFirebaseToken() : Future<StandardResponsePayload> {
        val responsePayload = CompletableFuture<StandardResponsePayload>()
        responsePayload.complete(StandardResponsePayload(
                payload = FirebaseAuth.getInstance().createCustomToken("11jkYR6yzWOsYD7gcjC1duVG3UE3")
        ))
        return responsePayload
    }


}