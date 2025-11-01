package com.patientmanagementapp.Patient.GeneraAssessmentPage.Di

import com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Remote.GeneralAssessmentApi
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Repository.GeneralAssessmentRepositoryImpl
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.Repository.GeneralAssessmentRepository
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.usecase.SubmitGeneralAssessmentUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GeneralAssessmentModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): GeneralAssessmentApi =
        retrofit.create(GeneralAssessmentApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: GeneralAssessmentApi): GeneralAssessmentRepository =
        GeneralAssessmentRepositoryImpl(api)

    @Provides
    fun provideSubmitUseCase(repository: GeneralAssessmentRepository): SubmitGeneralAssessmentUseCase =
        SubmitGeneralAssessmentUseCase(repository)
}