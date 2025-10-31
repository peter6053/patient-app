package com.patientmanagementapp.Patient.PatientList.Di.PatientListModule

import com.patientmanagementapp.Patient.PatientList.Data.Remote.PatientApi
import com.patientmanagementapp.Patient.PatientList.Data.Repository.PatientRepositoryImpl
import com.patientmanagementapp.Patient.PatientList.repository.PatientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PatientListModule {

    @Provides
    @Singleton
    fun providePatientApi(retrofit: Retrofit): PatientApi =
        retrofit.create(PatientApi::class.java)

    @Provides
    @Singleton
    fun providePatientRepository(api: PatientApi): PatientRepository =
        PatientRepositoryImpl(api)
}
