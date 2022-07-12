package com.example.duck.fastnotes.v2

import android.app.Activity
import android.widget.TextView
import com.example.duck.fastnotes.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.asFlow
import reactivecircus.flowbinding.android.widget.checkedChanges

object OrderNotesScreen {

    const val RESULT_KEY = "DeliverNotesScreen"

    interface ViewModel : BaseViewModel {
        class Impl(
            contractRef: ContractRef,
            private val view: View,
            private val fragmentHolder: FragmentHolder,
            private val dependency: OrderNoteDependency
        ) : ViewModel, BaseViewModel.Impl(Dispatchers.Main) {
            private companion object {
                private const val CHILD_DELIVERY_TYPE_SELECTION = "delivery_selection"
                private const val CHILD_DELIVERY_DETAILS = "delivery_details"
                private const val CHILD_NOTE_DETAILS = "note_details"
            }

            private val notesRepository: NotesRepository = dependency.provideNotesRepository()
            private val resources: Resources = dependency.provideResources()

            private val currentAddress = MutableSharedFlow<Address?>(replay = 1, extraBufferCapacity = 1)

            init {
                view.toolbarView.setTitle(resources.getString(R.string.note_title))

                view.orderButton.setEnabled(false)
                view.setContentVisibility(false)

                val data = combine(
                    notesRepository.loadDetailedCardContract(
                        contractId = contractRef.contractId,
                        cardId = contractRef.cardId ?: ""
                    ).asFlow()
                        .map { contract ->
                            contract.notes?.find { it.id == contractRef.noteId }?.let { note ->
                                NoteModel(contract, note, null).also {
                                    it.noteDetails = NoteDetailsModel(contract, card = note)
                                }
                            }
                        }
                        .catch { emit(null) },
                    notesHelper.getNote().asFlow()
                        .map { it.toNullable() }
                        .catch { emit(null) }
                ) { note, product ->
                    if (note != null && product != null) {
                        note to product
                    } else {
                        null
                    }
                }.shareIn(replay = 1)

                val note = data.map { it?.first }
                val product = data.map { it?.second }

                launch {
                    note.combine(userApiService.getDeliveryAddress()) { note, addresses -> note to addresses }
                        .onStart { view.setProgressVisibility(true) }
                        .collectLatest { (note, resultAddresses) ->

                            view.setProgressVisibility(false)

                            view.setContentVisibility(true)
                            if (resultCard != null && resultAddresses.isSuccess()) {
                                resultAddresses.doIfSuccess {
                                    when {
                                        it.home != null -> currentAddress.emit(it.home)
                                        it.default != null -> currentAddress.emit(it.default)
                                    }
                                }
                                addChild(CHILD_NOTE_DETAILS) {
                                    NoteView.ViewModel.Impl(note, view.noteView)
                                }
                            } else {
                                fragmentHolder.showErrorDialog(
                                    title = resources.getString(R.string.error),
                                    description = resources.getString(R.string.common_error),
                                    cancelButtonText = resources.getString(R.string.go_to_home),
                                    hasRepeatOption = false,
                                    hasChatOption = false,
                                    onCancel = {
                                        fragmentHolder.setResult(RESULT_KEY)
                                        fragmentHolder.navigateUp()
                                    },
                                    onShowChat = {},
                                    onRepeat = {}
                                )
                            }
                        }
                }

                launch { fragmentHolder.processBackButton(view.toolbarView).collect { fragmentHolder.navigateUp() } }

                val type = view.deliveryTypeSelection
                    .onStart { emit(View.DeliveryType.Post) }
                    .distinctUntilChanged().shareIn()

                val typeWithAddress =
                    combine(
                        type.distinctUntilChanged(),
                        currentLocation.onStart { emit(null) },
                        currentAddress.onStart { emit(null) }
                    ) { type, home, address -> Triple(type, home, address) }
                        .shareIn(replay = 1)

                launch {
                    typeWithAddress
                        .collect { (type, home, address) ->
                            when (type) {
                                View.DeliveryType.Home -> {
                                    view.orderButton.setEnabled(home != null)
                                    addChild(CHILD_DELIVERY_DETAILS) {
                                        DeliveryDescriptionView.ViewModel.OfficeImpl(
                                            view = view.deliveryView,
                                            office = home
                                        ).also { child ->
                                            child.launch { child.onEditClicked.collectLatest { selectLocation(home) } }
                                        }
                                    }
                                }
                                View.DeliveryType.Post -> {
                                    view.orderButton.setEnabled(address != null)
                                    addChild(CHILD_DELIVERY_DETAILS) {
                                        DeliveryDescriptionView.ViewModel.PostImpl(
                                            view = view.deliveryView,
                                            address = address
                                        ).also { child ->
                                            child.launch {
                                                child.onEditClicked.collectLatest { selectPostAddress(address) }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }

                launch {
                    view.orderButton.action
                        .withLatestFrom(typeWithAddress, note) { _, value, note ->
                            if (value != null && note != null) {
                                val (type, home, address) = value
                                val rbsCode = note.rbsCode
                                when {
                                    type == View.DeliveryType.Post && address != null -> {
                                        rbsCode to View.DeliveryType.Post(
                                            address = address
                                        )
                                    }
                                    type == View.DeliveryType.Home && home != null -> {
                                        code to DeliveryType.Home(home)
                                    }
                                    else -> null
                                }
                            } else null
                        }
                        .filterNotNull()
                        .onEach { view.setProgressVisibility(true) }
                        .flatMapLatest { (code, delivery) ->
                            notesRepository.orderNotes(
                                rbsCode = code,
                                deliveryType = delivery
                            )
                        }
                        .onEach {
                            view.setProgressVisibility(false)
                        }
                        .collectLatest { result ->
                            result.doActions(
                                success = {
                                    fragmentHolder.showConfirmDialog(
                                        title = resources.getString(R.string.success_note_order),
                                        description = resources.getString(R.string.success_note_message),
                                        continueBtnText = resources.getString(R.string.go_to_home),
                                        onContinue = {
                                            fragmentHolder.setResult(RESULT_KEY)
                                            fragmentHolder.navigateUp()
                                        }
                                    )
                                },
                                error = {
                                    fragmentHolder.showErrorDialog(
                                        title = resources.getString(R.string.failure_note_order),
                                        description = resources.getString(R.string.failure_order_error_message),
                                        cancelButtonText = resources.getString(R.string.go_to_card),
                                        hasRepeatOption = true,
                                        hasChatOption = true,
                                        onCancel = {
                                            fragmentHolder.setResult(RESULT_KEY)
                                            fragmentHolder.navigateUp()
                                        },
                                        onShowChat = {
                                            view.navigateToChat()
                                        },
                                        onRepeat = {}
                                    )
                                }
                            )
                        }
                }

                launch {
                    product.filterNotNull().collect { product ->
                        view.costValue.setText(
                            FormattingUtil.formatMoney(
                                amount = product.productConfig.physicalPlastic.cost,
                                currency = product.productConfig.physicalPlastic.currency
                            )
                        )
                    }
                }
            }

            private fun selectLocation(initialLocation: Location?) {
                addChild(CHILD_DELIVERY_TYPE_SELECTION) {
                    FindLocation.ViewModel.Impl(
                        view = view.createFindLocationview(),
                        currentOffice = initialLocation,
                        dependency = dependency,
                        fragmentHolder = fragmentHolder
                    ).also { child ->
                        child.launch {
                            child.onLocationSelected.collect { location ->
                                currentLocation.emit(location)
                                child.cancel()
                            }
                        }
                    }
                }
            }

            private fun selectPostAddress(initialAddress: Address?) {
                addChild(CHILD_DELIVERY_TYPE_SELECTION) {
                    FindPostAddress.ViewModel.Impl(
                        view = view.createFindPostAddressView(),
                        dependency = dependency,
                        address = initialAddress
                    ).also { child ->
                        child.launch {
                            child.onAddressSelected.collectLatest { address ->
                                currentAddress.emit(address)
                                child.cancel()
                            }
                        }
                    }
                }
            }
        }
    }

    interface View {
        val orderButton: ButtonView
        val toolbarView: ToolbarView
        val noteView: CardView.View
        val deliveryView: DeliveryDescriptionView.View
        val costValue: TextView
        val deliveryTypeSelection: Flow<DeliveryType>

        fun setProgressVisibility(visible: Boolean)
        fun setContentVisibility(visible: Boolean)
        fun navigateToChat()

        class Impl(private val binding: FragmentNoteDeliveryBinding) : View {
            override val orderButton: ButtonView = ButtonView.Impl(binding.orderButton)
            override val toolbarView: ToolbarView = ToolbarView.Impl(binding.toolbar)
            override val noteView: NoteView.View = NoteView.View.Impl(binding.noteView)
            override val costValue: TextView = TextView.Impl(binding.costValue)

            override val deliveryView: DeliveryDescriptionView.View = DeliveryDescriptionView.View.Impl(binding)

            override val deliveryTypeSelection: Flow<DeliveryType> = binding.deliveryTypeSelector
                .checkedChanges()
                .map { id -> if (id == R.id.delivery_type_office) DeliveryType.Home else DeliveryType.Post }

            override fun setProgressVisibility(visible: Boolean) {
                binding.progressLoading.setVisibleWithFade(visible)
            }

            override fun setContentVisibility(visible: Boolean) {
                binding.contentView.setVisibleWithFade(visible)
            }

            override fun navigateToChat() {
                NavigationHelper.startChatActivity(binding.rootView.context as Activity)
            }
        }

        enum class DeliveryType {
            Post, Home
        }
    }
}
