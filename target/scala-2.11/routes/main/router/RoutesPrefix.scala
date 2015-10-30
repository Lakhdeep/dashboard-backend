
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/lakhdeep/dashboard-backend/conf/routes
// @DATE:Thu Oct 29 14:24:57 SGT 2015


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
