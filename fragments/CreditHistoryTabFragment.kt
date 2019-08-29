package com.scrapshubvendor.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scrapshubvendor.R
import com.scrapshubvendor.activities.MainActivity
import com.scrapshubvendor.api.AppController
import com.scrapshubvendor.fragments.backpressed.RootFragment
import com.scrapshubvendor.interfaces.Credit
import com.scrapshubvendor.interfaces.NavigationHost
import com.scrapshubvendor.interfaces.SelectViewPager
import com.scrapshubvendor.utilities.Utility
import kotlinx.android.synthetic.main.credit_history_tab_fragment.*

class CreditHistoryTabFragment  : RootFragment(),Credit,View.OnClickListener {


    private val mTitles = arrayOf("Recharges", "Expenses")
    private var ctx: Context? = null


    companion object {
        fun newInstance(sectionNumber: Int): CreditHistoryTabFragment {
            val fragment = CreditHistoryTabFragment()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        return inflater.inflate(R.layout.credit_history_tab_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_pager.adapter = ViewPagerAdapter(childFragmentManager)
        view_pager.offscreenPageLimit = 2
        tab_layout.setupWithViewPager(view_pager)
        btnAvailablePkg.setOnClickListener(this)
    }
    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        override fun getItem(position: Int): Fragment? {
            when(position){
                0-> return CreditHistoryFragment.newInstance(0)
                1-> return CreditHistoryFragment.newInstance(1)
            }
            return null
        }

        override fun getCount(): Int {
            return 2
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }
    }
    override fun credit(credit: String) {
        tvCredits.text = credit

    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context

    }
    override fun onClick(v: View?) {
        when(v!!.id){
           R.id.btnAvailablePkg ->  Utility.enterNextFragment(R.id.container_credit_his_tab,PackagesFragment.newInstance(0),
                   (ctx as MainActivity).supportFragmentManager)
        }
    }


}