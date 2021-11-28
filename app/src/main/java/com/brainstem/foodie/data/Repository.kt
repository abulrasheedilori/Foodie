package com.brainstem.foodie.data


import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped // since it will be needed in the viewmodel
class Repository  @Inject constructor(
    remoteDataSource: RemoteDataSource
){
    val remote = remoteDataSource
}