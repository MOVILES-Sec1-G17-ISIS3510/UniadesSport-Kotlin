package com.example.uniandessoport_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.uniandessoport_kotlin.ui.components.MainScaffold
import com.example.uniandessoport_kotlin.ui.theme.UniandesSoportKotlinTheme
import com.example.uniandessoport_kotlin.ui.theme.ThemeMode
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var themeMode by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(ThemeMode.SYSTEM) }
            UniandesSoportKotlinTheme(themeMode = themeMode) {
                MainScaffold(
                    themeMode = themeMode,
                    onThemeChange = { newTheme -> themeMode = newTheme }
                )
            }
        }
    }
}