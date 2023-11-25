package com.express.nearbyappassgn.di.modules

import com.express.nearbyappassgn.data.HomeVenueRepository
import com.express.nearbyappassgn.data.HomeVenueRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindHomeVenueRepository(homeGifRepository: HomeVenueRepositoryImpl): HomeVenueRepository
}