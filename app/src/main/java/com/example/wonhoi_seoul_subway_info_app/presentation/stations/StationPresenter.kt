package com.example.wonhoi_seoul_subway_info_app.presentation.stations

import com.example.wonhoi_seoul_subway_info_app.data.repository.StationRepository
import com.example.wonhoi_seoul_subway_info_app.domain.Station
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StationPresenter(
    private val view: StationsContract.View,
    private val stationRepository: StationRepository
) : StationsContract.Presenter {

    override val scope: CoroutineScope = MainScope()

    // 검색
    private val queryString: MutableStateFlow<String> = MutableStateFlow("")

    // 지하철 역
    private val stations: MutableStateFlow<List<Station>> = MutableStateFlow(emptyList())

    init {
        observeStation()
    }

    override fun onViewCreated() {
        scope.launch {
            view.showStations(stations.value)   // observeStation 에서 넣어준 value 값
            stationRepository.refreshStations()
        }
    }

    override fun onDestroyView() {

    }

    override fun filterStations(query: String) {
        scope.launch {
            queryString.emit(query) // 현재 옵저빙 중인 .combine(queryString) 쪽으로 값을 한번 더 방출한다.
        }
    }

    private fun observeStation() {
        stationRepository
            .stations // DB 에 저장된 지하철역
            .combine(queryString) { listStations, query ->  // combine 함수로 인해 먼저 방출된 value 값부터 combine 한다. (즉, '서' 만 처도 나오는 방식)
                if (query.isBlank()) {
                    listStations
                } else {
                    listStations.filter {
                        it.name.contains(query)  // query 에 있는 값이 포함된 값을 필터링한다.
                    }
                }
            }
            .onStart { view.showLoadingIndicator() }
            .onEach {
                if (it.isNotEmpty()) {
                    view.hideLoadingIndication()
                }
                stations.value = it
                view.showStations(it)
            }
            .catch {
                it.printStackTrace()
                view.hideLoadingIndication()
            }
            .launchIn(scope) // collect() 함. Main scope 를 사용하겠다는 것
    }

    override fun toggleStationFavorite(station: Station) {
        scope.launch {
            stationRepository.updateStation(station.copy(isFavorited = !station.isFavorited))
        }
    }
}