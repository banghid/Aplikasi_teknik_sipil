package com.example.aplikasipembelajarantekniksipil.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.aplikasipembelajarantekniksipil.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class NavdrawActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navdraw2)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        mAuth = FirebaseAuth.getInstance()
        try {
            currentUser = mAuth.currentUser!!
        } catch (e: Exception) {
            Log.d(">>>>>NavdrawActivity", e.message.toString())
            Toast.makeText(this, "Sesi berakhir.", Toast.LENGTH_SHORT).show()
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
        init()
    }

    private fun init() {
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return true
                } else return false
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.dashboard_menu -> {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.main, true)
                    .build()
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(
                    R.id.dashboardScreen,
                    null,
                    navOptions
                )
            }
            R.id.developer_menu -> {
                if (isValidDestination(R.id.developer_menu)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(
                        R.id.aboutAppScreen
                    )
                }
            }
            R.id.rapor_menu -> {
                if (isValidDestination(R.id.rapor_menu)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(
                        R.id.reportScreen
                    )
                }
            }
            R.id.profile_menu -> {
                if (isValidDestination(R.id.profile_menu)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(
                        R.id.profileScreen
                    )
                }
            }
            R.id.logout_menu -> {
                mAuth.signOut()
                Toast.makeText(
                    this, "Logout berhasil.",
                    Toast.LENGTH_SHORT
                ).show()

                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
        }
        menuItem.isChecked = true
        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun isValidDestination(destination: Int): Boolean {
        return destination != Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        ).currentDestination?.id
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host_fragment),
            drawerLayout
        )
    }

}
