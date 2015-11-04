/* to run:  groovy extractQueries.groovy theQueryLog.0.gz  */
import java.util.zip.*

final patternQuery = ~/Query\s+((?:SELECT|INSERT|UPDATE|DELETE).*)/

new GZIPInputStream(new FileInputStream(args[0])).eachLine {
  theLine ->
    queryMatcher = patternQuery.matcher(theLine)
    if (queryMatcher.find())
       println queryMatcher[0][1]+";"
}
