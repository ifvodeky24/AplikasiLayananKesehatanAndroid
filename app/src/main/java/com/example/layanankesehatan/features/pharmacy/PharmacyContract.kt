package com.example.layanankesehatan.features.pharmacy

import com.example.layanankesehatan.models.Pharmacy

interface PharmacyContract {
    interface View {
        fun hideLoading()
        fun showLoading()
        fun displayPharmacy(pharmacy: List<Pharmacy>)
    }
    interface Presenter{
        fun getPharmacyData()
        fun onDestroyPresenter()
    }
}