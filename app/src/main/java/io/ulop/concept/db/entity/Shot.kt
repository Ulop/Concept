package io.ulop.concept.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(
        foreignKeys = [(ForeignKey(
                entity = Person::class,
                parentColumns = ["id"],
                childColumns = ["person_id"]
        ))]
)
class Shot(
        @PrimaryKey
        val id: String,
        val url: String,
        val color: Int,
        @ColumnInfo(name = "person_id") val personId: String
)
