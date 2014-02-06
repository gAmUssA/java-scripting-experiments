#! /usr/local/bin/jjs -scripting

var Thread = Java.type('java.lang.Thread'),
    Runnable = Java.type('java.lang.Runnable');

var thread1 = new Thread(new Runnable({
    run: function () {
        Thread.sleep(100);
        print('Hello from thread1!');
    }
}));

var thread2 = new Thread(new Runnable(
    function () {
        Thread.sleep(300);
        print('Hello from thread2!');
    }
));

var thread3 = new Thread(function () {
    print('Hello from thread3!');
});

thread1.start();
thread2.start();
thread3.start();

thread1.join();
thread2.join();
thread3.join();
