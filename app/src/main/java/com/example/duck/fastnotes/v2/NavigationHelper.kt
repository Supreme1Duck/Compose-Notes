package com.example.duck.fastnotes.v2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

object NavigationHelper {

    fun startCardStatementActivity(context: FragmentActivity?, contract: SimpleNoteModel) {
        context?.let {
            val intent = Intent(context, StatementActivity::class.java).also {
                it.putExtra(StatementActivity.EXTRA_CONTRACT, contract)
            }
            context.startActivity(intent)
        }
    }

    fun startAboutAppActivity(
        context: FragmentActivity?,
        needAppVersion: Boolean = false,
        callFromAuthenticatedScope: Boolean = true
    ) {
        context?.let {
            val intent = Intent(context, AboutAppActivity::class.java).also {
                it.putExtra(AboutAppActivity.EXTRA_NEED_APP_VERSION, needAppVersion)
                it.putExtra(BaseActivity.EXTRA_IS_AUTHENTICATED_SCOPE, callFromAuthenticatedScope)
            }
            context.startActivity(intent)
        }
    }

    fun startPdfActivity(
        context: Context?,
        url: String,
        callFromAuthenticatedScope: Boolean = true,
        fileName: String? = null
    ) {
        context?.let {
            val intent = Intent(context, PdfActivity::class.java).also {
                it.putExtra(PdfActivity.EXTRA_PATH, url)
                it.putExtra(PdfActivity.EXTRA_FILENAME, fileName)
                it.putExtra(BaseActivity.EXTRA_IS_AUTHENTICATED_SCOPE, callFromAuthenticatedScope)
            }
            context.startActivity(intent)
        }
    }

    fun startSettingsActivity(context: FragmentActivity?) {
        startActivity(context, SettingsActivity::class.java)
    }

    fun startChatActivity(context: Activity?, args: Bundle? = null) {
        startActivity(context, ChatActivity::class.java)
    }

    private fun <T> startActivity(context: Activity?, cls: Class<T>, args: Bundle? = null) {
        context?.let {
            val intent = Intent(context, cls)
            args?.let { notNullArgs -> intent.putExtras(notNullArgs) }
            context.startActivity(intent)
        }
    }

    fun startPdfActivityWithEncoded(context: Context?, encoded: String,  callFromAuthenticatedScope: Boolean = true, fileName: String?) {
        context?.let {
            val intent = Intent(context, PdfActivity::class.java).also {
                it.putExtra(PdfActivity.EXTRA_BYTES, encoded)
                it.putExtra(PdfActivity.EXTRA_FILENAME, fileName)
                it.putExtra(BaseActivity.EXTRA_IS_AUTHENTICATED_SCOPE, callFromAuthenticatedScope)
            }
            context.startActivity(intent)
        }
    }

}
