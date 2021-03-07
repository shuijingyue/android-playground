package android.playio.repository.entities

import android.os.Bundle
import androidx.fragment.app.Fragment

data class SampleFragmentInfo(var fragmentClass: Class<out Fragment?>, var extra: Bundle? = null) {
    override fun toString(): String {
        return fragmentClass.simpleName
    }
}