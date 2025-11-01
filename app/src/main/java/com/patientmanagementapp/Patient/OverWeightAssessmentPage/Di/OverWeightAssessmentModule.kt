package com.patientmanagementapp.Patient.OverweightAssessmentPage.Di

import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Remote.OverWeightAssessmentApi
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Repository.OverWeightAssessmentRepositoryImpl
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.Repository.OverweightAssessmentRepository
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.usecase.SubmitOverweightAssessmentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OverweightAssessmentModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): OverWeightAssessmentApi =
        retrofit.create(OverWeightAssessmentApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: OverWeightAssessmentApi): OverweightAssessmentRepository {
        return OverWeightAssessmentRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideSubmitUseCase(repository: OverweightAssessmentRepository): SubmitOverweightAssessmentUseCase {
        return SubmitOverweightAssessmentUseCase(repository)
    }
}
