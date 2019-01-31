package com.sillylife.opinionpoll.NewSettings.Interface

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

interface DbCallbacks {
    fun onSuccess(dataSnapshot: DataSnapshot)
    fun onCancelled(databaseError: DatabaseError)
}
