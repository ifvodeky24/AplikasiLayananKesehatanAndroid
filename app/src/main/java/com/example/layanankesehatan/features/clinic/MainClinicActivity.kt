package com.example.layanankesehatan.features.clinic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.ViewPagerAdapter
import com.example.layanankesehatan.features.clinic.clinicList.ClinicListFragment
import com.example.layanankesehatan.features.clinic.clinicMap.ClinicMapFragment
import com.example.layanankesehatan.utils.Tools
import kotlinx.android.synthetic.main.activity_main_clinic.*

class MainClinicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_clinic)
        initToolbar()
        setupViewPager(view_pager)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Data Klinik"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this)
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.fragment(ClinicListFragment(), "Daftar Klinik")
        adapter.fragment(ClinicMapFragment(), "Klinik Map")
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
