package com.example.layanankesehatan.responses

import com.example.layanankesehatan.models.Pharmacy
import com.example.layanankesehatan.models.PublicHealthCenter
import com.google.gson.annotations.SerializedName

data class PublicHealthCenterResponse(
    @SerializedName("puskesmas") var puskesmas: List<PublicHealthCenter>
)