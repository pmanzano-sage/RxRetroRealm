package com.lappsus.rxretrorealm

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.lappsus.android.rxretrorealm.R
import com.lappsus.android.rxretrorealm.databinding.ActivityMainBinding
import com.lappsus.rxretrorealm.api.retrieveFollowingDetails
import com.lappsus.rxretrorealm.api.retrieveGithubGuy
import com.lappsus.rxretrorealm.db.getCurrentUser
import com.lappsus.rxretrorealm.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        followingList.layoutManager = LinearLayoutManager(this)

        val savedUser = getCurrentUser()

        updateView(binding, savedUser)

        retrieveGithubGuy("pmanzano-sage")
                .subscribe(
                        { user ->
                            updateView(binding, user)
                            retrieveFollowingDetails(user)
                                    .subscribe(
                                            { users ->
                                                Log.d("Success", "Following ${users.size} people")
                                                updateFollowingList(users)
                                            },
                                            { error ->
                                                Log.e("Error", error.message)
                                            }
                                    )

                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )


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

    private fun updateView(binding: ActivityMainBinding, savedUser: User?) {
        Glide.with(this).load(savedUser?.avatarUrl).into(binding.userImage)
        binding.userName.text = savedUser?.name
        binding.publicRepos.text = "Public Repos: " + savedUser?.publicRepos.toString()
    }
}



