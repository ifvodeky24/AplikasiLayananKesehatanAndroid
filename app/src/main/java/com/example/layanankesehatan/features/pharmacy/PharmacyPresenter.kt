package com.example.layanankesehatan.features.pharmacy

import android.util.Log
import com.example.layanankesehatan.repositories.PharmacyRepositoryImp
import com.example.layanankesehatan.responses.PharmacyResponse
import com.rahmat.app.footballclub.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*


class PharmacyPresenter(
    val mView: PharmacyContract.View,
    val pharmacyRepositoryImp: PharmacyRepositoryImp,
    val schedule: SchedulerProvider
) : PharmacyContract.Presenter{
    val compositeDisposable = CompositeDisposable()
    override fun getPharmacyData() {
        mView.showLoading()
        compositeDisposable.add(pharmacyRepositoryImp.getPharmacy()
            .observeOn(schedule.ui())
            .subscribeOn(schedule.io())
            .subscribeWith(object : ResourceSubscriber<PharmacyResponse>(){
                override fun onComplete() {
                    mView.hideLoading()
                }

                override fun onNext(t: PharmacyResponse) {
                    mView.displayPharmacy(t.apotek)
                }

                override fun onError(e: Throwable) {
                    mView.hideLoading()
                    //Log.d("ayam ", "${e.printStackTrace()}")
                    mView.displayPharmacy(Collections.emptyList())
                }
            })
        )
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}