package io.ulop.concept.base.flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect


@UseExperimental(ExperimentalCoroutinesApi::class)
@FlowPreview
fun <T> LiveData<T>.asFlow() = channelFlow {
    offer(value)
    val observer = Observer<T> { t -> offer(t) }
    observeForever(observer)
    invokeOnClose {
        removeObserver(observer)
    }
}