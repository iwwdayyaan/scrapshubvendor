package com.scrapshubvendor.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.scrapshubvendor.R
import com.scrapshubvendor.activities.MainActivity
import com.scrapshubvendor.api.AppController
import com.scrapshubvendor.api.Responce
import com.scrapshubvendor.api.interfaces.ApiService
import com.scrapshubvendor.fragments.backpressed.RootFragment
import com.scrapshubvendor.interfaces.PazerRefresh
import com.scrapshubvendor.recyclerview.setUp
import com.scrapshubvendor.utilities.Utility

import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import kotlinx.android.synthetic.main.order_fragment.*
import kotlinx.android.synthetic.main.order_fragment_item.view.*
import kotlinx.android.synthetic.main.packages_fragment_item.view.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PackagesFragment   : RootFragment(), View.OnClickListener{
    @Inject
    internal lateinit var apiService: ApiService
    private var ctx : Context? = null

    private var section_number : Int? = 0
    private var rr : MutableList<Responce.FetchPackage.Data>? = null
    companion object {
        fun newInstance(sectionNumber: Int): PackagesFragment {
            val fragment = PackagesFragment()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        return  inflater.inflate(R.layout.packages_fragment, container, false)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        section_number = arguments?.getInt("section_number",0)
        rr = mutableListOf()
        rv.setUp(rr!!, R.layout.packages_fragment_item, { it1 ->
            this.tvPickages.text = it1.title
            this.tvPrice.text = "₹ ${it1.price}"
            this.tvCredit.text = "${it1.credit} Credits"
        }, { view1: View, i: Int ->
            Utility.enterNextFragment(R.id.container_packages,PaymentFragment.newInstance(0),(ctx as MainActivity).supportFragmentManager)
        })
        rv. addItemDecoration(LayoutMarginDecoration( 1, 16 ) )

        fetchPackage("fetch_package")


        swipe_refresh.setOnRefreshListener {
            fetchPackage("fetch_package")
          }
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            /*   R.id.llNext ->{
                  if(signinStatus){
                      // (ctx as Activity as NavigationHost).navigateTo(OrderTabFragment.newInstance(0),true,false)
                      //  validate()
                  }
              }
              R.id.tvReg ->{
                   (ctx as Activity).title = "REGISTRATION"
                   (ctx as Activity as NavigationHost).navigateTo(EditProfileFragment.newInstance(0),false)
               }*/
        }
    }
   private fun fetchPackage(api : String){
            swipe_refresh.isRefreshing = true
            apiService.fetchPackage(api,AppController.getSharedPref().getString("user_id","")).
                    enqueue(object : Callback<Responce.FetchPackage> {
                        override fun onResponse(call: Call<Responce.FetchPackage>, response: Response<Responce.FetchPackage>) {
                            if (response.isSuccessful && response.body()!!.status) {
                                rr!!.clear()
                                rr!!.addAll(response.body()!!.data)
                                rv.adapter!!.notifyDataSetChanged()
                                swipe_refresh.isRefreshing = false
                                }
                            }

                        override fun onFailure(call: Call<Responce.FetchPackage>, t: Throwable) {
                            t.printStackTrace()
                            swipe_refresh.isRefreshing = false
                        }
                    })

        }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
       ((context!! as Activity).application as AppController).component.inject(this@PackagesFragment)
    }
}