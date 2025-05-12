package com.example.course_add_and_drop_manager_app.presentation.components

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

@Composable
internal fun SystemBackButtonHandler(
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    // Create a callback and remember it so it's not recreated on recomposition
    val callback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    // Update enabled status when recomposing
    DisposableEffect(dispatcher) {
        callback.isEnabled = enabled
        dispatcher?.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }
}
