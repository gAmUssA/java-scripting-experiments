var Tax = (function () {
    "use strict";
    var taxDeduction = 500;
    var Tax = {
        doTaxes: function (income, customerName) {
            var yourTax = {};
            yourTax.customer = customerName;
            if (customerName !== "Tony Soprano") {
                yourTax.value = income * 0.05 - taxDeduction;
            } else {
                yourTax.value = mafiaSpecial(income);
            }
            //print("\tDear " + customerName + ", your tax is " + yourTax.value);
            print(JSON.stringify(yourTax));
            return yourTax;
        }
    };

    function mafiaSpecial(income) {
        return income * 0.05 - taxDeduction * 3;
    }

    return Tax;
})();

// The closure remembers its context with taxDeduction=500
Tax.doTaxes(100000, "John Smith");
Tax.doTaxes(100000, "Tony Soprano");