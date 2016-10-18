package com.lappsus.rxretrorealm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.lappsus.android.rxretrorealm.R
import com.lappsus.rxretrorealm.api.retrieveFollowingDetails
import com.lappsus.rxretrorealm.api.retrieveGithubGuy
import com.lappsus.rxretrorealm.db.getCurrentUser
import com.lappsus.rxretrorealm.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        followingList.layoutManager = LinearLayoutManager(this)

        val savedUser = getCurrentUser()

        updateView(savedUser)

        retrieveGithubGuy("pmanzano-sage")
                .subscribe(
                        { onRetrieveGuySuccess(it) },
                        { onRequestError(it) }
                )
    }

    private fun onRetrieveGuySuccess(user: User) {
        updateView(user)
        retrieveFollowingDetails(user)
                .subscribe(
                        { users ->
                            Log.d("Success", "Following ${users.size} people")
                            updateFollowingList(users)
                        },
                        { onRequestError(it) }
                )
    }

    private fun onRequestError(error: Throwable) {
        Log.e("Error", error.message)
    }

    private fun updateFollowingList(users: List<User>) {
        val adapter = FollowingListAdapter(users,
                {
                    Log.d("onClick", "${it.login}")
                },
                {
                    Log.d("onLongClick", "${it.login}")
                    true
                }
        )
        followingList.adapter = adapter
    }

    private fun updateView(savedUser: User?) {
        Glide.with(this).load(savedUser?.avatarUrl).into(userImage)
        userName.text = savedUser?.name
        publicRepos.text = "Public Repos: " + savedUser?.publicRepos.toString()
    }
}



