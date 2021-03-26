package com.android.consumerapp.ui.main.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.consumerapp.R
import com.android.consumerapp.ui.main.view.FollowersFragment
import com.android.consumerapp.ui.main.view.FollowingFragment

class DetailAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    @StringRes
    private val TAB_TITLES =
        intArrayOf(R.string.tab_text_1, R.string.tab_text_2)

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment

    }
}