import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Serj
 * Main body that tests and runs Baysion Network code.
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

        Factor restrictedOnXTrue = factorXY.restrictFactor("X", true);

        Factor restrictedOnYTrue = factorXY.restrictFactor("Y", true);

        Factor restrictedOnXFalse = factorXY.restrictFactor("X", false);

        Factor restrictedOnYFalse = factorXY.restrictFactor("Y", false);

        Factor combinationFactor = restrictedOnXFalse.productFactor(restrictedOnYTrue);

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

        Factor example1SumOutB = example1CombinationFactor.sumout("B");

        Factor normalized = example1SumOutB.normalize();

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

        //Factor infered = Factor.inference(factorList, new ArrayList<String>(), orderedListOfHiddenVariables, evidenceList);
        //System.out.println("BayesionNetwork.main : " + infered);


        List<String> FraudVariables1 = Arrays.asList("T");
        List<Float> fraudFactors1 = Arrays.asList(0.05f, 0.95f);
        List<List<Boolean>> fraudBools1 = (List) new ArrayList<ArrayList<Boolean>>();
        fraudBools1.add( Arrays.asList(true));
        fraudBools1.add( Arrays.asList(false));
        Factor fraudFactor1 = new Factor(FraudVariables1, fraudBools1, fraudFactors1);

        List<String> FraudVariables2 = Arrays.asList("OC");
        List<Float> fraudFactors2 = Arrays.asList(0.6f, 0.4f);
        List<List<Boolean>> fraudBools2 = (List) new ArrayList<ArrayList<Boolean>>();
        fraudBools2.add(Arrays.asList(true));
        fraudBools2.add(Arrays.asList(false));
        Factor fraudFactor2 = new Factor(FraudVariables2, fraudBools2, fraudFactors2);

        List<String> FraudVariables3 = Arrays.asList("FP", "T", "F");
        List<Float> fraudFactors3 = Arrays.asList(0.9f, 0.9f, 0.1f, 0.01f, 0.1f, 0.1f, 0.9f, 0.99f);
        List<List<Boolean>> fraudBools3 = (List) new ArrayList<ArrayList<Boolean>>();
        fraudBools3.add(Arrays.asList(true, true, true));
        fraudBools3.add(Arrays.asList(true, true, false));
        fraudBools3.add(Arrays.asList(true, false, true));
        fraudBools3.add(Arrays.asList(true, false, false));
        fraudBools3.add(Arrays.asList(false, true, true));
        fraudBools3.add(Arrays.asList(false, true, false));
        fraudBools3.add(Arrays.asList(false, false, true));
        fraudBools3.add(Arrays.asList(false, false, false));
        Factor fraudFactor3 = new Factor(FraudVariables3, fraudBools3, fraudFactors3);

        List<String> FraudVariables4 = Arrays.asList("IP", "OC", "F");
        List<Float> fraudFactors4 = Arrays.asList(0.02f, 0.01f, 0.011f, 0.001f, 0.98f, 0.99f, 0.989f, 0.999f);
        List<List<Boolean>> fraudBools4 = (List) new ArrayList<ArrayList<Boolean>>();
        fraudBools4.add(Arrays.asList(true, true, true));
        fraudBools4.add(Arrays.asList(true, true, false));
        fraudBools4.add(Arrays.asList(true, false, true));
        fraudBools4.add(Arrays.asList(true, false, false));
        fraudBools4.add(Arrays.asList(false, true, true));
        fraudBools4.add(Arrays.asList(false, true, false));
        fraudBools4.add(Arrays.asList(false, false, true));
        fraudBools4.add(Arrays.asList(false, false, false));
        Factor fraudFactor4 = new Factor(FraudVariables4, fraudBools4, fraudFactors4);

        List<String> FraudVariables5 = Arrays.asList("F", "T");
        List<Float> fraudFactors5 = Arrays.asList(0.01f, 0.004f, 0.99f, 0.996f);
        List<List<Boolean>> fraudBools5 = (List) new ArrayList<ArrayList<Boolean>>();
        fraudBools5.add(Arrays.asList(true, true));
        fraudBools5.add(Arrays.asList(true, false));
        fraudBools5.add(Arrays.asList(false, true));
        fraudBools5.add(Arrays.asList(false, false));
        Factor fraudFactor5 = new Factor(FraudVariables5, fraudBools5, fraudFactors5);

        List<String> FraudVariables6 = Arrays.asList("CRP", "OC");
        List<Float> fraudFactors6 = Arrays.asList(0.01f, 0.001f, 0.99f, 0.999f);
        List<List<Boolean>> fraudBools6 = (List) new ArrayList<ArrayList<Boolean>>();
        fraudBools6.add(Arrays.asList(true, true));
        fraudBools6.add(Arrays.asList(true, false));
        fraudBools6.add(Arrays.asList(false, true));
        fraudBools6.add(Arrays.asList(false, false));
        Factor fraudFactor6 = new Factor(FraudVariables6, fraudBools6, fraudFactors6);

        List<Factor> factorListFraud = new ArrayList<Factor>();
        factorListFraud.add(fraudFactor1);
        factorListFraud.add(fraudFactor2);
        factorListFraud.add(fraudFactor3);
        factorListFraud.add(fraudFactor4);
        factorListFraud.add(fraudFactor5);
        factorListFraud.add(fraudFactor6);

        List<String> orderedListOfHiddenVariablesFraud = new ArrayList<String>();
        orderedListOfHiddenVariablesFraud.add("T");
        orderedListOfHiddenVariablesFraud.add("FP");
        orderedListOfHiddenVariablesFraud.add("IP");
        orderedListOfHiddenVariablesFraud.add("OC");
        orderedListOfHiddenVariablesFraud.add("CRP");

        List<Evidence> evidenceListFraud = new ArrayList<Evidence>();
        evidenceListFraud.add(new Evidence("FP", true));
        evidenceListFraud.add(new Evidence("IP", false));
        evidenceListFraud.add(new Evidence("CRP", true));
        evidenceListFraud.add(new Evidence("T", true));

        Factor fraudInfered = Factor.inference(factorListFraud, new ArrayList<String>(), orderedListOfHiddenVariablesFraud, evidenceListFraud);

        System.out.println("Non-normalized probability of Fraud: " + fraudInfered);
        Factor normalizedFraud = fraudInfered.normalize();
        System.out.println("Normalized probability of Fraud: " + normalizedFraud);
    }
}
