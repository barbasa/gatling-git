import Dependencies._

enablePlugins(GatlingPlugin)

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "gerritforge",
      scalaVersion := "2.12.8",
      version := "0.1.0"
    )),
    name := "gatling-git",
    libraryDependencies ++=
      gatling ++
        Seq("io.gatling" % "gatling-core" % "3.1.1" ) ++
        Seq("io.gatling" % "gatling-app" % "3.1.1" ) ++
        Seq("org.eclipse.jgit" % "org.eclipse.jgit" % "5.3.0.201903130848-r") ++
        Seq("com.google.inject" % "guice" % "3.0") ++
        Seq("commons-io" % "commons-io" % "2.6")
  )

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}
