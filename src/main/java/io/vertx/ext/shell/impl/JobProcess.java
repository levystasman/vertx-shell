package io.vertx.ext.shell.impl;

import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.shell.Stream;

import io.vertx.ext.shell.Job;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class JobProcess implements Job {

  final Vertx vertx;
  final Process commandContext;
  volatile Handler<Integer> endHandler;
  volatile Stream stdin;
  volatile Stream stdout;
  volatile Handler<String> signalHandler;

  public JobProcess(Vertx vertx, Process process) {
    this.vertx = vertx;
    this.commandContext = process;
  }

  @Override
  public Stream stdin() {
    return stdin;
  }

  @Override
  public void setStdout(Stream stdout) {
    this.stdout = stdout;
  }

  @Override
  public void run() {
    run(v -> {});
  }

  @Override
  public void run(Handler<Void> beginHandler) {
    Context context = vertx.getOrCreateContext();
    Handler<Integer> endHandler = this.endHandler;
    ProcessContext processContext = new ProcessContext() {
      @Override
      public void begin() {
        context.runOnContext(beginHandler);
      }
      @Override
      public void setStdin(Stream stdin) {
        JobProcess.this.stdin = stdin;
      }
      @Override
      public Stream stdout() {
        return txt -> {
          context.runOnContext(v -> {
            Stream abc = stdout;
            if (abc != null) {
              abc.handle(txt);
            }
          });
        };
      }
      @Override
      public void setSignalHandler(Handler<String> handler) {
        signalHandler = handler;
      }
      @Override
      public void end(int code) {
        if (endHandler != null) {
          context.runOnContext(v -> endHandler.handle(code));
        }
      }
    };
    commandContext.execute(processContext);
  }

  @Override
  public void sendSignal(String signal) {
    signalHandler.handle(signal);
  }

  @Override
  public void endHandler(Handler<Integer> handler) {
    endHandler = handler;
  }
}
