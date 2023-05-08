package ru.laneboy.sportgoversiontwo.presentation.participant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.laneboy.sportgoversiontwo.databinding.FragmentMatchesListForParticipantBinding

class MatchesListForParticipantFragment : Fragment() {

    private var _binding: FragmentMatchesListForParticipantBinding? = null
    private val binding: FragmentMatchesListForParticipantBinding
        get() = _binding
            ?: throw RuntimeException("FragmentMatchesListForParticipantBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesListForParticipantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = MatchesListForParticipantFragment()
    }
}