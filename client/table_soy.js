// This file was automatically generated from table.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace voy.
 */

if (typeof voy == 'undefined') { var voy = {}; }


voy.table = function(opt_data, opt_ignored) {
  var output = '<div id="mytable">';
  for (var i4 = 0; i4 < 1000; i4++) {
    output += '<div>';
    for (var j6 = 0; j6 < 20; j6++) {
      output += '<span>' + soy.$$escapeHtml(opt_data.data[i4]) + '</span>:<span>' + soy.$$escapeHtml(opt_data.data[j6]) + '</span>|';
    }
    output += '</div>';
  }
  output += '</div>';
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  voy.table.soyTemplateName = 'voy.table';
}
