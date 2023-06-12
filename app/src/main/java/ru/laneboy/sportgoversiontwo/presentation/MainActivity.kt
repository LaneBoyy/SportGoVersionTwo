package ru.laneboy.sportgoversiontwo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.fragment.app.Fragment
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.databinding.ActivityMainBinding
import ru.laneboy.sportgoversiontwo.presentation.game_diagram.GameDiagramFragment
import ru.laneboy.sportgoversiontwo.presentation.splash_screen.SplashScreenFragment
import kotlin.math.log2

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        removeStatusBar()
        Log.d("MainLog", "Round ${log2(0F)}")
        setFragment(SplashScreenFragment.newInstance())
//        setFragment(MatchesListForParticipantFragment.newInstance())
//        setFragment(GameDiagramFragment.newInstance())
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_main, fragment)
            .commit()
    }

    private fun removeStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}