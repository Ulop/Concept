package io.ulop.concept.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.ulop.concept.db.dao.PersonDao
import io.ulop.concept.db.entity.Person
import io.ulop.concept.db.entity.PersonFriends
import io.ulop.concept.db.entity.Shot

@Database(entities = [Person::class, PersonFriends::class, Shot::class], version = 1)
abstract class ConceptDatabase : RoomDatabase() {
    abstract fun getDao(): PersonDao
}