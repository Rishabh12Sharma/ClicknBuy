package com.example.clicknbuy.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clicknbuy.NextActivity
import com.example.clicknbuy.products.Product
import com.example.clicknbuy.R

class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<Product>) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard, parent, false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val Products=itemList[position]
        holder.txtProductName.text=Products.productName
        holder.txtProductAuthor.text=Products.productAuthor
        holder.txtProductPrice.text=Products.productPrice
        holder.txtProductRating.text=Products.productRating

        Glide.with(holder.itemView.context)
            .load(Products.imageUrl)
            .into(holder.imgProductImage)




        holder.llContent.setOnClickListener{
            /*Toast.makeText(context, "Clicked on ${holder.txtScrapName.text}",Toast.LENGTH_SHORT).show()*/
            val intent= Intent(context, NextActivity::class.java)
            intent.putExtra("ProductName",holder.txtProductName.text)
            intent.putExtra("ProductType",holder.txtProductAuthor.text)
            intent.putExtra("ProductPrice",Products.productPrice)
            intent.putExtra("ProductRating",holder.txtProductRating.text)
            intent.putExtra("ProductImage",Products.imageUrl)
            context.startActivity(intent)
        }
    }
    class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtProductName: TextView =view.findViewById(R.id.txtProductName)
        val txtProductAuthor: TextView =view.findViewById(R.id.txtProductAuthor)
        val txtProductPrice: TextView =view.findViewById(R.id.txtProductPrice)
        val txtProductRating: TextView =view.findViewById(R.id.txtProductRating)
        val imgProductImage: ImageView =view.findViewById(R.id.imgProductImage)
        val llContent: LinearLayout =view.findViewById(R.id.llContent)
    }
}