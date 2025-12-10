import java.util.Stack;

public class State {
    private final int textIndex;
    private final int patternIndex;
    private final int startPosition;
    private final int step;
    private final int status;

    public State(int textIndex, int patternIndex, int startPosition, int step, int status) {
        this.textIndex = textIndex;
        this.patternIndex = patternIndex;
        this.startPosition = startPosition;
        this.step = step;
        this.status = status;
    }

    public int getTextIndex() {
        return textIndex;
    }

    public int getPatternIndex() {
        return patternIndex;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getStep() {
        return step;
    }

    public int getStatus() {
        return status;
    }
}