package com.example.layanankesehatan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.layanankesehatan.R
import com.example.layanankesehatan.config.ServerConfig
import com.example.layanankesehatan.features.pubicHealthCenter.publicHealthCenterDetail.PublicHealthCenterDetailActivity
import com.example.layanankesehatan.models.PublicHealthCenter
import kotlinx.android.synthetic.main.phc_item.view.*
import org.jetbrains.anko.startActivity

class PublicHealthCenterAdapter(val phcList: List<PublicHealthCenter>, val context: Context?) : RecyclerView.Adapter<PublicHealthCenterAdapter.PHCViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PHCViewHolder {
        return PHCViewHolder(LayoutInflater.from(context).inflate(R.layout.phc_item, parent, false))
    }

    override fun getItemCount(): Int = phcList.size

    override fun onBindViewHolder(holder: PHCViewHolder, position: Int) {
        holder.bind(phcList[position])
    }

    inner class PHCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pch: PublicHealthCenter) {

            itemView.tv_name_phc.text = pch.nama_puskesmas
            itemView.tv_address_phc.text = pch.alamat

            Glide.with(itemView.context)
                .load(ServerConfig.PHC_PATH+pch.foto)
                .apply(RequestOptions().placeholder(R.drawable.ic_hourglass_empty_black_24dp))
                .into(itemView.iv_phc)

            itemView.setOnClickListener {
                context?.startActivity<PublicHealthCenterDetailActivity>(
                    PublicHealthCenterDetailActivity.EXTRA_PHC to pch)
            }
        }
    }

}