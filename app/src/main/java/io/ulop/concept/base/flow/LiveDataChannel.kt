package io.ulop.concept.base.flow

import android.system.Os.close
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowViaChannel


@UseExperimental(ExperimentalCoroutinesApi::class)
@FlowPreview
fun <T> LiveData<T>.asFlow() = channelFlow<T?> {
    offer(value)
    val observer = Observer<T> { t -> offer(t) }
    observeForever(observer)
    invokeOnClose {
        removeObserver(observer)
    }
}