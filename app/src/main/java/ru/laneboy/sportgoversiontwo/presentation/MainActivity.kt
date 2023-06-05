package ru.laneboy.sportgoversiontwo.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.databinding.ActivityMainBinding
import ru.laneboy.sportgoversiontwo.presentation.gamesdiagram.GameDiagramFragment
import ru.laneboy.sportgoversiontwo.presentation.splash_screen.SplashScreenFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        removeStatusBar()
//        setFragment(SplashScreenFragment.newInstance())
//        setFragment(MatchesListForParticipantFragment.newInstance())
        setFragment(GameDiagramFragment.newInstance())
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