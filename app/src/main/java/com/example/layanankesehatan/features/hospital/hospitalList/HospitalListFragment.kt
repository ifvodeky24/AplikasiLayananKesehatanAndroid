package com.example.layanankesehatan.features.hospital.hospitalList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.HospitalAdapter
import com.example.layanankesehatan.features.hospital.HospitalContract
import com.example.layanankesehatan.features.hospital.HospitalPresenter
import com.example.layanankesehatan.models.Hospital
import com.example.layanankesehatan.repositories.HospitalRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.example.layanankesehatan.utils.hide
import com.example.layanankesehatan.utils.show
import com.rahmat.app.footballclub.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_hospital_list.*

/**
 * A simple [Fragment] subclass.
 */
class HospitalListFragment : Fragment(), HospitalContract.View {
    lateinit var mPresenter: HospitalPresenter
    private var hospitalList: MutableList<Hospital> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hospital_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEnv()
    }

    private fun initEnv() {
        val service = HealthApiService.getClient().create(HealthRest::class.java)
        val request = HospitalRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = HospitalPresenter(this, request, scheduler)
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

    override fun displayHospitals(hospitals: List<Hospital>) {
        hospitalList.clear()
        hospitalList.addAll(hospitals)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvHospital.layoutManager =layoutManager
        rvHospital.setLayoutManager(LinearLayoutManager(activity))
        rvHospital.setHasFixedSize(true)
        rvHospital.adapter = HospitalAdapter(hospitalList, activity)
    }

}
