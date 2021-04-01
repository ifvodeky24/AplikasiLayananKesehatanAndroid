package com.example.layanankesehatan.features.clinic.clinicList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.ClinicAdapter
import com.example.layanankesehatan.features.clinic.ClinicContract
import com.example.layanankesehatan.models.Clinic
import com.example.layanankesehatan.repositories.ClinicRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.example.layanankesehatan.utils.hide
import com.example.layanankesehatan.utils.show
import com.rahmat.app.footballclub.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.activity_clinic.*
import kotlinx.android.synthetic.main.activity_pharmacy.mainProgress

/**
 * A simple [Fragment] subclass.
 */
class ClinicListFragment : Fragment(), ClinicContract.View {
    lateinit var mPresenter: ClinicListPresenter
    private var clinicList: MutableList<Clinic> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clinic_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEnv()
    }

    private fun initEnv() {
        val service = HealthApiService.getClient().create(HealthRest::class.java)
        val request = ClinicRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = ClinicListPresenter(this, request, scheduler)
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
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvClinic.layoutManager =layoutManager
        rvClinic.setLayoutManager(LinearLayoutManager(activity))
        rvClinic.setHasFixedSize(true)
        rvClinic.adapter = ClinicAdapter(clinicList, activity)
    }
}

