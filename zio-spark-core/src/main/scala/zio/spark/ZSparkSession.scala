package zio.spark
import zio._
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.SparkSessionExtensions
import org.apache.avro.{Schema, SchemaNormalization}

final case class ZSparkSession(session: SparkSession)
object ZSparkSession {
  val builder = Builder()
  final case class Builder(
    enableHiveSupport: Boolean = false,
    extentions: SparkSessionExtensions => Unit = _ => (),
    sparkConf: ZSparkConf = ZSparkConf(Map())
  ) {
    def make: Task[ZSparkSession] =
      for {
        sparkConf <- sparkConf.toSparkConf()
        builder <- Task.effect(SparkSession.builder.config(sparkConf))
        session <- Task.effect(builder.getOrCreate())
      } yield ZSparkSession(session)
  }

  def live: ZLayer[Has[ZSparkConf], Throwable, Has[ZSparkSession]] = (
    for {
      sparkConf <- ZIO.service[ZSparkConf]
      session <- builder.copy(sparkConf = sparkConf).make
    } yield session
  ).toLayer
}

final case class ZSparkConf(config: Map[String, String] = Map()) {

  def withMaster(master: String): ZSparkConf =
    copy(config = config.updated(SparkConf.Keys.sparkMaster, master))

  def withAppName(appName: String): ZSparkConf = ???
  def withJars(jars: Seq[String]): ZSparkConf = ???
  def withExecutorEnvironment(env: Map[String, String]): ZSparkConf = ???
  def withSparkHome(sparkHome: String): ZSparkConf = ???
  def withKryoClasses(clases: Seq[Class[_]]): ZSparkConf = ???
  def withAvroSchemas(schemas: Seq[org.apache.avro.Schema]): ZSparkConf = ???
  def withAppId(appId: String): ZSparkConf = ???
  def withAvroSchema(schema: Map[AnyRef, String]): ZSparkConf = ???

  def master: String = config.getOrElse(SparkConf.Keys.sparkMaster, "")
  def appName: String = ???
  def jars: Seq[String] = ???
  def executorEnvironment: Map[String, String] = ???
  def sparkHome: String = ???
  def kryoClasses: Seq[Class[_]] = ???
  def avroSchemas: Seq[org.apache.avro.Schema] = ???
  def appId: String = ???
  def avroSchema: Map[AnyRef, String] = ???

  def getDuration(key: String): Option[java.time.Duration] = ???
  def getString(key: String): Option[String] = ???
  def getInt(key: String): Option[Int] = ???
  def getLong(key: String): Option[Long] = ???
  def getDouble(key: String): Option[Double] = ???
  def getBoolean(key: String): Option[Boolean] = ???
  def getSize(key: String): Option[Size] = ???
  def getAllWithPrefix(prefix: String): Map[String, String] = ???
  def contains(key: String): Boolean = ???

  def toSparkConf(loadDefaults: Boolean = true): Task[org.apache.spark.SparkConf] = Task.effect {
    new org.apache.spark.SparkConf(loadDefaults).setAll(config)
  }

}
object SparkConf {

  object Keys {
    val sparkMaster = "spark.master"
    val appName = "spark.app.name"
    val sparkJars = "spark.jars"
    val executorEnvPrefix = "spark.executorEnv."
    val sparkHome = "spark.home"
    val kryoClassesToRegister = "spark.kryo.classesToRegister"
    val serializer = "spark.serializer"
    val appId = "spark.app.id"
    val localDir = "spark.local.dir"
    val driverLibraryPath = "spark.driver.libraryPath"
    val memoryFraction = "spark.memory.fraction"
    val storageFraction = "spark.memory.storageFraction"
    val master = "spark.master"
    val deployMode = "spark.submit.deployMode"
    val coresMax = "spark.cores.max"
    val executorCores = "spark.executor.cores"
    val networkTimeout = "spark.network.timeout"
    val heartbeatInterval = "spark.executor.heartbeatInterval"
  }
  def isExecutorStartUpConfiguration(name: String): Boolean = ???
  def isSparkPortConf(name: String): Boolean = ???
  def logDeprecationWarning(key: String): UIO[Unit] = ???
}

final case class Size(sizeInBytes: Long) {
  def asBytes: Long = ???
  def asKb: Long = ???
  def asMb: Long = ???
  def asGb: Long = ???
}
