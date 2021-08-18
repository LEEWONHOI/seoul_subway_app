package com.example.wonhoi_seoul_subway_info_app.di

import android.app.Activity
import com.example.wonhoi_seoul_subway_info_app.data.api.StationApi
import com.example.wonhoi_seoul_subway_info_app.data.api.StationArrivalsApi
import com.example.wonhoi_seoul_subway_info_app.data.api.StationStorageApi
import com.example.wonhoi_seoul_subway_info_app.data.api.Url
import com.example.wonhoi_seoul_subway_info_app.data.db.AppDatabase
import com.example.wonhoi_seoul_subway_info_app.data.preference.PreferenceManager
import com.example.wonhoi_seoul_subway_info_app.data.preference.SharedPreferenceManager
import com.example.wonhoi_seoul_subway_info_app.data.repository.StationRepository
import com.example.wonhoi_seoul_subway_info_app.data.repository.StationRepositorylmpl
import com.example.wonhoi_seoul_subway_info_app.presentation.stationarrivals.StationArrivalsContract
import com.example.wonhoi_seoul_subway_info_app.presentation.stationarrivals.StationArrivalsFragment
import com.example.wonhoi_seoul_subway_info_app.presentation.stationarrivals.StationArrivalsPesenter
import com.example.wonhoi_seoul_subway_info_app.presentation.stations.StationPresenter
import com.example.wonhoi_seoul_subway_info_app.presentation.stations.StationsContract
import com.example.wonhoi_seoul_subway_info_app.presentation.stations.StationsFragment
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {

    single { Dispatchers.IO }

    // DataBase
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().stationDao() }

    // Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }




    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if(BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    // Api (seoul)
    single<StationArrivalsApi> {
        Retrofit.Builder().baseUrl(Url.SEOUL_DATA_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }

    // Api (Firebase)
    single<StationApi> { StationStorageApi(Firebase.storage) }

    // Repository
    single<StationRepository> { StationRepositorylmpl(get(), get(), get(), get(), get()) }

    // Presentation
    // scope 는 서로의 연결을 강하여 묶어준다. 서로 공유하기 쉬움
    // 그리고 StationsFragment 의 라이프 사이클이 종료되면 연결되었던 StationsContract.Presenter 도 사용 중지 (메모리 관리에도 유리 )
    // single 의 라이프사이클은 앱 전체
    scope<StationsFragment> {
        scoped<StationsContract.Presenter> { StationPresenter(getSource(), get()) }
    }

    scope<StationArrivalsFragment> {
        scoped<StationArrivalsContract.Presenter> { StationArrivalsPesenter(getSource(), get(), get()) }
    }

}




