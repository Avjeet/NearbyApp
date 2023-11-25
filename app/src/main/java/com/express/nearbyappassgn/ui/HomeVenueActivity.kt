package com.express.nearbyappassgn.ui

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.express.nearbyappassgn.databinding.ActivityHomeVenueBinding
import com.express.nearbyappassgn.model.LatLng
import com.express.nearbyappassgn.model.VenueUIItem
import com.express.nearbyappassgn.ui.adapters.AdapterVenueList
import com.express.nearbyappassgn.viewmodel.HomeVenueViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeVenueActivity : AppCompatActivity(), AdapterVenueList.OnItemClickListener {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    private lateinit var binding: ActivityHomeVenueBinding
    private lateinit var adapterVenueList: AdapterVenueList
    private lateinit var viewModel: HomeVenueViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeVenueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[HomeVenueViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setUpUi()
        getCurrentLocation()
    }

    private fun setUpUi() {
        with(binding.rvVenues) {
            layoutManager = LinearLayoutManager(this@HomeVenueActivity, LinearLayoutManager.VERTICAL, false)
            adapterVenueList = AdapterVenueList(this@HomeVenueActivity, this@HomeVenueActivity)
            adapter = adapterVenueList
        }

        binding.slider.addOnChangeListener(Slider.OnChangeListener { _, value, _ ->
            viewModel.updateRange(value.toInt())
        })
    }

    private fun setUpData(latLng: LatLng) {
        viewModel.setCurrentLocation(latLng)
        viewModel.updateRange(10)
        lifecycleScope.launch {
            viewModel.fetchVenueListFromRange().collectLatest {
                adapterVenueList.submitData(it)
            }
        }
    }

    private fun getCurrentLocation() {

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted, proceed to get the location
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        val latitude = it.latitude
                        val longitude = it.longitude

                        setUpData(LatLng(latitude, longitude))
                    }
                }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to get the location
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Location Permission Denied", Toast.LENGTH_LONG).show()
                // Permission denied, handle accordingly (e.g., show a message to the user)
            }
        }
    }

    override fun onItemClick(position: Int, item: VenueUIItem) {
        Toast.makeText(this, "${item.name} is clicked, open link in new screen", Toast.LENGTH_LONG).show()
        // implement click here
    }
}