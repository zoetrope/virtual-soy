
== vert.x

=== vert.xを動かす

 * http://vertx.io/ から最新版をダウンロード
 * 解凍してパスを通す
   * export PATH=$PATH:~/opt/vert.x-2.1.2/bin/
 * サンプルを動かしてみる
   * vertx run server/HelloVerticle.java
   * ブラウザで http://localhost:8181/ にアクセス
   * ラムダ式使ってるとパースできなくて動かない…？と思ったらJava1.7使ってたからだ。
 * コンパイルしてから動かす
   * vertx run HelloVerticle -cp ./out/production/virtual-soy
   * クラス名とクラスパスを指定する


== closure-templates

=== soyからjs

java -jar lib/SoyToJsSrcCompiler.jar --outputPathFormat ./output/dummy.js --srcs dummy.soy

=== soyからJava

java -jar lib/SoyParseInfoGenerator.jar --outputDirectory ./output --javaPackage app --javaClassNameSource filename --srcs dummy.soy

=== soyをレンダリングして返す

vertx run SoyVerticle -cp ./out/production/virtual-soy/:./lib/soy.jar



