package com.sillylife.opinionpoll.NewSettings.Interface

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

interface DbChildAddedOnlyCallbacks {
    fun onChildAdded(dataSnapshot: DataSnapshot, s: String)
}
