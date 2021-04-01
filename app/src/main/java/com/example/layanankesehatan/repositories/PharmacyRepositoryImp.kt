package com.example.layanankesehatan.repositories

import com.example.layanankesehatan.responses.PharmacyResponse
import com.example.layanankesehatan.rest.HealthRest
import io.reactivex.Flowable

class PharmacyRepositoryImp(private val healthRest : HealthRest) : PharmacyRepository {
    override fun getPharmacy(): Flowable<PharmacyResponse> = healthRest.getPharmacy()
}