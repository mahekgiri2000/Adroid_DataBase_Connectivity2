package com.example.crud

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class  MyDBHelper(context: Context) : SQLiteOpenHelper(context,"MYDB",null,1){
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE STUDENT(sid integer primary key autoincrement,sname text,sem number)")
        p0?.execSQL("Insert into student(sname,sem) values('mca',3)")
        p0?.execSQL("insert into student(sname,sem) values('atmiya',3)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}