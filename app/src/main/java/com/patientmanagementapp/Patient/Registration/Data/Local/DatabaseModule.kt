package com.patientmanagementapp.Patient.Registration.Data.Local

import android.content.Context
import androidx.room.Room
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Local.Dao.GeneralAssessmentDao
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Local.Dao.OverWeightAssessmentDao
import com.patientmanagementapp.Patient.Registration.Data.Local.AppDatabase.AppDatabase
import com.patientmanagementapp.Patient.Registration.Data.Local.Dao.PatientDao
import com.patientmanagementapp.Patient.Vitals.Data.Local.Dao.VitalsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "patient_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePatientDao(database: AppDatabase): PatientDao {
        return database.patientDao()
    }

    @Provides
    @Singleton
    fun provideVitalsDao(database: AppDatabase): VitalsDao {
        return database.vitalsDao()
    }
    @Provides
    @Singleton
    fun provideOverWeightDao(db: AppDatabase): OverWeightAssessmentDao {
        return db.overweightDao()
    }

    @Provides
    @Singleton
    fun provideGeneralAssessmentDao(database: AppDatabase): GeneralAssessmentDao {
        return database.generalAssessmentDao()
    }
}