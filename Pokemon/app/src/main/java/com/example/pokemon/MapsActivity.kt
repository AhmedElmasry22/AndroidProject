package com.example.pokemon

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Load Bokemon
        LoadPockemon()
        // End
        // Cheking Permission inside on create
        CheckPermission()
        // ******************* End

    }
// ******************************************************** Asking Permission
    var ACCESSLOCATION=123
    fun CheckPermission()
    {

        if(Build.VERSION.SDK_INT>=23){

            if(ActivityCompat.
                checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),ACCESSLOCATION)
                return
            }
        }

        GetUserLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        when(requestCode)
        {
            ACCESSLOCATION->{

                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    GetUserLocation()
                }
                else
                {
                    Toast.makeText(this,"We cannot access to your location",Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    // *************************************************************** End

    // ******************************************************* Getting Location
    fun GetUserLocation()
    {
        Toast.makeText(this , "Location Access now" , Toast.LENGTH_LONG).show()

        var myLocation= MylocationListener()

        var locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,myLocation)

        val mythread=MyThread()
        mythread.start()

    }

    // ****************************************************** End

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions()
            .position(sydney)
            .title("Me")
            .snippet("here is my location")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario))
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,8f))

    }

    // Class To arrive GPS *************************************
    //Get user location

    var mylocation: Location?=null
    inner class MylocationListener: LocationListener {

        constructor(){
            mylocation= Location("Start")
            mylocation!!.longitude=0.0
            mylocation!!.longitude=0.0
        }
        override fun onLocationChanged(location: Location) {
            mylocation = location

        }
    }
    // ***************************************************** End

    // Class Thread For updating Locations and Displaying it **************************
    var oldLocation:Location?=null
    inner class MyThread:Thread
    {
        constructor() : super()
        {
            oldLocation= Location("Old Location")
            oldLocation!!.longitude=0.0
            oldLocation!!.longitude=0.0
        }
        override fun run()
        {

            while (true)
            {
                try {

                    if(oldLocation!!.distanceTo(mylocation)==0f){
                        continue
                    }
                    oldLocation=mylocation

               runOnUiThread {  // Add a marker in Sydney and move the camera
                   mMap.clear()
                   val sydney = LatLng(mylocation!!.latitude, mylocation!!.longitude)
                   mMap.addMarker(MarkerOptions()
                           .position(sydney)
                           .title("Me")
                           .snippet("here is my location")
                           .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario))
                   )

                   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,8f))

            // Showing Bokemo *****************************

                   for(i in 0..listPockemons.size-1)
                   {

                       var newPockemon = listPockemons[i]

                       if (newPockemon.IsCatch == false)
                       {
                           val pokemonLocation = LatLng(newPockemon.location!!.latitude, newPockemon.location!!.longitude)
                           mMap.addMarker(MarkerOptions()
                                   .position(pokemonLocation)
                                   .title(newPockemon.name)
                                   .snippet(newPockemon.des + " Poweer " + newPockemon.power )
                                   .icon(BitmapDescriptorFactory.fromResource(newPockemon.image!!)))

                           var myPower:Double=0.0
                           if (mylocation!!.distanceTo(newPockemon.location)<2){
                               myPower+=newPockemon.power!!
                               newPockemon.IsCatch=true
                               listPockemons[i]=newPockemon
                               Toast.makeText(applicationContext,
                                       "You catch new pockemon your new pwoer is " + myPower,
                                       Toast.LENGTH_LONG).show()

                           }

                       }
                   }


            // *********************************************
               }


                    Thread.sleep(1000)
                }
                catch (ex:Exception){}

            }
        }
    }
        // ****************************************************************** End

    // Load Bokemon *******************************************************************8

    var listPockemons=ArrayList<Pockemon>()

    fun  LoadPockemon(){


        listPockemons.add(Pockemon(R.drawable.charmander,
                "Charmander", "Charmander living in japan", 55.0, 37.7789994893035, -122.401846647263))
        listPockemons.add(Pockemon(R.drawable.bulbasaur,
                "Bulbasaur", "Bulbasaur living in usa", 90.5, 37.7949568502667, -122.410494089127))
        listPockemons.add(Pockemon(R.drawable.squirtle,
                "Squirtle", "Squirtle living in iraq", 33.5, 37.7816621152613, -122.41225361824))
    }

    // *************************************************************************************************

}