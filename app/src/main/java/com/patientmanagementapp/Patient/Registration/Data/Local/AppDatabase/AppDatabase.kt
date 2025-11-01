package com.patientmanagementapp.Patient.Registration.Data.Local.AppDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Local.Dao.GeneralAssessmentDao
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Local.GeneralAssessmentEntity
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Local.Dao.OverWeightAssessmentDao
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Local.OverWeightAssessmentEntity
import com.patientmanagementapp.Patient.Registration.Data.Local.Dao.PatientDao
import com.patientmanagementapp.Patient.Registration.Data.Local.Entitity.RegisterPatientEntity
import com.patientmanagementapp.Patient.Vitals.Data.Local.Dao.VitalsDao
import com.patientmanagementapp.Patient.Vitals.Data.Local.Entity.VitalsEntity


@Database(
    entities = [RegisterPatientEntity::class, VitalsEntity::class, OverWeightAssessmentEntity::class, GeneralAssessmentEntity::class],

    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun vitalsDao(): VitalsDao
    abstract fun overweightDao(): OverWeightAssessmentDao
    abstract fun generalAssessmentDao(): GeneralAssessmentDao

}