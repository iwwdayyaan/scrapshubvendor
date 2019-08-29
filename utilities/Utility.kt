package com.scrapshubvendor.utilities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.CircularProgressDrawable
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.view.*
import android.widget.*
import com.androidadvance.topsnackbar.TSnackbar
import com.jsibbold.zoomage.ZoomageView
import com.scrapshubvendor.R
import com.scrapshubvendor.activities.LoginRegisterTabActivity
import com.scrapshubvendor.api.AppController
import com.scrapshubvendor.interfaces.SelectViewPager

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utility {
    @SuppressLint("SetTextI18n")
    fun alertDialog(activity : Activity, msg : String) {
        val dialog =   Dialog(activity)
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
            AppController.getSharedPref().edit().putBoolean("isLogin",false).apply()
            AppController.getSharedPref().edit().putString("user_id","").apply()
            AppController.getSharedPref().edit().putString("user_name","").apply()

            AppController.getSharedPref().edit().putString("email","").apply()
            AppController.getSharedPref().edit().putString("mobile","").apply()
            AppController.getSharedPref().edit().putString("city","").apply()
            AppController.getSharedPref().edit().putString("full_address","").apply()

            activity.startActivity(Intent(activity, LoginRegisterTabActivity::class.java))
            activity.finish()
        }
        dialog.show()
    }
    @SuppressLint("SetTextI18n")
    fun alertDialogOtp(activity : Activity, msg : String) {
        val dialog =   Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_alert)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = msg

        val btnYes = dialog.findViewById<View>(R.id.btnYes) as Button
        btnYes.text =  "OK"

        val space = dialog.findViewById<View>(R.id.space) as Space
        space.visibility= View.GONE
        val btnNo = dialog.findViewById<View>(R.id.btnNo) as Button
        btnNo.visibility= View.GONE
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        btnYes.setOnClickListener {
            dialog.dismiss()
            if (activity is SelectViewPager)
             activity.currentItem(0)
        }
        dialog.show()
    }
    fun dateTimeFormat( date : String): String{
        var outputDateStr = ""
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)

        try {
               if(date != ""){
                   val date2 = inputFormat.parse(date)
                   outputDateStr = outputFormat.format(date2)
               }
        }
        catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateStr
    }

   fun dateFormat1( date : String): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        var outputDateStr = ""
        try {
            val date2 = inputFormat.parse(date)
            outputDateStr = outputFormat.format(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateStr
    }
    fun dateFormat2( date : String): String{
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        var outputDateStr = ""
        try {
            val date2 = inputFormat.parse(date)
            outputDateStr = outputFormat.format(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateStr
    }
    fun timeFormat( date : String): String{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        var outputDateStr = ""
        try {
            val date2 = inputFormat.parse(date)
            outputDateStr = outputFormat.format(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateStr
    }
    public fun showDailog(activity : Activity,uri : Uri) {

        val dialog =  Dialog(activity,android.R.style.Theme_Light)


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN

        /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               dialog.window!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(MoreActivity.self, R.color.translucent_black)))
           } else {
               dialog.window!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.translucent_black)))
           }*/


        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_view_image)
        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val zoom = dialog.findViewById<ZoomageView>(R.id.ivZoomImage)
        val circularProgressDrawable = CircularProgressDrawable(zoom.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        GlideApp.with(zoom).load(uri) .placeholder(circularProgressDrawable).error(R.drawable.no_image).into(zoom)

        zoom.setImageURI(uri)
        val ivCancel = dialog.findViewById<ImageView>(R.id.ivCancel)
        ivCancel.setOnClickListener{  dialog.dismiss() }


        dialog.show()

    }
    fun isValidEmail(email: String): Boolean {
       return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
   }
   fun isConnected():Boolean {
        val cm = (AppController.getInstance().applicationContext
          .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
             val activeNetwork = cm.activeNetworkInfo
          return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
    fun setTextHTML(html: String): Spanned
    {
        val result: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
        return result
    }
    @SuppressLint("SetTextI18n")
    fun callDialog(activity : Activity,number: String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = "       Do you want to Call now?       "
        val btnYes = dialog.findViewById<Button>(R.id.btnYes)

        val btnNo = dialog.findViewById<Button>(R.id.btnNo)
        btnNo.setOnClickListener { dialog.dismiss() }
        btnYes.setOnClickListener {
            dialog.dismiss()
            val callIntent =  Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:"+number)

            if (ActivityCompat.checkSelfPermission(activity.applicationContext,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            }
            activity. startActivity(callIntent)
        }

        dialog.show()
    }
    @SuppressLint("SetTextI18n")
    fun mailDialog(activity : Activity,email: String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        val tvMsg = dialog.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = "       Do you want to mail now?       "
        val btnYes = dialog.findViewById<Button>(R.id.btnYes)

        val btnNo = dialog.findViewById<Button>(R.id.btnNo)
        btnNo.setOnClickListener { dialog.dismiss() }
        btnYes.setOnClickListener {
            dialog.dismiss()
            activity.startActivity( Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email)))
        }
        dialog.show()
    }

  /*  public fun showDailog(activity : Activity,uri : Uri) {

        val dialog = object : Dialog(activity,android.R.style.Theme_Light){
            override fun onTouchEvent(event: MotionEvent): Boolean {
                // Tap anywhere to close dialog.
                // this.dismiss()
                return true
            }
        }
        *//* val dialog = object : Dialog(DoctorsActivity.doctorsActivity) {
             override fun onTouchEvent(event: MotionEvent): Boolean {
                 // Tap anywhere to close dialog.
                // this.dismiss()
                 return true
             }
         }*//*
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN

        *//*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               dialog.window!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(MoreActivity.self, R.color.translucent_black)))
           } else {
               dialog.window!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.translucent_black)))
           }*//*


        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_view_image)
        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val zoom = dialog.findViewById<ZoomageView>(R.id.ivZoomImage)
        val circularProgressDrawable = CircularProgressDrawable(zoom.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        GlideApp.with(zoom).load(uri) .placeholder(circularProgressDrawable).error(R.drawable.no_image).into(zoom)

        zoom.setImageURI(uri)
        val ivCancel = dialog.findViewById<ImageView>(R.id.ivCancel)
        ivCancel.setOnClickListener{  dialog.dismiss() }


        dialog.show()

    }*/
  public fun  snackBar(fl : FrameLayout,msg : String){
      val snackbar = TSnackbar.make(fl, msg, Snackbar.LENGTH_LONG)
      val snackbarView = snackbar.view
      snackbarView.setBackgroundColor(Color.parseColor("#000000"))
      val textView =  snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
      textView.setTextColor(Color.WHITE)
      snackbar.show()
  }
    public fun enterNextFragment(container_id : Int,fr : Fragment, fragmentManager : FragmentManager) {
        val transaction = fragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(container_id, fr).commit()
    }

    public   fun  booleanisLocationEnabled(activity : Activity) : Boolean
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is new method provided in API 28
            val lm = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return lm.isLocationEnabled;
        } else {
            // This is Deprecated in API 28
            val mode = Settings.Secure.getInt(activity.contentResolver, Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF)
            return  (mode != Settings.Secure.LOCATION_MODE_OFF)

        }
    }
 }