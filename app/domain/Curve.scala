package domain

import scala.reflect._
import org.joda.time._


@BeanInfo
case class Curve(
  val id: Long = 0,
  val startDate: DateTime = new DateTime,
  val a: Double = 0,
  val b: Double = 0,
  val c: Double = 0,
  val d: Double = 0,

  val permutationHistory: Option[PermutationHistory] = None
){}

