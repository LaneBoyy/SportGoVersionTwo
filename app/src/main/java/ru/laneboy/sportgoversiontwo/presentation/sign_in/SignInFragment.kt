package ru.laneboy.sportgoversiontwo.presentation.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.databinding.FragmentSignInBinding
import ru.laneboy.sportgoversiontwo.presentation.fragments.AddRequestFragment
import ru.laneboy.sportgoversiontwo.presentation.organizer.MatchesListForOrganizerFragment
import ru.laneboy.sportgoversiontwo.presentation.participant.MatchesListForParticipantFragment
import ru.laneboy.sportgoversiontwo.presentation.sign_up.SignUpFragment
import ru.laneboy.sportgoversiontwo.presentation.sign_up.UserRole
import ru.laneboy.sportgoversiontwo.util.initProgressBar

class SignInFragment : Fragment() {

    private val dialog by lazy {
        initProgressBar(layoutInflater, requireContext())
    }

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

    private fun launchNextScreen(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_enter_left, R.anim.slide_exit_left)
            .replace(R.id.fragment_container_main, fragment)
            .commit()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = SignInFragment()
        private const val ERROR_NOT_INTERNET_STRING =
            "Отсутствует подключение к интернету. Проверьте соединение и попробуйте снова"
    }
}