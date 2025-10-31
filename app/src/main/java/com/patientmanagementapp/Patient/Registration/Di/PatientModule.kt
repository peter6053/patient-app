package com.patientmanagementapp.Patient.Registration.Di


import com.patientmanagementapp.Patient.Registration.Data.Repository.PatientRepositoryImpl
import com.patientmanagementapp.Patient.Registration.Dormain.UseCase.RegisterPatientUseCase
import com.patientmanagementapp.Patient.Registration.Repository.PatientRepository
import com.patientmanagementapp.PatientRegistration.data.remote.PatientApi
import com.patientmanagementapp.Utils.DataStoreManager

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PatientModule {

    @Provides
    @Singleton
    fun providePatientApi(retrofit: Retrofit): PatientApi =
        retrofit.create(PatientApi::class.java)

    @Provides
    @Singleton
    fun providePatientRepository(
        api: PatientApi,
        dataStoreManager: DataStoreManager
    ): PatientRepository = PatientRepositoryImpl(api, dataStoreManager)
    @Provides
    @Singleton
    fun provideRegisterPatientUseCase(repository: PatientRepository) =
        RegisterPatientUseCase(repository)
}