package com.example.wonhoi_seoul_subway_info_app.presentation.stations

import com.example.wonhoi_seoul_subway_info_app.domain.Station
import com.example.wonhoi_seoul_subway_info_app.presentation.BasePresenter
import com.example.wonhoi_seoul_subway_info_app.presentation.BaseView

interface StationsContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndication()

        fun showStations(station: List<Station>)
    }

    interface Presenter : BasePresenter {
        fun filterStations(query : String)

        fun toggleStationFavorite(station:Station)
    }
}

