package com.example.layanankesehatan.repositories

import com.example.layanankesehatan.responses.HospitalResponse
import com.example.layanankesehatan.responses.PharmacyResponse
import io.reactivex.Flowable

interface HospitalRepository {
    fun getHospital() : Flowable<HospitalResponse>
}