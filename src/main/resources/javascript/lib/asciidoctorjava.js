var convert = function(content, hash2) {
    return Opal.Asciidoctor.$convert(content, hash2);
};

var convert_file = function(file, hash2) {
    return Opal.Asciidoctor.$convert_file(file, hash2);
};

var load_document = function(content, hash2) {
    return Opal.Asciidoctor.$load(content, hash2);
};

var load_document_file = function(file, hash2) {
    return Opal.Asciidoctor.$load_file(file, hash2);
};

var runtime_version = function(){
    return Opal.Asciidoctor.scope.VERSION;
};
