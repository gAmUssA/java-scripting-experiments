#!/usr/bin/env jjs -scripting

load("./lib/opal.js");
load("./lib/asciidoctor.js");

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

print(Opal.Asciidoctor.$render(data));