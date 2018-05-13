package tti.lv.airdockapp.screens.main

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
import tti.lv.airdockapp.di.DaggerAppComponent
import tti.lv.airdockapp.screens.main.requests.RequestsFragment
import tti.lv.airdockapp.screens.main.tasks.TaskListFragment

class MainActivity : AppCompatActivity(), TaskListFragment.OnFragmentInteractionListener, RequestsFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        (application as App).dependencyGraph.inject(this)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                    .add(R.id.main_container, TaskListFragment())
                    .commit()

        bottom_navigation.setOnNavigationItemSelectedListener( object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when(item.itemId) {
                    R.id.nav_first_fragment  -> TaskListFragment.newInstance("", "").switchTo()
                    R.id.nav_second_fragment -> RequestsFragment.newInstance("", "").switchTo()
                    R.id.nav_third_fragment  -> Toast.makeText(applicationContext, "Third", Toast.LENGTH_SHORT).show()
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

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

