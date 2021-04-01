package com.example.layanankesehatan.features.hospital

import com.example.layanankesehatan.features.clinic.ClinicContract
import com.example.layanankesehatan.repositories.ClinicRepositoryImp
import com.example.layanankesehatan.repositories.HospitalRepositoryImp
import com.example.layanankesehatan.responses.ClinicResponse
import com.example.layanankesehatan.responses.HospitalResponse
import com.rahmat.app.footballclub.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*


class HospitalPresenter(
    val mView: HospitalContract.View,
    val hospitalRepositoryImp: HospitalRepositoryImp,
    val schedule: SchedulerProvider
) : HospitalContract.Presenter {
    val compositeDisposable = CompositeDisposable()
    override fun getHospitalData() {
        mView.showLoading()
        compositeDisposable.add(hospitalRepositoryImp.getHospital()
            .observeOn(schedule.ui())
            .subscribeOn(schedule.io())
            .subscribeWith(object : ResourceSubscriber<HospitalResponse>(){
                override fun onComplete() {
                    mView.hideLoading()
                }

                override fun onNext(t: HospitalResponse) {
                    mView.displayHospitals(t.rumah_sakit)
                }

                override fun onError(e: Throwable) {
                    mView.hideLoading()
                    //Log.d("ayam ", "${e.printStackTrace()}")
                    mView.displayHospitals(Collections.emptyList())
                }
            })
        )
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}