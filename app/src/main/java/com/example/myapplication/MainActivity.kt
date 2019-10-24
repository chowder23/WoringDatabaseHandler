package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var databaseHandler: DatabaseHandler= DatabaseHandler(this,null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        visibilityInit()
        val nextButton: Button = findViewById(R.id.buttonNextLayoutMain)
        val saveNewHumanButton: Button = findViewById(R.id.buttonAddNewHuman)
        val backToMainButton: Button = findViewById(R.id.buttonBack)
        val showAllHumanButton: Button = findViewById(R.id.buttonShowAllMain)
        val backToMainFromShowAll:Button = findViewById(R.id.buttonBackFromShowAllToMain)


        nextButton.setOnClickListener()
        {
            changeToAddNewHumanLayout()
        }

        saveNewHumanButton.setOnClickListener()
        {
            addNewHuman()
        }
        backToMainButton.setOnClickListener()
        {
            visibilityInit()
        }
        showAllHumanButton.setOnClickListener()
        {
            /*val humans = mutableListOf<Human>()
            for(i in 0..databaseHandler.getHumans().count())
            {
               humans.add(databaseHandler.getHuman(i))
            }*/
            val humans=databaseHandler.getHumans()
            val allHumanListView:ListView = findViewById(R.id.listViewShowAllHuman)
            allHumanListView.adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,humans)
            changeToShowAllHuman()
        }
        backToMainFromShowAll.setOnClickListener()
        {
            visibilityInit()
        }

    }

    fun visibilityInit()
    {
        findViewById<View>(R.id.addNewHumanLayout).visibility = View.INVISIBLE
        findViewById<View>(R.id.mainLayout).visibility=View.VISIBLE
        findViewById<View>(R.id.showAllHumanLayout).visibility=View.INVISIBLE
    }

    fun changeToAddNewHumanLayout()
    {
        findViewById<View>(R.id.addNewHumanLayout).visibility = View.VISIBLE
        findViewById<View>(R.id.mainLayout).visibility=View.INVISIBLE
    }

    fun changeToShowAllHuman()
    {
        findViewById<View>(R.id.mainLayout).visibility=View.INVISIBLE
        findViewById<View>(R.id.showAllHumanLayout).visibility=View.VISIBLE
    }

    fun addNewHuman()
    {
        try {


            var human = Human("","")
            val firstName = findViewById<EditText>(R.id.editTextNewHumanFirstName).text.toString()
            val lastName = findViewById<EditText>(R.id.editTextNewHumanLastName).text.toString()
            human.firstName = firstName
            human.lastName = lastName
            val thing= databaseHandler.addHuman(human)
            //human=databaseHandler.getHumans().first()
            Toast.makeText(this,"${human.toString()} $thing",Toast.LENGTH_LONG).show()
        }
        catch (exp:Exception)
        {
            Toast.makeText(this,exp.toString(),Toast.LENGTH_LONG).show()
        }
    }

    fun dropTable(view:View)
    {
        databaseHandler.dropTable("Humans")
    }
    fun createTable(view:View)
    {
        databaseHandler.createTable("Humans")
    }
}
