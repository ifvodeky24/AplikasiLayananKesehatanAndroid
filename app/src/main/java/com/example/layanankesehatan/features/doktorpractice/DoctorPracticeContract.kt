package com.example.layanankesehatan.features.doktorpractice

import com.example.layanankesehatan.models.Clinic
import com.example.layanankesehatan.models.DoctorPractice
import com.example.layanankesehatan.models.Hospital
import com.example.layanankesehatan.models.Pharmacy

interface DoctorPracticeContract {
    interface View {
        fun hideLoading()
        fun showLoading()
        fun displayDp(clinic: List<DoctorPractice>)
    }
    interface Presenter{
        fun getDpData()
        fun onDestroyPresenter()
    }
}