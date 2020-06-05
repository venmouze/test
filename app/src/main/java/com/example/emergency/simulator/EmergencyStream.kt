package com.example.emergency.simulator

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.internal.operators.observable.ObservableCreate
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class EmergencyStream {

    fun registerSimulatorObservable(emergencyStream: Observer<Boolean>) {
        val observable: Observable<Boolean> =
            ObservableCreate(ObservableOnSubscribe { emitter ->
                emitter.onNext(Random.nextBoolean())
            })

        observable.repeatWhen { o: Observable<Any?> ->
            o.concatMap<Any?> { v: Any? ->
                Observable.timer(
                    Random.nextLong(30,60),
                    TimeUnit.SECONDS
                )
            }
        }.delay(Random.nextLong(30,60), TimeUnit.SECONDS).subscribe(emergencyStream)
    }
}
