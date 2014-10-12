/*
var content = document.getElementById("content").getElementsByTagName("pre")[0];

var hello = voy.helloWorld({"name": "foo-bar"});
//document.write(hello);

var curvdom = virtualDom.virtualize(content);
var soyvdom = virtualDom.fromHtml(hello);

var patch = virtualDom.diff(curvdom, soyvdom);
virtualDom.patch(content, patch);
*/

/*
atomic.get("/data.json")
  .success(function (data, xhr) {
    var table = voy.table(data);
    document.write(table);
  });
*/

atomic.get("/data.json")
  .success(function (data, xhr) {

    data.data[123] = 9999;
    var table = voy.table(data);
    var content = document.getElementById("mytable");

    var curvdom = virtualDom.virtualize(content);
    var soyvdom = virtualDom.fromHtml(table);

    var patch = virtualDom.diff(curvdom, soyvdom);
    virtualDom.patch(content, patch);
  });
