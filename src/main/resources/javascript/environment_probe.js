#! /usr/local/bin/jjs -scripting

print("${envQuery()}");

function envQuery() {
    var command = "curl http://localhost:8080/nashorn_demo/service/script/environment.js";
    $EXEC(command);
    return $OUT;
}
