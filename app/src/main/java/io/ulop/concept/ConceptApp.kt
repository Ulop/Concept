package io.ulop.concept

import android.app.Application
import io.ulop.concept.data.PersonRepository
import io.ulop.concept.di.conceptModule
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

class ConceptApp : Application() {

    private val rRepo: PersonRepository by inject("random")
    private val aRepo: PersonRepository by inject("room")

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(conceptModule))

        if (aRepo.personCount() == 0) {
            rRepo.getPersons().forEach {
                aRepo.addPerson(it)
            }
            launch {
                val friends = rRepo.getPersons().map { person ->
                    async {
                        person.friends.map { friend ->
                            Pair(person.id, friend.id)
                        }
                    }
                }.map { it.await() }.flatten()
                aRepo.addFriends(friends = friends)
            }
        }
    }
}