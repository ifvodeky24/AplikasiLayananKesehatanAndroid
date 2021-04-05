package com.example.layanankesehatan.features.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.ViewPagerAdapter
import com.example.layanankesehatan.features.hospital.hospitalList.HospitalListFragment
import com.example.layanankesehatan.features.hospital.hospitalMap.HospitalMapFragment
import com.example.layanankesehatan.utils.Tools
import kotlinx.android.synthetic.main.activity_main_hospital.*

class MainHospitalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_hospital)
        initToolbar()
        setupViewPager(view_pager)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Data Rumah Sakit"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this)
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.fragment(HospitalListFragment(), "Daftar Rumah Sakit")
        adapter.fragment(HospitalMapFragment(), "Rumah Sakit Map")
        viewPager?.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }else{

        }
        return super.onOptionsItemSelected(item)
    }
}
