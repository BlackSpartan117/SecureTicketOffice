/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author david
 */
public class pruebaexpo {
    public static void main(String[] args) {
        BigInteger mod = modulo( new BigInteger("7"), new BigInteger("12"), new BigInteger("10"));
        System.out.println(""+mod);
        System.out.println(randomBigInteger(new BigInteger("10")));
    }
    
    static BigInteger modulo( BigInteger a, BigInteger b, BigInteger c ) {
        BigInteger x = new BigInteger("1");
        BigInteger y = a;
        BigInteger zero = new BigInteger("0");
        BigInteger dos = new BigInteger("2");

        while( b.compareTo( zero ) == 1 ) {
            if( b.mod( dos ).compareTo( new BigInteger("1") ) == 0 ) {
                x = ( x.multiply( y ) ).mod( c );
            }

            y = ( y.multiply( y ) ).mod( c ); // squaring the base
            b = b.divide( dos );
        }

        return x.mod( c );
    }
    
    public static BigInteger randomBigInteger(BigInteger n) {
        Random rnd = new Random();
        int maxNumBitLength = n.bitLength();
        BigInteger aRandomBigInt;
        do {
            aRandomBigInt = new BigInteger(maxNumBitLength, rnd);
            // compare random number lessthan ginven number
        } while (aRandomBigInt.compareTo(n.subtract(new BigInteger("2"))) > 0 || aRandomBigInt.compareTo(new BigInteger("2")) < 0); 
        return aRandomBigInt;
    }
}
