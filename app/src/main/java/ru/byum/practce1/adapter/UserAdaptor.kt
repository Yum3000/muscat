package ru.byum.practce1.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.byum.practce1.retrofit.User
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.byum.practce1.R

class UserAdaptor(private val context: Activity, private val arrayList: ArrayList<User>) :
    ArrayAdapter<User>(context, R.layout.list_item, arrayList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item, null)
        val tvUserName : TextView = view.findViewById(R.id.tvUserName)
        val tvDescription : TextView = view.findViewById(R.id.tvDescription)

        tvUserName.text = arrayList[position].username
        tvDescription.text = arrayList[position].email

        return view
    }
    }
