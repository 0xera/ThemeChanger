package ru.aydarov.themechanger.sample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.aydarov.themechanger.ThemeChanger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkTheme()) {
            setTheme(R.style.ThemeLight)
        } else {
            setTheme(R.style.ThemeDark)
        }

        setContentView(R.layout.activity_main)

        btnChange.setOnTouchListener { _, event ->
            val action = ThemeChanger.prepareToChange(this, event)
            if (action) {
                if (checkTheme())
                    updateTheme(false)
                else
                    updateTheme(true)
            }
            action
        }

    }

    private fun checkTheme() =
            getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).getBoolean(THEME_KEY, true)

    private fun updateTheme(light: Boolean) =
            getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE).edit().putBoolean(
                    THEME_KEY,
                    light
            ).apply()

    companion object {
        private const val THEME_KEY = "theme_key"
        private const val PREFERENCE_NAME = "preference_theme"
    }
}

