package com.scrapshubvendor.fragments

import android.annotation.SuppressLint
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
import com.scrapshubvendor.utilities.Utility
import kotlinx.android.synthetic.main.order_detail_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class OrderDetailFragment : Fragment(){
    @Inject
    internal lateinit var apiService: ApiService
    private var ctx : Context? = null
    companion object {
        fun newInstance(sectionNumber: String): OrderDetailFragment {
            val fragment = OrderDetailFragment()
            val args = Bundle()
            args.putString("order_id", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        return  inflater.inflate(R.layout.order_detail_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchOrderDetail(arguments!!.getString("order_id",""))
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        ((context!! as Activity).application as AppController).component.inject(this@OrderDetailFragment)
    }
    private fun fetchOrderDetail(order_id : String){

        progressBar.visibility = View.VISIBLE
        apiService.fetchOrderDetail("fetch_order_detail" ,order_id).
                enqueue(object : Callback<Responce.FetchOrderDetail> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<Responce.FetchOrderDetail>, response: Response<Responce.FetchOrderDetail>) {
                        if (response.isSuccessful && response.body()!!.status) {

                            tvDate.text = Utility.dateFormat1(response.body()!!.data[0].date)
                              tvOrderNo.text = "ORDER ID: ${response.body()!!.data[0].id}"
                              tvProducts.text = response.body()!!.data[0].productname
                              if (response.body()!!.data[0].date != "")
                                  tvPDate.text = Utility.dateFormat1(response.body()!!.data[0].adddate)
                              tvPTime.text = response.body()!!.data[0].addtime
                              tvPickupAdd.text = response.body()!!.data[0].address
                              tvStatus.text = response.body()!!.data[0].status
                              tvCount.text = response.body()!!.data[0].product.split(",").size.toString()
                              tvVName.text = response.body()!!.data[0].name
                              when (response.body()!!.data[0].status) {
                                  "Accepted" -> tvVphone.text = response.body()!!.data[0].phone
                                  "Completed" -> tvVphone.text = response.body()!!.data[0].phone
                              }
                            tvNoData.visibility = View.GONE
                            cl.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }   else{
                            tvNoData.visibility = View.VISIBLE
                            cl.visibility = View.GONE
                            progressBar.visibility = View.GONE
                        }
                    }
                    override fun onFailure(call: Call<Responce.FetchOrderDetail>, t: Throwable) {
                        t.printStackTrace()
                        tvNoData.visibility = View.VISIBLE
                        cl.visibility = View.GONE
                        progressBar.visibility = View.GONE
                    }
                })

               }
}