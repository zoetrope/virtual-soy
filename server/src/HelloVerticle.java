import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

public class HelloVerticle extends Verticle {

  public void start() {
    container.logger().info("MainVerticle start.");

    vertx.createHttpServer().requestHandler(req -> {
      req.response().headers().set("Content-Type", "text/plain");
      req.response().end("Hello World!");
    }).listen(8181);
  }
}
