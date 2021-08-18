package com.example.wonhoi_seoul_subway_info_app.presentation.stationarrivals

import com.example.wonhoi_seoul_subway_info_app.domain.ArrivalInformation
import com.example.wonhoi_seoul_subway_info_app.presentation.BasePresenter
import com.example.wonhoi_seoul_subway_info_app.presentation.BaseView

interface StationArrivalsContract {

    interface View : BaseView<Presenter> {

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showErrorDescription(message:String)

        fun showStationArrivals(arrivalInformation : List<ArrivalInformation>)
    }

    interface Presenter : BasePresenter {

        fun fetchStationArrivals()

        fun toggleStationFavorite()

    }

}