/**
 * to do sample project
 */

package controllers

import javax.inject._
import play.api.mvc._
import lib.model.Todo
import model.ViewValueHome

@Singleton
class HomeController @Inject() (val controllerComponents: ControllerComponents)
    extends BaseController {

  def index() = Action { implicit req =>
    val vv = ViewValueHome(
      title = "Home",
      cssSrc = Seq("main.css"),
      jsSrc = Seq("main.js"),
      // @TODO とりあえずスタブ
      todoes = (1 to 10).map(i =>
        Todo(
          title = s"title${i}",
          body = Some(s"body${i}"),
          state = Todo.Status.PENDING
        )
      )
    )
    Ok(views.html.Home(vv))
  }
}
