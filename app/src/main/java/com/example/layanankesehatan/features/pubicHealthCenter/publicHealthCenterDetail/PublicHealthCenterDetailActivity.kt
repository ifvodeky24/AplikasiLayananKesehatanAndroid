package com.example.layanankesehatan.features.pubicHealthCenter.publicHealthCenterDetail

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationListener
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.layanankesehatan.R
import com.example.layanankesehatan.config.ServerConfig
import com.example.layanankesehatan.models.PublicHealthCenter
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_phc_detail.*
import org.jetbrains.anko.find

class PublicHealthCenterDetailActivity : AppCompatActivity() {

    lateinit var phc: PublicHealthCenter
    lateinit var id: String
    lateinit var name: String
    lateinit var district: String
    lateinit var village: String
    lateinit var address: String
    lateinit var handphone: String
    lateinit var image: String
    lateinit var amenities: String
    lateinit var desc: String
    lateinit var latitude: String
    lateinit var longitude: String
    private var latitude_know: Double? = null
    private var longitude_know: Double? = null
    private var locationManager: LocationManager?= null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phc_detail)
        initIntent()
        initToolbar()
        initUI()
        /*Check location*/
        getLocation()
    }

    private fun getLocation() {

        var locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        var locationListener = object : LocationListener{
            override fun onLocationChanged(location: Location?) {
                latitude_know = location!!.latitude
                longitude_know = location!!.longitude
                Log.e("lat_now_tes", latitude_know.toString())
                Log.e("lng_now_tes", longitude_know.toString())
                Log.e("lat_tes", latitude)
                Log.e("lng_tes", longitude)
                fab_maps_phc.setOnClickListener {
                    val mapIntent = Uri.parse(
                        "http://maps.google.com/maps?saddr=$latitude_know,$longitude_know&daddr=$latitude,$longitude"
                    ).let { location ->
                        Intent(Intent.ACTION_VIEW, location);
                    }
                    startActivity(mapIntent)
                }
                Log.i("test", "Latitute: $latitude_know ; Longitute: $longitude_know")

            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }

        }

        try {
            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch (ex:SecurityException) {
            Toast.makeText(applicationContext, "Lokasi tidak ditemukan!", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_ACCESS_FINE_LOCATION
            )
            return
        }
        locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION) {
            when (grantResults[0]) {
                PackageManager.PERMISSION_GRANTED -> getLocation()
                PackageManager.PERMISSION_DENIED -> "Denied" //Tell to user the need of grant permission
            }
        }
    }

    private fun initUI() {
        // create persistent LocationManager reference
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        tv_name_phc.text = name
        tv_desc_phc.text = desc
        tv_address_phc.text = " ${district}, ${village}, ${address}"
        tv_telp_phc.text = handphone
        tv_amenities_phc.text = amenities
        Glide.with(this)
            .load(ServerConfig.PHC_PATH+image)
            .apply(RequestOptions().placeholder(R.drawable.ic_hourglass_empty_black_24dp))
            .into(iv_image_phc)
    }

    private fun initToolbar() {
        val toolbar = find(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initIntent() {
        phc = intent.getParcelableExtra(EXTRA_PHC)
        id = phc.id_puskesmas.toString()
        name = phc.nama_puskesmas.toString()
        district= phc.kecamatan.toString()
        village = phc.kabupaten.toString()
        address = phc.alamat.toString()
        handphone = phc.nomor_telp.toString()
        image = phc.foto.toString()
        amenities = phc.fasilitas.toString()
        desc = phc.deskripsi.toString()
        latitude = phc.latitude.toString()
        longitude = phc.longitute.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_PHC= "extra_phc"
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
    }

}
