package com.example.layanankesehatan.features.pharmacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.PharmacyAdapter
import com.example.layanankesehatan.models.Pharmacy
import com.example.layanankesehatan.repositories.PharmacyRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.example.layanankesehatan.utils.hide
import com.example.layanankesehatan.utils.show
import com.rahmat.app.footballclub.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_pharmacy.*
import kotlinx.android.synthetic.main.activity_pharmacy.mainProgress

class PharmacyActivity : AppCompatActivity(), PharmacyContract.View {

    lateinit var mPresenter: PharmacyPresenter
    private var pharmacyList: MutableList<Pharmacy> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy)
        initToolbar()
        initEnv()
    }

    private fun initToolbar() {
        supportActionBar?.title = "Apotek";
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
        val request = PharmacyRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = PharmacyPresenter(this, request, scheduler)
        mPresenter.getPharmacyData()
    }

    override fun hideLoading() {
        mainProgress.hide()
        rvPharmacy.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mainProgress.show()
        rvPharmacy.visibility = View.INVISIBLE
    }

    override fun displayPharmacy(pharmacy: List<Pharmacy>) {
        pharmacyList.clear()
        pharmacyList.addAll(pharmacy)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvPharmacy.layoutManager =layoutManager
        rvPharmacy.setLayoutManager(LinearLayoutManager(this))
        rvPharmacy.setHasFixedSize(true)
        rvPharmacy.adapter = PharmacyAdapter(pharmacyList, this)
    }
}
