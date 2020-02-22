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
//        showPrompt()
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
//        return Navigation.findNavController(this, R.id.nav_host_fragment)
//            .popBackStack(
//                R.id.drawer_layout,
//                true
//            )
        supportFragmentManager.popBackStack()
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host_fragment),
            drawerLayout
        )
    }

//    private fun showPrompt(){
//        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)
//
//        if (!prefManager.getBoolean("masterPrompt", false)){
//            MaterialTapTargetPrompt.Builder(this)
//                .setTarget(R.id.textView)
//                .setPrimaryText("Navigation Button")
//                .setSecondaryText("Tab untuk menampilkan menu pada aplikasi")
//                .setBackButtonDismissEnabled(true)
//                .setPromptStateChangeListener { prompt, state ->
//                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
//                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED){
//                        val prefEditor = prefManager.edit()
//                        prefEditor.putBoolean("masterPrompt", true)
//                        prefEditor.apply()
//                        showChapterPrompt()
//                    }
//                }.show()
//        }
//    }
//
//    private fun showChapterPrompt(){
//        MaterialTapTargetPrompt.Builder(this)
//            .setTarget(R.id.rv_chapter)
//            .setPrimaryText("List Materi")
//            .setSecondaryText("Klik salah satu materi untuk menampilkan materi!")
//            .setBackButtonDismissEnabled(true)
//            .setPromptBackground(RectanglePromptBackground())
//            .setPromptFocal(RectanglePromptFocal())
//            .show()
//    }


}
