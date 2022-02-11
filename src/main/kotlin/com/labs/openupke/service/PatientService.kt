package com.labs.openupke.service

import com.labs.openupke.model.Patient
import com.labs.openupke.repository.PatientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PatientService (@Autowired val patientRepository: PatientRepository) {

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
                    .orElseThrow {throw RuntimeException("Cannot find patient by name")}

    fun deletePatient(id : String) =
            patientRepository.deleteById(id)

}