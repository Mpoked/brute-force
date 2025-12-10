import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class GUI extends JFrame {
    private JTextArea textArea1;
    private JPanel pnMain;
    private JTextField textField1;
    private JButton btVyhledat;
    private JButton btDalsi;
    private JButton btPredchozi;
    private JButton btZnova;
    private JButton btKonec;
    private JTextField txStav;

    private NaiveSearch algorithm;

    public GUI() {
        initComponents();
        initListeners();
    }

    private void initComponents() {
        this.setTitle("Moje GUI aplikace");
        this.setContentPane(pnMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 300);
        this.setVisible(true);

        // Disable unnecessary buttons at the start
        setButtonStates(false, false, false);
        txStav.setEditable(false);
    }

    private void initListeners() {
        btVyhledat.addActionListener(e -> handleVyhledat());
        btDalsi.addActionListener(e -> handleDalsi());
        btPredchozi.addActionListener(e -> handlePredchozi());
        btZnova.addActionListener(e -> handleZnova());
        btKonec.addActionListener(e -> handleKonec());
    }

    private void handleVyhledat() {
        String text = textArea1.getText();
        String pattern = textField1.getText();

        if (text.isEmpty() || pattern.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Zadej text a hledaný vzor.");
            return;
        }

        algorithm = new NaiveSearch();
        algorithm.step0_readInput(text, pattern);

        setButtonStates(true, false, true);
        clearHighlights();
        highlightCurrentCharacter();
    }

    private void handleDalsi() {
        if (algorithm == null) return;

        algorithm.executeStep();
        highlightCurrentCharacter();

        // Update button states
        btPredchozi.setEnabled(algorithm.hasHistory());
        if (algorithm.getStatus() != 0) {
            btDalsi.setEnabled(false);
            JOptionPane.showMessageDialog(this,
                    algorithm.getStatus() == 1 ?
                            "✅ Vzor nalezen na pozici: " + algorithm.getStartPosition() :
                            "❌ Vzor nebyl nalezen.");
        }
    }

    private void handlePredchozi() {
        if (algorithm == null) return;

        algorithm.undoStep();
        highlightCurrentCharacter();

        // Update button states
        btPredchozi.setEnabled(algorithm.hasHistory());
        btDalsi.setEnabled(algorithm.getStatus() == 0);
    }

    private void handleZnova() {
        clearHighlights();
        setButtonStates(false, false, false);
        btVyhledat.setEnabled(true);
    }

    private void handleKonec() {
        if (algorithm != null) {
            while (algorithm.getStatus() == 0) {
                algorithm.executeStep();
            }
            highlightCurrentCharacter();

            String message = (algorithm.getStatus() == 1) ?
                    "✅ Vzor nalezen na pozici: " + algorithm.getStartPosition() :
                    "❌ Vzor nebyl nalezen.";
            JOptionPane.showMessageDialog(this, message);
        }
        System.exit(0);
    }

    private void setButtonStates(boolean dalsi, boolean predchozi, boolean znova) {
        btDalsi.setEnabled(dalsi);
        btPredchozi.setEnabled(predchozi);
        btZnova.setEnabled(znova);
    }

    private void clearHighlights() {
        textArea1.getHighlighter().removeAllHighlights();
    }

    private void highlightCurrentCharacter() {
        try {
            Highlighter highlighter = textArea1.getHighlighter();
            highlighter.removeAllHighlights();

            if (algorithm == null) return;

            int textIndex = algorithm.getTextIndex();
            int startPos = algorithm.getStartPosition();
            int patternLen = algorithm.getPatternLength();
            int status = algorithm.getStatus();

            // Display the current step in txStav
            txStav.setText(algorithm.getStepDescription());

            if (status == 1) {
                highlighter.addHighlight(startPos, startPos + patternLen,
                        new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN));
            } else if (status == -1) {
                highlighter.addHighlight(0, 0, new DefaultHighlighter.DefaultHighlightPainter(Color.WHITE));
            } else if (textIndex < textArea1.getText().length()) {
                highlighter.addHighlight(textIndex, textIndex + 1,
                        new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}