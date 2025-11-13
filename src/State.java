public class State {
    int textIndex, patternIndex, startPosition, step, status;
    public State(int t, int p, int sPos, int stp, int stat) {
        textIndex = t;
        patternIndex = p;
        startPosition = sPos;
        step = stp;
        status = stat;
    }
}
