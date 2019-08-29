package com.scrapshubvendor.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class OrderFragment   : Fragment(){
    @Inject
    internal lateinit var apiService: ApiService
    private var ctx : Context? = null
    //private var signinStatus : Boolean = true
    private var section_number : Int? = 0

    private var rr : MutableList<Responce.FetchVendorOrders.Data>? = null
    companion object {
        fun newInstance(sectionNumber: Int): OrderFragment {
            val fragment = OrderFragment()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        return  inflater.inflate(R.layout.order_fragment, container, false)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        section_number = arguments?.getInt("section_number",0)
        rr = mutableListOf()

        rv .setUp(rr!!, R.layout.order_fragment_item, { it1 ->
            this.tvDate.text = Utility.dateFormat1(it1.date)
            this.tvPDate.text = Utility.dateFormat1(it1.adddate)
            this.tvPTime.text = it1.addtime
            this.tvCName.text = it1.name
            this.tvCount.text = it1.product.split(",").size.toString()
            when(section_number){
                0->{
                    this.tvOrderNo.text = "ORDER ID: ${it1.orderid}"
                    this.tvStatus.text = "Accept"
                    this.tvChangeStatus.visibility = View.GONE
                    this.tvStatus.setOnClickListener {
                        fetchVendorRemCredits(it1.orderid,"Are you sure? You want to accept order ${it1.orderid}.","Accepted")
                    }
                }
                    1->{

                    when(it1.status){
                           "Accepted" ->{
                               this.tvOrderNo.text = "ORDER ID: ${it1.id}"
                               this.tvStatus.isClickable = false
                               this.tvStatus.isEnabled = false
                               this.tvStatus.text = "Accepted"
                               this.tvCPhone.text = it1.phone
                               this.tvChangeStatus.visibility = View.VISIBLE
                               this.tvStatus.setTextColor(ContextCompat.getColor(ctx as Activity,android.R.color.black))
                               this.tvStatus.setBackgroundResource(R.drawable.rectangle)
                               this.tvChangeStatus.setOnClickListener {
                                   alertDialog(it1.id,"Are you sure? You want to complete order ${it1.id}.","Completed")
                               }
                           }

                       }

                   }
                  2->{
                       when(it1.status){
                           "Completed" ->{
                               this.tvOrderNo.text = "ORDER ID: ${it1.id}"
                               this.tvStatus.isClickable = false
                               this.tvStatus.isEnabled = false
                               this.tvStatus.text = "Completed"
                               this.tvCPhone.text = it1.phone
                               this.tvChangeStatus.visibility = View.GONE
                               this.tvStatus.setTextColor(ContextCompat.getColor(ctx as Activity,android.R.color.black))
                               this.tvStatus.setBackgroundResource(R.drawable.rectangle)
                           }
                       }

                   }
            }

        }, { view1: View, i: Int ->
          if(section_number == 0)
            Utility.enterNextFragment(R.id.container,OrderDetailFragment.newInstance(rr!![i].orderid),  (ctx as MainActivity).supportFragmentManager)
            else   Utility.enterNextFragment(R.id.container,OrderDetailFragment.newInstance(rr!![i].id),  (ctx as MainActivity).supportFragmentManager)
        })
        rv!!. addItemDecoration(LayoutMarginDecoration( 1, 16 ) )
        if(section_number==0)
            fetchVendorOrders("fetch_vendor_orders")
           else
            fetchVendorOrders("fetch_vendor_my_orders")


        swipe_refresh.setOnRefreshListener {
            if(section_number==0)
                fetchVendorOrders("fetch_vendor_orders")
            else
                fetchVendorOrders("fetch_vendor_my_orders")
          }
    }

    private fun fetchVendorOrders(api : String){
            swipe_refresh.isRefreshing = true
            apiService.fetchVendorOrders(api,AppController.getSharedPref().getString("user_id","")).
                    enqueue(object : Callback<Responce.FetchVendorOrders> {
                        override fun onResponse(call: Call<Responce.FetchVendorOrders>, response: Response<Responce.FetchVendorOrders>) {
                            if (response.isSuccessful) {
                                if (response.body()!!.status) {
                                    if (section_number == 0) {
                                        val pending = mutableListOf<Responce.FetchVendorOrders.Data>()
                                        for (item in response.body()!!.data) {
                                            if (item.status == "Pending")
                                                pending.add(item)
                                        }
                                        rr!!.clear()
                                        rr!!.addAll(pending)
                                        if (pending.size == 0) {
                                            tvNoData.visibility = View.VISIBLE
                                            rv!!.visibility = View.GONE
                                        } else {
                                            tvNoData.visibility = View.GONE
                                            rv!!.visibility = View.VISIBLE
                                        }
                                        rv!!.adapter!!.notifyDataSetChanged()
                                        swipe_refresh.isRefreshing = false
                                    } else {
                                            when (section_number) {
                                          1 -> {
                                                 rr!!.clear()
                                                 for (acceptedItem in response.body()!!.data)
                                                     if (acceptedItem.status == "Accepted")
                                                         rr!!.add(acceptedItem)


                                                 if (rr!!.size == 0) {
                                                     tvNoData.visibility = View.VISIBLE
                                                     rv!!.visibility = View.GONE
                                                 } else {
                                                     tvNoData.visibility = View.GONE
                                                     rv!!.visibility = View.VISIBLE
                                                 }
                                                 rv!!.adapter!!.notifyDataSetChanged()
                                                 swipe_refresh.isRefreshing = false
                                             }
                                             2 -> {
                                                 rr!!.clear()

                                                 for (completedItem in response.body()!!.data)
                                                     if (completedItem.status == "Completed")
                                                         rr!!.add(completedItem)


                                                 if (rr!!.size == 0) {
                                                     tvNoData.visibility = View.VISIBLE
                                                     rv!!.visibility = View.GONE
                                                 } else {
                                                     tvNoData.visibility = View.GONE
                                                     rv!!.visibility = View.VISIBLE
                                                 }
                                                 rv!!.adapter!!.notifyDataSetChanged()
                                                 swipe_refresh.isRefreshing = false
                                             }
                                         }


                                    }
                                } else {
                                    tvNoData.visibility = View.VISIBLE
                                    rv!!.visibility = View.GONE
                                    swipe_refresh.isRefreshing = false
                                }
                            }else {
                                tvNoData.visibility = View.VISIBLE
                                rv!!.visibility = View.GONE
                                swipe_refresh.isRefreshing = false
                            }
                        }
                        override fun onFailure(call: Call<Responce.FetchVendorOrders>, t: Throwable) {
                            t.printStackTrace()
                            tvNoData.visibility = View.VISIBLE
                            tvNoData.text = t.toString()
                           rv!!.visibility = View.GONE
                            swipe_refresh.isRefreshing = false
                        }
                    })

        }
    @SuppressLint("SetTextI18n")
    fun alertDialog(orderId : String, msg : String, order_status : String) {
        val dialog =   Dialog(ctx as Activity)
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
            orderAcceptReject(dialog,orderId,order_status)
        }
        dialog.show()
    }
    private fun orderAcceptReject(dialog : Dialog,orderId : String,order_status : String){

        apiService.orderAcceptReject("order_accept_reject",AppController.getSharedPref().getString("user_id",""),
                orderId,order_status).
                enqueue(object : Callback<Responce.Status> {
                    override fun onResponse(call: Call<Responce.Status>, response: Response<Responce.Status>) {
                        if (response.isSuccessful && response.body()!!.status) {
                            dialog.dismiss()
                            (ctx as Activity as PazerRefresh).refresh()
                        }
                    }
                    override fun onFailure(call: Call<Responce.Status>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
     }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
       ((context!! as Activity).application as AppController).component.inject(this@OrderFragment)
    }
    private fun onOffVendor(orderId : String,msg : String,order_status : String){

        apiService.fetchUserStatus("fetch_vendor_status", AppController.getSharedPref().getString("user_id","")).
                enqueue(object : Callback<Responce.FetchUserStatus> {
                    override fun onResponse(call: Call<Responce.FetchUserStatus>, response: Response<Responce.FetchUserStatus>) {
                        if (response.isSuccessful && response.body()!!.status) {
                            if(response.body()!!.data[0].on_off == "1")
                                alertDialog(orderId,msg,order_status)
                            else
                                Utility.snackBar(container,"You are not activated")
                        }
                    }
                    override fun onFailure(call: Call<Responce.FetchUserStatus>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
    }
    @SuppressLint("SetTextI18n")
    fun alertDialoggetCreditStatus( msg : String) {
        val dialog =   Dialog(ctx as Activity)
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
            dialog.dismiss()
            Utility.enterNextFragment(R.id.container,PackagesFragment.newInstance(0),
                    (ctx as MainActivity).supportFragmentManager)
        }
        dialog.show()
    }
    private fun fetchVendorRemCredits(orderid:String, msg:String,status : String){

        apiService.fetchVendorRemCredits("fetch_vendor_rem_credits", AppController.getSharedPref().getString("user_id","")).
                enqueue(object : Callback<Responce.Status> {
                    override fun onResponse(call: Call<Responce.Status>, response: Response<Responce.Status>) {
                        if (response.isSuccessful ) {
                            if(response.body()!!.status)
                                //onOffVendor(it1.orderid,"Are you sure? You want to accept order ${it1.orderid}.","Accepted")
                                onOffVendor(orderid,msg,status)
                            else
                                alertDialoggetCreditStatus(response.body()!!.message)
                        }
                    }
                    override fun onFailure(call: Call<Responce.Status>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
    }
}