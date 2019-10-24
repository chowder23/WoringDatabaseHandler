package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.ID
import java.sql.RowId

class DatabaseHandler(context: Context,
                      factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DB_NAME,
        factory, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE Humans(humanID INTEGER PRIMARY KEY NOT NULL , firstName TEXT, lastName TEXT)")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        db.execSQL(DROP_TABLE)
        onCreate(db)


    }

    fun createTable(name:String)
    {
        var db = this.writableDatabase
        var tableCreate="CREATE TABLE $name( ID INTEGER PRIMARY KEY, firstName TEXT, lastName TEXT)"
        db.execSQL(tableCreate)

    }

    fun dropTable(name:String)
    {
        var db = this.writableDatabase
        var tableDrop = "DROP TABLE $name"
        db.execSQL(tableDrop)
    }

    fun addHuman(human:Human): Boolean {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FIRSTNAME, human.firstName)
        values.put(LASTNAME,human.lastName)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }
    fun getHuman(_id: Int): Human {
        var human = Human("","")
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                var id:Int = Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")))
                if(id ==_id)
                {
                human.id = (cursor.getString(cursor.getColumnIndex("ID")))
                human.firstName = cursor.getString(cursor.getColumnIndex(FIRSTNAME))
                human.lastName = cursor.getString(cursor.getColumnIndex(LASTNAME))
                }

            }
        }
        cursor.close()
        return human
    }

    fun getHumans():List<Human>
    {

        var humans = mutableListOf<Human>()
        val db= writableDatabase
        val SELECT_QUERY ="SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(SELECT_QUERY,null)
        if(cursor != null)
        {

            while(cursor.moveToNext())
            {
                var human=Human("","")
                human.id = (cursor.getString(cursor.getColumnIndex("ID")))
                human.firstName = cursor.getString(cursor.getColumnIndex(FIRSTNAME))
                human.lastName = cursor.getString(cursor.getColumnIndex(LASTNAME))

                humans.add(human)
            }
        }


        return humans
    }

    companion object {

        private val DB_VERSION = 1
        private val DB_NAME = "Humans"
        private val TABLE_NAME = "Humans"
        private val HUMANID = "humanId"
        private val FIRSTNAME = "firstName"
        private val LASTNAME = "lastName"

    }
}

class Column(var name:String,var type:String)