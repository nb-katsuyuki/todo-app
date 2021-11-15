/**
 * to do sample project
 */

package controllers

import javax.inject._
import play.api.mvc._
import lib.model.Todo
import lib.model.TodoCategory
import model.TodoItem
import model.TodoPage
import lib.persistence._

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration
// import scala.util.{Failure, Success} // Try,

// おまじないだと思って無視してください
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class TodoController @Inject() (
    val controllerComponents: ControllerComponents
) extends BaseController {

  // def F(id: String): Future[Seq[String]] = Future({ (1 to 10).map(_ + id) })
  //def F(id:String): Future[Seq[String]] = Future({ (1 to 10).map(_ + id) })

  def index() =
    Action { implicit req =>
      // @TODO Actionに直接処理を書くのは良くない

      // この処理だとデータ大量にあったら終了
      // 本当はTODOリポジトリの時点でカテゴリとマージした状態にしたい。
      // `select * from to_do t,to_do_category c where t.category_id = c.id`こんなの
      val cf: Future[Seq[TodoCategory]] = onMySQL.TodoCategoryRepository.all
      val tf: Future[Seq[Todo]]         = onMySQL.TodoRepository.all

      val res: Future[Seq[TodoItem]] = for {
        categorys <- cf
        todos     <- tf
      } yield {
        todos.map((todo: Todo) =>
          TodoItem(
            todo = todo,
            category = categorys
              .find(_.id.getOrElse(0) == todo.categoryId)
              .getOrElse {
                TodoCategory(
                  id = None,
                  name = "不明" + todo.categoryId,
                  slug = "unknown",
                  color = TodoCategory.Color.UNKNOWN
                )
              }
          )
        )
      }
      val todoList: Seq[TodoItem]    = Await.result(res, duration.Duration.Inf)

      val page = TodoPage(
        title = "Todo一覧",
        cssSrc = Seq("main.css"),
        jsSrc = Seq("main.js"),
        todoList = todoList,
        debug = None
      )

      Ok(views.html.Todo(page))

    }
}
