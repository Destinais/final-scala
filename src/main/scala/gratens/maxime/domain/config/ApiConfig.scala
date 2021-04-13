package gratens.maxime.domain.config

import ciris.Secret

case class ApiConfig(endpoint: String,
                     apiKey: Secret[String]
                    )
