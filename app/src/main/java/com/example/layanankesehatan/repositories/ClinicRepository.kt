package com.example.layanankesehatan.repositories

import com.example.layanankesehatan.responses.ClinicResponse
import com.example.layanankesehatan.responses.PharmacyResponse
import io.reactivex.Flowable

interface ClinicRepository {
    fun getClinic() : Flowable<ClinicResponse>
}