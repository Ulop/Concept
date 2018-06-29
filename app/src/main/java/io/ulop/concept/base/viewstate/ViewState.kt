package io.ulop.concept.base.viewstate

sealed class ViewState {
    object Loading : ViewState()
    object Idle : ViewState()
    class ItemClick<T>(val item: T) : ViewState()
    class Result<out T>(val result: T) : ViewState()
    class Error(val error: Throwable) : ViewState()
}