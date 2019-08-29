package com.scrapshubvendor.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scrapshubvendor.R
import com.scrapshubvendor.api.AppController
import com.scrapshubvendor.api.Responce
import com.scrapshubvendor.api.interfaces.ApiService
import com.scrapshubvendor.utilities.Utility
import kotlinx.android.synthetic.main.forgot_password_fragment.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class ForgotPasswordFragment   : Fragment(), View.OnClickListener{//, PazerRefresh {
    @Inject
    internal lateinit var apiService: ApiService
    private var ctx : Context? = null
    private var signinStatus : Boolean = true

    companion object {
        fun newInstance(sectionNumber: Int): ForgotPasswordFragment {
            val fragment = ForgotPasswordFragment()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        return  inflater.inflate(R.layout.forgot_password_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSubmit.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btnSubmit ->{
                if(signinStatus)
                   validate()
            }

        }
    }
  private fun validate() {
      if (!TextUtils.isEmpty(et_mobile.text)) {
               if (Utility.isConnected()) {
                   forgetPassword()
                  } else {
                      Utility.snackBar(fl, "No internet connection!")
                  }
            }else{
             Utility.snackBar(fl, "Please enter 10 digit mobile number!")
           }
  }
    private fun forgetPassword(){
        signinStatus = false
        progressBar.visibility = View.VISIBLE
        apiService.forgetPassword("forget_password",et_mobile.text.toString()).
                enqueue(object : Callback<Responce.Status> {
                    override fun onResponse(call: Call<Responce.Status>, response: Response<Responce.Status>) {
                        if (response.isSuccessful && response.body()!!.status) {
                            progressBar.visibility = View.GONE
                           Utility. alertDialogOtp((ctx as Activity),response.body()!!.message)
                        } else {
                            signinStatus = true
                            progressBar.visibility = View.GONE
                            Utility.snackBar(fl, response.body()!!.message)
                        }
                    }
                    override fun onFailure(call: Call<Responce.Status>, t: Throwable) {
                        t.printStackTrace()
                        signinStatus = true
                        progressBar.visibility = View.VISIBLE
                    }
                })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
      ((context!! as Activity).application as AppController).component.inject(this@ForgotPasswordFragment)
    }

}