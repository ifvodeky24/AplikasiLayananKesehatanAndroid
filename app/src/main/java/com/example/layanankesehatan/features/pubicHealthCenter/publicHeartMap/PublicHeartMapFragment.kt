package com.example.layanankesehatan.features.pubicHealthCenter.publicHeartMap

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
import com.example.layanankesehatan.features.pubicHealthCenter.PublicHealthCenterActivity
import com.example.layanankesehatan.features.pubicHealthCenter.PublicHealthCenterContract
import com.example.layanankesehatan.features.pubicHealthCenter.PublicHealthCenterPresenter
import com.example.layanankesehatan.features.pubicHealthCenter.publicHealthCenterDetail.PublicHealthCenterDetailActivity
import com.example.layanankesehatan.models.PublicHealthCenter
import com.example.layanankesehatan.repositories.PublicHealthCenterRepositoryImp
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
class PublicHeartMapFragment : Fragment(), OnMapReadyCallback, PublicHealthCenterContract.View {

    private lateinit var mMap: GoogleMap

    lateinit var mPresenter: PublicHealthCenterPresenter
    private lateinit var publicHeart: PublicHealthCenter

    private var latitude: String? = null
    private var longitude: String? = null
    private var placeName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_public_heart_map, container, false)

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
            val intent = Intent(context, PublicHealthCenterActivity::class.java)
            intent.putExtra(PublicHealthCenterDetailActivity.EXTRA_PHC, publicHeart)
            startActivity(intent)
        }

        checkPermissions()
    }

    override fun hideLoading() {
    }

    override fun showLoading() {
    }

    override fun displayPhc(phc: List<PublicHealthCenter>) {
        for (index in 0 until phc.size){
            latitude = phc[index].latitude!!
            longitude = phc[index].longitute!!
            placeName = phc[index].nama_puskesmas!!

            publicHeart = PublicHealthCenter(
                phc[index].id_puskesmas!!,
                phc[index].nama_puskesmas!!,
                phc[index].kecamatan!!,
                phc[index].kabupaten!!,
                phc[index].alamat!!,
                phc[index].nomor_telp!!,
                phc[index].foto!!,
                phc[index].fasilitas!!,
                phc[index].deskripsi!!,
                phc[index].latitude!!,
                phc[index].longitute!!
            )

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

        }
    }

    private fun initEnv() {
        val service = HealthApiService.getClient().create(HealthRest::class.java)
        val request = PublicHealthCenterRepositoryImp(service)
        val scheduler = AppSchedulerProvider()
        mPresenter = PublicHealthCenterPresenter(this, request, scheduler)
        mPresenter.getPhcData()
    }

}
