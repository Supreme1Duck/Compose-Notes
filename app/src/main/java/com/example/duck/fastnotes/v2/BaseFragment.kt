package com.example.duck.fastnotes.v2

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {

    private var model: BaseViewModel? = null
    private val visibility = MutableSharedFlow<Boolean>(extraBufferCapacity = 1, replay = 1)

    private val fragmentHolder: FragmentHolder by lazy {
        FragmentHolder.Impl(
            this,
            visibility
        )
    }

    protected open val hideBottomMenu: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val graph = (NoteApp.appContext()).graph
        setupScreen(savedInstanceState, graph)
    }

    private fun setupScreen(
        savedInstanceState: Bundle?,
        graph: AppComponent
    ) {
        model = onInit(savedInstanceState, fragmentHolder, graph)
    }

    abstract fun onInit(
        savedInstanceState: Bundle?,
        fragmentHolder: FragmentHolder,
        graph: AppComponent,
    ): BaseViewModel

    override fun onDestroyView() {
        cleanUpViewModels()
        super.onDestroyView()
    }

    private fun cleanUpViewModels() {
        model?.cancel()
        model = null
    }

    override fun onStart() {
        super.onStart()
        setBottomMenuVisible(hideBottomMenu.not())
        visibility.tryEmit(true)
    }

    override fun onStop() {
        visibility.tryEmit(false)
        super.onStop()
    }

    protected fun setBottomMenuVisible(value: Boolean) {
        activity?.let { act ->
            if (act is MainActivity) {
                if (value) {
                    act.showBottomMenu()
                } else {
                    act.hideBottomMenu()
                }
            }
        }
    }
}
