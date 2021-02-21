package com.android.dcdsubfunddua.SplashScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.dcdsubfunddua.Kontak.KontakActivity
import com.android.dcdsubfunddua.R


class OneFragment : Fragment() {

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(index: Int) =
            OneFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, index)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvLabel: TextView = view.findViewById(R.id.labelSplash)
        val imgSplash : ImageView = view.findViewById(R.id.imageSplash)
        val imgNext : ImageView = view.findViewById(R.id.imageNext)

        when(arguments?.getInt(ARG_SECTION_NUMBER, 0)){
            1 -> {
                tvLabel.text = "Do you want Collaboration?"
                imgSplash.setImageResource(R.mipmap.ic_hand)
            }
            2 -> {
                tvLabel.text = "Make a best Innovation"
                imgSplash.setImageResource(R.mipmap.ic_idea)
            }
            3 -> {
                tvLabel.text = "Swipe this"
                imgSplash.setImageResource(R.mipmap.ic_collab)
                imgNext.setOnClickListener {

                    val intent = Intent(activity, KontakActivity::class.java)
                    startActivity(intent)

                }

            }

        }

    }


}