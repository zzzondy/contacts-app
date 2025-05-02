package com.contactsapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp

data class ContactsAppPaddings(
    val default: Dp,
    val extraExtraSmall: Dp,
    val extraSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
)

data class ContactsAppSizes(
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val extraExtraLarge: Dp,
    val small: Dp,
    val extraSmall: Dp,
)

object ContactsAppTheme {

    val paddings: ContactsAppPaddings
        @Composable
        get() = LocalContactsAppPaddings.current

    val sizes: ContactsAppSizes
        @Composable
        get() = LocalContactsAppSizes.current
}

val LocalContactsAppPaddings =
    staticCompositionLocalOf<ContactsAppPaddings> { error("No paddings provided") }

val LocalContactsAppSizes =
    staticCompositionLocalOf<ContactsAppSizes> { error("No sizes provided") }