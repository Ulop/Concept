package io.ulop.concept.base.livedata

import androidx.lifecycle.MutableLiveData
import java.util.*

class StackLiveData<T> : MutableLiveData<T>() {

    private val stack = Stack<T>()
    private var dontPush = false

    override fun setValue(value: T) {
        if (!dontPush)
            stack.push(value)
        dontPush = false
        super.setValue(value)
    }

    fun pop(): T? {
        return if (stack.empty()) null else {
            val result = stack.pop()
            dontPush = true
            if (stack.isNotEmpty())
                setValue(stack.peek())
            else return null

            result
        }
    }
}