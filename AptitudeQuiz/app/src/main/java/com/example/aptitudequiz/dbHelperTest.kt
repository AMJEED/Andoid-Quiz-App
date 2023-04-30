package com.example.aptitudequiz



import android.content.ContentValues
import android.content.Context
import android.content.LocusId
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class dbHelperTest (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE IF NOT EXIST" + TABLE_NAME + " ("
                + col_id + " INTEGER PRIMARY KEY, " +
                col_category + " TEXT," +
                col_timeTaken + " TEXT," +
                col_marks + " TEXT," + "" +
                col_uid + " INTEGER" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addName(category: String , timetaken : String ,score:String,userId:Int){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(col_category, category)
        values.put(col_timeTaken,timetaken)
        values.put(col_marks, score)
        values.put(col_uid, userId)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getName(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " +testdbHelper.TABLE_NAME, null)

    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "Quiz"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "Test"

        // below is the variable for id column
        val col_id = "id"

        // below is the variable for name column
        val col_category= "category"

        // below is the variable for age column
        val col_timeTaken = "timeTaken"
        val col_marks = "marks"
        val col_uid = "userId"
    }

}
