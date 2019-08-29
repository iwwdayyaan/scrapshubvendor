package com.scrapshubvendor.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scrapshubvendor.R
import com.scrapshubvendor.api.interfaces.ApiService
import javax.inject.Inject

class PaymentFragment   : Fragment() {
    @Inject
    internal lateinit var apiService: ApiService
    //private var ctx : Context? = null

    companion object {
        fun newInstance(sectionNumber: Int): PaymentFragment {
            val fragment = PaymentFragment()
            val args = Bundle()
            args.putInt("section_number", sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        return  inflater.inflate(R.layout.payment_fragment, container, false)
    }
    /*  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

     }
    override fun onAttach(context: Context?) {
         super.onAttach(context)
         this.ctx = context

     }*/

}