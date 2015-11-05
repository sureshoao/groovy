/*
   Get flight plans from flightaware,com
   */
class FlightawareIntro {
  static main(args) {
  	def theMainURL = "http://flightaware.com/live"
  	def theURL = theMainURL + "/aircrafttype/"
  	def outFile = "C:\\Users\\chuck\\My Documents\\My Dropbox\\routes.txt"
  	File file = new File(outFile) << (new Date().toString()) << "\r\n"
  	['SR22', 'LNC4', 'COL4', 'M20T', 'C750'].each {
  	  def theTypeCodeHTML = new URL(theURL + it).openConnection().content.text
  	  def tripMatcher = theTypeCodeHTML =~ /<tr>\n(<td.*<\/td>\n){7}<\/tr>/
  	  def typeCode = it
  	  tripMatcher.each {
    		def planeMatcher = it =~ /href.*">(.*)?<\/a><\/span><\/td>/
    		def theRouteHTMLURL = theMainURL + "/flight/" + planeMatcher[0][1]
    		def theRouteHTML = new URL(theRouteHTMLURL).openConnection().content.text
    		
    		def routeMatcher = theRouteHTML =~ /<td\ colspan="3"\sclass="smallrow2"[^>]+>([^\(]+)\(<a/
    		def airportsMatcher = theRouteHTML =~ /"origin":"(.*?)","destination":"(.*?)"/
    		def distanceMatcher = theRouteHTML =~ /Direct: (\d+\ )/
    		def altitudeMatcher = theRouteHTML =~ /filed_alt\s*=\s*(\d+)/
    		def speedMatcher = theRouteHTML =~ /Speed<\/th>[\s\S]+<td><span\ title="Mach[^"]+">(\d+ kts)/
    		
    		if (airportsMatcher.size() > 0) {    // make sure we have origin and dest airports
    		  def origin = airportsMatcher[0][1]
    		  def dest = airportsMatcher[0][2]
    		  if (origin != dest) { // not interested in routes where origin and dest are the same  
    		    def theRoute = routeMatcher.size() > 0 ? routeMatcher[0][1].replaceAll("\\s+"," ") : " DIRECT "
    		    def theDistance = (distanceMatcher.size() > 0 ? distanceMatcher[0][1] : "").replaceAll("\\s+", "")
    		    def theAltitude = (altitudeMatcher.size() > 0 ? altitudeMatcher[0][1] : "").replaceAll("\\s+", "")
    		    def theSpeed = (speedMatcher.size() > 0 ? speedMatcher[0][1] : "").replaceAll("\\s+", "")
    		    file << typeCode + ": " + theRouteHTMLURL + " [" + origin + " " + theRoute + " " +  dest + "] " + theAltitude + "', " + theDistance + "nm, " + theSpeed << "\r\n"
          }
        }
      }
    }
  }
}
