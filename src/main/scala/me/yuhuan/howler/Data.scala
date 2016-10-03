package me.yuhuan.howler
import java.io.InputStream

import scala.xml._

/**
  * Created by yuhuan on 10/03/2016.
  */
class Data(rubricXmlStream: InputStream,
           studentsXmlStream: InputStream,
           gradingsXmlStream: InputStream) {

  //region Parse Assignment Definition

  val rubric = loadRubric(XML.load(rubricXmlStream))
  val students = loadStudentsDefinition(XML.load(studentsXmlStream))
  val gradings = loadGradings(XML.load(gradingsXmlStream))

  //endregion

  val rules: Map[RuleId, Rule] = rubric.globalRules ++
    (for {
      (pid, p) <- rubric.problems
      (rid, r) <- p.rules
    } yield s"$pid.$rid" -> r).toMap

  val bp = 0

  def gradesOf(studentId: String, problemId: String): Double = {
    var totalScore = rubric.problems(problemId).score
    val rulesApplied = gradings(studentId).problemGradings(problemId)
    rulesApplied foreach { ruleId => totalScore += rules(ruleId).score }
    totalScore
  }

  def rulesAppliedOn(studentId: StudentId, problemId: ProblemId): Set[Rule] = ???
  def penaltiesAppliedOn(studentId: StudentId, problemId: ProblemId) = rulesAppliedOn(studentId, problemId).filter(_.score < 0)
  def bonusesAppliedOn(studentId: StudentId, problemId: ProblemId) = rulesAppliedOn(studentId, problemId).filter(_.score > 0)

  /**
    * The email generated for a student
    * <blockquote>
    *   Dear LastName, <br/><br/>
    *
    *   You have received a total of <strong>TOTAL_SCORE</strong> in <strong>Assignment 1</strong>. <br/><br/>
    *
    *   == Problem 1 == <br/>
    *
    *   The following aspects have negative affected your grade:
    *   <ul>
    *     <li>Blah -4</li>
    *     <li>Blah -2</li>
    *     <li>Blah -6</li>
    *   </ul>
    *
    *   The following aspects have positively affected your grade:
    *   <ul>
    *     <li>Blah +4</li>
    *     <li>Blah +2</li>
    *   </ul>
    *
    *
    *   == Problem 2 == <br/>
    *
    *   The following aspects have negative affected your grade:
    *   <ul>
    *     <li>Blah -4</li>
    *     <li>Blah -2</li>
    *     <li>Blah -6</li>
    *   </ul>
    *
    *   The following aspects have positively affected your grade:
    *   <ul>
    *     <li>Blah +4</li>
    *     <li>Blah +2</li>
    *   </ul>
    *
    * </blockquote>
    * @return
    */
  def generateEmails: Map[StudentId, String] = {
    ???
  }



  def loadRubric(x: xml.Node) = {
    Rubric(
      id = x \@ "id",
      problems = (x \ "Problems" \ "Problem").map { p =>
        (p \@ "id") -> Problem(
          id = p \@ "id",
          score = (p \@ "score").toDouble,
          rules = (p \ "Rules" \ "Rule").map { r =>
            (r \@ "id") -> Rule(
              id = r \@ "id",
              score = (r \@ "score").toDouble,
              description = r \@ "description"
            )
          }.toMap,
          description = p \@ "description"
        )
      }.toMap,
      globalRules = (x \ "GlobalRules" \ "Rule").map { r =>
        (r \@ "id") -> Rule(
          id = r \@ "id",
          score = (r \@ "score").toDouble,
          description = r \@ "description"
        )
      }.toMap,
      description = x \@ "description"
    )
  }

  def loadStudentsDefinition(x: xml.Node) = {
    (x \ "Student").map { s =>
      Student(
        id = s \@ "id",
        firstName = s \@ "firstName",
        lastName = s \@ "lastName",
        email = s \@ "email"
      )
    }
  }

  def loadGradings(x: xml.Node): Map[StudentId, Grading] = {
    (x \ "Grading").map { g =>
      (g \@ "studentId") -> Grading(
        studentId = g \@ "studentId",
        problemGradings = (g \ "ProblemGrading").map { p =>
          (p \@ "problemId") -> (p \@ "rules").split(' ').toSet
        }.toMap
      )
    }.toMap
  }

}
