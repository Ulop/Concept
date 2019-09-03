package io.ulop.concept.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
