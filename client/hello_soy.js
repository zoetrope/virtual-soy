// This file was automatically generated from hello.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace voy.
 */

if (typeof voy == 'undefined') { var voy = {}; }


voy.helloWorld = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<pre>Hello ' + soy.$$escapeHtml(opt_data.name) + '!</pre>');
};
if (goog.DEBUG) {
  voy.helloWorld.soyTemplateName = 'voy.helloWorld';
}
