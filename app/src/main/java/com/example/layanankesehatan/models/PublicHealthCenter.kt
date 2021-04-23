package com.example.layanankesehatan.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PublicHealthCenter(
    @SerializedName("id_puskesmas") var id_puskesmas: String?,
    @SerializedName("nama_puskesmas") var nama_puskesmas: String?,
    @SerializedName("kecamatan") var kecamatan: String?,
    @SerializedName("kabupaten") var kabupaten: String?,
    @SerializedName("alamat") var alamat: String?,
    @SerializedName("nomor_telp") var nomor_telp: String?,
    @SerializedName("foto") var foto: String?,
    @SerializedName("jenis_puskesmas") var jenis_puskesmas: String?,
    @SerializedName("deskripsi") var deskripsi: String?,
    @SerializedName("latitude") var latitude: String?,
    @SerializedName("longitute") var longitute: String?
    ): Parcelable