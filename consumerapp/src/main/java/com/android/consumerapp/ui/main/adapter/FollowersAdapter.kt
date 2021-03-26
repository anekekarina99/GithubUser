package com.android.consumerapp.ui.main.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.consumerapp.model.Kontak
import com.android.consumerapp.R
import com.android.consumerapp.databinding.ItemFollowersBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.ArrayList



class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    private val mData = ArrayList<Kontak>()

    fun setData(itemsSmt: ArrayList<Kontak>) {
        mData.clear()
        mData.addAll(itemsSmt)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FollowersViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_followers, parent, false)
        return FollowersViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) =
        holder.bind(mData[position])


    override fun getItemCount() = mData.size

    inner class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFollowersBinding.bind(itemView)
        fun bind(kontak: Kontak) {
            binding.textListFollowers.text = kontak.username
            binding.textListNameFollowers.text = kontak.name
            Glide.with(itemView.context)
                .load(kontak.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(binding.imgListFollowers)

        }

    }

}