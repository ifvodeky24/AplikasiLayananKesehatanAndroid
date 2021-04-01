package com.example.layanankesehatan.responses

import com.example.layanankesehatan.models.Pharmacy
import com.google.gson.annotations.SerializedName

data class PharmacyResponse(
    @SerializedName("apotek") var apotek: List<Pharmacy>
)