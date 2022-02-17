package com.labs.openupke.model

data class StandardResponse(
        var status : Int ?= 200,
        var message : String ?= "success"
)

data class StandardResponsePayload(
        var status : Int ?= 200,
        var message : String ?= "success",
        var payload : Any ?= null
)
