package com.example.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.room.entity.Student


/**
 * 关于升级
 * 如果用户安装的数据库版本是1
 * 直接安装版本3的话
 * Room会依次执行2、
 */
@Database(entities = [Student::class], version = 4, exportSchema = true)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getStudentDao(): StudentDao

    companion object {
        private const val DATABASE_NAME = "my_db.db"
        @Volatile
        private var mInstance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return mInstance?: synchronized(this) {
                mInstance ?: Room
                    .databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        DATABASE_NAME
                    )
                        //升级数据库时该方法会被调用
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3,MIGRATION_3_4)
                        //升级发生异常时该方法会被调用
                        //此时应用程序不会崩溃，切数据库会重建到之前的版本，
                       // 但数据会丢失

                    .fallbackToDestructiveMigration()
                    .build()
            }
        }

        private val MIGRATION_1_2 = Migration(1,2){
            it.execSQL("ALTER TABLE student ADD COLUMN sex INTEGER NOT NULL DEFAULT 1")
        }

        private val MIGRATION_2_3 = Migration(2,3){
            it.execSQL("ALTER TABLE student ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1")
        }

        //销毁重建策略
        private val MIGRATION_3_4 = Migration(3,4){
            it.execSQL("CREATE TABLE temp_student(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "name TEXT," +
                    "age INTEGER NOT NULL," +
                    "sex TEXT NOT NULL," +
                    "bar_data INTEGER NOT NULL " +
                    ")")
            it.execSQL("INSERT INTO temp_student(name,age,sex,bar_data)" +
                    "SELECT name,age,sex,bar_data FROM student")
            it.execSQL("DROP TABLE student")
            it.execSQL("ALTER TABLE temp_student RENAME TO student")
        }

    }

}