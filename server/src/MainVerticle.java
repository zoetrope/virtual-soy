import com.google.template.soy.SoyFileSet;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.tofu.SoyTofu;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.platform.Verticle;

import java.io.File;

public class MainVerticle extends Verticle {

  public void start() {
    container.logger().info("MainVerticle start.");

    HttpServer server = vertx.createHttpServer();

    RouteMatcher matcher = new RouteMatcher();

    matcher.get("/", req->{
      req.response().headers().set("Content-Type", "text/plain");
      req.response().end("Hello World!");
    });

    SoyFileSet sfs = SoyFileSet.builder().add(new File("main.soy")).build();
    SoyTofu tofu = sfs.compileToTofu();

    matcher.get("/soy", req->{
      String output = tofu.newRenderer("voy.main")
          .setData(new SoyMapData("name", "hoge"))
          .render();
      req.response().end(output);
    });

    matcher.get("/app.js", req -> req.response().sendFile("client/app.js"));
    matcher.get("/mercury.js", req -> req.response().sendFile("client/mercury.js"));

    server.requestHandler(matcher).listen(8181);
  }
}
