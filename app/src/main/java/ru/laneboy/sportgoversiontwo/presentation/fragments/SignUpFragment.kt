package ru.laneboy.sportgoversiontwo.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.laneboy.sportgoversiontwo.R
import ru.laneboy.sportgoversiontwo.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickOnButtonSignIn()
    }

    private fun setClickOnButtonSignIn() {
        binding.btnSignIn.setOnClickListener {
            launchNextScreen()
        }
    }

    private fun launchNextScreen() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_enter_left,
                R.anim.slide_exit_left,
                R.anim.slide_enter_right,
                R.anim.slide_exit_right
            )
            .replace(R.id.fragment_container_main, AddRequestFragment.newInstance())
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