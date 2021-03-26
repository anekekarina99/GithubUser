package com.android.consumerapp.ui.main.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.consumerapp.R
import com.android.consumerapp.databinding.ItemFavoriteBinding
import com.android.consumerapp.model.Favorite
import com.android.consumerapp.ui.main.CustomOnItemClickListener
import com.android.consumerapp.ui.main.view.DetailActivity
import com.android.consumerapp.ui.main.view.FavorActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.ArrayList

class FavoriteAdapter(private val activity: Activity) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    var listFavorite = ArrayList<Favorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(listFavorite[position])


    override fun getItemCount(): Int = listFavorite.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bind = ItemFavoriteBinding.bind(itemView)
        fun bind(favor: Favorite) {
            bind.textList.text = favor.username
            bind.textListName.text = favor.name
            bind.textView.text = favor.location
            bind.textView2.text = favor.company
            Glide.with(itemView.context)
                .load(favor.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(bind.imgList)

                itemView.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition,
                        object : CustomOnItemClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val i = Intent(activity, FavorActivity::class.java)
                                i.putExtra(DetailActivity.EXTRA_POSITION, position)
                                i.putExtra(DetailActivity.EXTRA_FAVOR, favor)
                                activity.startActivity(i)
                            }


                        }
                    )
                )


        }
    }
}