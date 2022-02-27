package com.labs.openupke.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Component

@Component
@Document("patient")
data class Patient(
        @Id
        val id: String ?= null,
        @Indexed(unique = true)
        var name:String ?= null
)
