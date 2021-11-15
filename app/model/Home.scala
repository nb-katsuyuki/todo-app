/**
 * to do sample project
 */

package model

import lib.model.Todo

// Topページのviewvalue
case class ViewValueHome(
    title: String,
    cssSrc: Seq[String],
    jsSrc: Seq[String],
    todoes: Seq[Todo.WithNoId] // @TODO とりあえず
) extends ViewValueCommon
