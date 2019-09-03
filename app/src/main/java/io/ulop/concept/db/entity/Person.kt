package io.ulop.concept.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
        @PrimaryKey var id: String = "",
        var name: String,
        var surname: String,
        var avatar: String,
        var about: String,
        var place: String
)