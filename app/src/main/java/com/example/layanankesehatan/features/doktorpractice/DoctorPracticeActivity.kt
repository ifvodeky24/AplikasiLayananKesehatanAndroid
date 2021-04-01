package com.example.layanankesehatan.features.doktorpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.DoctorPracticeAdapter
import com.example.layanankesehatan.adapter.HospitalAdapter
import com.example.layanankesehatan.features.hospital.HospitalPresenter
import com.example.layanankesehatan.models.DoctorPractice
import com.example.layanankesehatan.models.Hospital
import com.example.layanankesehatan.repositories.DoctorPracticeRepositoryImp
import com.example.layanankesehatan.repositories.HospitalRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.example.layanankesehatan.utils.hide
import com.example.layanankesehatan.utils.show
import com.rahmat.app.footballclub.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_doctor_practice.*
import kotlinx.android.synthetic.main.activity_hospital.*
import kotlinx.android.synthetic.main.activity_pharmacy.mainProgress

class DoctorPracticeActivity : AppCompatActivity(),
    DoctorPracticeContract.View {

    lateinit var mPresenter: DoctorPracticePresenter
    private var dpList: MutableList<DoctorPractice> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_practice)
        initToolbar()
        initEnv()
    }

    private fun initToolbar() {
        supportActionBar?.title = "Dokter Praktek";
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
        val request = DoctorPracticeRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter =
            DoctorPracticePresenter(
                this,
                request,
                scheduler
            )
        mPresenter.getDpData()
    }

    override fun hideLoading() {
        mainProgress.hide()
        rvDP.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mainProgress.show()
        rvDP.visibility = View.INVISIBLE
    }

    override fun displayDp(dp: List<DoctorPractice>) {
        dpList.clear()
        dpList.addAll(dp)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvDP.layoutManager =layoutManager
        rvDP.setLayoutManager(LinearLayoutManager(this))
        rvDP.setHasFixedSize(true)
        rvDP.adapter = DoctorPracticeAdapter(dpList, this)
    }
}
