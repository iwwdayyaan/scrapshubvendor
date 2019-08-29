package com.scrapshubvendor.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scrapshubvendor.R
import kotlinx.android.synthetic.main.order_tab_fragment.*

import android.support.v4.view.PagerAdapter
import com.scrapshubvendor.interfaces.PazerRefresh


class OrderTabFragment : Fragment() {

    private var ctx : Context? = null
   // private var signinStatus : Boolean = true
    private val mTitles = arrayOf("New Order", "Accepted Order", "Completed Order")
    companion object {
        fun newInstance(sectionNumber: Int): OrderTabFragment {
            val fragment = OrderTabFragment()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        return  inflater.inflate(R.layout.order_tab_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_pager.adapter = ViewPagerAdapter(childFragmentManager)
        view_pager.offscreenPageLimit = 3
        tab_layout.setupWithViewPager(view_pager)

    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.ctx = context

    }
    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        override fun getItem(position: Int): Fragment? {
            when(position){
                  0-> return OrderFragment.newInstance(0)
                  1-> return OrderFragment.newInstance(1)
                  2-> return OrderFragment.newInstance(2)
            }
            return null
        }

        override fun getCount(): Int {
            return 3
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position]
        }
    }

}