package me.yuhuan.howler

/**
  * @param studentId
  * @param problemGradings A mapping from Problem ID to the set of Rule IDs that the student triggered.
  *
  * Created by yuhuan on 10/03/2016.
  */
case class Grading(studentId: String, problemGradings: Map[String, Set[String]])
