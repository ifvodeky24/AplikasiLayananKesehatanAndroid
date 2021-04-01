package com.example.layanankesehatan.features.doktorpractice.doctorPracticeList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.DoctorPracticeAdapter
import com.example.layanankesehatan.features.doktorpractice.DoctorPracticeContract
import com.example.layanankesehatan.features.doktorpractice.DoctorPracticePresenter
import com.example.layanankesehatan.models.DoctorPractice
import com.example.layanankesehatan.repositories.DoctorPracticeRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.example.layanankesehatan.utils.hide
import com.example.layanankesehatan.utils.show
import com.rahmat.app.footballclub.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_doctor_practice_list.*

/**
 * A simple [Fragment] subclass.
 */
class DoctorPracticeListFragment : Fragment(), DoctorPracticeContract.View {
    lateinit var mPresenter: DoctorPracticePresenter
    private var doctorPracticeList: MutableList<DoctorPractice> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_practice_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEnv()
    }

    private fun initEnv() {
        val service = HealthApiService.getClient().create(HealthRest::class.java)
        val request = DoctorPracticeRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = DoctorPracticePresenter(this, request, scheduler)
        mPresenter.getDpData()
    }

    override fun hideLoading() {
        mainProgress.hide()
        rvDoctorPractice.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mainProgress.show()
        rvDoctorPractice.visibility = View.INVISIBLE
    }

    override fun displayDp(doctorPractice: List<DoctorPractice>) {
        doctorPracticeList.clear()
        doctorPracticeList.addAll(doctorPractice)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvDoctorPractice.layoutManager =layoutManager
        rvDoctorPractice.setLayoutManager(LinearLayoutManager(activity))
        rvDoctorPractice.setHasFixedSize(true)
        rvDoctorPractice.adapter = DoctorPracticeAdapter(doctorPracticeList, activity)
    }

}
