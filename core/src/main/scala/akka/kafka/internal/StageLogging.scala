/*
 * Copyright (C) 2014 - 2016 Softwaremill <http://softwaremill.com>
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package akka.kafka.internal

import akka.event.{LoggingAdapter, NoLogging}
import akka.stream.ActorMaterializer
import akka.stream.stage.GraphStageLogic

// TODO this can be removed when https://github.com/akka/akka/issues/18793 has been implemented
trait StageLogging { self: GraphStageLogic =>

  private var _log: LoggingAdapter = _

  protected def logSource: Class[_] = this.getClass

  def log: LoggingAdapter = {
    // only used in StageLogic, i.e. thread safe
    if (_log eq null) {
      materializer match {
        case a: ActorMaterializer =>
          _log = akka.event.Logging(a.system, logSource)
        case _ =>
          _log = NoLogging
      }
    }
    _log
  }

}
