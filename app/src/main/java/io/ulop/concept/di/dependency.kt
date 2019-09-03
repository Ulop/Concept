package io.ulop.concept.di

import io.ulop.concept.data.PersonRepository
import io.ulop.concept.data.RandomPersonRepository
import io.ulop.concept.data.RoomPersonRepository
import io.ulop.concept.ui.person.PersonPageViewModel
import io.ulop.concept.ui.persons.PersonsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.newFixedThreadPoolContext
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val conceptModule = module {
    single(named("random")) { RandomPersonRepository() as PersonRepository }
    single(named("room")) { RoomPersonRepository(androidApplication(), get(named(Scopes.Presentation))) as PersonRepository }
    viewModel { (id: String) -> PersonPageViewModel(id, get(named("room"))) }
    viewModel { PersonsViewModel(get(named("room"))) }
}

object Scopes {
    const val Requests = "requests"
    const val Presentation = "scope presentation"
    const val Main = "main"
    const val Own = "own"
}

val coroutineModule = module {
    single(named(Scopes.Requests)) { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    single(named(Scopes.Presentation)) { CoroutineScope(Dispatchers.Default) }
    single(named(Scopes.Own)) { CoroutineScope(newFixedThreadPoolContext(2, Scopes.Own)) }
}