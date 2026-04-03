package com.nimo.nimons360.core.utils

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.nimo.nimons360.R

object Constants {
    const val BASE_URL = "https://mad.labpro.hmif.dev"
    const val WS_URL = "https://mad.labpro.hmif.dev/ws/live"
    const val PREF_NAME = "nimons_pref"
    const val AUTH_TOKEN = "auth_token"
}
object StringKeys {
    @StringRes val APP_NAME: Int = R.string.login_app_name
    @StringRes val TAGLINE: Int = R.string.login_tagline
    @StringRes val HINT_EMAIL: Int = R.string.login_hint_email
    @StringRes val HINT_PASSWORD: Int = R.string.login_hint_password
    @StringRes val BTN_SIGN_IN: Int = R.string.login_button_sign_in
    @StringRes val BTN_SHOW_PASSWORD: Int = R.string.login_button_show_password
    @StringRes val BTN_HIDE_PASSWORD: Int = R.string.login_button_hide_password
    @StringRes val LOADING: Int = R.string.login_loading

    // Validation
    @StringRes val ERROR_EMAIL_EMPTY: Int = R.string.login_error_email_empty
    @StringRes val ERROR_EMAIL_INVALID: Int = R.string.login_error_email_invalid
    @StringRes val ERROR_PASSWORD_EMPTY: Int = R.string.login_error_password_empty
    @StringRes val ERROR_PASSWORD_SHORT: Int = R.string.login_error_password_too_short
    @StringRes val ERROR_INVALID_CREDENTIALS: Int = R.string.login_error_invalid_credentials
    @StringRes val ERROR_NETWORK: Int = R.string.login_error_network
    @StringRes val ERROR_GENERIC: Int = R.string.login_error_generic
}

object ColorKeys {
    @ColorRes val PRIMARY: Int = R.color.color_primary
    @ColorRes val PRIMARY_MEDIUM: Int = R.color.color_primary_medium
    @ColorRes val PRIMARY_LIGHT: Int = R.color.color_primary_light
    @ColorRes val PRIMARY_PALE: Int = R.color.color_primary_pale
    @ColorRes val PRIMARY_SURFACE: Int = R.color.color_primary_surface
    @ColorRes val BACKGROUND: Int = R.color.color_background
    @ColorRes val SURFACE: Int = R.color.color_surface
    @ColorRes val ON_PRIMARY: Int = R.color.color_on_primary
    @ColorRes val ON_BACKGROUND: Int = R.color.color_on_background
    @ColorRes val ON_SURFACE_SECONDARY: Int = R.color.color_on_surface_secondary
    @ColorRes val ON_SURFACE_TERTIARY: Int = R.color.color_on_surface_tertiary
    @ColorRes val DIVIDER: Int = R.color.color_divider
    @ColorRes val ERROR: Int = R.color.color_error
}