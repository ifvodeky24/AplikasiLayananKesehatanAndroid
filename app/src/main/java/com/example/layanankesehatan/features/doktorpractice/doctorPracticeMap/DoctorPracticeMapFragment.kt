package com.example.layanankesehatan.features.doktorpractice.doctorPracticeMap

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.layanankesehatan.R
import com.example.layanankesehatan.features.doktorpractice.DoctorPracticeContract
import com.example.layanankesehatan.features.doktorpractice.DoctorPracticePresenter
import com.example.layanankesehatan.features.doktorpractice.doctorpracticedetail.DoctorPracticeDetailActivity
import com.example.layanankesehatan.models.Clinic
import com.example.layanankesehatan.models.DoctorPractice
import com.example.layanankesehatan.repositories.DoctorPracticeRepositoryImp
import com.example.layanankesehatan.rest.HealthApiService
import com.example.layanankesehatan.rest.HealthRest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rahmat.app.footballclub.utils.AppSchedulerProvider

/**
 * A simple [Fragment] subclass.
 */
class DoctorPracticeMapFragment : Fragment(), OnMapReadyCallback, DoctorPracticeContract.View{

    private lateinit var mMap: GoogleMap

    lateinit var mPresenter: DoctorPracticePresenter
    private lateinit var doctorPractice: DoctorPractice
    private val doctorPracticeList by lazy { ArrayList<DoctorPractice>() }

    private var latitude: Double? = null
    private var longitude: Double? = null
    private var placeName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_doctor_practice_map, container, false)

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


//        mMap.setMinZoomPreference(15f)

        Log.d("ff ddd", "cek ${Gson().toJson(doctorPracticeList)}")

        for (i in doctorPracticeList.indices){
            Log.d("ff ddd", "cek jumlah ${doctorPracticeList[i]}")
            val location = LatLng(doctorPracticeList[i].latitude!!.toDouble(), doctorPracticeList[i].longitute!!.toDouble())
            mMap.addMarker(MarkerOptions().position(location).title(doctorPracticeList[i].nama_dokter_praktek!!))
        }

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(0.356319, 100.118494)))
        mMap.uiSettings.isMapToolbarEnabled = true
        mMap.uiSettings.isCompassEnabled = true

        mMap.setOnInfoWindowClickListener { marker ->
            Toast.makeText(context, marker.title, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, DoctorPracticeDetailActivity::class.java)
            intent.putExtra(DoctorPracticeDetailActivity.EXTRA_DP, doctorPractice)
            startActivity(intent)
        }

        checkPermissions()
    }

    override fun hideLoading() {
    }

    override fun showLoading() {
    }

    override fun displayDp(doctorPractices: List<DoctorPractice>) {
        for (index in doctorPractices.indices){
            latitude = doctorPractices[index].latitude!!
            longitude = doctorPractices[index].longitute!!
            placeName = doctorPractices[index].nama_dokter_praktek!!

            doctorPractice = DoctorPractice(
                doctorPractices[index].id_dockter_praktek!!,
                doctorPractices[index].nama_dokter_praktek!!,
                doctorPractices[index].kecamatan!!,
                doctorPractices[index].kabupaten!!,
                doctorPractices[index].alamat!!,
                doctorPractices[index].foto!!,
                doctorPractices[index].fasilitas!!,
                doctorPractices[index].deskripsi!!,
                doctorPractices[index].latitude!!,
                doctorPractices[index].longitute!!
            )

            doctorPracticeList.add(doctorPractices[index])
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initEnv() {
        val service = HealthApiService.getClient().create(HealthRest::class.java)
        val request = DoctorPracticeRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = DoctorPracticePresenter(this, request, scheduler)
        mPresenter.getDpData()
    }

}
