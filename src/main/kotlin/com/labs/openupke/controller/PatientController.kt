package com.labs.openupke.controller

import com.google.firebase.auth.FirebaseAuth
import com.labs.openupke.model.Patient
import com.labs.openupke.model.StandardResponse
import com.labs.openupke.model.StandardResponsePayload
import com.labs.openupke.service.PatientService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future


@RestController
@RequestMapping("/api/patient")
class PatientController (@Autowired val patientService: PatientService) {

    private val TAG = "PatientController"

    private val logger: Logger = LoggerFactory.getLogger(PatientController::class.java)

    @GetMapping
    fun index() : ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }

    @PostMapping
    @RequestMapping("/savenewuser")
    fun savePatient(@RequestBody patient : Patient,
                   @RequestHeader("SKEY") skey: String?) : Future<StandardResponse>? {
        val response = CompletableFuture<StandardResponse>()

        if (patientService.verifyFirebaseToken(skey).get() == 200) {
            if (patientService.getPatientById(patient.id!!) == null) {
                if (patientService.getPatientByName(patient.name!!) == null) {
                    patientService.addPatient(patient)
                    response.complete(StandardResponse())
                } else {
                    response.complete(
                            StandardResponse(
                                    402,
                                    "Username has been taken"
                            ))
                }
            } else {
                response.complete(
                        StandardResponse(
                                401,
                                "Patient already exists"
                        ))
            }
        } else {
            response.complete(
                    StandardResponse(
                    503,
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
        response.payload = patientService.getPatientByName(patient.name!!)
        return response
    }

    @PostMapping
    @RequestMapping("/getbyid")
    fun getPatientById(@RequestBody patient: Patient) : StandardResponsePayload {
        var response = StandardResponsePayload()
        try {
            val returnedPatient = patientService.getPatientById(patient.id!!)
            if (returnedPatient != null) {
                response.payload = patientService.getPatientById(patient.id!!)
            } else {
                response = StandardResponsePayload(
                        501,
                        "Could not find the patient"
                )
            }
        } catch (e : Exception) {
            logger.error(TAG, "getPatientById", e)
            response = StandardResponsePayload(
                    501,
                    "Could not find the patient"
            )
        }
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