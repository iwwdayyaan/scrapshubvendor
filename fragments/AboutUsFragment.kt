package com.scrapshubvendor.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.scrapshubvendor.R
import com.scrapshubvendor.api.AppController
import com.scrapshubvendor.api.Responce
import com.scrapshubvendor.api.interfaces.ApiService
import kotlinx.android.synthetic.main.about_us_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class AboutUsFragment   : Fragment(), View.OnClickListener{//, PazerRefresh {
    @Inject
    internal lateinit var apiService: ApiService
    private var ctx : Context? = null

    companion object {
        fun newInstance(sectionNumber: Int): AboutUsFragment {
            val fragment = AboutUsFragment()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        return  inflater.inflate(R.layout.about_us_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAboutus()
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
          /*  R.id.btnConfirm ->{
                if(signinStatus){
                    //  validate()
                   // (ctx as Activity as NavigationHost).navigateTo(OrderHistoryFragment.newInstance(0),true,false)
                }
            }*/

        }
    }

    private fun fetchAboutus(){

        apiService.fetchAboutus("fetch_aboutus").
                enqueue(object : Callback<Responce.FetchAboutus> {
                    override fun onResponse(call: Call<Responce.FetchAboutus>, response: Response<Responce.FetchAboutus>) {
                        if (response.isSuccessful && response.body()!!.status) {
                            tvAboutUs.text = response.body()!!.data[0].aboutus
                        } else {
                            tvAboutUs.text = "No data"
                        }
                    }
                    override fun onFailure(call: Call<Responce.FetchAboutus>, t: Throwable) {
                        t.printStackTrace()

                    }
                })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
      ((context!! as Activity).application as AppController).component.inject(this@AboutUsFragment)
    }

}