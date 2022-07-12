package com.example.duck.fastnotes.v2

import com.example.duck.fastnotes.R
import kotlinx.coroutines.Dispatchers

object NoteView {
    interface ViewModel : BaseViewModel {
        class Impl(
            view: View
        ) : ViewModel, BaseViewModel.Impl(Dispatchers.Main) {
            init {
                view.setName(noteModel.titleLame)
                view.setSkin(noteModel.getNoteSkinUrl())
                view.setDate(noteModel.date)
            }
        }
    }

    interface View {
        fun setName(name: String)
        fun setDate(date: String)
        fun setNoteType(type: String)
        fun setSkin(skin: String)

        class Impl(private val binding: NoteModelItemBinding) : View {
            override fun setName(name: String) {
                binding.noteTitle.text = name
            }

            override fun setDate(date: String) {
                binding.noteDateTitle.text = date
            }

            override fun setNoteType(type: String) {
                binding.noteType.text = type
            }

            override fun setSkin(skin: String) {
                binding.noteBackground.noteSkin(
                    skin,
                    cornerRadius = 6,
                    defDrawable = R.drawable.note_item_skin
                )
            }
        }
    }
}
