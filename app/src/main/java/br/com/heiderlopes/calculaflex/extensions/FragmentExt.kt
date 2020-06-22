package br.com.heiderlopes.calculaflex.extensions

import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showKeyboard() {
    view?.let { activity?.showKeyboard() }
}

