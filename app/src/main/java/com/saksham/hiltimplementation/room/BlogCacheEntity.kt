package com.saksham.hiltimplementation.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BlogCacheEntity(

    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var title: String,
    var body: String,
    var image: String,
    var category: String
)