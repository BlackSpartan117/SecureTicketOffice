/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.math.BigInteger;

/**
 *
 * @author david
 */
public class pruebaexpo {
    public static void main(String[] args) {
        BigInteger mod = modulo( new BigInteger("3"), new BigInteger("15"), new BigInteger("7") );
        System.out.println(""+mod);
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
            b.divide( dos );
        }

        return x.mod( c );
    }
}
