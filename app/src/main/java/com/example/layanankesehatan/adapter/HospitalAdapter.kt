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
import com.example.layanankesehatan.features.hospital.hospitaldetail.HospitalDetailActivity
import com.example.layanankesehatan.models.Hospital
import kotlinx.android.synthetic.main.hospital_item.view.*
import org.jetbrains.anko.startActivity

class HospitalAdapter(val hospitalList: List<Hospital>, val context: Context?) : RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        return HospitalViewHolder(LayoutInflater.from(context).inflate(R.layout.hospital_item, parent, false))
    }

    override fun getItemCount(): Int = hospitalList.size


    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        holder.bind(hospitalList[position])
    }

    inner class HospitalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hospital: Hospital) {

            itemView.tv_name_hos.text = hospital.nama_rumah_sakit
            itemView.tv_address_hos.text = hospital.alamat

            Glide.with(itemView.context)
                .load(ServerConfig.HOSPITAL_PATH+hospital.foto)
                .apply(RequestOptions().placeholder(R.drawable.ic_hourglass_empty_black_24dp))
                .into(itemView.iv_hos)

            itemView.setOnClickListener {
                context?.startActivity<HospitalDetailActivity>(
                    HospitalDetailActivity.EXTRA_HOSPITAL to hospital)
            }
        }
    }

}