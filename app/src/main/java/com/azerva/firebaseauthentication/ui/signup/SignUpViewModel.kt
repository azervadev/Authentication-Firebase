package com.azerva.firebaseauthentication.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azerva.firebaseauthentication.core.utils.ErrorType
import com.azerva.firebaseauthentication.core.utils.Event
import com.azerva.firebaseauthentication.core.utils.ValidateFields
import com.azerva.firebaseauthentication.model.SignUp
import com.azerva.firebaseauthentication.usecases.SignUpUseCases
import com.azerva.firebaseauthentication.viewstate.SignUpViewState
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCases: SignUpUseCases,
    private val validateFields: ValidateFields

) : ViewModel() {

    var errorType : ErrorType? = null

    private val _viewState = MutableStateFlow(SignUpViewState())
    val viewState : StateFlow<SignUpViewState> get() = _viewState

    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin : LiveData<Event<Boolean>> get() = _navigateToLogin

    private var _showDialog = MutableLiveData(false)
    val showDialog : LiveData<Boolean> get() = _showDialog

    fun createAccount(signUp: SignUp){
        val viewSate = signUp.toViewState()
        if (viewSate.isValidFirebaseAccount() && signUp.isNotEmpty()){
            onCreateAccount(signUp)
        }else{
            showErrorDialog(ErrorType.ERROR_FIELDS)
        }

    }

    private fun onCreateAccount(signUp: SignUp){
        viewModelScope.launch {
            _viewState.value = SignUpViewState(isLoading = true)
            try {
                val accountCreated = signUpUseCases(signUp)
                if (accountCreated) {
                    _navigateToLogin.value = Event(true)
                }else{
                    showErrorDialog(ErrorType.ERROR_CREATE_ACCOUNT)
                }
            }catch (e: FirebaseAuthUserCollisionException){
                showErrorDialog(ErrorType.EXCEPTION_FIREBASE_ACCOUNT_EXIST)
                Log.e("RegisterViewModel","Error creating account: $e")
            }finally {
                _viewState.value = SignUpViewState(isLoading = false)
            }
        }
    }

    private fun SignUp.toViewState(): SignUpViewState {
        return SignUpViewState(
            isEmailValid = validateFields.isEmailValid(email),
            isPasswordValid = validateFields.isPasswordValid(password),
            isConfirmPasswordValid = validateFields.isConfirmPasswordValid(password, passwordConfirmation)
        )
    }


    // Método para mostrar el diálogo de error
    private fun showErrorDialog(errorType: ErrorType) {
        // Guarda el tipo de error
        this.errorType = errorType
        // Notifica al fragmento que debe mostrar el diálogo
        _showDialog.value = true
    }


    fun navigateToLogin(){
        _navigateToLogin.value = Event(true)
    }

}