name := "scala_ray"

version := "0.1"

scalaVersion := "2.11.12"

idePackagePrefix := Some("org.example.application")

assemblyJarName in assembly := "echo.jar"

mainClass in assembly := Some("org.example.application.MainApp")

// https://mvnrepository.com/artifact/io.ray/ray-api
libraryDependencies += "io.ray" % "ray-api" % "1.2.0"

// https://mvnrepository.com/artifact/io.ray/ray-runtime
libraryDependencies += "io.ray" % "ray-runtime" % "1.2.0"

assemblyMergeStrategy in assembly := {
  case "module-info.class" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}