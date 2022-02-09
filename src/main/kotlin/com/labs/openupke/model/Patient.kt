package com.labs.openupke.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.stereotype.Component

@Component
@Document("patient")
data class Patient(
        @Id
        val id: String,
        @Field(name = "name")
        @Indexed(unique = true)
        var patientName:String
)
