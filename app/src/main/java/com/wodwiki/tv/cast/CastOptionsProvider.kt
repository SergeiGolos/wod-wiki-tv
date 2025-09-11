package com.wodwiki.tv.cast

import android.content.Context
import com.google.android.gms.cast.framework.CastOptions
import com.google.android.gms.cast.framework.OptionsProvider
import com.google.android.gms.cast.framework.SessionProvider
import timber.log.Timber

class CastOptionsProvider : OptionsProvider {
    
    override fun getCastOptions(context: Context): CastOptions {
        Timber.d("Setting up Cast options")
        
        return CastOptions.Builder()
            .setReceiverApplicationId("CC1AD845") // Default Cast receiver app ID
            .build()
    }
    
    override fun getAdditionalSessionProviders(context: Context): List<SessionProvider>? {
        return null
    }
}