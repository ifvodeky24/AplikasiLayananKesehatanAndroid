package com.example.layanankesehatan.repositories

import com.example.layanankesehatan.responses.PharmacyResponse
import com.example.layanankesehatan.responses.PublicHealthCenterResponse
import io.reactivex.Flowable

interface PublicHealthCenterRepository {
    fun getPublicHealthCenter() : Flowable<PublicHealthCenterResponse>
}