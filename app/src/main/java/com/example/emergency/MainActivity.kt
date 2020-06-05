package com.example.emergency

import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import com.example.emergency.model.EmployeesData
import com.example.emergency.services.CheckEmployeesService
import com.example.emergency.simulator.EmergencyStream
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val emergencyStream: EmergencyStream = EmergencyStream()

        val emergencyStreamObserver = createEmergencyStreamObserver()
        emergencyStream.registerSimulatorObservable(emergencyStreamObserver)

    }

    fun createEmergencyStreamObserver():  Observer<Boolean>{
        return object : Observer<Boolean> {
            override fun onSubscribe(d: Disposable) {
                println("OnSubscribe")
            }

            override fun onNext(emergencyStatus: Boolean) {
                println("EmergencyStatus:" + emergencyStatus)
                checkPeopleInsideBuilding(emergencyStatus)
            }

            override fun onError(e: Throwable) {
                println("OnError")
            }
            override fun onComplete() {
                println("OnComplete")
            }
        }
    }

    fun checkPeopleInsideBuilding(emergencyStatus: Boolean) {
        if (emergencyStatus) {
            val observable: Observable<EmployeesData>? = CheckEmployeesService().getEmployeesData()
            val obsInterval = Observable.timer(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())

            val employeesYellow =
                observable?.flatMapIterable { employeesData -> employeesData.data }
                    ?.filter { employee -> employee.employee_age < "18" }
            val employeesBlue = observable?.flatMapIterable { employeesData -> employeesData.data }
                ?.filter { employee -> employee.employee_age >= "18" && employee.employee_age < "60" }
            val employeesBrown = observable?.flatMapIterable { employeesData -> employeesData.data }
                ?.filter { employee -> employee.employee_age > "60" }

            employeesYellow?.subscribe { value -> println("FT_VALUE_YELLOW: ${value}")}
            employeesBlue?.subscribe { value -> println("FT_VALUEB_BLUE: ${value}")}
            employeesBrown?.subscribe { value -> println("FT_VALUE_BROWN: ${value}")}
        }
    }
}
