package ru.aydarov.themechanger.sample

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.aydarov.themechanger.ThemeChanger

class MainActivity : AppCompatActivity() {
    private val onTouchListener = View.OnTouchListener { _, event ->
        val action = ThemeChanger.prepareToChange(this, event, 1000)
        if (action) {
            if (checkTheme())
                updateTheme(false)
            else
                updateTheme(true)
        }
        action
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkTheme()) {
            setTheme(R.style.ThemeLight)
        } else {
            setTheme(R.style.ThemeDark)
        }

        setContentView(R.layout.activity_main)

        setListeners()

    }

    private fun setListeners() {
        btnChange1.setOnTouchListener(onTouchListener)
        btnChange2.setOnTouchListener(onTouchListener)
        btnChange3.setOnTouchListener(onTouchListener)
        btnChange4.setOnTouchListener(onTouchListener)
        btnChange5.setOnTouchListener(onTouchListener)
        btnChange6.setOnTouchListener(onTouchListener)
        btnChange7.setOnTouchListener(onTouchListener)
        btnChange8.setOnTouchListener(onTouchListener)
        btnChange9.setOnTouchListener(onTouchListener)
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

