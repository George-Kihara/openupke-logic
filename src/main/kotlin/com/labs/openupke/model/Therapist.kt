package com.labs.openupke.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Component

@Component
@Document("therapist")
data class Therapist(
        @Id
        val id: String ?= null,
        var name:String ?= null,
        @Indexed(unique = true)
        var email:String ?= null
)
