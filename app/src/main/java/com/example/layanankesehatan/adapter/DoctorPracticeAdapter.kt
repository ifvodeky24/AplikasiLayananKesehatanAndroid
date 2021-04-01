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
import com.example.layanankesehatan.features.doktorpractice.doctorpracticedetail.DoctorPracticeDetailActivity
import com.example.layanankesehatan.models.DoctorPractice
import kotlinx.android.synthetic.main.dp_item.view.*
import org.jetbrains.anko.startActivity

class DoctorPracticeAdapter(val dpList: List<DoctorPractice>, val context: Context?) : RecyclerView.Adapter<DoctorPracticeAdapter.DpViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DpViewHolder {
        return DpViewHolder(LayoutInflater.from(context).inflate(R.layout.dp_item, parent, false))
    }

    override fun getItemCount(): Int = dpList.size

    override fun onBindViewHolder(holder: DpViewHolder, position: Int) {
        holder.bind(dpList[position])
    }

    inner class DpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dp: DoctorPractice) {

            itemView.tv_name_dp.text = dp.nama_dokter_praktek
            itemView.tv_address_dp.text = dp.alamat

            Glide.with(itemView.context)
                .load(ServerConfig.DP_PATH+dp.foto)
                .apply(RequestOptions().placeholder(R.drawable.ic_hourglass_empty_black_24dp))
                .into(itemView.iv_dp)

            itemView.setOnClickListener {
                context?.startActivity<DoctorPracticeDetailActivity>(
                    DoctorPracticeDetailActivity.EXTRA_DP to dp)
            }
        }
    }

}