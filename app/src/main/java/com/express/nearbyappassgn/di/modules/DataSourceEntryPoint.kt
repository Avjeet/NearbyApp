package com.express.nearbyappassgn.di.modules

import com.express.nearbyappassgn.data.network.NearbyApiInterface
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DataSourceEntryPoint {
    var nearbyApiService: NearbyApiInterface
}