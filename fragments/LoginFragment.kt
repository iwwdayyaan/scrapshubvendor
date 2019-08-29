package com.scrapshubvendor.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast

import com.scrapshubvendor.R
import com.scrapshubvendor.activities.MainActivity
import com.scrapshubvendor.api.AppController
import com.scrapshubvendor.api.Responce
import com.scrapshubvendor.api.interfaces.ApiService
import com.scrapshubvendor.utilities.Utility
import kotlinx.android.synthetic.main.login_fragment.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.scrapshubvendor.activities.LoginRegisterTabActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class LoginFragment : Fragment(),View.OnClickListener{
    @Inject
    internal lateinit var apiService: ApiService
    private var ctx : Context? = null
    private var signinStatus : Boolean = true

    companion object {
        fun newInstance(sectionNumber: Int): LoginFragment {
            val fragment = LoginFragment()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        return  inflater.inflate(R.layout.login_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener(this)
        tvForgotPwd.setOnClickListener(this)
    }
  override fun onClick(p0: View?) {
      when(p0!!.id){
          R.id.btnLogin ->{
              if(signinStatus)
                  validate()
          }
          R.id.tvForgotPwd ->{
              if(signinStatus){
                  Utility.enterNextFragment(R.id.container_login_reg,ForgotPasswordFragment.newInstance(0),(ctx as LoginRegisterTabActivity).supportFragmentManager)
              }
          }
      }
    }
    private fun validate(){
        if(!TextUtils.isEmpty(et_email_mobile.text)){
            if( TextUtils.isDigitsOnly(et_email_mobile.text)){
                if(et_email_mobile.text!!.length==10){
                    if(!TextUtils.isEmpty(et_pwd.text)){
                        if(Utility.isConnected()){
                            memberLogin(true)
                        }else{
                            Utility.  snackBar(fl,"No internet connection!")
                        }
                    }else{
                        Utility. snackBar(fl,"Please enter password!")
                    }
                }else{
                    Utility.  snackBar(fl,"Please enter correct mobile number!")
                }
              }else{
                  if(Utility.isValidEmail(et_email_mobile.text.toString())){
                      if(!TextUtils.isEmpty(et_pwd.text)){
                          if(Utility.isConnected()){
                              memberLogin(false)
                          }else{
                              Utility.  snackBar(fl,"No internet connection!")
                          }
                      }else{
                          Utility. snackBar(fl,"Please enter password!")
                      }
                     }else{
                       Utility. snackBar(fl,"Please enter correct email address!")
                      }
                   }
              }else{
                    Utility. snackBar(fl,"Please enter email or mobile number!")
                  }
    }
    private fun memberLogin(emaiOrMobile : Boolean){
        signinStatus = false
        progressBar.visibility = View.VISIBLE
        var mobile  = ""
        var email  = ""
        if(emaiOrMobile){
            mobile =  et_email_mobile.text.toString()
            email = ""
        }else{
            email = et_email_mobile.text.toString()
            mobile = ""
        }
        // Get token
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("TAG", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    // Get new Instance ID token
                    val token = task.result?.token
                    // Log and toast
                    val msg = getString(R.string.msg_token_fmt, token)
                    Log.d("TAG", msg)

        apiService.login("login_vender" ,email,
                et_pwd.text.toString(),mobile,token).enqueue(object : Callback<Responce.SignupMember> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Responce.SignupMember>, response: Response<Responce.SignupMember>) {
                if (response.isSuccessful && response.body()!!.status) {

                    AppController.getSharedPref().edit().putBoolean("isLogin",true).apply()
                    AppController.getSharedPref().edit().putString("user_id",response.body()!!.data[0].id).apply()
                    AppController.getSharedPref().edit().putString("user_name",response.body()!!.data[0].name).apply()

                    AppController.getSharedPref().edit().putString("email",response.body()!!.data[0].email).apply()
                    AppController.getSharedPref().edit().putString("mobile",response.body()!!.data[0].phoneNumber).apply()
                    AppController.getSharedPref().edit().putString("city",response.body()!!.data[0].city).apply()
                    AppController.getSharedPref().edit().putString("full_address",response.body()!!.data[0].address).apply()

                    signinStatus = true
                    progressBar.visibility = View.GONE

                    startActivity(Intent(ctx as Activity, MainActivity::class.java))
                    (ctx as Activity).finish()
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
                })
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        ((context!! as Activity).application as AppController).component.inject(this@LoginFragment)
    }

}