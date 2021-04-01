package com.example.layanankesehatan.config

 class ServerConfig {
     companion object{
         val DOMAIN_SERVER = "http://arslyn.com/kesehatan/"
         val DOMAIN_SERVER_API: String = "http://arslyn.com/kesehatanapi/api/v1/"
         val PHARMACY_PATH: String = "${DOMAIN_SERVER}web/gambar/apotek/"
         val CLINIC_PATH: String = "${DOMAIN_SERVER}web/gambar/klinik/"
         val HOSPITAL_PATH: String = "${DOMAIN_SERVER}web/gambar/rumahsakit/"
         val PHC_PATH: String = "${DOMAIN_SERVER}web/gambar/puskesmas/"
         val DP_PATH: String = "${DOMAIN_SERVER}web/gambar/dokterpraktek/"
     }
}