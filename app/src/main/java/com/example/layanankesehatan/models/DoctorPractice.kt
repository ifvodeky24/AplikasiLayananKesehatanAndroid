package com.example.layanankesehatan.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DoctorPractice(
    @SerializedName("id_dokter_praktek") var id_dockter_praktek: Int?,
    @SerializedName("nama_dokter_praktek") var nama_dokter_praktek: String?,
    @SerializedName("kecamatan") var kecamatan: String?,
    @SerializedName("kabupaten") var kabupaten: String?,
    @SerializedName("alamat") var alamat: String?,
    @SerializedName("foto") var foto: String?,
    @SerializedName("fasilitas") var fasilitas: String?,
    @SerializedName("deskripsi") var deskripsi: String?,
    @SerializedName("latitude") var latitude: Double?,
    @SerializedName("longitute") var longitute: Double?
    ): Parcelable