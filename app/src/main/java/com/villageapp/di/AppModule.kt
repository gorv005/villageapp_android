package com.villageapp.di


import com.villageapp.base.BaseRepository
import com.villageapp.managers.ActivityLifecycleManager
import com.villageapp.managers.PreferenceManager
import com.villageapp.ui.catageoryprodlist.ProductListRepository
import com.villageapp.ui.catageoryprodlist.ProductListViewModel
import com.villageapp.ui.dailyalerts.RepositoryDailyAlerts
import com.villageapp.ui.dailyalerts.ViewModelDailyAlerts
import com.villageapp.ui.home.HomeRepository
import com.villageapp.ui.home.HomeViewModel
import com.villageapp.ui.home.RepositoryMeal
import com.villageapp.ui.home.ViewModelMeal
import com.villageapp.ui.notification.RepositoryNotification
import com.villageapp.ui.notification.ViewModelNotification
import com.villageapp.ui.productdetail.ProductDetailRepository
import com.villageapp.ui.productdetail.ProductDetailViewModel
import com.villageapp.ui.saveditems.SavedProductListRepository
import com.villageapp.ui.saveditems.SavedProductListViewModel
import com.villageapp.ui.search.HomeSearchListRepository
import com.villageapp.ui.search.ViewModelHomeSearch
import com.villageapp.ui.splash.SplashViewModel
import com.villageapp.ui.user.password.RepositoryPassword
import com.villageapp.ui.user.password.ViewModelPassword
import com.villageapp.ui.user.profile.RepositoryProfile
import com.villageapp.ui.user.profile.ViewModelProfile
import com.villageapp.ui.user.register.LoginSignUpRepository
import com.villageapp.ui.user.register.LoginSignUpViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

val appModule = applicationContext {

    bean { PreferenceManager(androidApplication()) }

    bean { ActivityLifecycleManager() }

//     for splash activity
    viewModel { SplashViewModel(get()) }
    bean { BaseRepository() }


    // for Home  fragment
    viewModel { HomeViewModel(get()) } // get() will resolve Repository instance
    bean { HomeRepository() }


    viewModel { ProductListViewModel(get()) } // get() will resolve Repository instance
    bean { ProductListRepository() }

    viewModel { ProductDetailViewModel(get()) } // get() will resolve Repository instance
    bean { ProductDetailRepository() }

    viewModel { LoginSignUpViewModel(get()) } // get() will resolve Repository instance
    bean { LoginSignUpRepository() }


    viewModel { ViewModelProfile(get()) } // get() will resolve Repository instance
    bean { RepositoryProfile() }


    viewModel { ViewModelPassword(get()) } // get() will resolve Repository instance
    bean { RepositoryPassword() }

    viewModel { ViewModelMeal(get()) } // get() will resolve Repository instance
    bean { RepositoryMeal() }

    viewModel { ViewModelDailyAlerts(get()) } // get() will resolve Repository instance
    bean { RepositoryDailyAlerts() }

    viewModel { SavedProductListViewModel(get()) } // get() will resolve Repository instance
    bean { SavedProductListRepository() }

    viewModel { ViewModelHomeSearch(get()) } // get() will resolve Repository instance
    bean { HomeSearchListRepository() }

    viewModel { ViewModelNotification(get()) } // get() will resolve Repository instance
    bean { RepositoryNotification() }

}