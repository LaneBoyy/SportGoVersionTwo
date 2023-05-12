package ru.laneboy.sportgoversiontwo.presentation.organizer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONObject
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.databinding.FragmentMatchesListForOrganizerBinding
import ru.laneboy.sportgoversiontwo.presentation.add_competition.AddCompetitionFragment
import ru.laneboy.sportgoversiontwo.util.gone
import ru.laneboy.sportgoversiontwo.util.showToast
import ru.laneboy.sportgoversiontwo.util.visible

class MatchesListForOrganizerFragment : Fragment() {

    private var _binding: FragmentMatchesListForOrganizerBinding? = null
    private val binding: FragmentMatchesListForOrganizerBinding
        get() = _binding ?: throw RuntimeException("FragmentMatchesListForOrganizerBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[MatchListOrganizerViewModel::class.java]
    }

    private val adapter = MatchListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesListForOrganizerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setUI()
    }

    private fun setObservers() {
        viewModel.organizerList.observe(viewLifecycleOwner) {
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
        binding.linearLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(
                    R.anim.slide_enter_left,
                    R.anim.slide_exit_left,
                    R.anim.slide_enter_right,
                    R.anim.slide_exit_right
                )
                .replace(R.id.fragment_container_main, AddCompetitionFragment.newInstance())
                .commit()

        }
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
        fun newInstance() = MatchesListForOrganizerFragment()
    }
}