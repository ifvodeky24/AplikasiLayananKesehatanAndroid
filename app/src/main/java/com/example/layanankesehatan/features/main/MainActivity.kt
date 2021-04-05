package com.example.layanankesehatan.features.main

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.layanankesehatan.R
import com.example.layanankesehatan.features.clinic.MainClinicActivity
import com.example.layanankesehatan.features.doktorpractice.MainDoctorPracticeActivity
import com.example.layanankesehatan.features.hospital.MainHospitalActivity
import com.example.layanankesehatan.features.pharmacy.MainPharmacyActivity
import com.example.layanankesehatan.features.pubicHealthCenter.MainPublicHeartActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun initUi() {
        ll_clinic.setOnClickListener {
            val intent = Intent(this, MainClinicActivity::class.java)
            startActivity(intent)
        }

        ll_doctor_practice.setOnClickListener {
            val intent = Intent(this, MainDoctorPracticeActivity::class.java)
            startActivity(intent)
        }

        ll_hospital.setOnClickListener {
            val intent = Intent(this, MainHospitalActivity::class.java)
            startActivity(intent)
        }

        ll_pharmacy.setOnClickListener {
            val intent = Intent(this, MainPharmacyActivity::class.java)
            startActivity(intent)
        }

        ll_phc.setOnClickListener {
            val intent = Intent(this, MainPublicHeartActivity::class.java)
            startActivity(intent)
        }

        ll_info.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage("Aplikasi pencarian pelayanan kesehatan untuk mempermudah masyarakat dalam mencari lokasi pelayanan kesehatan\n\nVersi 1.0")
                // if the dialog is cancelable
//                .setCancelable(false)
//                // positive button text and action
//                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
//                        dialog, id -> finish()
//                })
//                // negative button text and action
//                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
//                        dialog, id -> dialog.cancel()
//                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Aplikasi Layanan Masyarakat")
            // show alert dialog
            alert.show()
        }
    }
}
