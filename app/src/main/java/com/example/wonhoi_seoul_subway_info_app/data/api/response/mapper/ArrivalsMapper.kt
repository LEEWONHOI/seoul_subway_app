package com.example.wonhoi_seoul_subway_info_app.data.api.response.mapper

import com.example.wonhoi_seoul_subway_info_app.data.api.response.RealtimeArrival
import com.example.wonhoi_seoul_subway_info_app.domain.ArrivalInformation
import com.example.wonhoi_seoul_subway_info_app.domain.Subway
import java.text.SimpleDateFormat
import java.util.*


private val apiDateFormat = SimpleDateFormat("yyy-MM-dd HH:mm:ss.'0'", Locale.KOREA)
private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.KOREA)

private const val INVALID_FIELD = "-"

fun RealtimeArrival.toArrivalInformation(): ArrivalInformation =
    ArrivalInformation(
        subway = Subway.findById(subwayId),
        direction = trainLineNm?.split("-")
            ?.get(1)
            ?.trim()
            ?: INVALID_FIELD,
        destination = bstatnNm ?: INVALID_FIELD,
        message = arvlMsg2
            ?.replace(statnNm.toString(), "당역")
            ?.replace("[\\[\\]]".toRegex(), "")
            ?: INVALID_FIELD,
        updatedAt = recptnDt
            ?.let {
                apiDateFormat.parse(it)
            }
            ?.let {
                dateFormat.format(it)
            }
            ?: INVALID_FIELD
    )

fun List<RealtimeArrival>.toArrivalInformation(): List<ArrivalInformation> =
    map { realtimeArrival ->
        realtimeArrival.toArrivalInformation()
    }








