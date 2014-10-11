var diff = require("virtual-dom/diff");
var patch = require("virtual-dom/patch");
var h = require("virtual-dom/h");
var virtualize = require("vdom-virtualize");
var fromHtml = require("vdom-virtualize").fromHTML;

module.exports = {
    diff: diff,
    patch: patch,
    h: h,
    virtualize: virtualize,
    fromHtml: fromHtml
};
