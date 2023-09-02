package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    /*
    Expected:
    TableInfo{name='student', columns={name=Column{name='name', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, age=Column{name='age', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, sex=Column{name='sex', type='TEXT', affinity='2', notNull=true, primaryKeyPosition=0, defaultValue='null'}, id=Column{name='id', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='null'}, bar_data=Column{name='bar_data', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}}, foreignKeys=[], indices=[]}
     Found:
    TableInfo{name='student', columns={name=Column{name='name', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='null'}, age=Column{name='age', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='null'}, sex=Column{name='sex', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue=''M''}, id=Column{name='id', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='null'}, bar_data=Column{name='bar_data', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=0, defaultValue='1'}}, foreignKeys=[], indices=null}
     */

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var name: String?,
    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.INTEGER)
    var age: Int
    ){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    var id: Int = 0

//    //V2.0
//    @ColumnInfo(name = "sex", typeAffinity = ColumnInfo.INTEGER)
//    var sex: Int = 0

    //V4.0
    @ColumnInfo(name = "sex", typeAffinity = ColumnInfo.TEXT)
    var sex: String  = "M"

    @ColumnInfo(name = "bar_data", typeAffinity = ColumnInfo.INTEGER)
    var barData: Int  = 1


}
