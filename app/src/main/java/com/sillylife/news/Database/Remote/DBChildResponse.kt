package com.sillylife.news.Database.Remote

import com.google.firebase.database.*
import com.sillylife.news.Utils.DbConstants
import com.sillylife.opinionpoll.NewSettings.Interface.DbChildCallbacks

class DBChildResponse(private val requestCallbacks: DbChildCallbacks) {
    private var query: Query? = null

    private val childEventListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            requestCallbacks.onChildAdded(dataSnapshot, s!!)
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
            requestCallbacks.onChildChanged(dataSnapshot, s!!)
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            requestCallbacks.onChildRemoved(dataSnapshot)
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
            requestCallbacks.onChildMoved(dataSnapshot, s!!)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            requestCallbacks.onCancelled(databaseError)
        }
    }

    fun getLocalIssueData(state: String, district: String) {
        query = FirebaseDatabase.getInstance().reference
                .child(DbConstants.LOCAL_ISSUES)
                .child(state)
                .child(district)
        query!!.addChildEventListener(childEventListener)
    }

}
