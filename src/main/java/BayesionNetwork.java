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

        Factor example1SumOutB = example1CombinationFactor.sumout("B");
        System.out.println("BayesionNetwork.main SUMOUT: " + example1SumOutB);

        Factor normalized = example1SumOutB.normalize();
        System.out.println("BayesionNetwork.main NORMALIZE: " + normalized);



/*
    public Factor inference(List<Factor> factorList,
                            List<String> queryVariables,
                            List<String> orderedListOfHiddenVariables,
                            List<Evidence> evidenceList) {
                            */

        List<String> inferenceVariableNames1 = Arrays.asList("A");
        List<Float> inferenceFactors1 = Arrays.asList(0.3f, 0.7f);
        List<List<Boolean>> inferenceBooleans1 = (List) new ArrayList<ArrayList<Boolean>>();
        inferenceBooleans1.add( Arrays.asList(true));
        inferenceBooleans1.add( Arrays.asList(false));
        Factor inferenceFactor1 = new Factor(inferenceVariableNames1, inferenceBooleans1, inferenceFactors1);

        List<String> inferenceVariableNames2 = Arrays.asList("A", "C");
        List<Float> inferenceFactors2 = Arrays.asList(0.8f, 0.2f, 0.15f, 0.85f);
        List<List<Boolean>> inferenceBooleans2 = (List) new ArrayList<ArrayList<Boolean>>();
        inferenceBooleans2.add( Arrays.asList(true, true));
        inferenceBooleans2.add( Arrays.asList(true, false));
        inferenceBooleans2.add( Arrays.asList(false, true));
        inferenceBooleans2.add( Arrays.asList(false, false));
        Factor inferenceFactor2 = new Factor(inferenceVariableNames2, inferenceBooleans2, inferenceFactors2);

        List<String> inferenceVariableNames3 = Arrays.asList("C", "G");
        List<Float> inferenceFactors3 = Arrays.asList(1.0f, 0.0f, 0.2f, 0.8f);
        List<List<Boolean>> inferenceBooleans3 = (List) new ArrayList<ArrayList<Boolean>>();
        inferenceBooleans3.add( Arrays.asList(true, true));
        inferenceBooleans3.add( Arrays.asList(true, false));
        inferenceBooleans3.add( Arrays.asList(false, true));
        inferenceBooleans3.add( Arrays.asList(false, false));
        Factor inferenceFactor3 = new Factor(inferenceVariableNames3, inferenceBooleans3, inferenceFactors3);

        List<String> inferenceVariableNames4 = Arrays.asList("G", "L");
        List<Float> inferenceFactors4 = Arrays.asList(0.7f, 0.3f, 0.2f, 0.8f);
        List<List<Boolean>> inferenceBooleans4 = (List) new ArrayList<ArrayList<Boolean>>();
        inferenceBooleans4.add( Arrays.asList(true, true));
        inferenceBooleans4.add( Arrays.asList(true, false));
        inferenceBooleans4.add( Arrays.asList(false, true));
        inferenceBooleans4.add( Arrays.asList(false, false));
        Factor inferenceFactor4 = new Factor(inferenceVariableNames4, inferenceBooleans4, inferenceFactors4);

        List<String> inferenceVariableNames5 = Arrays.asList("L", "S");
        List<Float> inferenceFactors5 = Arrays.asList(0.9f, 0.1f, 0.3f, 0.7f);
        List<List<Boolean>> inferenceBooleans5 = (List) new ArrayList<ArrayList<Boolean>>();
        inferenceBooleans5.add( Arrays.asList(true, true));
        inferenceBooleans5.add( Arrays.asList(true, false));
        inferenceBooleans5.add( Arrays.asList(false, true));
        inferenceBooleans5.add( Arrays.asList(false, false));
        Factor inferenceFactor5 = new Factor(inferenceVariableNames5, inferenceBooleans5, inferenceFactors5);

        List<Factor> factorList = new ArrayList<Factor>();
        factorList.add(inferenceFactor1);
        factorList.add(inferenceFactor2);
        factorList.add(inferenceFactor3);
        factorList.add(inferenceFactor4);
        factorList.add(inferenceFactor5);

        List<String> orderedListOfHiddenVariables = new ArrayList<String>();
        orderedListOfHiddenVariables.add("L");
        orderedListOfHiddenVariables.add("G");
        orderedListOfHiddenVariables.add("C");
        orderedListOfHiddenVariables.add("A");

        List<Evidence> evidenceList = new ArrayList<Evidence>();
        evidenceList.add(new Evidence("S", true));

        Factor infered = Factor.inference(factorList, new ArrayList<String>(), orderedListOfHiddenVariables, evidenceList);

        System.out.println("BayesionNetwork.main : " + infered);

    }


}
