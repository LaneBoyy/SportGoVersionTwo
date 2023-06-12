package ru.laneboy.sportgoversiontwo.presentation.participant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.databinding.FragmentMatchesListBinding
import ru.laneboy.sportgoversiontwo.presentation.add_competition.AddCompetitionFragment
import ru.laneboy.sportgoversiontwo.presentation.add_request.AddRequestFragment
import ru.laneboy.sportgoversiontwo.presentation.game_diagram.GameDiagramFragment
import ru.laneboy.sportgoversiontwo.presentation.participant.adapter.MatchListAdapter
import ru.laneboy.sportgoversiontwo.presentation.participant.adapter.MatchListViewModel
import ru.laneboy.sportgoversiontwo.presentation.request_list.RequestListFragment
import ru.laneboy.sportgoversiontwo.util.gone
import ru.laneboy.sportgoversiontwo.util.initProgressBar
import ru.laneboy.sportgoversiontwo.util.showToast
import ru.laneboy.sportgoversiontwo.util.visible

class MatchListFragment : Fragment() {

    private var _binding: FragmentMatchesListBinding? = null
    private val binding: FragmentMatchesListBinding
        get() = _binding
            ?: throw RuntimeException("FragmentMatchesListForParticipantBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[MatchListViewModel::class.java]
    }

    private val dialog by lazy {
        initProgressBar(layoutInflater, requireContext())
    }


    private val adapter by lazy {
        MatchListAdapter(viewModel.isUser)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setUI()
    }

    private fun setObservers() {
        viewModel.matchList.observe(viewLifecycleOwner) { list ->
            binding.root.isRefreshing = false
            list.ifLoading {
                dialog.show()
            }.ifError {
                dialog.dismiss()
                showToast(it.message)
            }.ifSuccess {
                dialog.dismiss()
                if (it?.isNotEmpty() == true) {
                    binding.rvMatchList.visible()
                    binding.tvEmptyList.gone()
                    adapter.setList(it.reversed())
                } else {
                    binding.rvMatchList.gone()
                    binding.tvEmptyList.visible()
                }
            }
        }
    }

    private fun setUI() {
        binding.rvMatchList.adapter = adapter
        binding.root.setOnRefreshListener {
            viewModel.loadCompetitionList()
        }
        adapter.onItemClick = { competitionId ->
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_enter_left,
                    R.anim.slide_exit_left,
                    R.anim.slide_enter_right,
                    R.anim.slide_exit_right
                )
                .addToBackStack(null)
                .replace(
                    R.id.fragment_container_main,
                    GameDiagramFragment.newInstance(!viewModel.isUser,competitionId)
                ).commit()
        }
        if (viewModel.isUser) {
            //User
            binding.btnOpenAllRequests.visible()
            binding.btnAdd.gone()
            binding.btnOpenAllRequests.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_enter_left,
                        R.anim.slide_exit_left,
                        R.anim.slide_enter_right,
                        R.anim.slide_exit_right
                    )
                    .addToBackStack(null)
                    .replace(
                        R.id.fragment_container_main,
                        RequestListFragment.newInstance(isUserAccount = true, null)
                    )
                    .commit()
            }
            adapter.onButtonClick = { competitionId ->
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_enter_left,
                        R.anim.slide_exit_left,
                        R.anim.slide_enter_right,
                        R.anim.slide_exit_right
                    )
                    .addToBackStack(null)
                    .replace(
                        R.id.fragment_container_main,
                        AddRequestFragment.newInstance(competitionId)
                    ).commit()
            }
        } else {
            //Admin
            binding.btnOpenAllRequests.gone()
            binding.btnAdd.visible()
            binding.btnAdd.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_enter_left,
                        R.anim.slide_exit_left,
                        R.anim.slide_enter_right,
                        R.anim.slide_exit_right
                    )
                    .addToBackStack(null)
                    .replace(
                        R.id.fragment_container_main,
                        AddCompetitionFragment.newInstance()
                    ).commit()
            }
            adapter.onButtonClick = { competitionId ->
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_enter_left,
                        R.anim.slide_exit_left,
                        R.anim.slide_enter_right,
                        R.anim.slide_exit_right
                    )
                    .addToBackStack(null)
                    .replace(
                        R.id.fragment_container_main,
                        RequestListFragment.newInstance(isUserAccount = false, competitionId)
                    ).commit()
            }
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
        fun newInstance() = MatchListFragment()
    }
}