package com.d.foodapp.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.d.foodapp.Interceptor.LoginInterceptor
import com.d.foodapp.api.Constants.Companion.BASE_URL
import com.d.foodapp.api.MealApiService
import com.d.foodapp.db.MealDataBase
import com.intuit.sdp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun providesBaseUrl(): String {
        return BASE_URL
    }
    // Creating an interceptor for logging HTTP requests and responses.
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    // Creating an OkHttpClient builder to configure various properties of the HTTP client.
    @Provides
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        //okHttpClient.addInterceptor(LoginInterceptor())
        okHttpClient.build()


        return okHttpClient.build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory{
        return GsonConverterFactory.create()
    }

    // Creating a Retrofit instance for making HTTP requests.
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient, baseUrl: String, converterFactory: Factory): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): MealApiService{
        return retrofit.create(MealApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MealDataBase =
        Room.databaseBuilder(context, MealDataBase::class.java,
            "meal.db")
            .addMigrations(MIGRATION_1_2)
            .build()

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Create the new table with updated schema
            database.execSQL(
                """
            CREATE TABLE mealInformation_new (
                dateModified TEXT,
                idMeal TEXT NOT NULL PRIMARY KEY,
                strArea TEXT NOT NULL,
                strCategory TEXT NOT NULL,
                strCreativeCommonsConfirmed TEXT,
                strDrinkAlternate TEXT,
                strImageSource TEXT,
                strIngredient1 TEXT,
                strIngredient10 TEXT,
                strIngredient11 TEXT,
                strIngredient12 TEXT,
                strIngredient13 TEXT,
                strIngredient14 TEXT,
                strIngredient15 TEXT,
                strIngredient16 TEXT,
                strIngredient17 TEXT,
                strIngredient18 TEXT,
                strIngredient19 TEXT,
                strIngredient2 TEXT,
                strIngredient20 TEXT,
                strIngredient3 TEXT,
                strIngredient4 TEXT,
                strIngredient5 TEXT,
                strIngredient6 TEXT,
                strIngredient7 TEXT,
                strIngredient8 TEXT,
                strIngredient9 TEXT,
                strInstructions TEXT,
                strMeal TEXT,
                strMealThumb TEXT,
                strMeasure1 TEXT,
                strMeasure10 TEXT,
                strMeasure11 TEXT,
                strMeasure12 TEXT,
                strMeasure13 TEXT,
                strMeasure14 TEXT,
                strMeasure15 TEXT,
                strMeasure16 TEXT,
                strMeasure17 TEXT,
                strMeasure18 TEXT,
                strMeasure19 TEXT,
                strMeasure2 TEXT,
                strMeasure20 TEXT,
                strMeasure3 TEXT,
                strMeasure4 TEXT,
                strMeasure5 TEXT,
                strMeasure6 TEXT,
                strMeasure7 TEXT,
                strMeasure8 TEXT,
                strMeasure9 TEXT,
                strSource TEXT,
                strTags TEXT,
                strYoutube TEXT
            )
            """
            )
            Log.e("database", "New table created")

            // Copy the data from the old table to the new table
            database.execSQL(
                """
            INSERT INTO mealInformation_new (
                dateModified, idMeal, strArea, strCategory, strCreativeCommonsConfirmed,
                strDrinkAlternate, strImageSource, strIngredient1, strIngredient10,
                strIngredient11, strIngredient12, strIngredient13, strIngredient14,
                strIngredient15, strIngredient16, strIngredient17, strIngredient18,
                strIngredient19, strIngredient2, strIngredient20, strIngredient3,
                strIngredient4, strIngredient5, strIngredient6, strIngredient7,
                strIngredient8, strIngredient9, strInstructions, strMeal, strMealThumb,
                strMeasure1, strMeasure10, strMeasure11, strMeasure12, strMeasure13,
                strMeasure14, strMeasure15, strMeasure16, strMeasure17, strMeasure18,
                strMeasure19, strMeasure2, strMeasure20, strMeasure3, strMeasure4,
                strMeasure5, strMeasure6, strMeasure7, strMeasure8, strMeasure9,
                strSource, strTags, strYoutube
            )
            SELECT
                dateModified, idMeal, strArea, strCategory, strCreativeCommonsConfirmed,
                strDrinkAlternate, strImageSource, strIngredient1, strIngredient10,
                strIngredient11, strIngredient12, strIngredient13, strIngredient14,
                strIngredient15, strIngredient16, strIngredient17, strIngredient18,
                strIngredient19, strIngredient2, strIngredient20, strIngredient3,
                strIngredient4, strIngredient5, strIngredient6, strIngredient7,
                strIngredient8, strIngredient9, strInstructions, strMeal, strMealThumb,
                strMeasure1, strMeasure10, strMeasure11, strMeasure12, strMeasure13,
                strMeasure14, strMeasure15, strMeasure16, strMeasure17, strMeasure18,
                strMeasure19, strMeasure2, strMeasure20, strMeasure3, strMeasure4,
                strMeasure5, strMeasure6, strMeasure7, strMeasure8, strMeasure9,
                strSource, strTags, strYoutube
            FROM mealInformation
            """
            )
            Log.e("Database", "Data copied to new database")

            // Drop the old table
            database.execSQL("DROP TABLE mealInformation")

            Log.e("Database", "old table dropped")


            // Rename the new table to the old table's name
            database.execSQL("ALTER TABLE mealInformation_new RENAME TO mealInformation")
        }
    }
}