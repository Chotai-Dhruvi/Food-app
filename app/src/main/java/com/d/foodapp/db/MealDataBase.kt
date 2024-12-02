package com.d.foodapp.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.d.foodapp.model.Meal

@Database(entities = [Meal::class], version = 2)  // Increment version to 2
@TypeConverters(MealTypeConverter::class)
abstract class MealDataBase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
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


        // Singleton pattern to get the Room Database instance
        @Volatile
        private var INSTANCE: MealDataBase? = null

        fun getDatabase(context: Context): MealDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealDataBase::class.java,
                    "meal_database"
                )
                    .addMigrations(MIGRATION_1_2)  // Register migration here
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}