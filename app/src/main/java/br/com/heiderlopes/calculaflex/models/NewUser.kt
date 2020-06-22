package br.com.heiderlopes.calculaflex.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

data class NewUser(
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,
    @get:Exclude val password: String? = null
)
