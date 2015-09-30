/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.groovy.ext.shell.cli;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import java.util.List
import io.vertx.groovy.ext.shell.Session
import io.vertx.groovy.core.Vertx
/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
*/
@CompileStatic
public class Completion {
  private final def io.vertx.ext.shell.cli.Completion delegate;
  public Completion(Object delegate) {
    this.delegate = (io.vertx.ext.shell.cli.Completion) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * @return the current Vert.x instance
   * @return 
   */
  public Vertx vertx() {
    def ret= InternalHelper.safeCreate(this.delegate.vertx(), io.vertx.groovy.core.Vertx.class);
    return ret;
  }
  public Session session() {
    def ret= InternalHelper.safeCreate(this.delegate.session(), io.vertx.groovy.ext.shell.Session.class);
    return ret;
  }
  /**
   * @return the current line being completed in raw format, i.e without any char escape performed
   * @return 
   */
  public String rawLine() {
    def ret = this.delegate.rawLine();
    return ret;
  }
  public List<CliToken> lineTokens() {
    def ret = this.delegate.lineTokens()?.collect({underpants -> new io.vertx.groovy.ext.shell.cli.CliToken(underpants)});
      return ret;
  }
  public void complete(List<String> candidates) {
    this.delegate.complete(candidates);
  }
  public void complete(String value, boolean terminal) {
    this.delegate.complete(value, terminal);
  }
}