/**
 * Fast modular exponentiation for a ^ b mod n
 * @returns {number}
 */
var fastModularExponentiation = function(a, b, n) {
  a = a % n;
  var result = 1;
  var x = a;

  while(b > 0){
    var leastSignificantBit = b % 2;
    b = Math.floor(b / 2);

    if (leastSignificantBit == 1) {
      result = result * x;
      result = result % n;
    }

    x = x * x;
    x = x % n;
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