package com.example.duck.fastnotes.utils

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telecom.Call
import android.telecom.CallScreeningService
import android.telephony.TelephonyManager
import android.util.Log

class PhoneCallInterceptReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("DDebug", "Receiveng")
        val manager = context?.getSystemService(Service.TELEPHONY_SERVICE) as TelephonyManager
        if (manager.callState == TelephonyManager.CALL_STATE_RINGING)
            Log.d("DDebug", "Ringing, number = ${intent?.getStringExtra("incoming_number")}")
    }
}

// Service to intercept phone calls to get phone numbers
class PhoneNumberScreening : CallScreeningService() {

    companion object {
        const val TEL_PREFIX = "tel:"
    }

    override fun onScreenCall(callDetails: Call.Details) {
        Log.d("DDebug", "Phone Number = ${getPhoneNumber(callDetails)}")

        val phoneNumber = getPhoneNumber(callDetails)

        var response = CallResponse.Builder()
        response = handlePhoneCall(response, phoneNumber)

        respondToCall(callDetails, response.build())
    }

    private fun handlePhoneCall(
        response: CallResponse.Builder,
        phoneNumber: String
    ): CallResponse.Builder {
        response.apply {
            setRejectCall(true)
            setDisallowCall(true)
            setSkipCallLog(false)
        }
        return response
    }

    private fun getPhoneNumber(callDetails: Call.Details): String {
        return callDetails.handle.toString().removeTelPrefix().parseCountryCode()
    }

    private fun String.removeTelPrefix() = this.replace(TEL_PREFIX, "")

    private fun String.parseCountryCode(): String = Uri.decode(this)
}