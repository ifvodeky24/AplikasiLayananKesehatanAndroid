package com.example.layanankesehatan.repositories

import com.example.layanankesehatan.responses.DoctorPracticeResponse
import com.example.layanankesehatan.responses.HospitalResponse
import com.example.layanankesehatan.responses.PharmacyResponse
import com.example.layanankesehatan.rest.HealthRest
import io.reactivex.Flowable

class DoctorPracticeRepositoryImp(private val healthRest : HealthRest) : DoctorPracticeRepository {
    override fun getDoctorPractice(): Flowable<DoctorPracticeResponse> = healthRest.getDoctorPractice()
}