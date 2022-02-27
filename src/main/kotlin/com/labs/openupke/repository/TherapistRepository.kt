package com.labs.openupke.repository

import com.labs.openupke.model.Therapist
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TherapistRepository : MongoRepository<Therapist, String> {

    @Query("{'email':?0}")
    fun findByEmail(name : String) : Optional<Therapist>

    @Query("{'id':?0}")
    fun findByUniqueId(id : String) : Optional<Therapist>

}