#! /usr/local/bin/jjs -scripting

// create instance
var list = new java.util.ArrayList();

// package shortcut
var util = java.util;
var list = new util.ArrayList();

// class shortcut
var ArrayList = java.util.ArrayList;
var list = new ArrayList();

// using Java.type()
var ArrayList = Java.type('java.util.ArrayList');
var list = new ArrayList();

list.add(2);
list.add('string value');

print(list);
