package com.labs.openupke.controller

import com.labs.openupke.model.StandardResponse
import com.labs.openupke.model.StandardResponsePayload
import com.labs.openupke.model.Therapist
import com.labs.openupke.service.TherapistService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

@RestController
@RequestMapping("/api/therapist")
class TherapistController (@Autowired val therapistService: TherapistService) {
    private val TAG = "TherapistController"

    private val logger: Logger = LoggerFactory.getLogger(TherapistController::class.java)

    @GetMapping
    fun index() : ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }

    @PostMapping
    @RequestMapping("/savenewuser")
    fun saveTherapist(@RequestBody therapist : Therapist,
                    @RequestHeader("SKEY") skey: String?) : Future<StandardResponse>? {
        val response = CompletableFuture<StandardResponse>()

        if (therapistService.verifyFirebaseToken(skey).get() == 200) {
            if (therapistService.getTherapistById(therapist.id!!) == null) {
                if (therapistService.getTherapistByEmail(therapist.email!!) == null) {
                    therapistService.addTherapist(therapist)
                    response.complete(StandardResponse())
                } else {
                    response.complete(
                            StandardResponse(
                                    402,
                                    "Email has been taken"
                            ))
                }
            } else {
                response.complete(
                        StandardResponse(
                                401,
                                "Therapist already exists"
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
    fun updateTherapist(@RequestBody therapist: Therapist) : StandardResponse {
        therapistService.updateTherapist(therapist)
        return StandardResponse()
    }

    @PostMapping
    @RequestMapping("/getbyemail")
    fun getTherapist(@RequestBody therapist: Therapist) : StandardResponsePayload {
        val response = StandardResponsePayload()
        response.payload = therapistService.getTherapistByEmail(therapist.email!!)
        return response
    }

    @PostMapping
    @RequestMapping("/getbyid")
    fun getTherapistById(@RequestBody therapist: Therapist) : StandardResponsePayload {
        var response = StandardResponsePayload()
        try {
            val returnedTherapist = therapistService.getTherapistById(therapist.id!!)
            if (returnedTherapist != null) {
                response.payload = therapistService.getTherapistById(therapist.id!!)
            } else {
                response = StandardResponsePayload(
                        501,
                        "Could not find the therapist"
                )
            }
        } catch (e : Exception) {
            logger.error(TAG, "getTherapistById", e)
            response = StandardResponsePayload(
                    501,
                    "Could not find the therapist"
            )
        }
        return response
    }

    @GetMapping
    @RequestMapping("/getalltherapists")
    fun getAllTherapists() : StandardResponsePayload {
        val response = StandardResponsePayload()
        response.payload = therapistService.getAllTherapists()
        return response
    }
}