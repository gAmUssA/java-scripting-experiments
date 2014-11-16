#!/usr/bin/env jjs -scripting

var currentDir = new java.io.File('.'),
    allFiles = currentDir.list();
print(currentDir.getCanonicalPath());
for (var i = 0; i < allFiles.length; i++) {
    print(allFiles[i]);
}
