package com.example.layanankesehatan.responses

import com.example.layanankesehatan.models.Clinic
import com.example.layanankesehatan.models.Pharmacy
import com.google.gson.annotations.SerializedName

data class ClinicResponse(
    @SerializedName("klinik") var klinik: List<Clinic>
)