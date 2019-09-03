package io.ulop.concept.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
        tableName = "person_friends",
        primaryKeys = ["personId", "friendId"],
        foreignKeys = [
            ForeignKey(entity = Person::class, parentColumns = ["id"], childColumns = ["personId"], onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = Person::class, parentColumns = ["id"], childColumns = ["friendId"], onDelete = ForeignKey.CASCADE)
        ]
)
class PersonFriends(
        val personId: String,
        val friendId: String
)