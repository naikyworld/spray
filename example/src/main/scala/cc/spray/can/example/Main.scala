/*
 * Copyright (C) 2011 Mathias Doenitz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.spray.can
package example

import akka.config.Supervision._
import akka.actor.{Supervisor, Actor}

object Main extends App {

  // create, start and supervise the TestService actor, which holds our custom request handling logic
  Supervisor(
    SupervisorConfig(
      OneForOneStrategy(List(classOf[Exception]), 3, 100),
      List(Supervise(Actor.actorOf(new TestService("test-endpoint")), Permanent))
    )
  )

  // create, start and supervise the HttpServer actor and possibly (if configured) the TimeoutKeeper actor
  HttpServer.start()
}