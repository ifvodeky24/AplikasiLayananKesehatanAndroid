package com.example.layanankesehatan.features.hospital

import com.example.layanankesehatan.models.Clinic
import com.example.layanankesehatan.models.Hospital
import com.example.layanankesehatan.models.Pharmacy

interface HospitalContract {
    interface View {
        fun hideLoading()
        fun showLoading()
        fun displayHospitals(clinic: List<Hospital>)
    }
    interface Presenter{
        fun getHospitalData()
        fun onDestroyPresenter()
    }
}