package com.example.layanankesehatan.features.hospital.hospitalMap

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
import com.example.layanankesehatan.features.hospital.HospitalContract
import com.example.layanankesehatan.features.hospital.HospitalPresenter
import com.example.layanankesehatan.features.hospital.hospitaldetail.HospitalDetailActivity
import com.example.layanankesehatan.models.Hospital
import com.example.layanankesehatan.repositories.HospitalRepositoryImp
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
class HospitalMapFragment : Fragment(), OnMapReadyCallback, HospitalContract.View {

    private lateinit var mMap: GoogleMap

    lateinit var mPresenter: HospitalPresenter
    private lateinit var hospital: Hospital

    private var latitude: Double? = null
    private var longitude: Double? = null
    private var placeName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_hospital_map, container, false)

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
            val intent = Intent(context, HospitalDetailActivity::class.java)
            intent.putExtra(HospitalDetailActivity.EXTRA_HOSPITAL, hospital)
            startActivity(intent)
        }

        checkPermissions()
    }

    override fun hideLoading() {
    }

    override fun showLoading() {
    }

    override fun displayHospitals(hospitals: List<Hospital>) {
        for (index in 0 until hospitals.size){
            latitude = hospitals[index].latitude!!
            longitude = hospitals[index].longitude!!
            placeName = hospitals[index].nama_rumah_sakit!!

            hospital = Hospital(
                hospitals[index].id_rumah_sakit!!,
                hospitals[index].nama_rumah_sakit!!,
                hospitals[index].kecamatan!!,
                hospitals[index].kabupaten!!,
                hospitals[index].alamat!!,
                hospitals[index].nomor_telp!!,
                hospitals[index].foto!!,
                hospitals[index].fasilitas!!,
                hospitals[index].deskripsi!!,
                hospitals[index].latitude!!,
                hospitals[index].longitude!!
            )

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

        }
    }

    private fun initEnv() {
        val service = HealthApiService.getClient().create(HealthRest::class.java)
        val request = HospitalRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = HospitalPresenter(this, request, scheduler)
        mPresenter.getHospitalData()
    }

}
