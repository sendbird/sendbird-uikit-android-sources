package com.sendbird.uikit.samples.common.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.sendbird.uikit.samples.R
import com.sendbird.uikit.samples.common.extensions.getDrawable
import com.sendbird.uikit.samples.common.extensions.isUsingDarkTheme
import com.sendbird.uikit.samples.common.extensions.setAppearance
import com.sendbird.uikit.samples.common.preferences.PreferenceUtils
import com.sendbird.uikit.samples.databinding.ViewCustomTabBinding

/**
 * View displaying icon and badge in tabs.
 */
class CustomTabView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var tintColorRedId = 0
    private val binding: ViewCustomTabBinding

    init {
        val inflater = LayoutInflater.from(getContext())
        binding = ViewCustomTabBinding.inflate(inflater, this, true).apply {
            val isDarkMode = PreferenceUtils.themeMode.isUsingDarkTheme()
            tintColorRedId = if (isDarkMode) R.drawable.selector_tab_tint_dark else R.drawable.selector_tab_tint
            val badgeTextAppearance =
                if (isDarkMode) R.style.SendbirdCaption3OnLight01 else R.style.SendbirdCaption3OnDark01
            val badgeBackgroundRes =
                if (isDarkMode) R.drawable.shape_badge_background_dark else R.drawable.shape_badge_background
            val titleTextAppearance =
                if (isDarkMode) R.style.SendbirdCaption2Primary200 else R.style.SendbirdCaption2Primary300
            badge.setAppearance(context, badgeTextAppearance)
            badge.setBackgroundResource(badgeBackgroundRes)
            title.setAppearance(context, titleTextAppearance)
            title.setTextColor(AppCompatResources.getColorStateList(context, tintColorRedId))
        }
    }

    fun setBadgeVisibility(visibility: Int) {
        binding.badge.visibility = visibility
    }

    fun setBadgeCount(countString: String?) {
        binding.badge.text = countString
    }

    fun setIcon(@DrawableRes iconResId: Int) {
        binding.icon.setImageDrawable(context.getDrawable(iconResId, tintColorRedId))
    }

    fun setTitle(title: String?) {
        binding.title.text = title
    }
}
