package com.example.aplikasipembelajarantekniksipil.view.activity

import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.aplikasipembelajarantekniksipil.R
import com.example.aplikasipembelajarantekniksipil.view.fragment.DashboardFragment
import com.google.android.material.navigation.NavigationView


class NavdrawActivity : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navdraw2)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        init()
    }

    private fun init(){
        var navController: NavController = Navigation.findNavController(this,R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(navigationView,navController)
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return true
                }else return false
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.dashboard_menu -> {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.main,true)
                    .build()
                Navigation.findNavController(this,R.id.nav_host_fragment).navigate(
                    R.id.dashboardScreen,
                    null,
                    navOptions)
            }
            R.id.developer_menu -> {
                if (isValidDestination(R.id.developer_menu)){
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate(
                        R.id.aboutAppScreen)
                }
            }
            R.id.rapor_menu -> {
                if (isValidDestination(R.id.rapor_menu)){
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate(
                        R.id.reportScreen)
                }
            }
            R.id.profile_menu -> {
            }
            R.id.logout_menu -> {
            }
        }
        menuItem.isChecked = true
        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    fun isValidDestination(destination: Int):Boolean{
        return destination != Navigation.findNavController(this,R.id.nav_host_fragment).currentDestination?.id
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this,R.id.nav_host_fragment),drawerLayout)
    }
}
