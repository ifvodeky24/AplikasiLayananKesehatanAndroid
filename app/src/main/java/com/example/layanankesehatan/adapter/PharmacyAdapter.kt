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
import com.example.layanankesehatan.features.pharmacy.pharmacydetail.PharmacyDetailActivity
import com.example.layanankesehatan.models.Pharmacy
import kotlinx.android.synthetic.main.pharmacy_item.view.*
import org.jetbrains.anko.startActivity

class PharmacyAdapter(val pharmacyList: List<Pharmacy>, val context: Context?) : RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {
        return PharmacyViewHolder(LayoutInflater.from(context).inflate(R.layout.pharmacy_item, parent, false))
    }

    override fun getItemCount(): Int = pharmacyList.size


    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        holder.bind(pharmacyList[position])
    }

    inner class PharmacyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pharmacy: Pharmacy) {

            itemView.tv_name.text = pharmacy.nama_apotek
            itemView.tv_address.text = pharmacy.alamat

            Glide.with(itemView.context)
                .load(ServerConfig.PHARMACY_PATH+pharmacy.foto)
                .apply(RequestOptions().placeholder(R.drawable.ic_hourglass_empty_black_24dp))
                .into(itemView.iv_pharmacy)

            itemView.setOnClickListener {
                context?.startActivity<PharmacyDetailActivity>(
                    PharmacyDetailActivity.EXTRA_PHARMACY to pharmacy)
            }
        }
    }

}