package br.com.heiderlopes.calculaflex.ui.base.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.heiderlopes.calculaflex.models.RequestState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class BaseAuthViewModel : ViewModel() {


    private val mAuth = FirebaseAuth.getInstance()

    val loggedState = MutableLiveData<RequestState<FirebaseUser>>()

    fun isLoggedIn() {

        val user = mAuth.currentUser

        user?.reload()

        if(user == null) {
            loggedState.value = RequestState.Error(Throwable("Usuário deslogado"))
        } else {
            loggedState.value = RequestState.Success(user)
        }
    }
}