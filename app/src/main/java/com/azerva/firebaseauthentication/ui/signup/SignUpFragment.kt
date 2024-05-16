package com.azerva.firebaseauthentication.ui.signup

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.azerva.firebaseauthentication.R
import com.azerva.firebaseauthentication.core.utils.ErrorType
import com.azerva.firebaseauthentication.core.utils.ShowErrorType
import com.azerva.firebaseauthentication.databinding.FragmentSignUpBinding
import com.azerva.firebaseauthentication.model.SignUp
import com.azerva.firebaseauthentication.ui.login.LoginTransaction
import com.azerva.firebaseauthentication.viewstate.SignUpViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }
    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater,container, false)
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
            btnBack.setOnClickListener { viewModel.navigateToLogin() }
            signUpBtnCreateAccount.setOnClickListener {
                viewModel.createAccount(
                    SignUp(
                        email = signUpEtEmail.text.toString(),
                        password = signUpEtPassword.text.toString(),
                        passwordConfirmation = signUpEtConfirmPassword.text.toString()
                    )
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

        viewModel.navigateToLogin.observe(viewLifecycleOwner){navigate->
            navigate.getContentIfNotHandled()?.let {
                navigateToLogin()
            }
        }

        viewModel.showDialog.observe(viewLifecycleOwner){dialog->
            if (dialog) showDialog()

            ShowErrorType().resetDialog()
        }

    }

    private fun showDialog() {
        when(viewModel.errorType) {

            ErrorType.ERROR_FIELDS-> showAlertDialog("The fields cannot be empty!!")
            ErrorType.ERROR_CREATE_ACCOUNT-> showAlertDialog("Error creating the account. please try again.")
            ErrorType.EXCEPTION_FIREBASE_ACCOUNT_EXIST -> showAlertDialog("The account you want to register already exists ")

            else-> return

        }
    }

    private fun showAlertDialog(message : String) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(message)
            .setPositiveButton("Accept"){dialog, _ ->
                dialog.dismiss()
            }

        builder.create().show()
    }

    private fun navigateToLogin() {
        LoginTransaction()
            .replace(this@SignUpFragment.requireActivity().supportFragmentManager, R.id.launcher_container)
    }

    private fun updateUI(viewState: SignUpViewState) {
        binding.signUpLottie.isVisible = viewState.isLoading
    }
}