package com.android.dcdsubfunddua.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dcdsubfunddua.model.Kontak
import com.android.dcdsubfunddua.R
import com.android.dcdsubfunddua.databinding.ItemFollowingBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val mData = ArrayList<Kontak>()

    fun setData(itemsSmt: ArrayList<Kontak>) {
        mData.clear()
        mData.addAll(itemsSmt)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FollowingViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_following, parent, false)
        return FollowingViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(mData[position])

    }


    override fun getItemCount() = mData.size

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFollowingBinding.bind(itemView)
        fun bind(kontak: Kontak) {
            binding.textListFollowing.text = kontak.username
            binding.textListNameFollowing.text = kontak.name
            Glide.with(itemView.context)
                .load(kontak.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(binding.imgListFollowing)
/*
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(kontak)

                val user = Kontak(
                    kontak.id,
                    kontak.username,
                    kontak.name,
                    kontak.avatar,
                    kontak.company,
                    kontak.repository,
                    kontak.followers,
                    kontak.following
                )
                val i = Intent(m, FollowersAdapter::class.java)
                i.putExtra(FollowersFragment.EXTRA_DATA, user)
                m.startActivity(i)
            }
            */

        }

    }

}