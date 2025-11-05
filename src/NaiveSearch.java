public class NaiveSearch {

    private String text;
    private String pattern;
    private int textLength;
    private int patternLength;
    private int textIndex;
    private int patternIndex;
    private int status; // 0 = running, 1 = found, -1 = not found
    private int startPosition;

    // STEP 0 – Input reading
    public void step0_readInput(String t, String p) {
        this.text = t;
        this.pattern = p;
        step1_initializeVariables();
    }

    // STEP 1 – Initialize variables
    public void step1_initializeVariables() {
        textLength = text.length();
        patternLength = pattern.length();
        textIndex = 0;
        status = 0;
        step2_outerLoop();
    }

    // STEP 2 – Outer loop
    public void step2_outerLoop() {
        while (status == 0) {

            // If remaining text is shorter than pattern → stop
            if (textIndex > textLength - patternLength) {
                status = -1;
                break;
            }

            step3_savePosition();
            step4_innerLoop();
        }
        step10_end();
    }

    // STEP 3 – Save current position
    public void step3_savePosition() {
        startPosition = textIndex;
        patternIndex = 0;
    }

    // STEP 4 – Inner loop
    public void step4_innerLoop() {
        while (true) {
            step5_compareCharacters();
            if (status == 1 || status == -1) return;
            if (patternIndex == 0) return; // mismatch → back to outer loop
        }
    }

    // STEP 5 – Compare characters
    public void step5_compareCharacters() {
        if (textIndex >= textLength) {
            step8_endOfText();
            return;
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);

        if (textChar == patternChar) {
            step6_match();
        } else {
            step9_mismatch();
        }
    }

    // STEP 6 – Match
    public void step6_match() {
        textIndex++;
        patternIndex++;
        step7_checkEndOfPattern();
    }

    // STEP 7 – Check if pattern is fully matched
    public void step7_checkEndOfPattern() {
        if (patternIndex == patternLength) {
            status = 1;
        }
    }

    // STEP 8 – End of text
    public void step8_endOfText() {
        status = -1;
    }

    // STEP 9 – Mismatch
    public void step9_mismatch() {
        textIndex = startPosition + 1;
        patternIndex = 0;
    }

    // STEP 10 – End
    public void step10_end() {
        if (status == 1) {
            System.out.println("✅ The pattern was found at position: " + startPosition);
        } else {
            System.out.println("❌ The pattern was not found.");
        }
    }

    // Test
    public static void main(String[] args) {
        NaiveSearch algorithm = new NaiveSearch();
        String text = "This is a simple test text for pattern searching.";
        String pattern = "test";
        algorithm.step0_readInput(text, pattern);
    }
}
