package com.bazmashop.api

import com.bazmashop.BaseApp
import com.bazmashop.api.service_module.DashboardModule

object Api {

    fun dashboardModule(): DashboardModule {
        return BaseApp.retrofitInstance!!.create(DashboardModule::class.java)
    }
}
