package com.skysam.hchirinos.circulalo.ui.profile

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.skysam.hchirinos.circulalo.R

class ProfileFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}