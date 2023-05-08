package ru.laneboy.sportgoversiontwo.presentation.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.databinding.FragmentSignUpBinding
import ru.laneboy.sportgoversiontwo.presentation.fragments.AddRequestFragment
import ru.laneboy.sportgoversiontwo.presentation.organizer.MatchesListForOrganizerFragment
import ru.laneboy.sportgoversiontwo.presentation.participant.MatchesListForParticipantFragment
import ru.laneboy.sportgoversiontwo.util.initProgressBar

class SignUpFragment : Fragment() {

    private val dialog by lazy {
        initProgressBar(layoutInflater, requireContext())
    }

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpBinding == null")

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        observeViewModel()
        setClickOnButtonSignIn()
    }

    private fun observeViewModel() {
        viewModel.openOrganizerScreen.observe(viewLifecycleOwner) {
            when (it!!) {
                UserRole.LOADING -> {
                    dialog.show()
                }
                UserRole.ORGANIZER -> {
                    dialog.dismiss()
                    launchNextScreen(MatchesListForOrganizerFragment.newInstance())
                }
                UserRole.PARTICIPANT -> {
                    dialog.dismiss()
                    launchNextScreen(MatchesListForParticipantFragment.newInstance())
                }
                UserRole.ERROR -> {
                    dialog.dismiss()
                    Toast.makeText(context, "Error: ${it.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setClickOnButtonSignIn() {
        binding.btnSignUp.setOnClickListener {
            val roleId = if (binding.toggle.checkedRadioButtonId == R.id.participantRole0)
                0
            else
                1
            viewModel.signUp(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                roleId
            )
        }
    }

    private fun launchNextScreen(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_enter_left,
                R.anim.slide_exit_left,
                R.anim.slide_enter_right,
                R.anim.slide_exit_right
            )
            .replace(R.id.fragment_container_main, fragment)
            .commit()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }
}