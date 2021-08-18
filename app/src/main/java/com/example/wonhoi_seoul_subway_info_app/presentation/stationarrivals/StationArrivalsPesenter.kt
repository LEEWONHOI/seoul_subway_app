package com.example.wonhoi_seoul_subway_info_app.presentation.stationarrivals

import com.example.wonhoi_seoul_subway_info_app.data.repository.StationRepository
import com.example.wonhoi_seoul_subway_info_app.domain.Station
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class StationArrivalsPesenter(
    private val view: StationArrivalsContract.View,
    private val station: Station,
    private val stationRepository: StationRepository
) : StationArrivalsContract.Presenter {

    override val scope = MainScope()

    override fun onViewCreated() {
        fetchStationArrivals()
    }

    override fun onDestroyView() {

    }

    override fun fetchStationArrivals() {
        scope.launch {
            try {
                view.showLoadingIndicator()
                view.showStationArrivals(stationRepository.getStationArrivals(station.name))
            } catch (exception : Exception) {
                exception.printStackTrace()
                view.showErrorDescription(exception.message ?: "An unknown problem has occurred.")
            } finally {
                view.hideLoadingIndicator()
            }
        }
    }

    override fun toggleStationFavorite() {
        scope.launch {
            stationRepository.updateStation(station.copy(isFavorited = !station.isFavorited))
        }
    }
}