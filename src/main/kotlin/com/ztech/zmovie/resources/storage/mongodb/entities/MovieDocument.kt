package com.ztech.zmovie.resources.storage.mongodb.entities

import com.ztech.zmovie.domain.entities.Movie
import com.ztech.zmovie.domain.entities.Rate
import org.bson.types.ObjectId
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.util.*

data class MovieDocument(
    var id: ObjectId? = null,
    var title: String? = null,
    var releaseDate: Date? = null,
    var rate: Rate? = null,
    var director: String? = null,
    var actors: List<String>? = emptyList()
) {

    companion object {
        fun fromMovie(movie: Movie) = MovieDocument(
            title = movie.title,
            director = movie.director,
            rate = movie.rate,
            actors = movie.actors,
            releaseDate = movie.releaseDate
        )

        fun toMovie(movieDocument: MovieDocument): Movie {
            return movie(movieDocument) ?: throw IllegalArgumentException()
        }

        private fun movie(movieDocument: MovieDocument) =
            movieDocument?.let {
                it.title?.let { title ->
                    it.actors?.let { actors ->
                        it.director?.let { director ->
                            it.rate?.let { rate ->
                                it.releaseDate?.let { releaseDate ->
                                    Movie(
                                        title = title,
                                        director = director,
                                        rate = rate,
                                        releaseDate = releaseDate,
                                        actors = actors
                                    )
                                }
                            }
                        }
                    }
                }
            }
    }

}
