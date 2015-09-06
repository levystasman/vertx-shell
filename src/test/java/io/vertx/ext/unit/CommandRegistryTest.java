package io.vertx.ext.unit;

import io.vertx.core.Vertx;
import io.vertx.ext.shell.cli.CliToken;
import io.vertx.ext.shell.command.Command;
import io.vertx.ext.shell.registry.CommandRegistry;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@RunWith(VertxUnitRunner.class)
public class CommandRegistryTest {

  Vertx vertx = Vertx.vertx();

  @Test
  public void testEval(TestContext context) {
    CommandRegistry registry = CommandRegistry.get(vertx);
    Command command = Command.command("hello");
    command.processHandler(process -> {
      context.assertEquals(Arrays.asList(CliToken.createBlank(" "), CliToken.createText("world")), process.argsTokens());
      process.end(0);
    });
    registry.registerCommand(command, context.asyncAssertSuccess(v -> {
      registry.createProcess("hello world", context.asyncAssertSuccess(job -> {
        Async async = context.async();
        TestProcessContext ctx = new TestProcessContext();
        ctx.endHandler(code -> {
          async.complete();
        });
        job.execute(ctx);
      }));
    }));
  }

  @Test
  public void testRegister(TestContext context) {
    CommandRegistry registry = CommandRegistry.get(vertx);
    Command command = Command.command("hello");
    registry.registerCommand(command, context.asyncAssertSuccess(reg -> {
      registry.unregisterCommand("hello", context.asyncAssertSuccess(done -> {
        context.assertEquals(Collections.emptyList(), registry.registrations());
      }));
    }));
  }

  @Test
  public void testDuplicateRegistration(TestContext context) {
    CommandRegistry registry = CommandRegistry.get(vertx);
    Command command = Command.command("hello");
    registry.registerCommand(command, context.asyncAssertSuccess(reg -> {
      registry.registerCommand(command, context.asyncAssertFailure(err -> {
      }));
    }));
  }
}