package com.example.testapplication

import java.util.*

class ScopeFunctions {

    data class User(var name: String, var age: Int, var nation: String)

    // apply and also return the context object.
    // let, run, and with return the lambda result.

//    Function	- Object reference	- Return value	    - Is extension function
//    run	            -	            Lambda result	    No: called without the context object
//    with	            this	        Lambda result	    No: takes the context object as an argument.
//    run           	this	        Lambda result	    Yes
//    apply	            this	        Context object	    Yes
//    let	            it	            Lambda result	    Yes
//    also	            it	            Context object	    Yes

//    Executing a lambda on non-null objects: let
//    Introducing an expression as a variable in local scope: let
//    Object configuration: apply
//    Object configuration and computing the result: run
//    Running statements where an expression is required: non-extension run
//    Additional effects: also
//    Grouping function calls on an object: with

    fun scopeFunctions() {
        println(User("itzik", 34, "israel").apply {
            name = "nevo".capitalize()
        }.also {
            it.age = 3
        }.run {
            this.name.substring(2)
        }.let {
            it.count()
        }
        )
    }

    fun let() {
        println("let")
        println(User("itzik", 34, "israel")
                .let { it.name.toUpperCase(Locale.getDefault()) }
                .let { "count ${it.count()}"}
        )
        // print result of let
        // result - count 5
    }

    fun run() {
        println("run")
        println(User("itzik", 34, "israel")
            .run { name.capitalize() }
            .run { substring(3) }
            .run { count() }
//            .run { this }
        )
        // prints result of run
        // result - 2
    }

    fun also() {
        println("also")
        println(User("itzik", 34, "israel")
            .also { it.name = it.name.capitalize() }
            .also { it.nation = it.nation.capitalize() }
        )
        // prints object with changed values
        // User(name=Itzik, age=34, nation=Israel)
    }

    fun apply() {
        println("apply")
        println(User("itzik", 34, "israel")
            .apply { nation = nation.capitalize() }
            .apply { name = name.capitalize() }
        )
        // prints object with changed values
        // result - User(name=Itzik, age=34, nation=Israel)
    }

    fun with() {
        println("with")
        val user = User("itzik", 34, "israel")
        val user2 : User = with(user) {
            this.copy("nevo", 3) // will return new User object
        }
        println(user2)
        // normally returns unit
        // User(name=nevo, age=3, nation=israel)
    }

}
