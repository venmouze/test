package com.example.emergency.services

import com.example.emergency.model.EmployeesData
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class CheckEmployeesService() {

    val employees: CheckEmployeesService? = null

    private var employeesData: EmployeesDataApi? = null

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com/api/v1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        employeesData = retrofit.create(EmployeesDataApi::class.java)

    }

    fun getEmployeesData(): Observable<EmployeesData>? {
        return employeesData?.employeesData()?.map({ respond -> respond })
    }




}