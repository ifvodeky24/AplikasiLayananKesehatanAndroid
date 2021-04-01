package com.example.layanankesehatan.features.pubicHealthCenter

import com.example.layanankesehatan.repositories.PublicHealthCenterRepositoryImp
import com.example.layanankesehatan.responses.PublicHealthCenterResponse
import com.rahmat.app.footballclub.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import java.util.*


class PublicHealthCenterPresenter(
    val mView: PublicHealthCenterContract.View,
    val publicHealthCenterRepositoryImp: PublicHealthCenterRepositoryImp,
    val schedule: SchedulerProvider
) : PublicHealthCenterContract.Presenter {
    val compositeDisposable = CompositeDisposable()
    override fun getPhcData() {
        mView.showLoading()
        compositeDisposable.add(publicHealthCenterRepositoryImp.getPublicHealthCenter()
            .observeOn(schedule.ui())
            .subscribeOn(schedule.io())
            .subscribeWith(object : ResourceSubscriber<PublicHealthCenterResponse>(){
                override fun onComplete() {
                    mView.hideLoading()
                }

                override fun onNext(t: PublicHealthCenterResponse) {
                    mView.displayPhc(t.puskesmas)
                }

                override fun onError(e: Throwable) {
                    mView.hideLoading()
                    //Log.d("ayam ", "${e.printStackTrace()}")
                    mView.displayPhc(Collections.emptyList())
                }
            })
        )
    }

    override fun onDestroyPresenter() {
        compositeDisposable.dispose()
    }
}