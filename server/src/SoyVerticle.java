import com.google.template.soy.SoyFileSet;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.tofu.SoyTofu;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

import java.io.File;

public class SoyVerticle extends Verticle {

  public void start() {
    container.logger().info("MainVerticle start.");

    HttpServer server = vertx.createHttpServer();

    RouteMatcher matcher = new RouteMatcher();

    matcher.get("/", req->{
      req.response().headers().set("Content-Type", "text/plain");
      req.response().end("Hello World!");
    });

    SoyFileSet sfs = SoyFileSet.builder().add(new File("hello.soy")).build();
    SoyTofu tofu = sfs.compileToTofu();

    matcher.get("/soy", req->{
      String output = tofu.newRenderer("examples.simple.helloWorld")
          .setData(new SoyMapData("name", "hoge"))
          .render();
      req.response().end(output);
    });

    server.requestHandler(matcher).listen(8181);
  }
}
