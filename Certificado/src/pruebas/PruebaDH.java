/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;

import javax.crypto.spec.DHParameterSpec;

public class PruebaDH {
  public static void main(String[] argv) throws Exception {
    AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
    paramGen.init(1024);

    AlgorithmParameters params = paramGen.generateParameters();
    DHParameterSpec dhSpec = (DHParameterSpec) params.getParameterSpec(DHParameterSpec.class);
    BigInteger bi =dhSpec.getP().gcd(dhSpec.getG());

    System.out.println("" + dhSpec.getP() + "\n" + dhSpec.getG() + "\n" + dhSpec.getL());
    System.out.println("Gcd: " + bi);
  }
}