package io.ulop.concept.adapter

/*inline fun <reified T> injector(crossinline block: (View, T) -> Unit) = object : SlimInjector<T> {
    override fun onInject(data: T, injector: IViewInjector<out IViewInjector<*>>?) {
        injector?.with<View?>(R.id.root) { view ->
            if (view != null)
                block(view, data)
            else throw IllegalArgumentException("root layout must have id = root")
        }
    }
}

inline fun <reified T> slimInjector(crossinline block: IViewInjector<out IViewInjector<*>>.(T) -> Unit) = object : SlimInjector<T> {
    override fun onInject(data: T, injector: IViewInjector<out IViewInjector<*>>?) {
        injector?.block(data)
    }
}*/

