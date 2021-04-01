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
import com.example.layanankesehatan.features.clinic.clinicDetail.ClinicDetailActivity
import com.example.layanankesehatan.models.Clinic
import kotlinx.android.synthetic.main.clinic_item.view.*
import kotlinx.android.synthetic.main.pharmacy_item.view.tv_name
import org.jetbrains.anko.startActivity

class ClinicAdapter(val clinicList: List<Clinic>, val context: Context?) : RecyclerView.Adapter<ClinicAdapter.ClinicViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {
        return ClinicViewHolder(LayoutInflater.from(context).inflate(R.layout.clinic_item, parent, false))
    }

    override fun getItemCount(): Int = clinicList.size

    override fun onBindViewHolder(holder: ClinicViewHolder, position: Int) {
        holder.bind(clinicList[position])
    }

    inner class ClinicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(clinic: Clinic) {

            itemView.tv_name.text = clinic.nama_klinik
            itemView.tv_address_clinic.text = clinic.alamat

            Glide.with(itemView.context)
                .load(ServerConfig.CLINIC_PATH+clinic.foto)
                .apply(RequestOptions().placeholder(R.drawable.ic_hourglass_empty_black_24dp))
                .into(itemView.iv_clinic)

            itemView.setOnClickListener {
                context?.startActivity<ClinicDetailActivity>(
                    ClinicDetailActivity.EXTRA_CLINIC to clinic)
            }
        }
    }

}