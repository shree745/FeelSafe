package com.example.feelsafe

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.BATTERY_STATS,
        android.Manifest.permission.READ_CONTACTS

    )
    private val permissionCode = 78
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        askForPermission()

        val bottombar = findViewById<BottomNavigationView>(R.id.botbar)

        bottombar.setOnItemSelectedListener {menuItem->

            when (menuItem.itemId) {
                R.id.guard_icon -> {
                    inflateFragment(GuardFragment.newInstance())
                }
                R.id.home_icon -> {
                    inflateFragment(HomeFragment.newInstance())
                }
                R.id.profile_icon -> {
                    inflateFragment(ProfileFragment.newInstance())
                }
                R.id.dashboard_icon -> {
                    inflateFragment(MapsFragment())
                }
            }

            // inflate is used because here we are using fragment, intent is used when we are opening new activity

            true
        }
        bottombar.selectedItemId = R.id.home_icon
    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(this,permissions,permissionCode)
    }

    private fun inflateFragment(newInstance: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,newInstance)
        transaction.commit()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == permissionCode){
            if(allPermissionGranted()){

            }
            else{

            }
        }

    }

    private fun allPermissionGranted(): Boolean {
        for(item in permissions){
            if(ContextCompat.checkSelfPermission(this,item) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }



}