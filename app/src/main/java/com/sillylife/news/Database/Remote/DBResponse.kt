package com.sillylife.news.Database.Remote

import com.google.firebase.database.*
import com.sillylife.news.Database.ApiConstants
import com.sillylife.opinionpoll.NewSettings.Interface.DbCallbacks

class DBResponse(private val requestCallbacks: DbCallbacks) {
    private var query: Query? = null

    fun getHindiHeadlineLinks() {
        query = FirebaseDatabase.getInstance().reference.child(ApiConstants.hindi_headlines)
        startListener()
    }

    fun getHinglishHeadlineLinks() {
        query = FirebaseDatabase.getInstance().reference.child(ApiConstants.hinglish_headlines)
        startListener()
    }

    fun getEnglishHeadlineLinks() {
        query = FirebaseDatabase.getInstance().reference.child(ApiConstants.english_headlines)
        startListener()
    }

    private fun startListener(){
        query!!.keepSynced(true)
        query!!.addListenerForSingleValueEvent(valueEventListener)
    }

    private val valueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            requestCallbacks.onSuccess(dataSnapshot)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            requestCallbacks.onCancelled(databaseError)
        }
    }
}
