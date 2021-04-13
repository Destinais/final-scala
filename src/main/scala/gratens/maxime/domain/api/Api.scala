package gratens.maxime.domain.api

import gratens.maxime.domain.model.FullName

trait Api[F[_]] {
  def findActorId(name: String, surname: String): F[Int]

  def findActorMovies(id: Int): F[Set[(Int, String)]]

  def findMovieDirector(id: Int): F[(Int, String)]

  def request(actor1: FullName, actor2: FullName): F[Set[(String, String)]]
}
