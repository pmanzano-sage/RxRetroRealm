package com.lappsus.rxretrorealm

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lappsus.android.rxretrorealm.R
import com.lappsus.rxretrorealm.extensions.ctx
import com.lappsus.rxretrorealm.model.User
import kotlinx.android.synthetic.main.item_following.view.*

/**
 * Example of RecyclerView adapter with a couple of listeners.
 */
class FollowingListAdapter(val followingUsers: List<User>, val itemClick: (User) -> Unit, val itemLongClick: (User) -> Boolean) :
        RecyclerView.Adapter<FollowingListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_following, parent, false)
        return ViewHolder(view, itemClick, itemLongClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(followingUsers[position])
    }

    override fun getItemCount() = followingUsers.size

    class ViewHolder(view: View, val itemClick: (User) -> Unit, val itemLongClick: (User) -> Boolean) : RecyclerView.ViewHolder(view) {

        fun bindForecast(user: User) {
            with(user) {
                Glide.with(itemView.ctx).load(user.avatarUrl).into(itemView.icon)
                itemView.username.text = user.login
                itemView.date.text = user.createdAt.toString()
                itemView.following.text = user.following.toString()
                itemView.followers.text = user.followers.toString()
                itemView.setOnClickListener { itemClick(this) }
                itemView.setOnLongClickListener { itemLongClick(this) }
            }
        }
    }
}