public class NaivniVyhledavani {

    private String text;
    private String vzorek;
    private int delkaTextu;
    private int delkaVzorku;
    private int indexTextu;
    private int indexVzorku;
    private int hotovo; // 0 = běží, 1 = nalezeno, -1 = nenalezeno
    private int poziceZacatku;

    // KROK 0 – Načtení vstupu
    public void krok0_nacteniVstupu(String t, String v) {
        this.text = t;
        this.vzorek = v;
        krok1_nastaveniPromennych();
    }

    // KROK 1 – Nastavení proměnných
    public void krok1_nastaveniPromennych() {
        delkaTextu = text.length();
        delkaVzorku = vzorek.length();
        indexTextu = 0;
        hotovo = 0;
        krok2_vnejsiCyklus();
    }

    // KROK 2 – Vnější cyklus
    public void krok2_vnejsiCyklus() {
        while (hotovo == 0) {

            // pokud zbývající část textu je kratší než vzorek → konec
            if (indexTextu > delkaTextu - delkaVzorku) {
                hotovo = -1;
                break;
            }

            krok3_zapamatovaniPozice();
            krok4_vnitrniCyklus();
        }
        krok10_konec();
    }

    // KROK 3 – Zapamatování pozice
    public void krok3_zapamatovaniPozice() {
        poziceZacatku = indexTextu;
        indexVzorku = 0;
    }

    // KROK 4 – Vnitřní cyklus
    public void krok4_vnitrniCyklus() {
        while (true) {
            krok5_porovnaniZnaku();
            if (hotovo == 1 || hotovo == -1) return;
            if (indexVzorku == 0) return; // neshoda → návrat do vnějšího cyklu
        }
    }

    // KROK 5 – Porovnání znaků
    public void krok5_porovnaniZnaku() {
        if (indexTextu >= delkaTextu) {
            krok8_konecTextu();
            return;
        }

        char znakText = text.charAt(indexTextu);
        char znakVzorek = vzorek.charAt(indexVzorku);

        if (znakText == znakVzorek) {
            krok6_shoda();
        } else {
            krok9_neshoda();
        }
    }

    // KROK 6 – Shoda
    public void krok6_shoda() {
        indexTextu++;
        indexVzorku++;
        krok7_kontrolaKonceVzorku();
    }

    // KROK 7 – Kontrola, zda je vzor nalezen
    public void krok7_kontrolaKonceVzorku() {
        if (indexVzorku == delkaVzorku) {
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
        indexVzorku = 0;
    }

    // KROK 10 – Konec
    public void krok10_konec() {
        if (hotovo == 1) {
            System.out.println("Hledané slovo bylo nalezeno na pozici: " + poziceZacatku);
        } else {
            System.out.println("Hledané slovo nebylo nalezeno.");
        }
    }

    // Test
    public static void main(String[] args) {
        NaivniVyhledavani algoritmus = new NaivniVyhledavani();
        String text = "Toto je jednoduchý testovací text pro hledání vzoru.";
        String vzorek = "testovací";
        algoritmus.krok0_nacteniVstupu(text, vzorek);
    }
}
