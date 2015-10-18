import java.util.*;

/**
 * Created by Serj on 17/10/2015.
 */
public class Factor {

    private final List<String> variablenames;
    private final List<List<Boolean>> factors;
    private final List<Float> factorProbabilities;

    Factor(List<String> variablenames,
           List<List<Boolean>> factors,
           List<Float> factorProbabilities) {
        if(factors.size() != factorProbabilities.size()) {
            throw new IllegalArgumentException("number of probabilities and number of boolean combinations must be the same");
        }
        if(Math.round(Math.pow(2, variablenames.size())) != factors.size()){
            if(variablenames.size() != 1 || factors.size() != 2) {
                throw new IllegalArgumentException("There must be 2^variables number of boolean columns: " +
                        "variableNames: " + variablenames + ", factors: " + factors + ", factorProbabilities: " + factorProbabilities);
            }
        }
        for(List<Boolean> bools : factors) {
            if(bools.size() != variablenames.size()) {
                throw new IllegalArgumentException("There must be as many booleans as there are variables. Problem: " +
                bools);
            }
        }

        this.variablenames = variablenames;
        this.factorProbabilities = factorProbabilities;
        this.factors = factors;
    }

    public List<String> getVariablenames() {
        return variablenames;
    }

    public List<List<Boolean>> getFactors() {
        return factors;
    }

    public List<Float> getFactorProbabilities() {
        return factorProbabilities;
    }

    public Factor restrictFactor(String variable, boolean value) {
        List<Triple<String, List<Boolean>, Float>> newFactor = new ArrayList<Triple<String, List<Boolean>, Float>>();
        int index = this.variablenames.indexOf(variable);

        List<String> variablenames = new ArrayList<String>();
        List<List<Boolean>> factors = new ArrayList<List<Boolean>>();
        List<Float> factorProbabilities = new ArrayList<Float>();


        for(String name : this.variablenames) {
            variablenames.add(name);
        }
        variablenames.remove(index);

        for(int i = 0; i < this.factors.size(); i++) {
            Float probability = this.factorProbabilities.get(i);
            List<Boolean> bools = this.factors.get(i);

            //System.out.println("Factor.restrictFactor i : " + index);
            //System.out.println("Factor.restrictFactor b : " + bools);
            //System.out.println("Factor.restrictFactor v : " + value);

            if(bools.get(index).equals(value)) {
                //System.out.println("Factor.restrictFactor i : " + index);
                //System.out.println("Factor.restrictFactor b : " + bools);
                //System.out.println("Factor.restrictFactor v : " + value);

                List<Boolean> newBools = new ArrayList<Boolean>(bools.size());
                for(Boolean bool : bools) {
                    newBools.add(bool);
                }
                newBools.remove(index);

                factors.add(newBools);
                factorProbabilities.add(probability);
            }
        }
        return new Factor(variablenames, factors, factorProbabilities);
    }

    public Factor productFactor(Factor factor2) {
        // generate combination of variables
        Set<String> randomVariablesBetweenTwo = new HashSet<String>();
        for(String variable : this.variablenames) {
            randomVariablesBetweenTwo.add(variable);
        }
        for(String variable : factor2.getVariablenames()) {
            randomVariablesBetweenTwo.add(variable);
        }

        List<String> combinationVariables = new ArrayList<String>();
        combinationVariables.addAll(randomVariablesBetweenTwo);

        // generate the truth table
        List<List<Boolean>> combinationBooleans = new ArrayList<List<Boolean>>();
        int columns = combinationVariables.size();
        int rows = (int) Math.pow(2, columns);
        for (int i = 0; i < rows; i++) {
            List<Boolean> booleanRow = new ArrayList<Boolean>();
            for (int j = columns - 1; j >= 0; j--) {
                boolean isTrue = ((i / (int) Math.pow(2, j))%2) != 1;
                booleanRow.add(isTrue);
            }
            combinationBooleans.add(booleanRow);
        }

        //System.out.println("Factor.productFactor : " + combinationVariables);
        //System.out.println("Factor.productFactor : " + combinationBooleans);

        // multiply the factors. n^2. go through all combination of rows in both table 1 and table 2. match
        // them to the corresponding row in the combination table, and set the value by multiplying them.
        List<Float> combinationProbabilities = new ArrayList<Float>(combinationBooleans.size());
        for(int i = 0; i < combinationBooleans.size(); i++) {
            combinationProbabilities.add(0.0f);
        }
        List<String> table1Variables = this.getVariablenames();
        List<String> table2Variables = factor2.getVariablenames();
        for(int i = 0; i < this.getFactors().size(); i++) {
            List<Boolean> booleanRowTable1 = this.getFactors().get(i);
            Float probability1 = this.getFactorProbabilities().get(i);
            for(int j = 0; j < factor2.getFactors().size(); j++) {
                List<Boolean> booleanRowTable2 = this.getFactors().get(j);
                Float probability2 = factor2.getFactorProbabilities().get(j);

                // does this variable and value assignment match the one in BOTH
                // other rows i have? if so, move on. if all of them match, then multiply
                // both and assign to this row in combination table.
                //for(List<Boolean> combinationRow1 : combinationBooleans) {
                for(int x = 0; x < combinationBooleans.size(); x++) {
                    List<Boolean> combinationRow1 = combinationBooleans.get(x);

                    boolean isRowToAddFactor = true;
                    for(int k = 0; k < combinationRow1.size(); k++) {
                        Boolean combinationBool = combinationRow1.get(k);
                        String combinationVariable = combinationVariables.get(k);

                        for(int l = 0; l < booleanRowTable1.size(); l++) {
                            Boolean table1Bool = booleanRowTable1.get(l);
                            String table1Variable = table1Variables.get(l);

                            if(combinationVariable.equals(table1Variable) && !combinationBool.equals(table1Bool)) {
                                isRowToAddFactor = false;
                                break;
                            }
                        } // for table 1 row

                        if(!isRowToAddFactor) break;

                        for(int l = 0; l < booleanRowTable2.size(); l++) {
                            Boolean table2Bool = booleanRowTable2.get(l);
                            String table2Variable = table2Variables.get(l);

                            if(combinationVariable.equals(table2Variable) && !combinationBool.equals(table2Bool)) {
                                isRowToAddFactor = false;
                                break;
                            }
                        } // for table2 row
                    } // for combinationRow1
                    if(isRowToAddFactor) {
                        //System.out.println("Factor.product value: " + probability1 * probability2);
                        //System.out.println("Factor.product booleanRowTable1: " + booleanRowTable1);
                        //System.out.println("Factor.product table1Variables: " + table1Variables);
                        //System.out.println("Factor.product booleanRowTable2: " + booleanRowTable2);
                        //System.out.println("Factor.product table2Variables: " + table2Variables);
                        //System.out.println("Factor.product combinationRow1: " + combinationRow1);
                        //System.out.println("F==============================================F");

                        combinationProbabilities.set(x, probability1 * probability2);
                    }
                } // for combinationBooleans
            } // for table2 factors
        } // for table 1 factors

        //System.out.println("Factor.productFactor : " + combinationProbabilities);
        return new Factor(combinationVariables, combinationBooleans, combinationProbabilities);
    }

    public Factor sumout(String variable) {
        Factor restrictFactorTrue = this.restrictFactor(variable, true);
        Factor restrictFactorFalse = this.restrictFactor(variable, false);

        List<Float> restrictFactorTrueProbabilities = restrictFactorTrue.getFactorProbabilities();
        List<Float> restrictFactorFalseProbabilities = restrictFactorFalse.getFactorProbabilities();

        assert restrictFactorTrueProbabilities.size() == restrictFactorFalseProbabilities.size();

        List<Float> sumoutProbabilities = new ArrayList<Float>();
        for(int i = 0; i < restrictFactorTrueProbabilities.size(); i++) {
            Float probability1 = restrictFactorTrueProbabilities.get(i);
            Float probability2 = restrictFactorFalseProbabilities.get(i);

            sumoutProbabilities.add(probability1+probability2);
        }

        return new Factor(restrictFactorTrue.getVariablenames(), restrictFactorTrue.getFactors(), sumoutProbabilities);
    }

    @Override
    public String toString() {
        return "Factor{" +
                "variablenames=" + variablenames +
                ", factors=" + factors +
                ", factorProbabilities=" + factorProbabilities +
                '}';
    }
}
