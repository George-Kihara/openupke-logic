package com.labs.openupke.boot.patient

import com.labs.openupke.model.Patient
import com.labs.openupke.repository.PatientRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
class PatientIntegrationTest {

    @Autowired
    private lateinit var applicationContext : WebApplicationContext

    @Autowired
    private lateinit var patientRepository : PatientRepository

    private lateinit var mockMvc : MockMvc

    @BeforeEach
    fun setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build()
    }

    @Test
    @Throws(Exception::class)
    fun savePatientReturnsHttpStatusOk() {
        this.patientRepository.insert(Patient(
                "testId",
                "testName"
        ))
        mockMvc.perform(
                post("/api/patient/savenewuser")
                        .param("id","testId")
                        .param("name","testName"))
                .andExpect(status().isOk)
    }

}