package org.karatachi.whoareyou

import scala.Array.canBuildFrom
import scala.io.Source
import org.apache.commons.net.whois.WhoisClient
import java.net.SocketException

object Bootstrap extends App {
  for (ipaddress <- Source.fromFile("address.txt").getLines) {
    var retry = true
    while (retry) {
      try {
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
        retry = false
      } catch {
        case e: SocketException =>
          Thread.sleep(20000)
          retry = true
      }
    }
  }
}
