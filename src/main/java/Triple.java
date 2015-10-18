/**
 * Created by Serj on 17/10/2015.
 */
public class Triple<A, B, C> {

    private A first;
    private B second;
    private C thid;

    Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.thid = third;
    }

    A getFirst() {
        return first;
    }

    B getSecond() {
        return second;
    }

    C getThird() {
        return thid;
    }
}
