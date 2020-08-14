package com.kpit.listview.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kpit.listview.R
import com.kpit.listview.model.UserDetails

class ListAdapter(private val listdata : ArrayList<UserDetails>) : RecyclerView.Adapter<com.kpit.listview.view.ListAdapter.ViewHolder>() {



    override fun getItemCount(): Int {
        return listdata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.fname?.text= listdata[position].name;
        holder?.lname?.text= listdata[position].lname;
        holder?.image?.setImageBitmap(listdata[position].image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.row_data2, parent, false);
        return ViewHolder(v);
    }


    class ViewHolder (itemView : View):RecyclerView.ViewHolder(itemView) {

        val fname  = itemView.findViewById<TextView>(R.id.fname);
        val lname  = itemView.findViewById<TextView>(R.id.lname);
        val image  = itemView.findViewById<ImageView>(R.id.image);
    }


}