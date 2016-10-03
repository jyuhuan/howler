package me.yuhuan.howler

/**
  * Created by yuhuan on 10/03/2016.
  */
case class Rubric(id: RubricId, problems: Map[ProblemId, Problem], globalRules: Map[RuleId, Rule], description: String)
