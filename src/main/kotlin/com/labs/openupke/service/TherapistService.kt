package com.labs.openupke.service

import com.google.firebase.auth.FirebaseAuth
import com.labs.openupke.model.Therapist
import com.labs.openupke.repository.TherapistRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future


@Service
class TherapistService (@Autowired val therapistRepository: TherapistRepository) {

    private val TAG = "TherapistService"

    private val logger: Logger = LoggerFactory.getLogger(TherapistService::class.java)

    fun addTherapist(therapist : Therapist) : Therapist =
            therapistRepository.insert(therapist)

    fun updateTherapist(therapist: Therapist) {
        val existingTherapist : Therapist = therapistRepository
                .findById(therapist.id!!)
                .orElseThrow {throw RuntimeException("Cannot find Therapist by ID")}
        existingTherapist.name = therapist.name
        therapistRepository.save(existingTherapist)
    }

    fun getAllTherapists() : List<Therapist> = therapistRepository.findAll()

    fun getTherapistByEmail(email : String) : Therapist? =
            therapistRepository.findByEmail(email)
                    .orElse( null )

    fun getTherapistById(id : String) : Therapist? =
            therapistRepository.findByUniqueId(id)
                    .orElse( null )

    fun deleteTherapist(id : String) =
            therapistRepository.deleteById(id)

    fun verifyFirebaseToken(skey: String?) : Future<Int> {
        val response = CompletableFuture<Int>()
        try {
            var status = 200
            if (skey == null ||
                    skey == "") {
                status = 0
            }
            FirebaseAuth.getInstance().verifyIdToken(skey)
            response.complete(status)
        } catch (e : Exception) {
            logger.error(TAG, "verifyFirebaseToken",e)
            response.complete(200) // Change to 0 when app is ready
        }
        return response
    }

}