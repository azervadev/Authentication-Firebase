package com.azerva.firebaseauthentication.ui.login

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.azerva.firebaseauthentication.ui.home.HomeTransaction
import com.azerva.examplefirebaseauthmvc.ui.signup.SignUpTransaction
import com.azerva.firebaseauthentication.R
import com.azerva.firebaseauthentication.core.utils.ErrorType
import com.azerva.firebaseauthentication.core.utils.ShowErrorType
import com.azerva.firebaseauthentication.databinding.FragmentLoginBinding
import com.azerva.firebaseauthentication.viewstate.LoginViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater,container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        observers()
        listeners()
    }

    private fun listeners() {
        binding.apply {
            loginSignUp.setOnClickListener { viewModel.navigateSignUp() }

            loginBtnAccess.setOnClickListener {
                viewModel.onValidLogin(
                    email = loginEtEmail.text.toString(),
                    password = loginEtPassword.text.toString(),
                )
            }

        }
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect{viewState->
                updateUI(viewState)
            }
        }
        viewModel.navigateSignUp.observe(viewLifecycleOwner){navigate->
            navigate.getContentIfNotHandled()?.let {
                navigateToSignUp()
            }
        }
        viewModel.navigateHome.observe(viewLifecycleOwner){navigate->
            navigate.getContentIfNotHandled()?.let {
                navigateToHome()
            }
        }

        viewModel.showErrorDialog.observe(viewLifecycleOwner) { errorDialog ->
            if (errorDialog)
                showErrorDialog()

            ShowErrorType().resetDialog()
        }

    }

    private fun showErrorDialog() {
        when(viewModel.errorType){
            ErrorType.ERROR_REMOTE_ACCESS->
                showDialogMessage("Error authentication\nCheck fields data and try again.")
            ErrorType.ERROR_REMOTE_EXCEPTION_ACCESS ->
                showDialogMessage("Error connection to database.\nCheck your connection and try again. ")
            ErrorType.ERROR_FIELDS-> showDialogMessage("The fields cannot be empty!!")

            else -> return
        }
    }

    private fun showDialogMessage(message : String){
        val builder = AlertDialog.Builder(this@LoginFragment.requireActivity())
        builder.setMessage(message)
            .setPositiveButton("Accept"){dialog,_  ->
                dialog.dismiss()
            }

        builder.create().show()
    }

    private fun updateUI(viewState: LoginViewState) {
        binding.loginLottie.isVisible = viewState.isLoading
    }

    private fun navigateToHome(){
        HomeTransaction().launch(this@LoginFragment.requireActivity())
    }

    private fun navigateToSignUp(){
        SignUpTransaction().replace(
            this@LoginFragment.requireActivity().supportFragmentManager,
            R.id.launcher_container
        )
    }
}