package application;

import utils.ValidaCPF;

public class Test {

    public static void main( String[] args ) {

        ValidaCPF v = new ValidaCPF();
        boolean s = v.isCPFValido( "375.742.198-18" );
        System.out.println(s);
    }

}
