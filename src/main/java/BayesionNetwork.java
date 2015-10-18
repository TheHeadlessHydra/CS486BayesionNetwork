import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Serj on 17/10/2015.
 */
public class BayesionNetwork {


    public static void main(String[] args) {
        List<String> variablenames = Arrays.asList("X", "Y");
        List<Float> factorProbabilities = Arrays.asList(0.7f, 0.2f, 0.3f, 0.4f);
        List<List<Boolean>> factors = (List) new ArrayList<ArrayList<Boolean>>();

        factors.add( Arrays.asList(true, true));
        factors.add( Arrays.asList(true, false));
        factors.add( Arrays.asList(false, true));
        factors.add( Arrays.asList(false, false));

        Factor factorXY = new Factor(variablenames, factors, factorProbabilities);
        System.out.println(factorXY);

        Factor restrictedOnXTrue = factorXY.restrictFactor("X", true);
        //System.out.println(restrictedOnXTrue);

        Factor restrictedOnYTrue = factorXY.restrictFactor("Y", true);
        System.out.println(restrictedOnYTrue);

        Factor restrictedOnXFalse = factorXY.restrictFactor("X", false);
        System.out.println(restrictedOnXFalse);

        Factor restrictedOnYFalse = factorXY.restrictFactor("Y", false);
        //System.out.println(restrictedOnYFalse);

        Factor combinationFactor = restrictedOnXFalse.productFactor(restrictedOnYTrue);
        System.out.println("BayesionNetwork.main combination factor: " + combinationFactor);



        List<String> example1VariableNames1 = Arrays.asList("A", "B");
        List<Float> example1Probabilities1 = Arrays.asList(0.1f, 0.9f, 0.2f, 0.8f);
        List<List<Boolean>> example1Booleans1 = (List) new ArrayList<ArrayList<Boolean>>();
        example1Booleans1.add( Arrays.asList(true, true));
        example1Booleans1.add( Arrays.asList(true, false));
        example1Booleans1.add( Arrays.asList(false, true));
        example1Booleans1.add( Arrays.asList(false, false));
        Factor example1Factor1 = new Factor(example1VariableNames1, example1Booleans1, example1Probabilities1);

        List<String> example1VariableNames2 = Arrays.asList("B", "C");
        List<Float> example1Probabilities2 = Arrays.asList(0.3f, 0.7f, 0.6f, 0.4f);
        List<List<Boolean>> example1Booleans2 = (List) new ArrayList<ArrayList<Boolean>>();
        example1Booleans2.add( Arrays.asList(true, true));
        example1Booleans2.add( Arrays.asList(true, false));
        example1Booleans2.add( Arrays.asList(false, true));
        example1Booleans2.add( Arrays.asList(false, false));
        Factor example1Factor2 = new Factor(example1VariableNames2, example1Booleans2, example1Probabilities2);

        Factor example1CombinationFactor = example1Factor1.productFactor(example1Factor2);
        System.out.println("BayesionNetwork.main : " + example1CombinationFactor);

        Factor example1SumOutB = example1CombinationFactor.productFactor(example1Factor2);

    }


}
