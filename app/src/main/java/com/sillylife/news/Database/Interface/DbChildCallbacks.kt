package com.sillylife.opinionpoll.NewSettings.Interface

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

interface DbChildCallbacks {
    fun onCancelled(databaseError: DatabaseError)
    fun onChildAdded(dataSnapshot: DataSnapshot, s: String)
    fun onChildChanged(dataSnapshot: DataSnapshot, s: String)
    fun onChildMoved(dataSnapshot: DataSnapshot, s: String)
    fun onChildRemoved(dataSnapshot: DataSnapshot)
}
