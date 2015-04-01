#!/usr/bin/env jjs -scripting

load("./lib/asciidoctor-all.js");
load("./lib/asciidoctor-extensions.js");

var data = <<<EOS;
= asciidoctor.js, AsciiDoc in JavaScript
Doc Writer <docwriter@example.com>

Asciidoctor and Opal come together to bring
http://asciidoc.org[AsciiDoc] to the browser!.

== Technologies

* AsciiDoc
* Asciidoctor
* Opal

NOTE: That's all she wrote!!!
EOS

var options = Opal.hash2(['safe', 'attributes'], {'safe': 'server', attributes: ['showtitle']});
var html = Opal.Asciidoctor.$convert(data, options);
print(html);
