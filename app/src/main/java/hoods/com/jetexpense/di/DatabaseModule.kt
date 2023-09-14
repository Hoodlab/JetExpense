package hoods.com.jetexpense.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hoods.com.jetexpense.data.local.JetExpDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideExpenseDatabase(
        @ApplicationContext context: Context,
    ): JetExpDatabase {
        return Room.databaseBuilder(
            context,
            JetExpDatabase::class.java,
            "transaction_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideExpenseDao(database: JetExpDatabase) = database.expenseDao

    @Provides
    @Singleton
    fun provideIncomeDao(database: JetExpDatabase) = database.incomeDao

}



