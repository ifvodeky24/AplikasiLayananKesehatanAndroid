package com.example.layanankesehatan.features.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.HospitalAdapter
import com.example.layanankesehatan.adapter.PharmacyAdapter
import com.example.layanankesehatan.features.pharmacy.PharmacyContract
import com.example.layanankesehatan.features.pharmacy.PharmacyPresenter
import com.example.layanankesehatan.models.Hospital
import com.example.layanankesehatan.models.Pharmacy
import com.example.layanankesehatan.repositories.HospitalRepositoryImp
import com.example.layanankesehatan.repositories.PharmacyRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.example.layanankesehatan.utils.hide
import com.example.layanankesehatan.utils.show
import com.rahmat.app.footballclub.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_hospital.*
import kotlinx.android.synthetic.main.activity_pharmacy.*
import kotlinx.android.synthetic.main.activity_pharmacy.mainProgress

class HospitalActivity : AppCompatActivity(),
    HospitalContract.View {

    lateinit var mPresenter: HospitalPresenter
    private var hospitalList: MutableList<Hospital> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)
        initToolbar()
        initEnv()
    }

    private fun initToolbar() {
        supportActionBar?.title = "Rumah Sakit";
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initEnv() {
        val service = HealthApiService.getClient().create(HealthRest::class.java)
        val request = HospitalRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter =
            HospitalPresenter(
                this,
                request,
                scheduler
            )
        mPresenter.getHospitalData()
    }

    override fun hideLoading() {
        mainProgress.hide()
        rvHospital.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mainProgress.show()
        rvHospital.visibility = View.INVISIBLE
    }

    override fun displayHospitals(hospital: List<Hospital>) {
        hospitalList.clear()
        hospitalList.addAll(hospital)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvHospital.layoutManager =layoutManager
        rvHospital.setLayoutManager(LinearLayoutManager(this))
        rvHospital.setHasFixedSize(true)
        rvHospital.adapter = HospitalAdapter(hospitalList, this)
    }
}
