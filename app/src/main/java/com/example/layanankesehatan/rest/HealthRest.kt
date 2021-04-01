package com.example.layanankesehatan.rest

import com.example.layanankesehatan.models.PublicHealthCenter
import com.example.layanankesehatan.responses.*
import io.reactivex.Flowable
import retrofit2.http.GET

interface HealthRest {
    @GET("apotek")
    fun getPharmacy() : Flowable<PharmacyResponse>

    @GET("klinik")
    fun getClinic() : Flowable<ClinicResponse>

    @GET("rumahsakit")
    fun getHospital() : Flowable<HospitalResponse>

    @GET("puskesmas")
    fun getPublicHealthCenter() : Flowable<PublicHealthCenterResponse>

    @GET("dokterpraktek")
    fun getDoctorPractice() : Flowable<DoctorPracticeResponse>
}