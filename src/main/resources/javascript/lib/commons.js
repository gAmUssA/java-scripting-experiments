/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com) on 9/21/13.
 * @since 0.0.1
 */
// From http://javascript.crockford.com/remedial.html
(function () {
    if (!String.prototype.supplant) {
        String.prototype.supplant = function (o) {
            return this.replace(/{([^{}]*)}/g,
                function (a, b) {
                    var r = o[b];
                    return typeof r === 'string' || typeof r === 'number' ? r : a;
                }
            );
        };
    }
})();