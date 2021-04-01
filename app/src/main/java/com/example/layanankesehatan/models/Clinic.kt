package com.example.layanankesehatan.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Clinic(
    @SerializedName("id_klinik") var id_klinik: String?,
    @SerializedName("nama_klinik") var nama_klinik: String?,
    @SerializedName("kecamatan") var kecamatan: String?,
    @SerializedName("kabupaten") var kabupaten: String?,
    @SerializedName("alamat") var alamat: String?,
    @SerializedName("foto") var foto: String?,
    @SerializedName("fasilitas") var fasilitas: String?,
    @SerializedName("deskripsi") var deskripsi: String?,
    @SerializedName("latitude") var latitude: String?,
    @SerializedName("longitute") var longitute: String?
    ): Parcelable