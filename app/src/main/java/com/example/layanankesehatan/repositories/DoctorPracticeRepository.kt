package com.example.layanankesehatan.repositories

import com.example.layanankesehatan.responses.DoctorPracticeResponse
import io.reactivex.Flowable

interface DoctorPracticeRepository {
    fun getDoctorPractice() : Flowable<DoctorPracticeResponse>
}