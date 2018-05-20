package tti.lv.airdockapp.screens.main

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import tti.lv.airdockapp.App
import tti.lv.airdockapp.R
import tti.lv.airdockapp.screens.login.LoginActivity
import tti.lv.airdockapp.screens.main.requests.RequestsFragment
import tti.lv.airdockapp.screens.main.tasks.TasksFragment
import tti.lv.airdockapp.utilities.SharedPreferenceProvider
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var preferenceProvider: SharedPreferenceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        (application as App).dependencyGraph.inject(this)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                    .add(R.id.main_container, TasksFragment())
                    .commit()

        bottom_navigation.setOnNavigationItemSelectedListener( object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when(item.itemId) {
                    R.id.nav_first_fragment  -> TasksFragment.newInstance("", "").switchTo()
                    R.id.nav_second_fragment -> RequestsFragment().switchTo()
                    R.id.nav_third_fragment  -> {
                        Toast.makeText(applicationContext, "Logged out", Toast.LENGTH_SHORT).show()
                        preferenceProvider.eraseToken()
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        })
                    }
                }
                return true
            }
        })
    }

    private fun Fragment.switchTo() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, this)
                .commit()
    }

}

