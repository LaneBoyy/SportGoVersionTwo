package ru.laneboy.sportgoversiontwo.presentation.add_competition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.laneboy.sportgoversiontwo.databinding.FragmentAddCompetitionBinding
import ru.laneboy.sportgoversiontwo.util.initProgressBar
import ru.laneboy.sportgoversiontwo.util.showToast

class AddCompetitionFragment : Fragment() {

    private var _binding: FragmentAddCompetitionBinding? = null
    private val binding: FragmentAddCompetitionBinding
        get() = _binding ?: throw RuntimeException("FragmentAddCompetitionBinding == null")

    private val dialog by lazy {
        initProgressBar(layoutInflater, requireContext())
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[AddCompetitionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCompetitionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.competition.observe(viewLifecycleOwner) { competition ->
            competition.ifLoading {
                dialog.show()
            }.ifError {
                dialog.dismiss()
                showToast(it.message)
            }.ifSuccess {
                dialog.dismiss()
                showToast("Успешно")
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

        }
        binding.btnPublish.setOnClickListener {
            viewModel.addCompetition(
                binding.etMatchName.text?.toString(),
                binding.etDescription.text?.toString(),
                binding.etMatchType.text?.toString(),
                binding.etDate.text?.toString()
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = AddCompetitionFragment()
    }
}