import com.google.template.soy.SoyFileSet;
import com.google.template.soy.data.SoyListData;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.tofu.SoyTofu;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.impl.Json;
import org.vertx.java.platform.Verticle;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class MainVerticle extends Verticle {

  public void start() {
    container.logger().info("MainVerticle start.");

    HttpServer server = vertx.createHttpServer();

    RouteMatcher matcher = new RouteMatcher();

    matcher.get("/", req -> {
      req.response().headers().set("Content-Type", "text/plain");
      req.response().end("Hello World!");
    });

    SoyFileSet sfs = SoyFileSet.builder()
        .add(new File("main.soy"))
        .add(new File("hello.soy"))
        .add(new File("table.soy"))
        .build();
    SoyTofu tofu = sfs.compileToTofu();

    SoyListData data = IntStream.range(0, 1000 * 20)
        .boxed()
        .collect(SoyListData::new, (list, i) -> list.add(i), (list1, list2) -> list1.add(list2));

    matcher.get("/soy", req -> {
      String output = tofu.newRenderer("voy.main")
          .setData(new SoyMapData("name", "hoge", "data", data))
          .render();
      req.response().end(output);
    });

    matcher.get("/data.json", req -> {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("data", IntStream.range(0, 1000 * 20).toArray());
      String json = Json.encode(map);
      req.response().end(json);
    });

    // DANGEROUS!!
    matcher.get("/client/:filename", req -> {
      String filename = req.params().get("filename");
      System.out.println(filename);
      req.response().sendFile("client/" + filename);
    });

    server.requestHandler(matcher).listen(8181);
  }
}
