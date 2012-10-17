package dao.squerylorm

import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl._
import java.util.Date
import org.joda.time._
import scala.reflect._
import common._



@BeanInfo
case class Permutation(
  val curve_id: Long = 0, //fk
  val date: Date = new Date()
)extends domain.Permutation with KeyedEntity[Long]
{
  val id: Long = 0
  def dateTime = new DateTime(date)

  // Curve -* Permutation relation
  lazy val curveRel: ManyToOne[Curve] = AppSchema.curvePermutations.right(this)

  // Permutation -* Position relation
  lazy val positionsRel: OneToMany[Position] = AppSchema.permutationPositions.left(this)

  /** default put - save to db
  */
  def put(): Permutation = inTransaction { AppSchema.permutations insert this }


  /** creates permutation: Map[BannerPhrase, Position]
  */
  // TODO: Optimize
  def permutation: Map[domain.BannerPhrase, domain.Position] = inTransaction {
    // get positions
    val bp_p_seq = positionsRel map ((x: Position ) =>
      (x.bannerPhraseRel.head, x)
    )
    bp_p_seq.toList.toMap
  }


}

object Permutation{

  /** get Permutation from DB
  */
  def get_by_id(id: Long) = inTransaction{ AppSchema.permutations.where(a => a.id === id).headOption}

  def apply(curve: domain.Curve, p: domain.Permutation): Permutation =
    Permutation(
      curve_id = curve.id,
      date = p.dateTime.toDate
    )


  /** creates Permutation and its Positions
  */
  def create(curve: domain.Curve, permutation: domain.Permutation): Unit = inTransaction{
    //create Permutation
    val p = Permutation(curve, permutation).put
    // create Positions
    val positions = permutation.permutation map(
      //(bp , pos) =>
      (x: (domain.BannerPhrase, domain.Position)) =>
        Position(
          bannerphrase_id = x._1.id,
          permutation_id = p.id,
          position = x._2.position
        )
    )
    AppSchema.positions.insert(positions)
  }

}


