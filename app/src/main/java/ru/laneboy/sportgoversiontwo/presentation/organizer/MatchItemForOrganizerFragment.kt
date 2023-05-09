package ru.laneboy.sportgoversiontwo.presentation.organizer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.laneboy.sportgoversiontwo.databinding.FragmentMatchItemForOrganizerBinding

class MatchItemForOrganizerFragment : Fragment() {

    private var _binding: FragmentMatchItemForOrganizerBinding? = null
    private val binding: FragmentMatchItemForOrganizerBinding
        get() = _binding ?: throw RuntimeException("FragmentMatchItemForOrganizerBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchItemForOrganizerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = MatchItemForOrganizerFragment()
    }
}