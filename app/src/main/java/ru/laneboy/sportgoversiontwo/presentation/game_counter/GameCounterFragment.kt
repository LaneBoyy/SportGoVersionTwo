package ru.laneboy.sportgoversiontwo.presentation.game_counter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import ru.laneboy.sportgoversiontwo.data.network.responses.GameDiagramRequest
import ru.laneboy.sportgoversiontwo.databinding.FragmentGameCounterBinding
import ru.laneboy.sportgoversiontwo.domain.GameDiagram
import ru.laneboy.sportgoversiontwo.util.gone
import ru.laneboy.sportgoversiontwo.util.initProgressBar
import ru.laneboy.sportgoversiontwo.util.showToast
import ru.laneboy.sportgoversiontwo.util.visible

class GameCounterFragment : Fragment() {

    private var _binding: FragmentGameCounterBinding? = null
    private val binding: FragmentGameCounterBinding
        get() = _binding ?: throw RuntimeException("FragmentGameCounterBinding == null")

    private val dialog by lazy {
        initProgressBar(layoutInflater, requireContext())
    }

    private val gameDiagram by lazy {
        arguments?.getSerializable(GAME_DIAGRAM) as GameDiagram
    }

    private val viewModel by lazy {
        ViewModelProvider(this, GameViewModelFactory(gameDiagram))[GameCounterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        binding.root.setOnRefreshListener {
            viewModel.loadGame()
        }
    }

    private fun setObservers() {
        viewModel.game.observe(viewLifecycleOwner) { gameDiagramResource ->
            gameDiagramResource.ifLoading {
                dialog.show()
            }.ifError {
                dialog.dismiss()
                binding.root.isRefreshing = false
                showToast(it.message)
            }.ifSuccess { game ->
                binding.root.isRefreshing = false
                game?.let {
                    setUI(it)
                }
            }
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            it.ifLoading {
                dialog.show()
            }.ifError {
                dialog.dismiss()
                showToast(it.message)
            }.ifSuccess {
                dialog.dismiss()
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setUI(gameDiagram: GameDiagramRequest) {
        binding.tvTeamName1.text = gameDiagram.gameData!!.firstTeam
        binding.tvTeamName2.text = gameDiagram.gameData.secondTeam
        binding.tvCounterTeam1.text = gameDiagram.gameData.firstTeamScore.toString()
        binding.tvCounterTeam2.text = gameDiagram.gameData.secondTeamScore.toString()
        if (!gameDiagram.gameData.gameIsEnd) {
            binding.btnEndGame.visible()
            binding.btnTeamPlus1.setOnClickListener {
                viewModel.changeScore(true, true)
            }
            binding.btnTeamPlus2.setOnClickListener {
                viewModel.changeScore(false, true)

            }
            binding.btnTeamMinus1.setOnClickListener {
                viewModel.changeScore(true, false)

            }
            binding.btnTeamMinus2.setOnClickListener {
                viewModel.changeScore(false, false)
            }
            binding.btnEndGame.setOnClickListener {
                viewModel.endGame()
            }
        } else {
            binding.btnEndGame.gone()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {

        private const val GAME_DIAGRAM = "GAME_DIAGRAM"

        fun newInstance(gameDiagram: GameDiagram) = GameCounterFragment().apply {
            arguments = bundleOf(GAME_DIAGRAM to gameDiagram)
        }
    }
}