package com.example.layanankesehatan.repositories

import com.example.layanankesehatan.responses.HospitalResponse
import com.example.layanankesehatan.responses.PharmacyResponse
import com.example.layanankesehatan.rest.HealthRest
import io.reactivex.Flowable

class HospitalRepositoryImp(private val healthRest : HealthRest) : HospitalRepository {
    override fun getHospital(): Flowable<HospitalResponse> = healthRest.getHospital()
}