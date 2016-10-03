package me.yuhuan.howler

/**
  * Created by yuhuan on 10/03/2016.
  */
case class Assignment(id: AssignmentId, problems: Map[ProblemId, Problem], globalRules: Map[RuleId, Rule], description: String)
