package com.example.layanankesehatan.features.pharmacy.pharmacyList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.PharmacyAdapter
import com.example.layanankesehatan.features.pharmacy.PharmacyContract
import com.example.layanankesehatan.features.pharmacy.PharmacyPresenter
import com.example.layanankesehatan.models.Pharmacy
import com.example.layanankesehatan.repositories.PharmacyRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.example.layanankesehatan.utils.hide
import com.example.layanankesehatan.utils.show
import com.rahmat.app.footballclub.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_pharmacy.*

/**
 * A simple [Fragment] subclass.
 */
class PharmacyListFragment : Fragment(), PharmacyContract.View {
    lateinit var mPresenter: PharmacyPresenter
    private var pharmacyList: MutableList<Pharmacy> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pharmacy_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEnv()
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
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvPharmacy.layoutManager =layoutManager
        rvPharmacy.setLayoutManager(LinearLayoutManager(activity))
        rvPharmacy.setHasFixedSize(true)
        rvPharmacy.adapter = PharmacyAdapter(pharmacyList, activity)
    }

}
