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

        // Vypnutí nepotřebných tlačítek na začátku
        btDalsi.setEnabled(false);
        btPredchozi.setEnabled(false);
        btZnova.setEnabled(false);
        txStav.setEditable(false);
    }

    private void initListeners() {
        // === Tlačítko "Vyhledat" ===
        btVyhledat.addActionListener(e -> {
            String text = textArea1.getText();
            String pattern = textField1.getText();

            if (text.isEmpty() || pattern.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Zadej text a hledaný vzor.");
                return;
            }

            algorithm = new NaiveSearch();
            algorithm.step0_readInput(text, pattern);

            btDalsi.setEnabled(true);
            btVyhledat.setEnabled(false);
            btZnova.setEnabled(true);
            clearHighlights();
            highlightCurrentCharacter();
        });

        // === Tlačítko "Další" ===
        btDalsi.addActionListener(e -> {
            if (algorithm == null) return;

            algorithm.executeStep();
            highlightCurrentCharacter();

            if (algorithm.getStatus() != 0) {
                btDalsi.setEnabled(false);
                JOptionPane.showMessageDialog(this,
                        algorithm.getStatus() == 1 ?
                                "✅ Vzor nalezen na pozici: " + algorithm.getStartPosition() :
                                "❌ Vzor nebyl nalezen.");
            }
        });

        // === Tlačítko "Znova" ===
        btZnova.addActionListener(e -> {
            clearHighlights();
            btVyhledat.setEnabled(true);
            btDalsi.setEnabled(false);
        });

        // === Tlačítko "Konec" ===
        btKonec.addActionListener(e -> System.exit(0));
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

            // Zobrazení aktuálního kroku v txStav
            txStav.setText(algorithm.getStepDescription());

            if (status == 1) {
                // Zelené zvýraznění nalezeného vzoru
                highlighter.addHighlight(startPos, startPos + patternLen,
                        new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN));
            } else if (status == -1) {
                // Konec – nic nenalezeno
                highlighter.addHighlight(0, 0, new DefaultHighlighter.DefaultHighlightPainter(Color.WHITE));
            } else {
                // Žluté zvýraznění aktuálního porovnávaného znaku
                if (textIndex < textArea1.getText().length()) {
                    highlighter.addHighlight(textIndex, textIndex + 1,
                            new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
                }
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
