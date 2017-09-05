package t1;

import java.util.ArrayList;
import java.util.Random;

public class Ambiente {

    private char[][] campo;
    private int tamanho;
    private ArrayList<String> sujeiras, lixeiras, recargas;
    private Ponto posicaoAgente;

    public Ambiente(int n) {
        tamanho = n;
        campo = new char[tamanho][tamanho];
        init();
    }

    private void init() {

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                campo[i][j] = 'N';
            }
        }

        colocaLixeiras();
        colocaRecargas();
        colocaSujeiras();
        colocaParedes();
        imprimeAmbiente();
    }

    public boolean passouBorda(int i, int j) {
        return i >= tamanho || i < 0 || j >= tamanho || j < 0;
    }

    private void colocaLixeiras() {
        Random r = new Random();
        int random = r.nextInt(tamanho - 1);
        int n = (tamanho) - random;
        lixeiras = new ArrayList<String>();

        for (int x = 0; x < n; x++) {
            int i = r.nextInt(tamanho);
            int j = r.nextInt(tamanho);
            campo[i][j] = 'L';
            lixeiras.add(i + "," + j);
        }
    }

    private void colocaRecargas() {
        Random r = new Random();
        int random = r.nextInt(tamanho - 1);
        int n = (tamanho) - random;
        recargas = new ArrayList<String>();
        for (int x = 0; x < n; x++) {
            int i = r.nextInt(tamanho);
            int j = r.nextInt(tamanho);
            campo[i][j] = 'R';
            recargas.add(i + "," + j);
        }
    }

    private void colocaSujeiras() {
        Random r = new Random();
        int random = r.nextInt(tamanho - 1);
        int n = (tamanho * tamanho) - random;
        sujeiras = new ArrayList<String>();
        for (int x = 0; x < n; x++) {
            int i = r.nextInt(tamanho);
            int j = r.nextInt(tamanho);
            campo[i][j] = 'S';
            sujeiras.add(i + "," + j);
        }
    }

    private void colocaParedes() {

        int quantidadeParedesVerticais = (8 * tamanho) / 12;
        int distanciaMurinhos = (4 * tamanho) / 12;
        int vazioVertical = tamanho - quantidadeParedesVerticais;
        int quantidadeParaCadaLado = vazioVertical / 2;
        int i = 0 + quantidadeParaCadaLado;
        int j1 = 1 + quantidadeParaCadaLado;
        int j2 = j1 + distanciaMurinhos;
        int limiteParaPintar = quantidadeParedesVerticais + i;

        campo[i][j1 - 1] = '*';
        campo[i][j2 + 2] = '*';

        while (i < limiteParaPintar) {

            campo[i][j1] = '*';
            campo[i][j2 + 1] = '*';
            i++;

        }

        campo[i - 1][j1 - 1] = '*';
        campo[i - 1][j2 + 2] = '*';

    }

    public ArrayList<Ponto> getRedor(int i, int j) {
        ArrayList<Ponto> pontos = new ArrayList<>();
        if (!passouBorda(i + 1, j)) {
            pontos.add(new Ponto(i + 1, j, campo[i + 1][j]));
        }
        if (!passouBorda(i + 1, j + 1)) {
            pontos.add(new Ponto(i + 1, j + 1, campo[i + 1][j + 1]));
        }
        if (!passouBorda(i + 1, j - 1)) {
            pontos.add(new Ponto(i + 1, j - 1, campo[i + 1][j - 1]));
        }
        if (!passouBorda(i - 1, j)) {
            pontos.add(new Ponto(i - 1, j, campo[i - 1][j]));
        }
        if (!passouBorda(i - 1, j + 1)) {
            pontos.add(new Ponto(i - 1, j + 1, campo[i - 1][j + 1]));
        }
        if (!passouBorda(i - 1, j - 1)) {
            pontos.add(new Ponto(i - 1, j - 1, campo[i - 1][j - 1]));
        }
        if (!passouBorda(i, j - 1)) {
            pontos.add(new Ponto(i, j - 1, campo[i][j - 1]));
        }
        if (!passouBorda(i, j + 1)) {
            pontos.add(new Ponto(i, j + 1, campo[i][j + 1]));
        }

        return pontos;
    }

    private void imprimeAmbiente() {

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                System.out.print(" " + campo[i][j] + " ");
            }
            System.out.println();
        }

    }
    
    public boolean temDuasParedes(Ponto p){
        return campo[p.getX()][p.getY()+1] == '*';
    }

    public void setPosicaoAgente(int x, int y) {
        posicaoAgente.setPosicao(x,y);
        campo[x][y] = 'A';
        imprimeAmbiente();
    }

    public void tiraSujeira(Ponto posicao) {
        campo[posicao.getX()][posicao.getY()] = 'N';
        imprimeAmbiente();
    }

    public int getTamanho() {
        return tamanho;
    }

    public ArrayList<String> getSujeiras() {
        return sujeiras;
    }

}
