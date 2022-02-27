package com.labs.openupke.repository

import com.labs.openupke.model.Patient
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PatientRepository : MongoRepository<Patient, String> {

    @Query("{'name':?0}")
    fun findByName(name : String) : Optional<Patient>

    @Query("{'id':?0}")
    fun findByUniqueId(id : String) : Optional<Patient>

}