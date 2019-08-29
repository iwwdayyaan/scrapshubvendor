package com.scrapshubvendor.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.scrapshubvendor.R
import com.scrapshubvendor.activities.MainActivity
import com.scrapshubvendor.api.AppController
import com.scrapshubvendor.api.Responce
import com.scrapshubvendor.api.interfaces.ApiService
import com.scrapshubvendor.fragments.backpressed.RootFragment
import com.scrapshubvendor.interfaces.Credit
import com.scrapshubvendor.recyclerview.setUp
import com.scrapshubvendor.utilities.Utility
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import kotlinx.android.synthetic.main.credit_history_fragment.*
import kotlinx.android.synthetic.main.credit_history_fragment_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class CreditHistoryFragment  : RootFragment(), View.OnClickListener{
    @Inject
    internal lateinit var apiService: ApiService
    private var ctx : Context? = null
    private var section_number : Int = 0
    private var rr : MutableList<Responce.FetchVendorLeaderCredits.Data>? = null
    companion object {
        fun newInstance(sectionNumber: Int): CreditHistoryFragment {
            val fragment = CreditHistoryFragment()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        return  inflater.inflate(R.layout.credit_history_fragment, container, false)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        section_number = arguments!!.getInt("section_number",0)
        rr = mutableListOf()

        rv.setUp(rr!!, R.layout.credit_history_fragment_item, { it1 ->
            this.tvDate.text = Utility.dateFormat1(it1.date)
            if(section_number ==0){
                this.tvOrderId.text = "RECHARGE ID: ${it1.id}"
                this.tvCreadits.text = "${it1.credit} credits"
                this.tvAmt.text = "₹ ${it1.price}"
            }
            else {
                this.tvOrderId.text = "EXPENSE ID: ${it1.id}"
                this.tvCreadits.text = "${it1.expense} credits"
                this.tvName.text = resources.getString(R.string.expenseces)
                this.tvAmt.text = "View Expense"
                this.tvAmt.isFocusable = true
                this.tvAmt.isClickable = true

                this.tvAmt.setOnClickListener {
                    Utility.enterNextFragment(R.id.container_credit_his_tab,OrderDetailFragment.newInstance(it1.orderId),(ctx as MainActivity).supportFragmentManager)
                }
            }
                }, { view1: View, i: Int -> })
        rv. addItemDecoration(  LayoutMarginDecoration( 1, 16 ) )

        if(section_number == 0)
            fetchVendorLeaderCredits("fetch_vendor_credits")
        else
            fetchVendorLeaderCredits("fetch_vendor_leader_credits")

        swipe_refresh.setOnRefreshListener {   if(section_number == 0)
             fetchVendorLeaderCredits("fetch_vendor_credits")
         else
             fetchVendorLeaderCredits("fetch_vendor_leader_credits") }

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

    private fun fetchVendorLeaderCredits(api : String){

        apiService.fetchVendorLeaderCredits(api ,AppController.getSharedPref().getString("user_id","")).
                enqueue(object : Callback<Responce.FetchVendorLeaderCredits> {
                    override fun onResponse(call: Call<Responce.FetchVendorLeaderCredits>, response: Response<Responce.FetchVendorLeaderCredits>) {
                        if (response.isSuccessful && response.body()!!.status) {
                            when(section_number){
                                0 ->{
                                    rr!!.clear()
                                    rr!!.addAll(response.body()!!.data)
                                    rv.adapter!!.notifyDataSetChanged()
                                    swipe_refresh.isRefreshing = false
                                }
                                1 ->{
                                    rr!!.clear()
                                    for(item in response.body()!!.data)
                                        if(item.expense != "")
                                            rr!!.add(item)
                                    rv.adapter!!.notifyDataSetChanged()
                                    if(parentFragment != null){
                                        (parentFragment as Credit).credit("${response.body()!!.data[response.body()!!.data.size-1].remCredit} Credits")
                                        (ctx as Activity as Credit).credit("${response.body()!!.data[response.body()!!.data.size-1].remCredit} Credits")
                                    }
                                    if(rr!!.size == 0){
                                        tvNoData.visibility = View.VISIBLE
                                        rv.visibility = View.GONE
                                    }else{
                                        tvNoData.visibility = View.GONE
                                        rv.visibility = View.VISIBLE
                                    }
                                    swipe_refresh.isRefreshing = false
                                }
                            }

                            } else {
                               tvNoData.visibility = View.VISIBLE
                               rv.visibility = View.GONE
                               swipe_refresh.isRefreshing = false
                        }

                    }
                    override fun onFailure(call: Call<Responce.FetchVendorLeaderCredits>, t: Throwable) {
                        t.printStackTrace()
                        tvNoData.visibility = View.VISIBLE
                        rv.visibility = View.GONE
                        swipe_refresh.isRefreshing = false
                    }
                })
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context
        ((context!! as Activity).application as AppController).component.inject(this@CreditHistoryFragment)
    }
}