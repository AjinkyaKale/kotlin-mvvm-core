package com.ajinkya.mvvmdemo.presentation.userlist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ajinkya.mvvmdemo.R
import com.ajinkya.mvvmdemo.common.GlideApp
import com.ajinkya.mvvmdemo.data.remote.model.Users
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.layout_item_user_list.view.*
import java.util.*

class UserListRecyclerAdapter(
    private var userList: ArrayList<Users>,
    private var context: Context
) : RecyclerView.Adapter<UserListRecyclerAdapter.ViewHolderUserList>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUserList {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_user_list, parent, false)
        return ViewHolderUserList(view)
    }

    override fun onBindViewHolder(holder: ViewHolderUserList, position: Int) {

        val user = userList[position]

        val userFullName = user.mFirstName + user.mLastName;
        holder.textViewUserName.text = userFullName

        holder.textViewUserEmail.text = user.mEmail

        GlideApp.with(context)
            .load(user.mAvatar)
            .into(holder.imageViewUserProfile)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateAdministrationData(userList: ArrayList<Users>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    class ViewHolderUserList(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewUserName: TextView = itemView.userListTextViewUserName
        val textViewUserEmail: TextView = itemView.userListTextViewUserEmail
        val imageViewUserProfile: CircleImageView = itemView.userListImageViewUserProfile
    }
}