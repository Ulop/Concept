package io.ulop.concept.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.ulop.concept.db.entity.Person
import io.ulop.concept.db.entity.PersonFriends
import io.ulop.concept.db.entity.Shot


@Dao
interface PersonDao {
    @Query("SELECT * FROM Person")
    fun getPersons(): List<Person>

    @Query("SELECT * \nFROM Person \nINNER JOIN person_friends ON Person.id=person_friends.friendId \nWHERE person_friends.personId=:id")
    fun getPersonFriends(id: String): List<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPerson(vararg person: Person)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFriends(personAndFriends: List<PersonFriends>)

    @Query("SELECT COUNT(id) FROM Person")
    fun getPersonCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addShot(vararg shot: Shot)

    @Query("SELECT * FROM Shot WHERE Shot.person_id = :personId")
    fun getPersonShots(personId: String): MutableList<Shot>
}