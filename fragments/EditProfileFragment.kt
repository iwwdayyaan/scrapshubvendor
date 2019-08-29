package com.scrapshubvendor.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.edit_profile_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.view.Window
import android.widget.*
import com.androidadvance.topsnackbar.TSnackbar
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.scrapshubvendor.R
import com.scrapshubvendor.api.AppController
import com.scrapshubvendor.api.Responce
import com.scrapshubvendor.api.interfaces.ApiService
import com.scrapshubvendor.utilities.Utility


class EditProfileFragment  : Fragment() ,View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    internal lateinit var apiService: ApiService

    private var ctx : Context? = null
    private var signinStatus : Boolean = true
    private  var  city : String? = null
    private var sectionNumber : Int? = null

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    private var lat : String? = null
    private var long : String? = null

    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        fun newInstance(sectionNumber: Int): EditProfileFragment {
            val fragment = EditProfileFragment()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(ctx as Activity)
        return  inflater.inflate(R.layout.edit_profile_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionNumber = arguments!!.getInt("section_number")
        btnReg.setOnClickListener (this)
      /*  tvAsn.setOnClickListener (this)*/
        val list = resources.getStringArray(R.array.month)

        val adapter = ArrayAdapter<String>(ctx as Activity,
                android.R.layout.simple_dropdown_item_1line, list)


        spCity.setAdapter(adapter)
        spCity.onItemClickListener = this
        fetchCity()


    }
    private fun validate(){
        if(Utility.booleanisLocationEnabled(ctx as Activity)){
        if(!TextUtils.isEmpty(et_name.text)){
           if(!TextUtils.isEmpty(et_email.text)){
               if(!TextUtils.isEmpty(et_mobile.text)){
                   if(!TextUtils.isEmpty(et_pwd.text)){
                       if(city != null){
                           if(!TextUtils.isEmpty(et_full_add.text)){
                               if(Utility.isConnected()){
                                   alertDialog("Are you sure? You want to edit profile.")
                               }else{
                                   snackBar("No internet connection!")
                               }
                           }else{
                               snackBar("Please enter full name!")
                           }
                       }else{
                           snackBar("Please enter city!")
                       }
                   }else{
                       snackBar("Please enter full address!")
                   }
               }else{
                   snackBar("Please enter mobile number!")
               }
           }else{
               snackBar("Please enter email!")
           }
       }else{
           snackBar("Please enter name!")
       }
        }else{
            snackBar("Please on Location!")
        }
    }
    private fun fetchVendorProfile( list: MutableList<String>) {

        progressBar.visibility = View.VISIBLE
        apiService.fetchVendorProfile("fetch_vendor_profile",AppController.getSharedPref().getString("user_id",""))
                .enqueue(object : Callback<Responce.SignupMember> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Responce.SignupMember>, response: Response<Responce.SignupMember>) {
                        if (response.isSuccessful && response.body()!!.status) {

                                et_name.setText(response.body()!!.data[0].name)
                                et_email.setText(response.body()!!.data[0].email)
                                et_mobile.setText(response.body()!!.data[0].phoneNumber)
                                et_pwd.setText(response.body()!!.data[0].password)
                                spCity.setText(response.body()!!.data[0].city)
                                //et_city.setText(response.body()!!.data[0].city)
                                et_full_add.setText(response.body()!!.data[0].address)
                                progressBar.visibility = View.GONE



                        } else {
                            progressBar.visibility = View.GONE
                         //   Toast.makeText(ctx as Activity,response.body()!!.message, Toast.LENGTH_SHORT).show()

                        }
                    }
                    @SuppressLint("SetTextI18n")
                    override fun onFailure(call: Call<Responce.SignupMember>, t: Throwable) {
                        t.printStackTrace()

                        progressBar.visibility = View.GONE
                    }
                })
    }
    private fun editVender(){
        signinStatus = false
        progressBar.visibility = View.VISIBLE
        apiService.editVender("edit_vender",et_name.text.toString(),
                et_email.text.toString(),et_mobile.text.toString(), et_pwd.text.toString(),city,et_full_add.text.toString(),
                lat,long,AppController.getSharedPref().getString("user_id",""))
                .enqueue(object : Callback<Responce.SignupMember> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Responce.SignupMember>, response: Response<Responce.SignupMember>) {
                if (response.isSuccessful && response.body()!!.status) {

                    snackBar(response.body()!!.message)
                   /*
                        AppController.getSharedPref().edit().putString("user_name", et_name.text.toString()).apply()

                        AppController.getSharedPref().edit().putString("email",et_email.text.toString()).apply()
                        AppController.getSharedPref().edit().putString("mobile", response.body()!!.data[0].phoneNumber).apply()
                        AppController.getSharedPref().edit().putString("city", response.body()!!.data[0].city).apply()

                        AppController.getSharedPref().edit().putString("full_address", response.body()!!.data[0].address).apply()*/

                        signinStatus = true
                        progressBar.visibility = View.GONE


                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(ctx as Activity,response.body()!!.message, Toast.LENGTH_SHORT).show()
                    signinStatus = true
                }
            }
            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<Responce.SignupMember>, t: Throwable) {
                t.printStackTrace()
                signinStatus = true
                progressBar.visibility = View.GONE
            }
        })
    }
    override fun onClick(p0: View?) {
      when(p0!!.id){

          R.id.btnReg ->{
              if(signinStatus){
                  if(btnReg.text == resources.getString(R.string.edit)){
                      et_name.isEnabled = true
                      et_email.isEnabled = true
                      et_mobile.isEnabled = true
                      et_pwd.isEnabled = true
                       spCity.isFocusable = true

                      btnReg.text = resources.getString(R.string.submit)
                  }else{
                      validate()
                  }
              }else{
                  snackBar("Please wait registration is in progress...")
              }
          }
      }
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        ((context!! as Activity).application as AppController).component.inject(this@EditProfileFragment)
    }
   private fun  snackBar(msg : String){
        val snackbar = TSnackbar.make(fl, msg, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.parseColor("#E41900"))
        val textView =  snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
       snackbar.show()
    }
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        city = p0!!.getItemAtPosition(p2).toString()
    }
    @SuppressLint("SetTextI18n")
    fun alertDialog( msg : String) {
        val dialog =  Dialog(ctx as Activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = msg

        val btnYes = dialog.findViewById<View>(R.id.btnYes) as Button
        val btnNo = dialog.findViewById<View>(R.id.btnNo) as Button

        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        btnYes.setOnClickListener {
            dialog.dismiss()
            editVender()
        }
        dialog.show()
    }
    override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }
    private fun showSnackbar(snackStrId: Int, actionStrId: Int = 0, listener: View.OnClickListener? = null
    ) {
        val snackbar = Snackbar.make(fl, getString(snackStrId), Snackbar.LENGTH_INDEFINITE)
        if (actionStrId != 0 && listener != null) {
            snackbar.setAction(getString(actionStrId), listener)
        }
        snackbar.show()
    }
    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     *
     * Note: this method should be called after location permission has been granted.
     */

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        val builder = LocationSettingsRequest.Builder()


        val client: SettingsClient = LocationServices.getSettingsClient(ctx as Activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        fusedLocationClient.lastLocation
                .addOnCompleteListener(ctx as Activity) { task ->
                    if (task.isSuccessful && task.result != null) {
                        lat = task.result!!.latitude.toString()
                        long = task.result!!.longitude.toString()

                    } else {
                        lat = null
                        long = null
                        // Log.w(TAG, "getLastLocation:exception", task.exception)
                       // showSnackbar(R.string.no_location_detected)
                    }
                }

    }
    /**
     * Return the current state of the permissions needed.
     */
    private fun checkPermissions() =
            ActivityCompat.checkSelfPermission(ctx as Activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(ctx as Activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ctx as Activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Provide an additional rationale to the user. This would happen if the user denied the
            // request previously, but didn't check the "Don't ask again" checkbox.
            //  Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar(R.string.permission_rationale, android.R.string.ok, View.OnClickListener {
                // Request permission
                startLocationPermissionRequest()
            })

        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            //  Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {

        }
    }
    private fun fetchCity(){

        progressBar.visibility = View.VISIBLE
        apiService.fetchCity("fetch_city" ).
                enqueue(object : Callback<Responce.FetchCity> {
                    override fun onResponse(call: Call<Responce.FetchCity>, response: Response<Responce.FetchCity>) {
                        if (response.isSuccessful && response.body()!!.status) {
                            val list = mutableListOf<String>()
                            for(city in response.body()!!.data){
                                list.add(city.cityname)

                            }
                            val adapter = ArrayAdapter<String>(ctx as Activity,
                                    android.R.layout.simple_dropdown_item_1line, list)
                            spCity.setAdapter(adapter)

                            progressBar.visibility = View.GONE
                            fetchVendorProfile(list)
                            //Toast.makeText(ctx as Activity,response.body()!!.message, Toast.LENGTH_SHORT).show()

                        } else {
                            progressBar.visibility = View.GONE
                            //  Toast.makeText(ctx as Activity,response.body()!!.message, Toast.LENGTH_SHORT).show()

                        }
                    }
                    override fun onFailure(call: Call<Responce.FetchCity>, t: Throwable) {
                        t.printStackTrace()

                        progressBar.visibility = View.GONE
                    }
                })
    }
}