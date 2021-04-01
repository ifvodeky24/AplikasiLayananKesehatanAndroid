package com.example.layanankesehatan.repositories

import com.example.layanankesehatan.responses.ClinicResponse
import com.example.layanankesehatan.responses.PharmacyResponse
import com.example.layanankesehatan.rest.HealthRest
import io.reactivex.Flowable

class ClinicRepositoryImp(private val healthRest : HealthRest) : ClinicRepository {
    override fun getClinic(): Flowable<ClinicResponse> = healthRest.getClinic()
}