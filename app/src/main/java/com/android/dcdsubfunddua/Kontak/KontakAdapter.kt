package com.android.dcdsubfunddua.Kontak

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



class KontakAdapter(private val items: ArrayList<KontakItems>) : RecyclerView.Adapter<KontakAdapter.KontakViewHolder>() {
/*
    private val mDataKontak = ArrayList<KontakItems>()
    lateinit var mcontext: Context

    fun setData(items: ArrayList<KontakItems>) {
        mDataKontak.clear()
        mDataKontak.addAll(items)
        notifyDataSetChanged()
    } */


    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): KontakViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = KontakItemsBinding.inflate(inflater)
        return KontakViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KontakViewHolder, position: Int) =holder.bind(items[position])


    interface OnItemClickCallback {
        fun onItemClicked(dataKontak: KontakItems)
    }

    override fun getItemCount() = items.size

    inner class KontakViewHolder(val binding : ItemKontakBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(kontakItems: KontakItems) {
            with(binding) {
                textList.text = kontakItems.username
                textListName.text = kontakItems.name
                imgList.text = kontakItems.avatar
            }
        }

    }
}


