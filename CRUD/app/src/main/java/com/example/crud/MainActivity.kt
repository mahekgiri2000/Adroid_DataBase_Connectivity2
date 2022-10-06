package com.example.crud

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    lateinit var edSname : EditText
    lateinit var  edSem : EditText
    lateinit var  btnClear : Button
    lateinit var  rs : Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //creating an object of mydbhelper class
        var helper = MyDBHelper(applicationContext)
        var db= helper.writableDatabase
        rs= db.rawQuery("Select sid _id,sname,sem from student",null)



        edSname = findViewById(R.id.edtTextSname)
        edSem = findViewById(R.id.edtTextSem)
        btnClear = findViewById(R.id.btnClear)

        var btnshowAll : Button = findViewById(R.id.btnShowAll)
        var srcView : SearchView = findViewById(R.id.srcView)
        var lstView : ListView = findViewById(R.id.lstView)

        var btnInsert : Button = findViewById(R.id.btnInsert)
        var btnUpdate : Button = findViewById(R.id.btnUpdate)
        var btnDelete : Button = findViewById(R.id.btnDelete)
        var btnFirst : Button = findViewById(R.id.btnFirst)
        var btnNext : Button = findViewById(R.id.btnNext)
        var btnPrevious : Button = findViewById(R.id.btnPrevious)
        var btnLast : Button = findViewById(R.id.btnLast)

        //call the method to clear edittext
        btnClear.setOnClickListener {
            clear()
        }

        if(rs.moveToNext()){
            edSname.setText(rs.getString(1))
            edSem.setText(rs.getString(2))
        }

        btnInsert.setOnClickListener {

            var cv=  ContentValues()
            cv.put("sname",edSname.text.toString())
            cv.put("sem",edSem.text.toString())
            db.insert("student",null,cv)
            rs=db.rawQuery("Select sid _id,sname,sem from student",null)
            Toast.makeText(applicationContext,"Record Inserted",Toast.LENGTH_LONG).show()
            clear()

        }

        //update records
        btnUpdate.setOnClickListener {
            var cv = ContentValues()
            cv.put("sname",edSname.text.toString())
            cv.put("sem",edSem.text.toString())
            db.update("student",cv,"sid= ?", arrayOf(rs.getString(0)))
            rs= db.rawQuery("select sid _id,sname,sem from student",null)
            Toast.makeText(applicationContext,"Update Record",Toast.LENGTH_LONG).show()
            clear()
        }

        //delete record
        btnDelete.setOnClickListener {
//            while (edSname.text.toString().equals("sname")){
//                            db.delete("student","sid=?", arrayOf(rs.getString(0)))
//            }
            db.delete("student","sid=?", arrayOf(rs.getString(0)))
            rs = db.rawQuery("select sid _id,sname,sem from student",null)
            clear()

        }

        btnshowAll.setOnClickListener {
        srcView.isIconified = false
            srcView.queryHint = "Search Among ${rs.count} Records"

            var adapter = SimpleCursorAdapter(applicationContext,android.R.layout.simple_expandable_list_item_2,rs,
                arrayOf("sname","sem"),
                intArrayOf(android.R.id.text1,android.R.id.text2),0)

            lstView.adapter= adapter

            srcView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(p0: String?): Boolean {
                    rs = db.rawQuery(
                        "select sid _id,sname,sem from student where sname like '%${p0}'",
                        null)
                    adapter.changeCursor(rs)
//                    edSname.setText("${p0}".toString())
//                    edSem.setText("${p0}".toString())
                    return false
                }


            })



        }



        //get the first record
        btnFirst.setOnClickListener {
            if(rs.moveToFirst()){
                edSname.setText(rs.getString(1))
                edSem.setText(rs.getString(2))
            }else
                Toast.makeText(applicationContext,"Data Not Found",Toast.LENGTH_LONG).show()
        }

        //get The Last Record
        btnLast.setOnClickListener {
            if(rs.moveToLast()){
                edSname.setText(rs.getString(1))
                edSem.setText(rs.getString(2))

            }else
                Toast.makeText(applicationContext,"Data Not Found",Toast.LENGTH_LONG).show()
        }

        //get The Next Record
        btnNext.setOnClickListener {

            if(rs.moveToNext()){
                edSname.setText(rs.getString(1))
                edSem.setText(rs.getString(2))
            }else if(rs.moveToFirst()){
                edSname.setText(rs.getString(1))
                edSem.setText(rs.getString(2))
            }else
                Toast.makeText(applicationContext,"Data Not Found",Toast.LENGTH_LONG).show()

        }

        //get The previous record
        btnPrevious.setOnClickListener {
            if(rs.moveToPrevious()){
                edSname.setText(rs.getString(1))
                edSem.setText(rs.getString(2))
            }else if(rs.moveToLast()){
                edSname.setText(rs.getString(1))
                edSem.setText(rs.getString(2))
            }else
                Toast.makeText(applicationContext,"Data Not Found",Toast.LENGTH_LONG).show()

        }






    }

    //method to create text area
    private  fun clear(){
        edSname.setText("")
        edSem.setText("")
    }
}