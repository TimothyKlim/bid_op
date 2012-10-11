package domain

import scala.reflect._
import scala.collection.immutable.List
import org.joda.time._


@BeanInfo
class ActualBidHistory(
  val seq: Seq[( DateTime, Double )] = Seq()
){}

