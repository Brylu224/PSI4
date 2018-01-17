public class Main {

    static int noi = 36;					//ilość wejść
    static double learningRate = 0.001;					//współczynnik uczenia się
    static double forget = 0.0001;	//współczynnik zapominania
    static int nols = 10;						//liczba danych uczacych
    static int non = 11;						//liczba neuronów

    public static void main ( String[] args ) {

        int winner;
        Hebb[] hebbs = new Hebb[non];
        for (int i = 0; i < non; i++ )
            hebbs[i] = new Hebb(noi);

        int ages = learn( hebbs );

        System.out.println( "PO UCZENIU" );
        for (int i = 0; i < nols; i++ ) {
            winner = testHebb( hebbs, Letters.lettersLearn[i] );
            System.out.println( "Litera " + Letters.letter[i] + " - neuron  " + winner );
        }

        System.out.println( "\nTESTOWANIE" );
        for (int i = 0; i < nols; i++ ) {
            winner = testHebb( hebbs, Letters.lettersTest[i] );
            System.out.println( "Litera " + Letters.letter[i] + " - neuron  " + winner );
        }

        System.out.println( "\nIlość epok = " + ages );

    }


    public static int learn ( Hebb[] hebbs ) {

        int counter = 0;
        int limit = 1000;
        int[] winners = new int[non];
        for (int i = 0; i < non; i++ )
            winners[i] = - 1;

        while ( ! isUnique( winners ) ) {

            for (int j = 0; j < non; j++ ) {

                for (int k = 0; k < nols; k++ )
                    hebbs[j].learnUnsupervised( Letters.lettersLearn[k], learningRate, forget, Hebb.forget);


                for (int l = 0; l < nols; l++ )
                    winners[l] = testHebb( hebbs, Letters.lettersLearn[l] );
            }

            if ( ++ counter == limit )
                break;
        }

        return counter;
    }


    public static boolean isUnique ( int[] winners ) {

        for (int i = 0; i < non; i++ )
            for (int j = 0; j < non; j++ )
                if ( i != j )
                    if ( winners[i] == winners[j] )
                        return false;

        return true;
    }


    public static int testHebb ( Hebb[] hebbs, double[] letters ) {

        double max = hebbs[0].test( letters );
        int winner = 0;

        for (int i = 1; i < non; i++ ) {
            if ( hebbs[i].test( letters ) > max ) {
                max = hebbs[i].test( letters );
                winner = i;
            }
        }

        return winner;
    }

}