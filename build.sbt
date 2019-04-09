name := "cats-playground"
version := "1.0.0"
scalaVersion := "2.11.12"
scalacOptions ++= Seq("-Ypartial-unification")
libraryDependencies ++= Seq(
  "org.typelevel"      %% "cats-core"                % "1.6.0",
  "org.typelevel"      %% "cats-effect"              % "1.1.0",
  "org.typelevel"          %% "kittens" % "1.2.0"
)

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")
