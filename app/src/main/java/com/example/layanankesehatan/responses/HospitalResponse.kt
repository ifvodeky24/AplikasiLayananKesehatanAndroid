package com.example.layanankesehatan.responses

import com.example.layanankesehatan.models.Hospital
import com.example.layanankesehatan.models.Pharmacy
import com.google.gson.annotations.SerializedName

data class HospitalResponse(
    @SerializedName("rumah_sakit") var rumah_sakit: List<Hospital>
)