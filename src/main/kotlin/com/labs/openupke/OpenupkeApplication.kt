package com.labs.openupke

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.FileInputStream


@SpringBootApplication
class OpenupkeApplication

fun main(args: Array<String>) {
	val serviceAccount = FileInputStream(System.getenv("FIREBASE_SDK"))
	val options: FirebaseOptions = FirebaseOptions.builder()
			.setCredentials(GoogleCredentials.fromStream(serviceAccount))
			.build()
	FirebaseApp.initializeApp(options)
	runApplication<OpenupkeApplication>(*args)
}