public class Main {

    // Proměnné algoritmu
    private String text;
    private String vzor;

    private int delkaTextu;
    private int delkaVzoru;
    private int indexTextu;
    private int indexVzoru;
    private int hotovo; // 0 = běží, 1 = nalezeno, -1 = nenalezeno
    private int poziceZacatku;

    // KROK 0 – Načtení vstupu
    public void krok0_nacteniVstupu(String t, String v) {
        this.text = t;
        this.vzor = v;
        krok1_nastaveniPromennych();
    }

    // KROK 1 – Nastavení proměnných
    public void krok1_nastaveniPromennych() {
        delkaTextu = text.length();
        delkaVzoru = vzor.length();
        indexTextu = 0;
        indexVzoru = 0;
        hotovo = 0;
        krok2_vnejsiCyklus();
    }

    // KROK 2 – Vnější cyklus
    public void krok2_vnejsiCyklus() {
        while (hotovo == 0) {
            krok3_zapamatovaniPozice();
            krok4_vnitrniCyklus();
        }
        krok10_konec();
    }

    // KROK 3 – Zapamatování pozice
    public void krok3_zapamatovaniPozice() {
        poziceZacatku = indexTextu;
        indexVzoru = 0;
    }

    // KROK 4 – Začátek vnitřního cyklu
    public void krok4_vnitrniCyklus() {
        while (true) {
            krok5_porovnaniZnaku();
            if (hotovo != 0) return;
        }
    }

    // KROK 5 – Porovnání znaků
    public void krok5_porovnaniZnaku() {
        // Nejprve kontrola konce textu
        if (indexTextu >= delkaTextu) {
            krok8_konecTextu();
            return;
        }

        char znakText = text.charAt(indexTextu);
        char znakVzor = vzor.charAt(indexVzoru);

        if (znakText == znakVzor) {
            krok6_shoda();
        } else {
            krok9_neshoda();
        }
    }

    // KROK 6 – Znaky se shodují
    public void krok6_shoda() {
        indexTextu++;
        indexVzoru++;
        krok7_kontrolaKonceVzorku();
    }

    // KROK 7 – Kontrola konce vzorku
    public void krok7_kontrolaKonceVzorku() {
        if (indexVzoru == delkaVzoru) {
            hotovo = 1;
        }
    }

    // KROK 8 – Konec textu
    public void krok8_konecTextu() {
        hotovo = -1;
    }

    // KROK 9 – Neshoda znaků
    public void krok9_neshoda() {
        indexTextu = poziceZacatku + 1;
        if (indexTextu >= delkaTextu - delkaVzoru + 1) {
            hotovo = -1;
        }
    }

    // KROK 10 – Konec algoritmu
    public void krok10_konec() {
        if (hotovo == 1) {
            System.out.println("✅ Hledané slovo bylo nalezeno na pozici: " + (indexTextu - delkaVzoru));
        } else {
            System.out.println("❌ Hledané slovo nebylo nalezeno.");
        }
    }

    // Testovací main
    public static void main(String[] args) {
        Main algoritmus = new Main();
        String text = "Toto je jednoduchý testovací text pro hledání vzoru.";
        String vzor = "testovací";
        algoritmus.krok0_nacteniVstupu(text, vzor);
    }
}
