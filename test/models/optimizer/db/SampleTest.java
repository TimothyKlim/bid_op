package models.optimizer.db;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

//application data model
import models.db.schema.*;


public class SampleTest{

  @Test
  public void simpleCheck() {
      int a = 1 + 1;
      assertThat(a).isEqualTo(2);
  }

  @Test
  public void loosyCheck() {
      String s = "hi";
      assertThat(s).containsIgnoringCase("H");
  }


  //using AppDb - play application in memory DB
  //save and retrieve Campaign from DB
  //@Test
  @Test
  public void findCampaignById() {
    running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
              Campaign camp = new Campaign("Coda", "Yandex", "y4400");
              assertThat(camp.id()).isEqualTo(0);
              //save in DB
              Campaign c2 = camp.put();
              assertThat(c2.id()).isNotEqualTo(0);
              //get from DB
              Campaign res = camp.get_by_id(c2.id());
              assertThat(res.id()).isNotEqualTo(0);
            }
          });
  }

}
