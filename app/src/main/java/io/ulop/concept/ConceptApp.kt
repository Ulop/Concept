package io.ulop.concept

import android.app.Application
import io.ulop.concept.data.PersonRepository
import io.ulop.concept.di.conceptModule
import io.ulop.concept.di.coroutineModule
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named

class ConceptApp : Application() {

    private val rRepo: PersonRepository by inject(named("random"))
    private val aRepo: PersonRepository by inject(named("room"))

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ConceptApp)
            modules(listOf(conceptModule, coroutineModule))
        }

        if (aRepo.personCount() == 0) {
            rRepo.getPersons().forEach {
                aRepo.addPerson(it)
            }
            GlobalScope.launch {
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