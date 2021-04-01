package com.example.layanankesehatan.features.clinic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.ClinicAdapter
import com.example.layanankesehatan.models.Clinic
import com.example.layanankesehatan.models.Pharmacy
import com.example.layanankesehatan.repositories.ClinicRepositoryImp
import com.example.layanankesehatan.repositories.PharmacyRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.example.layanankesehatan.utils.hide
import com.example.layanankesehatan.utils.show
import com.rahmat.app.footballclub.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_clinic.*
import kotlinx.android.synthetic.main.activity_pharmacy.*
import kotlinx.android.synthetic.main.activity_pharmacy.mainProgress

class ClinicActivity : AppCompatActivity(), ClinicContract.View {
    lateinit var mPresenter: ClinicPresenter
    private var clinicList: MutableList<Clinic> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clinic)
        initToolbar()
        initEnv()
    }

    private fun initToolbar() {
        supportActionBar?.title = "Klinik";
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
        val request = ClinicRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = ClinicPresenter(this, request, scheduler)
        mPresenter.getClinicData()
    }

    override fun hideLoading() {
        mainProgress.hide()
        rvClinic.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mainProgress.show()
        rvClinic.visibility = View.INVISIBLE
    }

    override fun displayClinics(clinic: List<Clinic>) {
        clinicList.clear()
        clinicList.addAll(clinic)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvClinic.layoutManager =layoutManager
        rvClinic.setLayoutManager(LinearLayoutManager(this))
        rvClinic.setHasFixedSize(true)
        rvClinic.adapter = ClinicAdapter(clinicList, this)
    }
}
