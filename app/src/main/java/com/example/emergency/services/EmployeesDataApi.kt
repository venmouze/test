package com.example.emergency.services

import com.example.emergency.model.EmployeesData
import io.reactivex.Observable
import retrofit2.http.GET

interface EmployeesDataApi {
    //@GET("/jokes/random")
    @GET("/api/v1/employees")
    fun employeesData(): Observable<EmployeesData?>?
}