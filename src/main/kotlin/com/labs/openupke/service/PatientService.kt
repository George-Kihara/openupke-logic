package com.labs.openupke.service

import com.google.firebase.auth.FirebaseAuth
import com.labs.openupke.model.Patient
import com.labs.openupke.repository.PatientRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

@Service
class PatientService (@Autowired val patientRepository: PatientRepository) {

    private val TAG = "PatientService"

    private val logger: Logger = LoggerFactory.getLogger(PatientService::class.java)

    fun addPatient(patient : Patient) : Patient =
            patientRepository.insert(patient)

    fun updatePatient(patient: Patient) {
        val existingPatient : Patient = patientRepository
                .findById(patient.id!!)
                .orElseThrow {throw RuntimeException("Cannot find patient by ID")}
        existingPatient.patientName = patient.patientName
        patientRepository.save(existingPatient)
    }

    fun getAllPatients() : List<Patient> = patientRepository.findAll()

    fun getPatientByName(name : String) : Patient =
            patientRepository.findByName(name)
                    .orElseThrow {throw RuntimeException("Cannot find patient by name: $name")}

    fun getPatientById(id : String) : Patient =
            patientRepository.findByUniqueId(id)
                    .orElseThrow {throw RuntimeException("Cannot find patient by id: $id")}

    fun deletePatient(id : String) =
            patientRepository.deleteById(id)

    fun signInPatient() {

    }

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
            response.complete(0)
        }
        return response
    }

}