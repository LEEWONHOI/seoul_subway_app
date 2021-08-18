package com.example.wonhoi_seoul_subway_info_app.presentation

interface BaseView<PresenterT : BasePresenter> {

    val presenter : PresenterT

}