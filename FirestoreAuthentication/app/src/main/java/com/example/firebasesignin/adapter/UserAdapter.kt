package com.example.firebasesignin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasesignin.R
import com.example.firebasesignin.model.UserModel

class UserAdapter (
    private val context: Context,
    private val items: MutableList<UserModel>,
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.items_users, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model=items[position]

        holder.name.text=model.name
        holder.email.text=model.email
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name=view.findViewById<TextView>(R.id.tv_name)
        val email=view.findViewById<TextView>(R.id.tv_email)


    }

}