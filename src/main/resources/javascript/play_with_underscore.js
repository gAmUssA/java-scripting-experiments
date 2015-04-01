#!/usr/bin/env jjs -scripting

//load('http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min.js');
//load('./lib/underscore-min.js');
load('./lib/jvm-npm.js');
var _ = require('underscore');

var odds = _.filter([1, 2, 3, 4, 5, 6], function (num) {
    return num % 2 == 1;
});

print(odds);  // 1, 3, 5


var each = _.each([1, 2, 3], function (it) {
    return it;
});

print(each);

var map = _.map([1, 2, 3], function (num) {
    return num * 3;
});

print(map);

var reduce = _.reduce([1, 2, 3], function (memo, num) {
    return memo + num;
}, 0);

print(reduce);
