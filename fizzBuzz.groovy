import groovy.time.*
public class hello {
  // testing 2 different ways to fizzbuzz
    static int start = 1
  static int end = 100
  static int fizz = 3
  static int buzz = 5

  static def main(args) {
    def timeStart = new Date()
    (start..end).each{println "${it%fizz?'':'Fizz'}${it%buzz?'':'Buzz'}" ?: it }
    def timeStop = new Date()
    TimeDuration duration = TimeCategory.minus(timeStop, timeStart)
    println(duration)
    1.upto(3, {println("")})
    timeStart = new Date()
    start.upto(end, {
      if (it % fizz == 0) print("Fizz")
      if (it % buzz == 0) print("Buzz")
      if (it % fizz != 0 && it % buzz != 0) print(it)
      print("\n")})
    timeStop = new Date()
    duration = TimeCategory.minus(timeStop, timeStart)
    println(duration)
  }
}
