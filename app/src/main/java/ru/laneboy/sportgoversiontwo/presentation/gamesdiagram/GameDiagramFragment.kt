package ru.laneboy.sportgoversiontwo.presentation.gamesdiagram

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import ru.laneboy.sportgoversiontwo.R
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnPause
import androidx.core.animation.doOnStart
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.laneboy.sportgoversiontwo.databinding.FragmentGameDiagramBinding
import ru.laneboy.sportgoversiontwo.presentation.gamesdiagram.view.GameDiagram
import ru.laneboy.sportgoversiontwo.util.gone
import ru.laneboy.sportgoversiontwo.util.initProgressBar
import ru.laneboy.sportgoversiontwo.util.invisible
import ru.laneboy.sportgoversiontwo.util.visible


class GameDiagramFragment : Fragment() {

    private var _binding: FragmentGameDiagramBinding? = null
    private val binding: FragmentGameDiagramBinding
        get() = _binding ?: throw RuntimeException("FragmentGameDiagramBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[GameDiagramViewModel::class.java]
    }

    private val dialog by lazy {
        initProgressBar(layoutInflater, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDiagramBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setUI()
    }

    private fun setObservers() {

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUI() {
        binding.zoom.gone()
        binding.diagramView.setGameDiagram(bigGame)
        setupAnimation {
            lifecycleScope.launch {
                dialog.show()
                delay(1000)
                dialog.dismiss()
                binding.flStartMatch.gone()
                binding.zoom.invisible()
                binding.zoom.post {
                    binding.zoom.moveTo(0.8f, 0f, 0f, false)
                    binding.zoom.visible()
                }
            }
        }
    }

    private fun setupAnimation(onAccept: (() -> Unit)) {
        var isCancel = false
        var isEnd = false
        val vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val scaleOut = AnimatorInflater.loadAnimator(requireContext(), R.animator.scale_out).apply {
            setTarget(binding.flStartMatchActive)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    binding.flStartMatchActive.visible()
                }

                override fun onAnimationEnd(animation: Animator) {
                    if (!isCancel) {
                        isEnd = true
                        vibrator.vibrate(500)
                        onAccept.invoke()
                    }
                    isCancel = false
                }

                override fun onAnimationCancel(animation: Animator) {
                    binding.flStartMatchActive.gone()
                    binding.flStartMatchActive.layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                    )
                }

                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
        binding.flStartMatch.setOnTouchListener { v, event ->
            if (event.action != MotionEvent.ACTION_UP && event.action != MotionEvent.ACTION_CANCEL) {
                if (!scaleOut.isRunning && !isEnd) {
                    scaleOut.start()
                }
            } else {
                isCancel = true
                scaleOut.cancel()
            }
            return@setOnTouchListener true
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = GameDiagramFragment()


        val bigGame = GameDiagram(
            firstTeam = "Vlad",
            secondTeam = "Rodion",
            previousTopMatch = GameDiagram(
                firstTeam = "Vlad",
                secondTeam = "Vanya",
                previousTopMatch = GameDiagram(
                    firstTeam = "Vlad",
                    secondTeam = "Misha",
                    previousTopMatch = GameDiagram(
                        firstTeam = "Vlad",
                        secondTeam = "Veronika",
                        previousTopMatch = null,
                        previousBottomMatch = null
                    ),
                    previousBottomMatch = GameDiagram(
                        firstTeam = "Misha",
                        secondTeam = "Valya",
                        previousTopMatch = null,
                        previousBottomMatch = null
                    )
                ),
                previousBottomMatch = GameDiagram(
                    firstTeam = "Vanya",
                    secondTeam = "Ilnar",
                    previousTopMatch = GameDiagram(
                        firstTeam = "Vanya",
                        secondTeam = "Sofya",
                        previousTopMatch = null,
                        previousBottomMatch = null
                    ),
                    previousBottomMatch = GameDiagram(
                        firstTeam = "Ilnar",
                        secondTeam = "Katya",
                        previousTopMatch = null,
                        previousBottomMatch = null
                    )
                )
            ),
            previousBottomMatch = GameDiagram(
                "Rodion",
                "Sasha",
                previousTopMatch = GameDiagram(
                    "Vlad Kuz.",
                    "Rodion",
                    previousTopMatch = GameDiagram(
                        firstTeam = "Vlad Kuz.",
                        secondTeam = "Masha",
                        previousTopMatch = null,
                        previousBottomMatch = null
                    ),
                    previousBottomMatch = GameDiagram(
                        firstTeam = "Rodion",
                        secondTeam = "Masha",
                        previousTopMatch = null,
                        previousBottomMatch = null
                    )
                ),
                previousBottomMatch = GameDiagram(
                    "Sasha",
                    secondTeam = "Ilnaz",
                    previousTopMatch = GameDiagram(
                        firstTeam = "Ilnaz",
                        secondTeam = "Nastya",
                        previousTopMatch = null,
                        previousBottomMatch = null
                    ),
                    previousBottomMatch = GameDiagram(
                        firstTeam = "Sasha",
                        secondTeam = "Lera",
                        previousTopMatch = null,
                        previousBottomMatch = null
                    )
                )
            )
        )

        val middleGame = GameDiagram(
            firstTeam = "Vlad",
            secondTeam = "Rodion",
            previousTopMatch = GameDiagram(
                firstTeam = "Vlad",
                secondTeam = "Misha",
                previousTopMatch = GameDiagram(
                    firstTeam = "Vlad",
                    secondTeam = "Veronika",
                    previousTopMatch = null,
                    previousBottomMatch = null
                ),
                previousBottomMatch = GameDiagram(
                    firstTeam = "Misha",
                    secondTeam = "Valya",
                    previousTopMatch = null,
                    previousBottomMatch = null
                )
            ),
            previousBottomMatch = GameDiagram(
                firstTeam = "Rodion",
                secondTeam = "Vanya",
                previousTopMatch = GameDiagram(
                    firstTeam = "Rodion",
                    secondTeam = "Masha",
                    previousTopMatch = null,
                    previousBottomMatch = null
                ),
                previousBottomMatch = GameDiagram(
                    firstTeam = "Vanya",
                    secondTeam = "Sofya",
                    previousTopMatch = null,
                    previousBottomMatch = null
                )
            )
        )
        val game = GameDiagram(
            firstTeam = "Vlad",
            secondTeam = "Misha",
            previousTopMatch = GameDiagram(
                firstTeam = "Vlad",
                secondTeam = "Veronika",
                previousTopMatch = null,
                previousBottomMatch = null
            ),
            previousBottomMatch = GameDiagram(
                firstTeam = "Misha",
                secondTeam = "Valya",
                previousTopMatch = null,
                previousBottomMatch = null
            )
        )
    }
}