/**
 * Fast modular exponentiation for a ^ b mod n
 * @returns {number}
 */
var fastModularExponentiation = function(a, b, n) {
    var sn = SchemeNumber;
    var fn = sn.fn;
    var ns = fn["number->string"];
    a = fn.mod(a, n);
    var result = fn["string->number"]("1");
    var x = a;

  while(fn[">"](b, "0")){
    var leastSignificantBit = fn.mod(b, 2);
    b = fn.floor(fn["/"](b,2));

    if (fn["="](leastSignificantBit,"1")) {
      result = fn["*"](result,x);
      result = fn.mod(result, n);
    }

    x = fn["*"](x, x);
    x = fn.mod(x, n);
  }
  return result;
};

var assert = function(actual, expected){
  if (actual != expected){
    throw new Error('Assertion failed');
  }
};
/*
assert(fastModularExponentiation(12, 53, 7), 3);
assert(fastModularExponentiation(7, 12, 10), 1);
assert(fastModularExponentiation(3, 51, 13), 1);
*/