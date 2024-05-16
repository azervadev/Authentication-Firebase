package com.azerva.firebaseauthentication.ui.login

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azerva.firebaseauthentication.core.utils.ErrorType
import com.azerva.firebaseauthentication.core.utils.Event
import com.azerva.firebaseauthentication.data.LoginResponse
import com.azerva.firebaseauthentication.usecases.LoginUseCases
import com.azerva.firebaseauthentication.viewstate.LoginViewState
import com.google.firebase.FirebaseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases,
): ViewModel() {

    var errorType : ErrorType? = null

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState : StateFlow<LoginViewState> get() = _viewState

    private val _navigateSignUp = MutableLiveData<Event<Boolean>>()
    val navigateSignUp : LiveData<Event<Boolean>> get() = _navigateSignUp

    private val _navigateHome = MutableLiveData<Event<Boolean>>()
    val navigateHome : LiveData<Event<Boolean>> get() = _navigateHome

    private var _showError = MutableLiveData(false)
    val showErrorDialog: LiveData<Boolean> get() = _showError

    fun onValidLogin(email :String, password :String){
        if (email.isNotEmpty() && password.isNotEmpty()){
            onLogin(email, password)
        }else{
            showErrorDialog(ErrorType.ERROR_FIELDS)
        }
    }

    private fun onLogin(email :String, password :String){
        viewModelScope.launch {
            _viewState.value = LoginViewState(isLoading = true)
            try {
                when(loginUseCases(
                    email = email, password = password)){
                    LoginResponse.Error -> showErrorDialog(ErrorType.ERROR_REMOTE_ACCESS)
                    is LoginResponse.Success-> _navigateHome.value = Event(content = false)
                }
            }catch (e: FirebaseException){
                showErrorDialog(ErrorType.ERROR_REMOTE_EXCEPTION_ACCESS)
            }finally {
                _viewState.value = LoginViewState(isLoading = false)
            }
        }
    }

    private fun showErrorDialog(errorType: ErrorType) {
        // Guarda el tipo de error
        this.errorType = errorType
        // Notifica al fragmento que debe mostrar el diálogo
        _showError.value = true
    }

    fun navigateSignUp(){
        _navigateSignUp.value = Event(content = true)
    }

}