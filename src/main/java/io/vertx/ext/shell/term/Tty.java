/*
 * Copyright 2015 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 *
 *
 * Copyright (c) 2015 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 *
 */

package io.vertx.ext.shell.term;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

/**
 * Provide interactions with the Shell TTY.
 *
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface Tty {

  /**
   * @return the declared tty type, for instance {@literal vt100}, {@literal xterm-256},  etc... it can be null
   * when the tty does not have declared its type.
   */
  String type();

  /**
   * @return the current width, i.e the number of rows or {@literal -1} if unknown
   */
  int width();

  /**
   * @return the current height, i.e the number of columns or {@literal -1} if unknown
   */
  int height();

  /**
   * Set a stream handler on the standard input to read the data.
   *
   * @param handler the standard input
   * @return this object
   */
  @Fluent
  Tty stdinHandler(Handler<String> handler);

  /**
   * Write data to the standard output.
   *
   * @param data the data to write
   * @return this object
   */
  @Fluent
  Tty write(String data);

  /**
   * Set a resize handler, the handler is called when the tty size changes.
   *
   * @param handler the resize handler
   * @return this object
   */
  @Fluent
  Tty resizehandler(Handler<Void> handler);

}
