package gratens.maxime

import cats.effect.{ExitCode, IO, IOApp}
import gratens.maxime.infrastructure.Config
import gratens.maxime.infrastructure.api.MooviedbApi
import org.typelevel.log4cats.slf4j.Slf4jLogger

object Mooviedb extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = for {
    config <- Config.apply().load[IO]
    logger <- Slf4jLogger.create[IO]
    _ <- logger.info("Start querying api")
    api = new MooviedbApi(config)
    _ <- (for {
      actorId <- api.findActorId("Jason", "Statham")
      movies <- api.findActorMovies(actorId)
      _ <- api.findMovieDirector(movies.head._1).map(println)
      _ <- api.findActorId("Chien", "Chien").map(println)
    } yield ExitCode.Success).handleErrorWith(err => logger.info(err)("Something went wrong"))
  } yield ExitCode.Success
}
