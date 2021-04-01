package com.example.layanankesehatan.repositories

import com.example.layanankesehatan.responses.PharmacyResponse
import io.reactivex.Flowable

interface PharmacyRepository {
    fun getPharmacy() : Flowable<PharmacyResponse>
}