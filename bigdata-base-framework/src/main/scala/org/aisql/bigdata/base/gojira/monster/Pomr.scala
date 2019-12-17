package org.aisql.bigdata.base.gojira.monster

import scala.collection.mutable.ArrayBuffer

/**
  * Author: xiaohei
  * Date: 2019/12/17
  * Email: xiaohei.info@gmail.com
  * Host: xiaohei.info
  */
class Pomr(groupId: String, artifactId: String, version: String) {

  private val pomResultBuffer = ArrayBuffer[String]()

  private var coord: String = ""
  private var module: String = ""
  private var parent: String = ""
  private var aid: String = ""
  private var properties: String = ""
  private var dependencies: String = ""
  private var dependencyManagement: String = ""
  private var build: String = ""

  def setCoord(m: String): Unit = {
    coord = if (m == "root") {
      s"""
         |    <groupId>$groupId</groupId>
         |    <artifactId>$artifactId</artifactId>
         |    <version>$version</version>
         |    <packaging>pom</packaging>
      """.stripMargin
    } else {
      s"""
         |    <groupId>$groupId</groupId>
         |    <artifactId>$artifactId</artifactId>
         |    <version>$version</version>
    """.stripMargin
    }
  }

  def setModule(): Unit = {
    module =
      s"""
         |    <modules>
         |        <module>$artifactId-api</module>
         |        <module>$artifactId-context</module>
         |        <module>$artifactId-server</module>
         |    </modules>
    """.stripMargin
  }

  def setParent(): Unit = {
    parent =
      s"""
         |    <parent>
         |        <artifactId>$artifactId</artifactId>
         |        <groupId>$groupId</groupId>
         |        <version>$version</version>
         |    </parent>
    """.stripMargin
  }

  def setArtifactId(m: String): Unit = {
    aid =
      s"""
         |    <artifactId>$artifactId-$m</artifactId>
      """.stripMargin
  }

  def setDependencies(m: String): Unit = {
    dependencies = m match {
      case "context" =>
        s"""
           |    <dependencies>
           |        <dependency>
           |            <groupId>$groupId</groupId>
           |            <artifactId>$artifactId-server</artifactId>
           |            <version>$version</version>
           |        </dependency>
           |    </dependencies>
    """.stripMargin
      case "server" =>
        s"""
           |    <dependencies>
           |        <dependency>
           |            <groupId>$groupId</groupId>
           |            <artifactId>$artifactId-api</artifactId>
           |            <version>$version</version>
           |        </dependency>
        """.stripMargin +
          """
            |        <!--flink-->
            |        <dependency>
            |            <groupId>org.apache.flink</groupId>
            |            <artifactId>flink-streaming-scala_${scala.binary.version}</artifactId>
            |        </dependency>
            |        <dependency>
            |            <groupId>org.apache.flink</groupId>
            |            <artifactId>flink-connector-kafka_${scala.binary.version}</artifactId>
            |        </dependency>
            |        <!--spark-->
            |        <dependency>
            |            <groupId>org.apache.spark</groupId>
            |            <artifactId>spark-core_${scala.binary.version}</artifactId>
            |        </dependency>
            |        <dependency>
            |            <groupId>org.apache.spark</groupId>
            |            <artifactId>spark-streaming-kafka-0-10_${scala.binary.version}</artifactId>
            |        </dependency>
            |        <dependency>
            |            <groupId>org.apache.spark</groupId>
            |            <artifactId>spark-streaming_${scala.binary.version}</artifactId>
            |        </dependency>
            |        <dependency>
            |            <groupId>org.apache.spark</groupId>
            |            <artifactId>spark-sql_${scala.binary.version}</artifactId>
            |        </dependency>
            |        <!--bulkload-->
            |        <dependency>
            |            <groupId>org.apache.hbase</groupId>
            |            <artifactId>hbase-server</artifactId>
            |        </dependency>
            |        <dependency>
            |            <groupId>org.apache.hbase</groupId>
            |            <artifactId>hbase-mapreduce</artifactId>
            |        </dependency>
            |        <dependency>
            |            <groupId>org.apache.hbase</groupId>
            |            <artifactId>hbase-client</artifactId>
            |        </dependency>
            |    </dependencies>
          """.stripMargin
      case "root" =>
        """
          |    <dependencies>
          |        <dependency>
          |            <groupId>org.aisql.bigdata</groupId>
          |            <artifactId>bigdata-base-framework</artifactId>
          |            <version>1.0-SNAPSHOT</version>
          |        </dependency>
          |        <dependency>
          |            <groupId>org.aisql.bigdata</groupId>
          |            <artifactId>bigdata-base-component</artifactId>
          |            <version>1.0-SNAPSHOT</version>
          |        </dependency>
          |        <dependency>
          |            <groupId>org.scala-lang</groupId>
          |            <artifactId>scala-library</artifactId>
          |            <version>${scala.version}</version>
          |            <scope>provided</scope>
          |        </dependency>
          |        <dependency>
          |            <groupId>org.scalaj</groupId>
          |            <artifactId>scalaj-http_2.10</artifactId>
          |            <version>2.3.0</version>
          |        </dependency>
          |        <dependency>
          |            <groupId>com.alibaba</groupId>
          |            <artifactId>fastjson</artifactId>
          |            <version>1.2.25</version>
          |        </dependency>
          |        <!-- logback -->
          |        <dependency>
          |            <groupId>org.slf4j</groupId>
          |            <artifactId>slf4j-api</artifactId>
          |            <version>${slf4j.version}</version>
          |        </dependency>
          |        <dependency>
          |            <groupId>ch.qos.logback</groupId>
          |            <artifactId>logback-classic</artifactId>
          |            <version>${logback.version}</version>
          |        </dependency>
          |        <dependency>
          |            <groupId>ch.qos.logback</groupId>
          |            <artifactId>logback-access</artifactId>
          |            <version>${logback.version}</version>
          |        </dependency>
          |    </dependencies>
        """.stripMargin
      case _ => ""
    }
  }

  def setBuild(): Unit = {
    build =
      """
        |    <build>
        |        <sourceDirectory>src/main/scala</sourceDirectory>
        |        <testSourceDirectory>src/test/scala</testSourceDirectory>
        |        <plugins>
        |            <plugin>
        |                <groupId>org.apache.maven.plugins</groupId>
        |                <artifactId>maven-compiler-plugin</artifactId>
        |                <configuration>
        |                    <source>${java.version}</source>
        |                    <target>${java.version}</target>
        |                    <showWarnings>true</showWarnings>
        |                    <compilerArguments>
        |                        <verbose/>
        |                        <bootclasspath>${java.home}/lib/rt.jar:${java.home}/lib/jce.jar</bootclasspath>
        |                    </compilerArguments>
        |                </configuration>
        |            </plugin>
        |            <plugin>
        |                <groupId>org.scala-tools</groupId>
        |                <artifactId>maven-scala-plugin</artifactId>
        |                <version>2.15.0</version>
        |                <executions>
        |                    <execution>
        |                        <id>scala-compile-first</id>
        |                        <goals>
        |                            <goal>compile</goal>
        |                        </goals>
        |                    </execution>
        |                </executions>
        |            </plugin>
        |        </plugins>
        |    </build>
      """.stripMargin
  }

  def setProperties() = {
    properties =
      """
        |    <properties>
        |        <java.version>1.8</java.version>
        |        <scala.version>2.11.11</scala.version>
        |        <scala.binary.version>2.11</scala.binary.version>
        |        <spark.version>2.2.0</spark.version>
        |        <hadoop.version>3.0.0</hadoop.version>
        |        <hbase.version>2.0.0</hbase.version>
        |        <hive.version>2.1.1</hive.version>
        |        <mysql.version>5.1.35</mysql.version>
        |        <slf4j.version>1.7.21</slf4j.version>
        |        <logback.version>1.1.7</logback.version>
        |        <flink.version>1.7.2</flink.version>
        |    </properties>
      """.stripMargin
  }

  def setDependencyManagement(): Unit = {
    dependencyManagement =
      """
        |    <dependencyManagement>
        |        <dependencies>
        |            <!--flink-->
        |            <dependency>
        |                <groupId>org.apache.flink</groupId>
        |                <artifactId>flink-streaming-scala_${scala.binary.version}</artifactId>
        |                <version>${flink.version}</version>
        |                <scope>provided</scope>
        |            </dependency>
        |            <dependency>
        |                <groupId>org.apache.flink</groupId>
        |                <artifactId>flink-connector-kafka_${scala.binary.version}</artifactId>
        |                <version>${flink.version}</version>
        |                <scope>provided</scope>
        |            </dependency>
        |            <!--spark-->
        |            <dependency>
        |                <groupId>org.apache.spark</groupId>
        |                <artifactId>spark-core_${scala.binary.version}</artifactId>
        |                <version>${spark.version}</version>
        |                <scope>provided</scope>
        |            </dependency>
        |            <dependency>
        |                <groupId>org.apache.spark</groupId>
        |                <artifactId>spark-streaming-kafka-0-10_${scala.binary.version}</artifactId>
        |                <version>${spark.version}</version>
        |                <scope>provided</scope>
        |            </dependency>
        |            <dependency>
        |                <groupId>org.apache.spark</groupId>
        |                <artifactId>spark-streaming_${scala.binary.version}</artifactId>
        |                <version>${spark.version}</version>
        |                <scope>provided</scope>
        |            </dependency>
        |            <dependency>
        |                <groupId>org.apache.spark</groupId>
        |                <artifactId>spark-sql_${scala.binary.version}</artifactId>
        |                <version>${spark.version}</version>
        |                <scope>provided</scope>
        |            </dependency>
        |            <!--bulkload-->
        |            <dependency>
        |                <groupId>org.apache.hbase</groupId>
        |                <artifactId>hbase-server</artifactId>
        |                <version>${hbase.version}</version>
        |                <scope>provided</scope>
        |            </dependency>
        |            <dependency>
        |                <groupId>org.apache.hbase</groupId>
        |                <artifactId>hbase-mapreduce</artifactId>
        |                <version>${hbase.version}</version>
        |                <scope>provided</scope>
        |            </dependency>
        |            <dependency>
        |                <groupId>org.apache.hbase</groupId>
        |                <artifactId>hbase-client</artifactId>
        |                <version>${hbase.version}</version>
        |                <scope>provided</scope>
        |            </dependency>
        |            <!--mysql-->
        |            <dependency>
        |                <groupId>mysql</groupId>
        |                <artifactId>mysql-connector-java</artifactId>
        |                <version>${mysql.version}</version>
        |                <scope>provided</scope>
        |            </dependency>
        |            <!--redis-->
        |            <dependency>
        |                <groupId>org.redisson</groupId>
        |                <artifactId>redisson</artifactId>
        |                <version>3.5.4</version>
        |            </dependency>
        |            <dependency>
        |                <groupId>redis.clients</groupId>
        |                <artifactId>jedis</artifactId>
        |                <version>2.9.0</version>
        |            </dependency>
        |        </dependencies>
        |    </dependencyManagement>
      """.stripMargin
  }

  override def toString: String = {
    pomResultBuffer.append(
      """<?xml version="1.0" encoding="UTF-8"?>
        |<project xmlns="http://maven.apache.org/POM/4.0.0"
        |         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        |         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        |    <modelVersion>4.0.0</modelVersion>
      """.stripMargin)
    pomResultBuffer.append(coord)
    pomResultBuffer.append(module)
    pomResultBuffer.append(parent)
    pomResultBuffer.append(aid)
    pomResultBuffer.append(properties)
    pomResultBuffer.append(dependencies)
    pomResultBuffer.append(dependencyManagement)
    pomResultBuffer.append(build)
    pomResultBuffer.append(
      """
        |</project>
      """.stripMargin)
    pomResultBuffer.mkString("")
  }
}
