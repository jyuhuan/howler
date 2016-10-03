package me.yuhuan.howler

import java.nio.file._


/**
  * Created by yuhuan on 10/03/2016.
  */
object Howler extends App {

  val argsMap = args.grouped(2).map { arr =>
    arr(0) -> arr(1)
  }.toMap

  val rubricXmlPath = argsMap("-r")
  val studentsXmlPath = argsMap("-s")
  val gradingsXmlPath = argsMap("-g")
  val fromAddress = argsMap("-from")
  val byLine = argsMap("-by")

  val bp = 0

  val data = new Data(
    rubricXmlStream = Files.newInputStream(Paths.get(rubricXmlPath)),
    studentsXmlStream = Files.newInputStream(Paths.get(studentsXmlPath)),
    gradingsXmlStream = Files.newInputStream(Paths.get(gradingsXmlPath))
  )

}
