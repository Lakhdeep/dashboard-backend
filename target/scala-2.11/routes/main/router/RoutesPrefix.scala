
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/lakhdeep/Stuff/play/activator-1.3.6-minimal/play-java/conf/routes
// @DATE:Wed Oct 28 17:13:07 SGT 2015


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
