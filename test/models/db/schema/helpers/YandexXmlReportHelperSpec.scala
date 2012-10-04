package models.db.schema
package helpers

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import org.squeryl.PrimitiveTypeMode.{inTransaction}

import java.lang.RuntimeException

import play.api.test._
import play.api.test.Helpers._

import scala.io.Source


class YandexXmlReportHelperSpec extends FlatSpec with ShouldMatchers{

  "createBannerPhraseStats" should
    "throwgh an Error if start_date or end_date is missed" in {
      // get test data
      val file_name = "test/models/db/schema/xml/report1.xml"
      val node = scala.xml.XML.loadFile(file_name)
      // create a copy w/o end_date
      val stat = <report> {(node\"stat")}</report>

      // just checking - not Exceptions
      YandexXmlReportHelper.createBannerPhraseStats(node)
      // but w/ no dates
      intercept[RuntimeException] {
        YandexXmlReportHelper.createBannerPhraseStats(stat)
      }
    }

    it should "create List((BannerPhraseHelper, BannerPhraseStats))" in {
      // get test data
      val file_name = "test/models/db/schema/xml/report1.xml"
      val node = scala.xml.XML.loadFile(file_name)

      val res = YandexXmlReportHelper.createBannerPhraseStats(node)
      res.length should be (3)
      res(0)._1.network_banner_id should be ("123456")
    }
}


