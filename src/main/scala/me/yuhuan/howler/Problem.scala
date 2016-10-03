package me.yuhuan.howler

/**
  * Created by yuhuan on 10/03/2016.
  */
case class Problem(id: ProblemId, score: Double, rules: Map[RuleId, Rule], description: String)
