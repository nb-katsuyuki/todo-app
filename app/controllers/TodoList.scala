/**
 * to do sample project
 */

package controllers

import javax.inject._
import play.api.mvc._
import lib.model.Todo
import model.ViewValueTodoList
import lib.persistence._

// import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration
// import scala.util.{Failure, Success} // Try,

// おまじないだと思って無視してください
// import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class TodoListController @Inject() (
    val controllerComponents: ControllerComponents
) extends BaseController {

  def index() =
    Action { implicit req =>
      val todoList: Seq[Todo] =
        Await.result(onMySQL.TodoRepository.all, duration.Duration.Inf)
      //@FIXME エラー時動作

      val vv = ViewValueTodoList(
        title = "Todo一覧",
        cssSrc = Seq("main.css"),
        jsSrc = Seq("main.js"),
        todoList = todoList,
        debug = None
      )
      Ok(views.html.TodoList(vv))
    }
}
