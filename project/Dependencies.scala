import sbt._

object Dependencies {

  case object `dev.zio` {
    case object zio {
      val zio = "dev.zio" %% "zio" % "1.0.6"
      val test = "dev.zio" %% "zio-test" % "1.0.6" % "test"
      val `test-sbt` = "dev.zio" %% "zio-test-sbt" % "1.0.6" % "test"
      val `config_typesafe` = "dev.zio" %% "zio-config-typesafe" % "1.0.4"
    }
  }

  case object org {
    case object apache {
      case object spark {
        val `spark-core` = "org.apache.spark" %% "spark-core" % "3.1.1"
        val `spark-sql` = "org.apache.spark" %% "spark-sql" % "3.1.1"
        val `spark-hive` = "org.apache.spark" %% "spark-hive" % "3.1.1"
        val `spark-mllib` = "org.apache.spark" %% "spark-mllib" % "3.1.1"
        val `spark-streaming` = "org.apache.spark" %% "spark-streaming" % "3.1.1"
        val `spark-catalyst` = "org.apache.spark" %% "spark-catalyst" % "3.1.1"
        val `spark-tags` = "org.apache.spark" %% "spark-tags" % "3.1.1"
        val `spark-graphx` = "org.apache.spark" %% "spark-graphx" % "3.1.1"
        val `spark-sql-kafka` = "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.1.1"
      }
    }
  }
}
