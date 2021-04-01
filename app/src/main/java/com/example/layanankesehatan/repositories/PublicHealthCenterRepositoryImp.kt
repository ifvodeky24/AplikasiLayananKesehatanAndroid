package com.example.layanankesehatan.repositories

import com.example.layanankesehatan.responses.PharmacyResponse
import com.example.layanankesehatan.responses.PublicHealthCenterResponse
import com.example.layanankesehatan.rest.HealthRest
import io.reactivex.Flowable

class PublicHealthCenterRepositoryImp(private val healthRest : HealthRest) : PublicHealthCenterRepository {
    override fun getPublicHealthCenter(): Flowable<PublicHealthCenterResponse> = healthRest.getPublicHealthCenter()
}