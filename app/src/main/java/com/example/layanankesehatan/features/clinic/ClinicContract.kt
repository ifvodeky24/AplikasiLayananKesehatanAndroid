package com.example.layanankesehatan.features.clinic

import com.example.layanankesehatan.models.Clinic
import com.example.layanankesehatan.models.Pharmacy

interface ClinicContract {
    interface View {
        fun hideLoading()
        fun showLoading()
        fun displayClinics(clinic: List<Clinic>)
    }
    interface Presenter{
        fun getClinicData()
        fun onDestroyPresenter()
    }
}