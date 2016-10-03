package me.yuhuan.howler
import java.io.InputStream

import scala.xml._

/**
  * Created by yuhuan on 10/03/2016.
  */
class Data(assignmentXmlStream: InputStream,
           studentsXmlStream: InputStream,
           gradingsXmlStream: InputStream) {

  //region Parse Assignment Definition

  val assignment = loadAssignmentDefinition(XML.load(assignmentXmlStream))
  val students = loadStudentsDefinition(XML.load(studentsXmlStream))
  val gradings = loadGradings(XML.load(gradingsXmlStream))

  def gradesOf(studentId: String): Double = ???
  def rulesAppliedOn(studentId: String): Set[Rule] = ???
  def penaltiesAppliedOn(studentId: String) = rulesAppliedOn(studentId).filter(_.score < 0)
  def bonusesAppliedOn(studentId: String) = rulesAppliedOn(studentId).filter(_.score > 0)


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
  def generateEmails: Map[String, String] = {
    ???
  }

  val bp = 0

  //endregion

  def loadAssignmentDefinition(x: xml.Node) = {
    Assignment(
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

  def loadGradings(x: xml.Node) = {
    (x \ "Grading").map { g =>
      Grading(
        studentId = g \@ "studentId",
        problemGradings = (g \ "ProblemGrading").map { p =>
          (p \@ "problemId") -> (p \@ "rules").split(' ').toSet
        }.toMap
      )
    }
  }

}
