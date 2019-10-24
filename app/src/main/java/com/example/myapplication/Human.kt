package com.example.myapplication

class Human(var firstName:String,var lastName:String,var id:String ="")
{
    override fun toString(): String {
        return "$id $firstName $lastName"
    }
}