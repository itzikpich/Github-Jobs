package com.example.testapplication

import com.example.testapplication.data.GithubJobsRepository
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
        assertEquals(4, 2 + 2)
    }

    @Test
    suspend fun getPopularMovies() {
//        assertTrue(GithubJobsRepository.getPopularMoviesFromServer().isSuccessful)
    }
}