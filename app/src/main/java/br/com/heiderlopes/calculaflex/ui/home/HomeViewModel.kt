package br.com.heiderlopes.calculaflex.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.heiderlopes.calculaflex.extensions.fromRemoteConfig
import br.com.heiderlopes.calculaflex.models.RequestState
import br.com.heiderlopes.calculaflex.models.dashboardmenu.DashboardItem
import br.com.heiderlopes.calculaflex.models.dashboardmenu.DashboardMenu
import br.com.heiderlopes.calculaflex.utils.featuretoggle.FeatureToggleHelper
import br.com.heiderlopes.calculaflex.utils.featuretoggle.FeatureToggleListener
import br.com.heiderlopes.calculaflex.utils.firebase.RemoteConfigKeys
import br.com.heiderlopes.calculaflex.utils.firebase.RemoteConfigUtils
import com.google.gson.Gson

class HomeViewModel : ViewModel() {


    val menuState = MutableLiveData<RequestState<List<DashboardItem>>>()

    fun createMenu() {
        menuState.value = RequestState.Loading

        RemoteConfigUtils.fetchAndActivate()
            .addOnCompleteListener {
                val dashboardMenu =
                    Gson().fromRemoteConfig(
                        RemoteConfigKeys.MENU_DASHBOARD,
                        DashboardMenu::class.java
                    )

                val dashBoardItems = arrayListOf<DashboardItem>()

                val itemsMenu = dashboardMenu?.items ?: listOf()

                for (itemMenu in itemsMenu) {
                    FeatureToggleHelper().configureFeature(
                        itemMenu.feature,
                        object : FeatureToggleListener {
                            override fun onEnabled() {
                                dashBoardItems.add(itemMenu)
                            }

                            override fun onInvisible() {}

                            override fun onDisabled(clickListener: (Context) -> Unit) {
                                itemMenu.onDisabledListener = clickListener
                                dashBoardItems.add(itemMenu)
                            }
                        })
                }
                menuState.value = RequestState.Success(dashBoardItems)
            }
    }
}
