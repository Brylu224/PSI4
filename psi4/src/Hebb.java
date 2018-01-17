import java.util.Random;

public class Hebb {

    private int noi;    									//ilość wejść
    private double[] w; 									//wagi
    public static boolean forget = false;		//współczynnik zapominania
    public static boolean not_forget = true;	//bez współczynnika zapominania

    public Hebb ( int numbers_of_inputs ) {
        noi = numbers_of_inputs;
        w = new double[noi];

        for ( int i = 0; i < noi; i++ )
            w[i] = new Random().nextDouble();
        normalizeWeights();
    }


    private double active ( double y_p ) {
        return ( 1.0 / ( 1 + Math.pow( Math.E, - y_p ) ) );
    }

    private void normalizeWeights () {
        double dl = 0.0;
        for ( int i = 0; i < w.length; i++ )
            dl += Math.pow( w[i], 2 );

        dl = Math.sqrt( dl );

        for ( int i = 0; i < w.length; i++ )
            if ( w[i] > 0 && dl != 0 )
                w[i] = w[i] / dl;
    }

    private double sumator ( double[] x ) {
        double y_p = 0.0;
        for ( int i = 0; i < noi; i++ )
            y_p += x[i] * w[i];

        return y_p;
    }





    public double test ( double[] x ) {
        return active( sumator( x ) );
    }


    public double learnUnsupervised ( double[] x, double lr, double fr, boolean version ) {
        double y_p = active( sumator( x ) );


        for ( int i = 0; i < noi; i++ )
            if ( version ) w[i] = ( 1 - fr ) * w[i] + lr * x[i] * y_p;
            else w[i] += lr * x[i] * y_p;

        normalizeWeights();

        return active( sumator( x ) );
    }

}