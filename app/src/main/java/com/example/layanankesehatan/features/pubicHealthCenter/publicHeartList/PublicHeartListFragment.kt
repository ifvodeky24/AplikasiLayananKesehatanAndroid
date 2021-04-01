package com.example.layanankesehatan.features.pubicHealthCenter.publicHeartList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.PublicHealthCenterAdapter
import com.example.layanankesehatan.features.pubicHealthCenter.PublicHealthCenterContract
import com.example.layanankesehatan.features.pubicHealthCenter.PublicHealthCenterPresenter
import com.example.layanankesehatan.models.PublicHealthCenter
import com.example.layanankesehatan.repositories.PublicHealthCenterRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.example.layanankesehatan.utils.hide
import com.example.layanankesehatan.utils.show
import com.rahmat.app.footballclub.utils.AppSchedulerProvider
import kotlinx.android.synthetic.main.fragment_public_heart_list.*

/**
 * A simple [Fragment] subclass.
 */
class PublicHeartListFragment : Fragment(), PublicHealthCenterContract.View {
    lateinit var mPresenter: PublicHealthCenterPresenter
    private var publicHeartList: MutableList<PublicHealthCenter> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_public_heart_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEnv()
    }

    private fun initEnv() {
        val service = HealthApiService.getClient().create(HealthRest::class.java)
        val request = PublicHealthCenterRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = PublicHealthCenterPresenter(this, request, scheduler)
        mPresenter.getPhcData()
    }

    override fun hideLoading() {
        mainProgress.hide()
        rvPublicHeart.visibility = View.VISIBLE
    }

    override fun showLoading() {
        mainProgress.show()
        rvPublicHeart.visibility = View.INVISIBLE
    }

    override fun displayPhc(phc: List<PublicHealthCenter>) {
        publicHeartList.clear()
        publicHeartList.addAll(phc)
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvPublicHeart.layoutManager =layoutManager
        rvPublicHeart.setLayoutManager(LinearLayoutManager(activity))
        rvPublicHeart.setHasFixedSize(true)
        rvPublicHeart.adapter = PublicHealthCenterAdapter(publicHeartList, activity)
    }

}
