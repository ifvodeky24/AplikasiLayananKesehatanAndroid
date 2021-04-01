package com.example.layanankesehatan.features.pharmacy.pharmacyMap

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.layanankesehatan.R
import com.example.layanankesehatan.features.pharmacy.PharmacyContract
import com.example.layanankesehatan.features.pharmacy.PharmacyPresenter
import com.example.layanankesehatan.features.pharmacy.pharmacydetail.PharmacyDetailActivity
import com.example.layanankesehatan.models.Pharmacy
import com.example.layanankesehatan.repositories.PharmacyRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rahmat.app.footballclub.utils.AppSchedulerProvider

/**
 * A simple [Fragment] subclass.
 */
class PharmacyMapFragment : Fragment(), OnMapReadyCallback, PharmacyContract.View {

    private lateinit var mMap: GoogleMap

    lateinit var mPresenter: PharmacyPresenter
    private lateinit var pharmacy: Pharmacy

    private var latitude: String? = null
    private var longitude: String? = null
    private var placeName: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_pharmacy_map, container, false)

        initEnv()

        return view
    }

    private fun checkPermissions() {
        //tambahan untuk permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Dexter.withActivity(activity)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) { // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) { // do you work now
                            mMap.isMyLocationEnabled = true
                            mMap.uiSettings.isMyLocationButtonEnabled = true
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) { // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                }).withErrorListener {
                    Toast.makeText(activity, "Error occurred! ", Toast.LENGTH_SHORT)
                        .show()
                }
                .onSameThread()
                .check()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        mMap.setMinZoomPreference(15f)

        // Add a marker and move the camera
        val location = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
        mMap.addMarker(MarkerOptions().position(location).title(placeName))
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        mMap.uiSettings.isMapToolbarEnabled = true
        mMap.uiSettings.isCompassEnabled = true

        mMap.setOnInfoWindowClickListener { marker ->
            Toast.makeText(context, marker.title, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, PharmacyDetailActivity::class.java)
            intent.putExtra(PharmacyDetailActivity.EXTRA_PHARMACY, pharmacy)
            startActivity(intent)
        }

        checkPermissions()
    }

    override fun hideLoading() {
    }

    override fun showLoading() {
    }

    override fun displayPharmacy(pharmacys: List<Pharmacy>) {
        for (index in 0 until pharmacys.size){
            latitude = pharmacys[index].latitude!!
            longitude = pharmacys[index].longitude!!
            placeName = pharmacys[index].nama_apotek!!

            pharmacy = Pharmacy(
                pharmacys[index].id_apotek!!,
                pharmacys[index].nama_apotek!!,
                pharmacys[index].kecamatan!!,
                pharmacys[index].kabupaten!!,
                pharmacys[index].alamat!!,
                pharmacys[index].foto!!,
                pharmacys[index].fasilitas!!,
                pharmacys[index].deskripsi!!,
                pharmacys[index].latitude!!,
                pharmacys[index].longitude!!
            )

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

        }
    }

    private fun initEnv() {
        val service = HealthApiService.getClient().create(HealthRest::class.java)
        val request = PharmacyRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = PharmacyPresenter(this, request, scheduler)
        mPresenter.getPharmacyData()
    }

}
