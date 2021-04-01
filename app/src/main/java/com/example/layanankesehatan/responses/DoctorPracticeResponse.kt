package com.example.layanankesehatan.responses

import com.example.layanankesehatan.models.DoctorPractice
import com.example.layanankesehatan.models.Pharmacy
import com.google.gson.annotations.SerializedName

data class DoctorPracticeResponse(
    @SerializedName("dokter_praktek") var dokter_praktek: List<DoctorPractice>
)