package com.scrapshubvendor.activities

import android.Manifest
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.scrapshubvendor.R
import com.scrapshubvendor.fragments.LoginFragment
import com.scrapshubvendor.fragments.RegisterFragment
import com.scrapshubvendor.interfaces.SelectViewPager
import com.vistrav.ask.Ask

import kotlinx.android.synthetic.main.login_register_tabbed_activity.*

class LoginRegisterTabActivity : AppCompatActivity(), SelectViewPager {

    private val mTitles = arrayOf("Login", "Register")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_register_tabbed_activity)
        Ask.on(this)
                .id(187) // in case you are invoking multiple time Ask from same activity or fragment
                .forPermissions(Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION,  Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE)
                .withRationales("Location permission need for map to work properly") //optional
                .go()
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)
        view_pager.offscreenPageLimit = 2
        tab_layout.setupWithViewPager(view_pager)
    }
    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        override fun getItem(position: Int): Fragment? {
            when(position){
                0-> return LoginFragment.newInstance(0)
                1-> return RegisterFragment.newInstance(1)
            }
          return null
        }

        override fun getCount(): Int {
            return 2
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }
    }
    override fun currentItem(item: Int) {
        tab_layout.getTabAt(0)!!.select()

    }
}
