# virtual-soy

An experimental implementation for high performance template engine.

## Dependency

 * closure-templates
   * https://github.com/google/closure-templates
 * virtual-dom
   * https://github.com/Matt-Esch/virtual-dom
 * vdom-virtualize
   * https://github.com/marcelklehr/vdom-virtualize

## Requirement

 * vert.x
   * http://vertx.io/

## Usage

### run a server

~~~
$ ./lib/vert.x-2.1.2/bin/vertx run ./server/src/MainVerticle.java -cp ./lib/soy.jar
~~~

### make a client code

~~~
$ cd client
$ npm install
$ npm run dist
~~~
