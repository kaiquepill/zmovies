package com.ztech.zmovie.application.web.controllers

import com.ztech.zmovie.domain.entities.Movie
import com.ztech.zmovie.domain.entities.Rate
import com.ztech.zmovie.domain.exceptions.InvalidBodySuppliedException
import com.ztech.zmovie.domain.services.RetrieveMoviesService
import com.ztech.zmovie.resources.storage.sample.movieSample
import io.javalin.Context
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RetrieveMoviesControllerTest {

    private val ctx = mockk<Context>(relaxed = true)
    private val service = mockk<RetrieveMoviesService>()
    private val controller = RetrieveMoviesController(service)

    @Test
    fun `given the request was well formatted, should retrieve movies`() {

        val movieList = listOf<Movie>(
            movieSample(),
            movieSample(),
            movieSample(),
            movieSample(),
            movieSample()
        )

        every {ctx.pathParam("rate")} returns "SEM_CENSURA"
        every { service.retrieveMovies(Rate.SEM_CENSURA)} returns movieList

        controller.retrieve(ctx)
        verify(exactly = 1) { service.retrieveMovies(Rate.SEM_CENSURA) }
    }
    @Test
    fun `given the request was bad formatted, should throw an exception`() {
        every {ctx.body<Rate>()} throws Exception()
        val movieList = listOf<Movie>(
            movieSample(),
            movieSample(),
            movieSample(),
            movieSample(),
            movieSample()
        )
        every { service.retrieveMovies(Rate.SEM_CENSURA)} returns movieList
        assertThrows<InvalidBodySuppliedException> {  controller.retrieve(ctx) }
    }

}