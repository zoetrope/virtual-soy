var content = document.getElementById("content").getElementsByTagName("pre")[0];

var hello = voy.helloWorld({"name": "foo-bar"});
//document.write(hello);

var curvdom = virtualDom.virtualize(content);
var soyvdom = virtualDom.fromHtml(hello);

var patch = virtualDom.diff(curvdom, soyvdom);
virtualDom.patch(content, patch);