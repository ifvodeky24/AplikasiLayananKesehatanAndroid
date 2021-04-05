package com.example.layanankesehatan.features.pubicHealthCenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.example.layanankesehatan.R
import com.example.layanankesehatan.adapter.ViewPagerAdapter
import com.example.layanankesehatan.features.pubicHealthCenter.publicHeartList.PublicHeartListFragment
import com.example.layanankesehatan.features.pubicHealthCenter.publicHeartMap.PublicHeartMapFragment
import com.example.layanankesehatan.utils.Tools
import kotlinx.android.synthetic.main.activity_main_pharmacy.*

class MainPublicHeartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_public_heart)
        initToolbar()
        setupViewPager(view_pager)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Data Puskesmas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Tools.setSystemBarColor(this)
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.fragment(PublicHeartListFragment(), "Daftar Puskesmas")
        adapter.fragment(PublicHeartMapFragment(), "Puskesmas Map")
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
