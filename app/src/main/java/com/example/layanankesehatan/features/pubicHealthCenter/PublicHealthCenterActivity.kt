package com.example.layanankesehatan.features.pubicHealthCenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.PublicHealthCenterAdapter
import com.example.layanankesehatan.models.PublicHealthCenter
import com.example.layanankesehatan.repositories.PublicHealthCenterRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.example.layanankesehatan.utils.hide
import com.example.layanankesehatan.utils.show
import com.rahmat.app.footballclub.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_pharmacy.mainProgress
import kotlinx.android.synthetic.main.activity_public_health_center.*

class PublicHealthCenterActivity : AppCompatActivity(),
    PublicHealthCenterContract.View {

    lateinit var mPresenter: PublicHealthCenterPresenter
    private var phcList: MutableList<PublicHealthCenter> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_public_health_center)
        initToolbar()
        initEnv()
    }

    private fun initToolbar() {
        supportActionBar?.title = "Puskesmas";
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
        val request = PublicHealthCenterRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter =
            PublicHealthCenterPresenter(
                this,
                request,
                scheduler
            )
        mPresenter.getPhcData()
    }

    override fun hideLoading() {
        mainProgress.hide()
        rvPhc.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mainProgress.show()
        rvPhc.visibility = View.INVISIBLE
    }

    override fun displayPhc(phc: List<PublicHealthCenter>) {
        phcList.clear()
        phcList.addAll(phc)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvPhc.layoutManager =layoutManager
        rvPhc.setLayoutManager(LinearLayoutManager(this))
        rvPhc.setHasFixedSize(true)
        rvPhc.adapter = PublicHealthCenterAdapter(phcList, this)
    }
}
