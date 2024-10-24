package cfwos;

public class KMP {
    private int[] tabelaPrefixo;

    public void buscar(String padrao, String texto) {
        int M = padrao.length();
        int N = texto.length();
        tabelaPrefixo = new int[M];
        int j = 0;

        criarTabelaPrefixo(padrao, M);

        int i = 0;
        while (i < N) {
            if (padrao.charAt(j) == texto.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                System.out.println("Padrão encontrado no índice " + (i - j));
                j = tabelaPrefixo[j - 1];
            } else if (i < N && padrao.charAt(j) != texto.charAt(i)) {
                if (j != 0) {
                    j = tabelaPrefixo[j - 1];
                } else {
                    i++;
                }
            }
        }
    }

    private void criarTabelaPrefixo(String padrao, int M) {
        int tamanhoPrefixo = 0;
        int i = 1;
        tabelaPrefixo[0] = 0;

        while (i < M) {
            if (padrao.charAt(i) == padrao.charAt(tamanhoPrefixo)) {
                tamanhoPrefixo++;
                tabelaPrefixo[i] = tamanhoPrefixo;
                i++;
            } else {
                if (tamanhoPrefixo != 0) {
                    tamanhoPrefixo = tabelaPrefixo[tamanhoPrefixo - 1];
                } else {
                    tabelaPrefixo[i] = tamanhoPrefixo;
                    i++;
                }
            }
        }
    }
}