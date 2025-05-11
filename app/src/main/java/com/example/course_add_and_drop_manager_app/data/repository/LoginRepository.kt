// src/data/repository/LoginRepository.kt

package com.example.course_add_and_drop_manager_app.data.repository

import com.auth0.android.jwt.JWT
import com.example.course_add_and_drop_manager_app.data.model.LoginRequest
import com.example.course_add_and_drop_manager_app.data.network.RetrofitInstance
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
//
//object LoginRepository {
//
//    suspend fun login(username: String, password: String): String {
//        // Assuming you're using Retrofit to make the request
//        val loginRequest = LoginRequest(username, password)
//        val response = RetrofitInstance.api.login(loginRequest)
//
//        if (response.isSuccessful) {
//            // Return token from successful response
//            return response.body()?.token ?: throw Exception("No token returned")
//        } else {
//            throw Exception("Login failed with error: ${response.message()}")
//        }
//    }
//}

// LoginRepository.kt


import java.net.HttpURLConnection
import java.net.URL


object LoginRepository {
    suspend fun login(username: String, password: String): String {
        val url = URL("http://192.168.137.1:5000/api/auth/login/") // Replace with your local server IP or domain

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true

        val jsonBody = JSONObject()
        jsonBody.put("username", username)
        jsonBody.put("password", password)

        connection.outputStream.use { os ->
            os.write(jsonBody.toString().toByteArray())
        }

        val responseCode = connection.responseCode
        if (responseCode == 200) {
            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonResponse = JSONObject(response)
            return jsonResponse.getString("token")
        } else {
            val errorResponse = connection.errorStream?.bufferedReader()?.use { it.readText() }
            val errorMessage = errorResponse ?: "Login failed"
            throw Exception(errorMessage)
        }
    }
}

