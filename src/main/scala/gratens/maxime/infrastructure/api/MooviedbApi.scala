package gratens.maxime.infrastructure.api

import cats.effect.IO
import gratens.maxime.domain.api.Api
import gratens.maxime.domain.config.ApiConfig
import gratens.maxime.domain.model.{Actor, FullName}
import gratens.maxime.domain.response.{FindActorMoviesResponse, FindMovieDirectorResponse, PaginatedResponse}
import org.json4s.{DefaultFormats, Formats}

import java.net.URLEncoder
import scala.io.Source
import org.json4s._
import org.json4s.native.JsonMethods._

class MooviedbApi(apiConfig: ApiConfig) extends Api[IO] {
  implicit val formats: Formats = DefaultFormats

  private def buildUrl(route: String, maybeQuery: Option[String]): String = {
    val query = maybeQuery.getOrElse("")
    s"${apiConfig.endpoint}$route?api_key=${apiConfig.apiKey.value}&query=$query"
  }

  override def findActorId(name: String, surname: String): IO[Int] = for {
    query <- IO(URLEncoder.encode(s"$name $surname", "UTF-8"))
    response <- IO(Source.fromURL(buildUrl("/search/person", Some(query))))
    paginatedResponse <- IO(parse(response.mkString).camelizeKeys.extract[PaginatedResponse[Actor]])
    actor <- IO.fromOption(paginatedResponse.results.headOption)(new Throwable("Can't find any actor with this name"))
  } yield actor.id

  override def findActorMovies(id: Int): IO[Set[(Int, String)]] = for {
    response <- IO(Source.fromURL(buildUrl(s"/person/$id/movie_credits", None)))
    moviesResponse <- IO(parse(response.mkString).camelizeKeys.extract[FindActorMoviesResponse])
  } yield moviesResponse.cast.map(movie => (movie.id, movie.originalTitle)).toSet

  override def findMovieDirector(id: Int): IO[(Int, String)] = for {
    response <- IO(Source.fromURL(buildUrl(s"/movie/$id/credits", None)))
    movieResponse <- IO(parse(response.mkString).camelizeKeys.extract[FindMovieDirectorResponse])
    director <- IO.fromOption(movieResponse.crew.find(_.job == "Director"))(new Throwable("Can't find any actor with this name"))
  } yield (director.id, director.name)

  override def request(actor1: FullName, actor2: FullName): IO[Set[(String, String)]] = ??? // TODO : We don't understand the goal of this request
}
