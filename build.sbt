name := "mooviedb"

version := "0.1"

scalaVersion := "2.12.13"
scalacOptions += "-Ypartial-unification"

lazy val json4sVersion = "3.6.11"
lazy val catsEffectVersion = "3.0.0"
lazy val cirisVersion = "2.0.0-RC2"
lazy val catsVersion = "2.3.0"
lazy val log4catsVersion = "2.0.1"
lazy val logbackVersion = "1.1.3"

lazy val ciris = Seq(
  "is.cir" %% "ciris" % cirisVersion
)

lazy val catsEffect = Seq(
  "org.typelevel" %% "cats-effect" % catsEffectVersion
)

lazy val cats = Seq(
  "org.typelevel" %% "cats-core" % catsVersion
)

lazy val json4s = Seq(
  "org.json4s" %% "json4s-ast" % json4sVersion,
  "org.json4s" %% "json4s-native" % json4sVersion
)

lazy val log4cats = Seq(
  "org.typelevel" %% "log4cats-core"    % log4catsVersion,
  "org.typelevel" %% "log4cats-slf4j"   % log4catsVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion % Runtime,
)

libraryDependencies ++= json4s ++ catsEffect ++ ciris ++ cats ++ log4cats