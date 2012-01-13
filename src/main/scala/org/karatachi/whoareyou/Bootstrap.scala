package org.karatachi.whoareyou

import org.apache.commons.net.whois.WhoisClient

object Bootstrap extends App {

  val ipaddresses = Array("122.216.165.218", "122.216.165.218")

  for (ipaddress <- ipaddresses) {
    val whois = new WhoisClient()
    whois.connect("whois.nic.ad.jp")
    whois
      .query("NET " + ipaddress + "/e")
      .split("\n")
      .filter(_.startsWith("g."))
      .map(_.substring("g. [Organization]".length).trim)
      .map((ipaddress, _))
      .foreach(println)
    whois.disconnect();
  }
}
