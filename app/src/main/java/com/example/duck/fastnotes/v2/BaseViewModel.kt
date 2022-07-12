package com.example.duck.fastnotes.v2

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlin.coroutines.CoroutineContext

interface BaseViewModel : CoroutineScope {

    fun cancel()

    open class Impl(context: CoroutineContext) : BaseViewModel {
        private val job = SupervisorJob()
        private val children = mutableMapOf<String, List<BaseViewModel>>()

        override val coroutineContext: CoroutineContext = context + job

        init {
            launch {
                awaitClose {
                    children.let { it.forEach { (_, child) -> child.forEach(BaseViewModel::cancel) } }
                    children.clear()
                }
            }
        }

        protected fun <T : BaseViewModel> addChild(key: String, block: () -> T) {
            addChildren(key) { listOf(block()) }
        }

        protected fun <T : BaseViewModel> addChildren(key: String, block: () -> List<T>) {
            children.remove(key)?.forEach(BaseViewModel::cancel)
            val child = block()
            if (isActive) {
                children[key] = child
            } else {
                child.forEach(BaseViewModel::cancel)
            }
        }

        protected fun removeChild(key: String) {
            children[key]?.forEach(BaseViewModel::cancel)
        }

        protected fun <T> Flow<T>.shareIn(started: SharingStarted = SharingStarted.WhileSubscribed(), replay: Int = 0) =
            shareIn(this@Impl, started, replay)

        override fun cancel() {
            this.cancel(null)
        }
    }
}
