package ru.laneboy.sportgoversiontwo.presentation.participant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.databinding.FragmentMatchesListForParticipantBinding
import ru.laneboy.sportgoversiontwo.util.gone
import ru.laneboy.sportgoversiontwo.util.showToast
import ru.laneboy.sportgoversiontwo.util.visible

class MatchesListForParticipantFragment : Fragment() {

    private var _binding: FragmentMatchesListForParticipantBinding? = null
    private val binding: FragmentMatchesListForParticipantBinding
        get() = _binding
            ?: throw RuntimeException("FragmentMatchesListForParticipantBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[MatchListParticipantViewModel::class.java]
    }

    private val adapter = MatchListAdapterForParticipant()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesListForParticipantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setUI()
    }
    private fun setObservers() {
        viewModel.participantList.observe(viewLifecycleOwner) {
            binding.root.isRefreshing = false
            if (it.isSuccessful) {
                val list = it.body()
                if (list?.isNotEmpty() == true) {
                    binding.rvMatchList.visible()
                    binding.tvEmptyList.gone()
                    adapter.setList(list.reversed())
                } else {
                    binding.rvMatchList.gone()
                    binding.tvEmptyList.visible()
                }
            } else {
                showToast("Error: ${it.errorBody()}")
            }
        }
    }

    private fun setUI() {
        binding.rvMatchList.adapter = adapter
        binding.root.setOnRefreshListener {
            viewModel.loadCompetitionList()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadCompetitionList()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = MatchesListForParticipantFragment()
    }
}