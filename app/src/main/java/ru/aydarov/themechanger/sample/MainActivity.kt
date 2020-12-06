package ru.aydarov.themechanger.sample

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.android.synthetic.main.activity_main.*
import ru.aydarov.themechanger.ThemeChanger
import ru.aydarov.themechanger.ThemeChanger.changeTheme

class MainActivity : AppCompatActivity() {

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
        val inAminOnClickListener = View.OnClickListener { view ->
            changeTheme(view, 1000) {
                if (checkTheme()) updateTheme(false)
                else updateTheme(true)
            }
        }
        btnChange1.setOnClickListener(inAminOnClickListener)
        btnChange2.setOnClickListener(inAminOnClickListener)
        btnChange3.setOnClickListener(inAminOnClickListener)
        btnChange4.setOnClickListener(inAminOnClickListener)
        btnChange5.setOnClickListener(inAminOnClickListener)
        btnChange6.setOnClickListener(inAminOnClickListener)
        btnChange7.setOnClickListener(inAminOnClickListener)
        btnChange8.setOnClickListener(inAminOnClickListener)
        btnChange9.setOnClickListener(inAminOnClickListener)
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

