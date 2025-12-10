import java.util.Stack;

class NaiveSearch {

    private String text;
    private String pattern;
    private int textLength;
    private int patternLength;
    private int textIndex;
    private int patternIndex;
    private int status; // 0 = běží, 1 = nalezen, -1 = nenalezen
    private int startPosition;
    private int step;
    private String currentStepDescription = "";
    private Stack<State> history = new Stack<>();

    public NaiveSearch() {
        step = 0;
    }

    public void step0_readInput(String t, String p) {
        this.text = t;
        this.pattern = p;
        this.step = 1;
    }

    public void executeStep() {
        if (status != 0) return;

        // Save the current state before making changes
        history.push(saveState());
        switch (step) {
            case 1 -> {
                initializeVariables();
                currentStepDescription = "initializeVariables – inicializuji proměnné";
            }
            case 2 -> {
                outerLoop();
                currentStepDescription = "outerLoop – kontroluji pozici textu";
            }
            case 3 -> {
                savePosition();
                currentStepDescription = "savePosition – ukládám startovní pozici";
            }
            case 4 -> {
                innerLoop();
                currentStepDescription = "innerLoop – kontroluji znaky vzoru";
            }
            case 5 -> {
                compareCharacters();
                currentStepDescription = "compareCharacters – porovnávám písmenka";
            }
            case 6 -> {
                match();
                currentStepDescription = "match – znak odpovídá, posouvám indexy";
            }
            case 8 -> {
                endOfText();
                currentStepDescription = "endOfText – konec textu, vzor nenalezen";
            }
            case 9 -> {
                mismatch();
                currentStepDescription = "mismatch – písmena nesouhlasí, posouvám start";
            }
            case 10 -> {
                end();
                currentStepDescription = "end – algoritmus dokončen";
            }
        }
    }

    private void initializeVariables() {
        textLength = text.length();
        patternLength = pattern.length();
        textIndex = 0;
        status = 0;
        step = 2;
    }

    private void outerLoop() {
        if (status != 0) {
            step = 10;
            return;
        }
        if (textIndex > textLength - patternLength) {
            status = -1;
            step = 10;
            return;
        }
        step = 3;
    }

    private void savePosition() {
        startPosition = textIndex;
        patternIndex = 0;
        step = 4;
    }

    private void innerLoop() {
        if (status != 0) {
            step = 10;
            return;
        }
        if (patternIndex < patternLength && textIndex < textLength) {
            step = 5;
        } else if (patternIndex == patternLength) {
            status = 1;
            step = 10;
        } else {
            step = 2;
        }
    }

    private void compareCharacters() {
        if (textIndex >= textLength) {
            step = 8;
            return;
        }
        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);
        if (textChar == patternChar) {
            step = 6;
        } else {
            step = 9;
        }
    }

    private void match() {
        textIndex++;
        patternIndex++;
        if (patternIndex == patternLength) {
            status = 1;
            step = 10;
        } else {
            step = 5;
        }
    }

    private void endOfText() {
        status = -1;
        step = 10;
    }

    private void mismatch() {
        textIndex = startPosition + 1;
        patternIndex = 0;
        step = 2;
    }

    private void end() {
        // konec
    }

    // === Uložení stavu ===
    public State saveState() {
        return new State(textIndex, patternIndex, startPosition, step, status);
    }

    public void restoreState(State s) {
        this.textIndex = s.getTextIndex();
        this.patternIndex = s.getPatternIndex();
        this.startPosition = s.getStartPosition();
        this.step = s.getStep();
        this.status = s.getStatus();
    }

    public void undoStep() {
        if (history != null && !history.isEmpty()) {
            State previousState = history.pop();
            this.textIndex = previousState.getTextIndex();
            this.patternIndex = previousState.getPatternIndex();
            this.startPosition = previousState.getStartPosition();
            this.step = previousState.getStep();
            this.status = previousState.getStatus();
        }
    }

    // === Gettery ===
    public int getTextIndex() { return textIndex; }
    public int getPatternIndex() { return patternIndex; }
    public int getStartPosition() { return startPosition; }
    public int getStatus() { return status; }
    public int getPatternLength() { return patternLength; }
    public String getStepDescription() {
        return currentStepDescription;
    }
    public Stack<State> getHistory() {return history;}
    public boolean hasHistory() {return history != null && !history.isEmpty();}
}