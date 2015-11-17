
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/lakhdeep/dashboard-backend/conf/routes
// @DATE:Tue Nov 17 17:48:47 SGT 2015


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
