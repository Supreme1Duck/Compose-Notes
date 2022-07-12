package com.example.duck.fastnotes.v2

import android.content.pm.PackageManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

interface FragmentHolder {
    val fragment: Fragment
    val systemBackPressed: Flow<Unit>
    val screenVisibility: Flow<Boolean>
    val requestPermissionResult: Flow<Boolean>

    fun navigateUp()
    fun setResult(key: String, vararg values: Pair<String, String>)
    fun isGrantPermission(permission: String): Boolean
    fun requestPermission(permission: String)

    fun showConfirmDialog(title: String, description: String, continueBtnText: String, onContinue: () -> Unit)
    fun showErrorDialog(title: String?, description: String?, cancelButtonText: String, hasRepeatOption: Boolean, hasChatOption: Boolean, onCancel: () -> Unit, onShowChat: () -> Unit, onRepeat: () -> Unit)

    fun processBackButton(
        view: ViewUtils.HandleBackView,
        canHandleSource: Flow<Boolean>? = null
    ): Flow<Unit>

    class Impl(
        override val fragment: Fragment,
        override val screenVisibility: Flow<Boolean>
    ) : FragmentHolder {
        override val systemBackPressed = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        override val requestPermissionResult = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)

        private val activityResultLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { requestPermissionResult.tryEmit(it) }

        init {
            val backPressedDispatcherCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    systemBackPressed.tryEmit(Unit)
                }
            }

            fragment.requireActivity().onBackPressedDispatcher.addCallback(fragment, backPressedDispatcherCallback)
        }

        override fun navigateUp() {
            Navigation.findNavController(fragment.requireView()).navigateUp()
        }

        override fun setResult(key: String, vararg values: Pair<String, String>) {
            fragment.setFragmentResult(key, bundleOf(*values))
        }

        override fun isGrantPermission(permission: String): Boolean =
            ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED


        override fun requestPermission(permission: String) {
            activityResultLauncher.launch(permission)
        }

        override fun showErrorDialog(title: String?, description: String?, cancelButtonText: String, hasRepeatOption: Boolean, hasChatOption: Boolean, onCancel: () -> Unit, onShowChat: () -> Unit, onRepeat: () -> Unit) {
            showActionErrorDialog(
                context = fragment.context,
                layoutInflater = fragment.layoutInflater,
                title = title,
                description = description,
                cancelBtnText = cancelButtonText,
                hasRepeatOption = hasRepeatOption,
                showChatBtn = hasChatOption,
                onCancel = onCancel,
                onShowChat = onShowChat,
                onRepeat = onRepeat
            )
        }

        override fun showConfirmDialog(title: String, description: String, continueBtnText: String, onContinue: () -> Unit) {
            showActionSuccessDialog(
                context = fragment.context,
                layoutInflater = fragment.layoutInflater,
                title = title,
                description = description,
                continueBtnText = continueBtnText,
                onContinue = onContinue
            )
        }

        override fun processBackButton(view: ViewUtils.HandleBackView, canHandleSource: Flow<Boolean>?): Flow<Unit> =
            merge(view.onBackClicked, systemBackPressed)
                .run {
                    if (canHandleSource != null) {
                        withLatestFrom(canHandleSource) { _, canHandle -> canHandle.isTrue() }
                            .filterTrue()
                    } else {
                        this
                    }
                }
                .map { }
    }
}
