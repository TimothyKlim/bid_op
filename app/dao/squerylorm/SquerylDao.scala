package dao.squerylorm

import org.squeryl.{Schema, KeyedEntity, Query}
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.dsl._
import java.util.Date
import org.joda.time._
import scala.reflect._
import common._
import domain.{Campaign => dCam, Performance => dPerf, Permutation => dPerm}


@BeanInfo
class SquerylDao extends dao.Dao
{



  override def getCampaign(userName: String, networkName: String, networkCampaignId: String) =
    Campaign.select(userName: String, networkName: String, networkCampaignId: String).headOption

  override def getCampaigns(userName: String, networkName: String) =
    Campaign.select(userName: String, networkName: String)


  /** creates CampaignPerformance in DB
  * TODO: Optimize. It has 2 DB trips now
  */
  def createCampaignPerformanceReport(campaign: dCam, performance: dPerf) =
    CampaignPerformance(campaign, performance).put


  /** creates BannerPhrasePerformance records in DB
  * TODO: fix. it should create new BannerPhrase in case it's not present in DB.
  */
  def createBannerPhrasesPerformanceReport(campaign: domain.Campaign, report: Map[domain.BannerPhrase, dPerf]) =
    //BannerPhrasePerformance.create(report)
    Campaign.get_by_id(campaign.id).create(report)


  /** retrieves full domain model (Campaign and its Histories) for given Dates from DB
  * TODO: Optimize. CampaignHistory is not complete. It's done partially only
  */
  def getCampaignHistory(campaign_id: Long, startDate: DateTime, endDate: DateTime): domain.CampaignHistory =
    Campaign.selectCampaignHistory(campaign_id: Long, startDate: DateTime, endDate: DateTime, daos = this)




  /** creates Campaign record
  */
  def create(campaign:dCam) = Campaign.create(cc = campaign)

  /** creates new records in EndDateHistory or BudgetHistory
  * TODO: change definition. funct. should take {new_budget: date, new_endDate: date} ...
  */
  def update(campaign:dCam, date: DateTime) = ???



  /** creates new User record
  */
  def create(user: domain.User): domain.User = ???
  /** updates user.name
  */
  def update(user: domain.User): domain.User = ???
  /** select User by name
  */
  def getUser(name: String): Option[domain.User] = User.select(name)


  /** creates new Network record
  */
  def create(network: domain.Network): domain.Network = ???
  /** select Network by name
  */
  def getNetwork(name: String): Option[Network] = Network.select(name)


  /** creates Permutation record
  * @throw java.util.RunTimeException
  * TODO: add Exception checking in Controllers
  * probably add back curve to Permutation and don't use curve in def.
  */
  def create(campaign: domain.Campaign, permutation: domain.Permutation) =
    Permutation.create(campaign, permutation)



  /** creates Recommendation record
  */
  def create(recommendation:domain.Recommendation) = RecommendationHistory.create(recommendation)




}

