package com.example.layanankesehatan.features.pharmacy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.ViewPagerAdapter
import com.example.layanankesehatan.features.pharmacy.pharmacyList.PharmacyListFragment
import com.example.layanankesehatan.features.pharmacy.pharmacyMap.PharmacyMapFragment
import com.example.layanankesehatan.utils.Tools
import kotlinx.android.synthetic.main.activity_main_pharmacy.*

class MainPharmacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pharmacy)
        initToolbar()
        setupViewPager(view_pager)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Data Apotek"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this)
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.fragment(PharmacyListFragment(), "Daftar Apotek")
        adapter.fragment(PharmacyMapFragment(), "Apotek Map")
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
