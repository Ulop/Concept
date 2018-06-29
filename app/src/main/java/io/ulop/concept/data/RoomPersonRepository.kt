package io.ulop.concept.data

import android.app.Application
import android.arch.persistence.room.Room
import io.ulop.concept.db.ConceptDatabase
import io.ulop.concept.db.entity.PersonFriends
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import io.ulop.concept.db.entity.Shot as DBShot
import io.ulop.concept.db.entity.Person as DBPerson

class RoomPersonRepository(application: Application) : PersonRepository {

    private val dao = Room
            .databaseBuilder(application.applicationContext, ConceptDatabase::class.java, "concept-db")
            .build().getDao()

    override fun getPersons(): List<Person> {
        return dao.getPersons().map { dbPerson ->
            Person(
                    id = dbPerson.id,
                    name = dbPerson.name,
                    surname = dbPerson.surname,
                    avatar = dbPerson.avatar,
                    about = dbPerson.about,
                    place = dbPerson.place,
                    shots = dao.getPersonShots(personId = dbPerson.id).map { Shot(it.url, it.color) }.toMutableList(),
                    friends = dao.getPersonFriends(dbPerson.id).map { dp ->
                        Person(
                                id = dp.id,
                                name = dp.name,
                                surname = dp.surname,
                                avatar = dp.avatar,
                                about = dp.about,
                                place = dp.place
                        )
                    }.toMutableList()
            )
        }
    }

    override fun addPerson(vararg person: Person) {
        launch {
            val persons = person.map {
                with(it) {
                    DBPerson(id, name, surname, avatar, about, place)
                }
            }.toTypedArray()
            dao.addPerson(*persons)
            /*friends.forEach { friend ->
                println("add Friend(${friend.id}, ${friend.name}) to Person($id, $name)")
                dao.addPersonFriends(personWithFriends = PersonFriends(id, friend.id))
            }*/
            val shots = person.map { person ->
                person.shots.map { shot ->
                    DBShot(shot.url.substringAfter('='), shot.url, shot.color, person.id)
                }
            }.flatten()
            dao.addShot(*shots.toTypedArray())
        }
    }

    override fun addFriends(friends: List<Pair<String, String>>) {
        launch {
            val dbFriends = friends.map { (personId, friendId) -> PersonFriends(personId, friendId) }
            dao.addFriends(dbFriends)
        }
    }

    override fun personCount(): Int {
        return runBlocking { async { dao.getPersonCount() }.await() }
    }
}