/**
 * @author Serj
 */
public class Evidence {
    private final String variable;
    private final Boolean value;

    public Evidence(String variable, Boolean value) {
        this.variable = variable;
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public String toString() {
        return "Evidence{" +
                "variable='" + variable + '\'' +
                ", value=" + value +
                '}';
    }
}
