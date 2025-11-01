package com.patientmanagementapp.Patient.Vitals.Di

import com.patientmanagementapp.Patient.Vitals.Data.Remote.VitalsApi
import com.patientmanagementapp.Patient.Vitals.Data.Repository.VitalsRepositoryImpl
import com.patientmanagementapp.Patient.Vitals.Dormain.Usecases.SubmitVitalsUseCase
import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsRepository
import com.patientmanagementapp.Utils.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VitalsModule {

    @Provides
    @Singleton
    fun provideVitalsApi(retrofit: Retrofit): VitalsApi {
        return retrofit.create(VitalsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVitalsRepository(api: VitalsApi): VitalsRepository =
        VitalsRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideSubmitVitalsUseCase(repository: VitalsRepository) = SubmitVitalsUseCase(repository)
}
