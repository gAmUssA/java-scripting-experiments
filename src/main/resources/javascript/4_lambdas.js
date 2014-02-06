#!/usr/bin/env jjs -scripting

/*
Many constructs are familiar to JavaScript developers.  The main things to note;
where you can use a Lambda you can use a JavaScript function
JavaScript arrays need to be converted to Java collections
JavaScript syntax requires the '.' end a phrase, not start one (forces continuation)
Other than that, the JavaScript source and Java source looks very much the same.
*/

var copyright = <<<EOS;
/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
EOS

var Collectors = java.util.stream.Collectors;

// Break the copyright into tokens.
var tokens = copyright.split(/\s+/);

// Convert to ArrayList.
var list = new java.util.ArrayList();
tokens.map(function(e) list.add(e));

// The JavaScript collection for the result.
var result = [];

// Parallelize some of the activity.
list.parallelStream().
    // Select only words.
    filter(function(t) t.match(/^[A-Za-z]+$/)).
    // Make case comparable.
    map(function(t) t.toLowerCase()).
    // Fold duplicates.
    collect(Collectors.groupingBy(function(t) t)).
    // Move results to JavaScript collection.
    forEach(function(t) result.push(t));

// Sort the result.
result.sort();

print(result);