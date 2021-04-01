package com.example.layanankesehatan.features.doktorpractice

import com.example.layanankesehatan.features.hospital.HospitalContract
import com.example.layanankesehatan.repositories.DoctorPracticeRepositoryImp
import com.example.layanankesehatan.repositories.HospitalRepositoryImp
import com.example.layanankesehatan.responses.DoctorPracticeResponse
import com.example.layanankesehatan.responses.HospitalResponse
import com.rahmat.app.footballclub.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*


class DoctorPracticePresenter(
    val mView: DoctorPracticeContract.View,
    val doctorPracticeRepositoryImp: DoctorPracticeRepositoryImp,
    val schedule: SchedulerProvider
) : DoctorPracticeContract.Presenter {
    val compositeDisposable = CompositeDisposable()
    override fun getDpData() {
        mView.showLoading()
        compositeDisposable.add(doctorPracticeRepositoryImp.getDoctorPractice()
            .observeOn(schedule.ui())
            .subscribeOn(schedule.io())
            .subscribeWith(object : ResourceSubscriber<DoctorPracticeResponse>(){
                override fun onComplete() {
                    mView.hideLoading()
                }

                override fun onNext(t: DoctorPracticeResponse) {
                    mView.displayDp(t.dokter_praktek)
                }

                override fun onError(e: Throwable) {
                    mView.hideLoading()
                    //Log.d("ayam ", "${e.printStackTrace()}")
                    mView.displayDp(Collections.emptyList())
                }
            })
        )
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}