package com.example.layanankesehatan.features.clinic.clinicMap

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.layanankesehatan.R
import com.example.layanankesehatan.features.clinic.ClinicContract
import com.example.layanankesehatan.features.clinic.clinicDetail.ClinicDetailActivity
import com.example.layanankesehatan.features.clinic.clinicList.ClinicListPresenter
import com.example.layanankesehatan.models.Clinic
import com.example.layanankesehatan.repositories.ClinicRepositoryImp
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
class ClinicMapFragment : Fragment(), OnMapReadyCallback, ClinicContract.View {

    private lateinit var mMap: GoogleMap

    lateinit var mPresenter: ClinicListPresenter
    private lateinit var clinics: Clinic

    private var latitude: String? = null
    private var longitude: String? = null
    private var placeName: String? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view= inflater.inflate(R.layout.fragment_clinic_map, container, false)

        initEnv()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            val intent = Intent(context, ClinicDetailActivity::class.java)
            intent.putExtra(ClinicDetailActivity.EXTRA_CLINIC, clinics)
            startActivity(intent)
        }

        checkPermissions()
    }

    override fun hideLoading() {
    }

    override fun showLoading() {
    }

    override fun displayClinics(clinic: List<Clinic>) {

        for (index in 0 until clinic.size){
            latitude = clinic[index].latitude!!
            longitude = clinic[index].longitute!!
            placeName = clinic[index].nama_klinik!!

            clinics = Clinic(
                clinic[index].id_klinik!!,
                clinic[index].nama_klinik!!,
                clinic[index].kecamatan!!,
                clinic[index].kabupaten!!,
                clinic[index].alamat!!,
                clinic[index].foto!!,
                clinic[index].fasilitas!!,
                clinic[index].deskripsi!!,
                clinic[index].latitude!!,
                clinic[index].longitute!!
            )

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

        }
    }

    private fun initEnv() {
        val service = HealthApiService.getClient().create(HealthRest::class.java)
        val request = ClinicRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = ClinicListPresenter(this, request, scheduler)
        mPresenter.getClinicData()
    }

}
