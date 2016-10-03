package me.yuhuan.howler

import java.nio.file._


/**
  * Created by yuhuan on 10/03/2016.
  */
object Howler extends App {

  val argsMap = args.grouped(2).map { arr =>
    arr(0) -> arr(1)
  }.toMap

  val assignmentXmlPath = argsMap("-a")
  val studentsXmlPath = argsMap("-s")
  val gradingsXmlPath = argsMap("-g")

  val data = new Data(
    assignmentXmlStream = Files.newInputStream(Paths.get(assignmentXmlPath)),
    studentsXmlStream = Files.newInputStream(Paths.get(studentsXmlPath)),
    gradingsXmlStream = Files.newInputStream(Paths.get(gradingsXmlPath))
  )

}
