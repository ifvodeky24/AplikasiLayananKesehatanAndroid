package com.example.layanankesehatan.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Hospital(
    @SerializedName("id_rumah_sakit") var id_rumah_sakit: String?,
    @SerializedName("nama_rumah_sakit") var nama_rumah_sakit: String?,
    @SerializedName("kecamatan") var kecamatan: String?,
    @SerializedName("kabupaten") var kabupaten: String?,
    @SerializedName("alamat") var alamat: String?,
    @SerializedName("nomor_telp") var nomor_telp: String?,
    @SerializedName("foto") var foto: String?,
    @SerializedName("fasilitas") var fasilitas: String?,
    @SerializedName("deskripsi") var deskripsi: String?,
    @SerializedName("latitude") var latitude: Double?,
    @SerializedName("longitude") var longitude: Double?
    ): Parcelable