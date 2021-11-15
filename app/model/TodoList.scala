/**
 * to do sample project
 */

package model

import lib.model.Todo

// Todo一覧ページのviewvalue
case class ViewValueTodoList(
    title: String,
    cssSrc: Seq[String],
    jsSrc: Seq[String],
    todoList: Seq[Todo],
    debug: Any
) extends ViewValueCommon
