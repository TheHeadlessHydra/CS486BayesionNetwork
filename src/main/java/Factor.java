import java.util.*;

/**
 * Created by Serj on 17/10/2015.
 */
public class Factor {

    private final List<String> variableNames;
    private final List<List<Boolean>> booleans;
    private final List<Float> factors;

    Factor(List<String> variableNames,
           List<List<Boolean>> booleans,
           List<Float> factors) {

        if(booleans.size() != factors.size()) {
            if(factors.size() != 1) {
                throw new IllegalArgumentException("number of probabilities and number of boolean combinations must be the same");
            }
        }
        if(Math.round(Math.pow(2, variableNames.size())) != booleans.size()){
            if(variableNames.size() != 1 || booleans.size() != 2) {
                throw new IllegalArgumentException("There must be 2^variables number of boolean columns: " +
                        "variableNames: " + variableNames + ", booleans: " + booleans + ", factors: " + factors);
            }
        }

        for(List<Boolean> bools : booleans) {
            if(bools.size() != variableNames.size()) {
                throw new IllegalArgumentException("There must be as many booleans as there are variables. Problem: " +
                bools);
            }
        }

        this.variableNames = variableNames;
        this.factors = factors;
        this.booleans = booleans;
    }

    public List<String> getVariableNames() {
        return variableNames;
    }

    public List<List<Boolean>> getBooleans() {
        return booleans;
    }

    public List<Float> getFactors() {
        return factors;
    }

    public Factor restrictFactor(String variable, boolean value) {
        if(getVariableNames().isEmpty()) {
            return this;
        }

        int index = getVariableNames().indexOf(variable);
        if(index == -1) {
            return this;
        }

        List<String> variablenames = new ArrayList<String>();
        List<List<Boolean>> factors = new ArrayList<List<Boolean>>();
        List<Float> factorProbabilities = new ArrayList<Float>();


        for(String name : getVariableNames()) {
            variablenames.add(name);
        }
        variablenames.remove(index);

        for(int i = 0; i < getBooleans().size(); i++) {
            Float probability = getFactors().get(i);
            List<Boolean> bools = getBooleans().get(i);

            if(bools.get(index).equals(value)) {
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

    public Factor productFactor(Factor secondFactor) {
        if(getVariableNames().isEmpty()) {
            return this;
        }

        // generate combination of variables
        Set<String> randomVariablesBetweenTwo = new HashSet<String>();
        for(String variable : getVariableNames()) {
            randomVariablesBetweenTwo.add(variable);
        }
        for(String variable : secondFactor.getVariableNames()) {
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

        System.out.println("Factor.productFactor : product 1: " + this);
        System.out.println("Factor.productFactor : product 2: " + secondFactor);

        // multiply the booleans. n^2. go through all combination of rows in both table 1 and table 2. match
        // them to the corresponding row in the combination table, and set the value by multiplying them.
        List<Float> combinationFactors = new ArrayList<Float>(combinationBooleans.size());
        for(int i = 0; i < combinationBooleans.size(); i++) {
            combinationFactors.add(0.0f);
        }
        List<String> table1Variables = this.getVariableNames();
        List<String> table2Variables = secondFactor.getVariableNames();
        for(int i = 0; i < this.getBooleans().size(); i++) {
            List<Boolean> booleanRowTable1 = this.getBooleans().get(i);
            Float factor1 = this.getFactors().get(i);
            for(int j = 0; j < secondFactor.getBooleans().size(); j++) {
                List<Boolean> booleanRowTable2 = this.getBooleans().get(j);
                Float factor2 = secondFactor.getFactors().get(j);

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
                            System.out.println("Factor.productFactor : b" + booleanRowTable2);
                            System.out.println("Factor.productFactor : v" + table2Variables);

                            Boolean table2Bool = booleanRowTable2.get(l);
                            String table2Variable = table2Variables.get(l);

                            if(combinationVariable.equals(table2Variable) && !combinationBool.equals(table2Bool)) {
                                isRowToAddFactor = false;
                                break;
                            }
                        } // for table2 row
                    } // for combinationRow1
                    if(isRowToAddFactor) {
                        combinationFactors.set(x, factor1 * factor2);
                    }
                } // for combinationBooleans
            } // for table2 booleans
        } // for table 1 booleans

        return new Factor(combinationVariables, combinationBooleans, combinationFactors);
    }

    public Factor sumout(String variable) {
        if(getVariableNames().isEmpty()) {
            return this;
        }

        Factor restrictFactorTrue = this.restrictFactor(variable, true);
        Factor restrictFactorFalse = this.restrictFactor(variable, false);

        List<Float> restrictFactorTrueProbabilities = restrictFactorTrue.getFactors();
        List<Float> restrictFactorFalseProbabilities = restrictFactorFalse.getFactors();

        assert restrictFactorTrueProbabilities.size() == restrictFactorFalseProbabilities.size();

        List<Float> sumoutProbabilities = new ArrayList<Float>();
        for(int i = 0; i < restrictFactorTrueProbabilities.size(); i++) {
            Float factor1 = restrictFactorTrueProbabilities.get(i);
            Float factor2 = restrictFactorFalseProbabilities.get(i);

            sumoutProbabilities.add(factor1+factor2);
        }

        return new Factor(restrictFactorTrue.getVariableNames(), restrictFactorTrue.getBooleans(), sumoutProbabilities);
    }

    public Factor normalize() {
        float sum = 0;
        for(Float factor : this.getFactors()) {
            sum += factor;
        }
        List<Float> newFactors = new ArrayList<Float>(this.getFactors().size());
        for(Float factor : this.getFactors()) {
            newFactors.add(factor / sum);
        }
        return new Factor(this.getVariableNames(), this.getBooleans(), newFactors);
    }

    public static Factor inference(List<Factor> factorList,
                            List<String> queryVariables,
                            List<String> orderedListOfHiddenVariables,
                            List<Evidence> evidenceList) {

        // restrict the factors in factorList using the evidence in evidenceList
        List<Factor> restrictedFactorList = new ArrayList<Factor>();
        for(Evidence evidence : evidenceList) {
            String variable = evidence.getVariable();
            Boolean value = evidence.getValue();

            for(Factor factor : factorList) {
                Factor restrictedFactor = factor.restrictFactor(variable, value);
                restrictedFactorList.add(restrictedFactor);
            }
        }
        factorList = restrictedFactorList;

        for(String hiddenVariable : orderedListOfHiddenVariables) {
            // compute the product of all the factors in factorList that contain this variable
            List<Integer> indexesOfFactorsToSum = new ArrayList<Integer>();
            for(int i = 0; i < factorList.size(); i++) {
                Factor restrictedFactor = factorList.get(i);
                if(restrictedFactor.isVariableInFactor(hiddenVariable)) {
                    indexesOfFactorsToSum.add(i);
                }
            }
            if(indexesOfFactorsToSum.isEmpty()) {
                System.out.println("Is something wrong here?");
                continue;
            }
            Factor factorBeingMultiplied = factorList.get(indexesOfFactorsToSum.get(0));
            for(int i = 1; i < indexesOfFactorsToSum.size(); i++) {
                if(i >= indexesOfFactorsToSum.size()) break;
                Integer index = indexesOfFactorsToSum.get(i);
                factorBeingMultiplied = factorBeingMultiplied.productFactor(factorList.get(index));
            }

            System.out.println("Factor.inference indexesSummed: " + indexesOfFactorsToSum);

            //remove all the factors we just took the product of from factorList
            for(Integer index : indexesOfFactorsToSum) {
                factorList.remove(index);
            }

            // sum out the variable
            Factor summedFactor = factorBeingMultiplied.sumout(hiddenVariable);

            // add the resulting factor back to factorList
            factorList.add(summedFactor);
        }

        assert(factorList.size() == 1);
        System.out.println("Factor.inference : " + factorList);
        return factorList.get(0);
    }

    private boolean isVariableInFactor(String checkVariable) {
        for(String variable : getVariableNames()) {
            if(variable.equals(checkVariable))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Factor{" +
                "variableNames=" + variableNames +
                ", booleans=" + booleans +
                ", factors=" + factors +
                '}';
    }
}
