package gratens.maxime.infrastructure

import cats.implicits._
import ciris.{ConfigValue, Effect, env}
import gratens.maxime.domain.config.ApiConfig


object Config {
  def apply(): ConfigValue[Effect, ApiConfig] = {
    (
      env("API_ENDPOINT").as[String],
      env("API_KEY").as[String].secret
      ).parMapN(ApiConfig)
  }
}
