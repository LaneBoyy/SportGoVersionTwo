package ru.laneboy.sportgoversiontwo.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.databinding.FragmentSignInBinding
import ru.laneboy.sportgoversiontwo.presentation.SignInViewModel
import ru.laneboy.sportgoversiontwo.presentation.sign_up.SignUpFragment

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding ?: throw RuntimeException("FragmentSignInBinding == null")

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        observeViewModel()
        setClickOnSignUpButton()
        setClickOnSignInButton()

    }

    private fun setClickOnSignInButton() {
        binding.btnSignIn.setOnClickListener {
            viewModel.signIn(binding.etEmail.text?.toString(), binding.etPassword.text?.toString())
        }
    }

    private fun setClickOnSignUpButton() {
        binding.startSignUp.setOnClickListener {
            launchSignUpFragment()
        }
    }

    private fun observeViewModel() {
        viewModel.openParticipantScreen.observe(viewLifecycleOwner) {
            launchAddRequestFragment()
        }
        viewModel.openOrganizerScreen.observe(viewLifecycleOwner) {

        }
    }

    private fun launchSignUpFragment() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_enter_left,
                R.anim.slide_exit_left,
                R.anim.slide_enter_right,
                R.anim.slide_exit_right
            )
            .replace(R.id.fragment_container_main, SignUpFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    private fun launchAddRequestFragment() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_enter_left, R.anim.slide_exit_left)
            .replace(R.id.fragment_container_main, AddRequestFragment.newInstance())
            .commit()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = SignInFragment()
    }
}