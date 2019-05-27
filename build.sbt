name := "cats-playground"
version := "1.0.0"
scalaVersion := "2.11.12"
scalacOptions ++= Seq("-Ypartial-unification")
libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-async"   % "0.9.6",
  "org.typelevel"          %% "cats-core"     % "1.6.0",
  "org.typelevel"          %% "cats-effect"   % "1.1.0",
  "org.typelevel"          %% "kittens"       % "1.2.0",
  "com.twitter"            %% "algebird-core" % "0.13.5",
  "com.chuusai"            %% "shapeless"     % "2.2.4"
)

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.8")
