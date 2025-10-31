package com.patientmanagementapp.Auth.Di

import com.patientmanagementapp.Auth.AuthApi.AuthApi
import com.patientmanagementapp.Auth.Data.Remote.AuthRepositoryImpl
import com.patientmanagementapp.Auth.Dormain.Repository.AuthRepository
import com.patientmanagementapp.Auth.Dormain.UseCase.LoginUseCase
import com.patientmanagementapp.Auth.Dormain.UseCase.SignupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository) = LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideSignupUseCase(repository: AuthRepository) = SignupUseCase(repository)
}

