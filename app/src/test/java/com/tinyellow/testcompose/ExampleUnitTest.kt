package com.tinyellow.testcompose

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val i = 1
        val j = 1

//        (i + j == 3).yes {
//            println("yes")
//        }.otherwise {
//            println("yes->otherwise")
//        }
//
//        (i + j == 3).no {
//            println("no")
//        }.otherwise {
//            println("no->otherwise")
//        }

        (i + j == 3).yes2{

        }

        assertEquals(4, 2 + 2)
    }
}