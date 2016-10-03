package me.yuhuan.howler

/**
  * Created by yuhuan on 10/03/2016.
  */
case class Grading(studentId: StudentId, problemGradings: Map[ProblemId, Set[RuleId]])
