package dao

import scala.collection.JavaConversions._
import java.util.{Map => JMap, List => JList}
import org.joda.time._
import _root_.domain._

/** Trait Dao
*/
trait Dao {
  // Shallow copies of Campaigns. Fast DB retrieval
  def getCampaign(userName: String, networkName: String, networkCampaignId: String): Option[ Campaign ]
  def getCampaigns(userName: String, networkName: String): List[Campaign]

  /** retrieves full domain model (Campaign and its Histories) for given Dates from DB
  */
  def getCampaignHistory(campaign_id: Long, startDate: DateTime, endDate: DateTime): CampaignHistory


  /** creates CampaignPerformance in DB
  */
  def createCampaignPerformanceReport(campaign: Campaign, performance: Performance):  Performance

  /** creates BannerPhrasePerformance records in DB
  * @throw java.util.RunTimeException
  * TODO: add Exception checking in Controllers
  * TODO: fix. it should create new BannerPhrase in case it's not present in DB.
  */
  def createBannerPhrasesPerformanceReport(campaign: Campaign, report: Map[BannerPhrase, Performance]): Boolean

  /** mutable counterpart
  */
  def createBannerPhrasesPerformanceReport(campaign: Campaign,
    report: collection.mutable.Map[BannerPhrase, Performance]): Boolean =
    createBannerPhrasesPerformanceReport( campaign, report.toMap)

  def createBannerPhrasesPerformanceReport(campaign: Campaign,
    report: JMap[BannerPhrase, Performance]): Boolean =
    createBannerPhrasesPerformanceReport( campaign, report.toMap)



  /** creates new Campaign record
  */
  def create(campaign: Campaign): Campaign
  /** creates new records in EndDateHistory or BudgetHistory
  * TODO: change definition. funct. should take {new_budget: date, new_endDate: date} ...
  */
  def update(campaign: Campaign, date: DateTime): Campaign


  /** creates new User record
  */
  def create(user: User): User
  /** updates user.name
  */
  def update(user: User): User
  /** select User by name
  */
  def getUser(name: String): Option[User]


  /** creates new Network record
  */
  def create(network: Network): Network
  /** select Network by name
  */
  def getNetwork(name: String): Option[Network]



  /** creates Permutation record
  * @throw java.util.RunTimeException
  * TODO: add Exception checking in Controllers
  * probably add back curve to Permutation and don't use curve in def.
  */
  def create(curve: domain.Curve, permutation: domain.Permutation): Unit

  /** creates Recommendation records
  */
  def create(recommendation: Recommendation): Unit

}
