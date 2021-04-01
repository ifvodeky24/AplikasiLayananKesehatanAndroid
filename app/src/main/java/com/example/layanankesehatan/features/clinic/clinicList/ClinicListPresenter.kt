package com.example.layanankesehatan.features.clinic.clinicList

import com.example.layanankesehatan.features.clinic.ClinicContract
import com.example.layanankesehatan.repositories.ClinicRepositoryImp
import com.example.layanankesehatan.responses.ClinicResponse
import com.rahmat.app.footballclub.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*


class ClinicListPresenter(
    val mView: ClinicContract.View,
    val clinicRepositoryImp: ClinicRepositoryImp,
    val schedule: SchedulerProvider
) : ClinicContract.Presenter {
    val compositeDisposable = CompositeDisposable()
    override fun getClinicData() {
        mView.showLoading()
        compositeDisposable.add(clinicRepositoryImp.getClinic()
            .observeOn(schedule.ui())
            .subscribeOn(schedule.io())
            .subscribeWith(object : ResourceSubscriber<ClinicResponse>(){
                override fun onComplete() {
                    mView.hideLoading()
                }

                override fun onNext(t: ClinicResponse) {
                    mView.displayClinics(t.klinik)
                }

                override fun onError(e: Throwable) {
                    mView.hideLoading()
                    //Log.d("ayam ", "${e.printStackTrace()}")
                    mView.displayClinics(Collections.emptyList())
                }
            })
        )
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}