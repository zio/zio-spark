import Dependencies._

ThisBuild / organization := "dev.zio"
ThisBuild / version := ".1.0"
ThisBuild / scalaVersion := "2.12.13"
ThisBuild / homepage := Some(url("https://zio.github.io/zio-spark"))
ThisBuild / description := "A ZIO-idiomatic interface for building Spark application."
ThisBuild / licenses += ("Apache-2.0", url(
  "http://www.apache.org/licenses/LICENSE-2.0"
))
ThisBuild / developers := List(
  Developer(
    "johnDoe",
    "John Doe",
    "@johndoe",
    url("https://github.com/johndoe")
  ),
  Developer(
    "janedoe",
    "Jane Doe",
    "@janedoe",
    url("https://github.com/janedoe")
  )
)

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias(
  "check",
  "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck"
)

lazy val root = (project in file("."))
  .settings(
    name := "zio-spark",
    libraryDependencies ++= Seq(
      `dev.zio`.zio.zio,
      `dev.zio`.zio.test,
      `dev.zio`.zio.`test-sbt`,
      `dev.zio`.zio.`config_typesafe`,
      org.apache.spark.`spark-core`,
      org.apache.spark.`spark-sql`,
      org.apache.spark.`spark-hive`
    ),
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )
lazy val core = project
  .in(file("zio-spark-core"))
  .settings(
    skip.in(publish) := true,
    moduleName := "zio-spark-core",
    scalacOptions -= "-Yno-imports",
    scalacOptions -= "-Xfatal-warnings",
    libraryDependencies ++= Seq(
      `dev.zio`.zio.zio
    )
  )
  .dependsOn(root)

lazy val docs = project
  .in(file("zio-spark-docs"))
  .settings(
    skip.in(publish) := true,
    moduleName := "zio-spark-docs",
    scalacOptions -= "-Yno-imports",
    scalacOptions -= "-Xfatal-warnings",
    libraryDependencies ++= Seq(
      `dev.zio`.zio.zio
    ),
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inProjects(root),
    target in (ScalaUnidoc, unidoc) := (baseDirectory in LocalRootProject).value / "website" / "static" / "api",
    cleanFiles += (target in (ScalaUnidoc, unidoc)).value,
    docusaurusCreateSite := docusaurusCreateSite
      .dependsOn(unidoc in Compile)
      .value,
    docusaurusPublishGhpages := docusaurusPublishGhpages
      .dependsOn(unidoc in Compile)
      .value
  )
  .dependsOn(root)
  .enablePlugins(MdocPlugin, DocusaurusPlugin, ScalaUnidocPlugin)
