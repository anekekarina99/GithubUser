package com.android.consumerapp.ui.main.adapter




import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.consumerapp.model.Kontak
import com.android.consumerapp.R
import com.android.consumerapp.databinding.ItemListBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*




class KontakAdapter : RecyclerView.Adapter<KontakAdapter.KontakViewHolder>() {
    private val mData = ArrayList<Kontak>()


    fun setData(itemsSmt: ArrayList<Kontak>) {
        mData.clear()
        mData.addAll(itemsSmt)
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): KontakViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return KontakViewHolder(mView)
    }

    override fun onBindViewHolder(holder: KontakViewHolder, position: Int) {
        holder.bind(mData[position])

    }


    interface OnItemClickCallback {
        fun onItemClicked(data: Kontak)
    }

    override fun getItemCount() = mData.size

    inner class KontakViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(kontak: Kontak) {
            binding.textList.text = kontak.username
            binding.textListName.text = kontak.name
            Glide.with(itemView.context)
                .load(kontak.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(binding.imgList)

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(kontak)

            }
        }

    }

}




