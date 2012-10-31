package dao.squerylorm

import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl._
import java.sql.Timestamp
import org.joda.time._
import scala.reflect._
import common._



@BeanInfo
case class NetAdvisedBidHistory(
  val bannerphrase_id: Long = 0, //fk
  val date: Timestamp = new Timestamp(0),
  val a: Double = 0,
  val b: Double = 0,
  val c: Double = 0,
  val d: Double = 0
)extends domain.NetAdvisedBids with KeyedEntity[Long] with History
{
  val id: Long = 0
  def dateTime = date

  /**
  * default put - save to db
  **/
  def put(): NetAdvisedBidHistory = inTransaction { AppSchema.netadvisedbidhistory insert this }

}


