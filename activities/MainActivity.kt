package com.scrapshubvendor.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import com.scrapshubvendor.R
import com.scrapshubvendor.api.AppController
import com.scrapshubvendor.api.Responce
import com.scrapshubvendor.api.interfaces.ApiService
import com.scrapshubvendor.fragments.*
import com.scrapshubvendor.interfaces.Credit
import com.scrapshubvendor.interfaces.NavigationHost
import com.scrapshubvendor.interfaces.PazerRefresh
import com.scrapshubvendor.utilities.Utility
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MainActivity : AppCompatActivity(),Credit, PazerRefresh,FragmentManager.OnBackStackChangedListener, CompoundButton.OnCheckedChangeListener {
    @Inject
    internal lateinit var apiService: ApiService

    private var doubleBackToExitPressedOnce = false
    private var tvCredit: TextView? = null
   // private var  menu1 : Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as AppController).component.inject(this@MainActivity)
        setContentView(R.layout.activity_main)
        title = "SH Vendor"
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)
        view_pager.offscreenPageLimit = 4

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        view_pager.addOnPageChangeListener(onPageChangeListener)
        supportFragmentManager.addOnBackStackChangedListener(this)

    }
    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_order -> {
                view_pager.setCurrentItem(0,true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_recharge -> {
                view_pager.setCurrentItem(1,true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                view_pager.setCurrentItem(2,true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about_us -> {
                view_pager.setCurrentItem(3,true)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }
    val onPageChangeListener = object  : ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(p0: Int) {

        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

        }

        override fun onPageSelected(p0: Int) {
            navigation.menu.getItem(p0).isChecked = true
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
       // this.menu1 = menu
        tvCredit  =  menu.findItem(R.id.action_credit).actionView as TextView
        menu.findItem(R.id.action_credit).actionView.setOnClickListener{
            onOptionsItemSelected( menu.findItem(R.id.action_credit)) }

      // val swOnOff = menu.findItem(R.id.action_on_off).getActionView().findViewById(R.id.swOnOff) as SwitchCompat
        fetchUserStatus(menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_logout -> {
                Utility.alertDialog(this,"Do you want to logout?")
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_credit -> {
                view_pager.setCurrentItem(1,true)
                navigation.menu.getItem(1).isChecked = true
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        override fun getItem(position: Int): Fragment? {
            when(position){
                0-> return OrderTabFragment.newInstance(0)
                1-> return CreditHistoryTabFragment.newInstance(1)
                2-> return EditProfileFragment.newInstance(1)
                3-> return AboutUsFragment.newInstance(0)
            }
            return null
        }

        override fun getCount(): Int {
            return 4
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }
    override fun credit(credit: String) {
        tvCredit!!.text = credit
    }
    override fun refresh() {
       view_pager.adapter!!.notifyDataSetChanged()
    }
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount>0){
            super.onBackPressed()
        }else{
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }

    }
    override fun onBackStackChanged() {
        if(supportFragmentManager.backStackEntryCount >0){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)

        }
        else  {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
    }

    override  fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeOnBackStackChangedListener(this)
    }
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if(isChecked)
        alertDialog("Do you want Activate","1")
        else  alertDialog("Do you want Deactivate","0")

    }
      @SuppressLint("SetTextI18n")
    fun alertDialog( msg : String, status : String) {
        val dialog =   Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = msg

        val btnYes = dialog.findViewById<View>(R.id.btnYes) as Button
        val btnNo = dialog.findViewById<View>(R.id.btnNo) as Button

        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        btnYes.setOnClickListener {
            onOffVendor(dialog,status)
        }
        dialog.show()
    }
    private fun onOffVendor(dialog : Dialog,status : String){

        apiService.onOffVendor("on_off_vendor", AppController.getSharedPref().getString("user_id",""), status).
                enqueue(object : Callback<Responce.Status> {
                    override fun onResponse(call: Call<Responce.Status>, response: Response<Responce.Status>) {
                        if (response.isSuccessful && response.body()!!.status) {
                            dialog.dismiss()

                        }
                    }
                    override fun onFailure(call: Call<Responce.Status>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
     }
    private fun fetchUserStatus(menu : Menu){

        apiService.fetchUserStatus("fetch_vendor_status", AppController.getSharedPref().getString("user_id","")).
                enqueue(object : Callback<Responce.FetchUserStatus> {
                    override fun onResponse(call: Call<Responce.FetchUserStatus>, response: Response<Responce.FetchUserStatus>) {
                        val swOnOff = menu.findItem(R.id.action_on_off).actionView.findViewById(R.id.swOnOff) as SwitchCompat
                        if (response.isSuccessful && response.body()!!.status) {
                            swOnOff.isChecked = response.body()!!.data[0].on_off == "1"
                            swOnOff.setOnCheckedChangeListener(this@MainActivity)
                        }
                    }
                    override fun onFailure(call: Call<Responce.FetchUserStatus>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
    }
}
