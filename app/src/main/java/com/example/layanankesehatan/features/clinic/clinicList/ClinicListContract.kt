package com.example.layanankesehatan.features.clinic.clinicList

import com.example.layanankesehatan.models.Clinic

interface ClinicListContract {
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