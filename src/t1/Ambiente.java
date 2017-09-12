package t1;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Ambiente {

    private char[][] campo;
    private int tamanho;
    private ArrayList<Ponto> sujeiras, lixeiras, recargas;
    private Ponto posicaoAgente, inicioMuroEsquerda, finalMuroEsquerda, inicioMuroDireita, finalMuroDireita;
    private int quantLixeiras;
    private int quantRecargas;
    private int quantPontos;

    public Ambiente(int n, int lixeiras, int pontos) {
        tamanho = n;
        quantLixeiras = lixeiras;
        quantPontos = pontos;
        campo = new char[tamanho][tamanho];
        posicaoAgente = new Ponto(0, 0, 'A');
        init();
    }

    private void init() {

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                campo[i][j] = 'N';
            }
        }

        colocaSujeiras();
        colocaParedes();
        colocaLixeiras();
        colocaRecargas();
        campo[0][0] = 'A';
        imprimeAmbiente();
    }

    public boolean passouBorda(int i, int j) {
        return i >= tamanho || i < 0 || j >= tamanho || j < 0;
    }

    private void colocaLixeiras() {
        Random r = new Random();
        //int random = r.nextInt(tamanho - 1);
        lixeiras = new ArrayList<Ponto>();
        Ponto p;

        for (int x = 0; x < quantLixeiras / 2; x++) {
            int i = ThreadLocalRandom.current().nextInt(inicioMuroEsquerda.getX() + 1, finalMuroEsquerda.getX());
            int j = ThreadLocalRandom.current().nextInt(0, finalMuroEsquerda.getY());
            campo[i][j] = 'L';
            p = new Ponto(i, j, campo[i][j]);
            lixeiras.add(p);
        }

        for (int x = 0; x < quantLixeiras / 2; x++) {
            int i = ThreadLocalRandom.current().nextInt(inicioMuroDireita.getX() + 1, finalMuroDireita.getX());
            int j = ThreadLocalRandom.current().nextInt(finalMuroDireita.getY(), tamanho - 1);
            campo[i][j] = 'L';
            p = new Ponto(i, j, campo[i][j]);

            lixeiras.add(p);
        }
    }

    private void colocaRecargas() {
        Random r = new Random();
        recargas = new ArrayList<Ponto>();
        Ponto p;

        for (int x = 0; x < quantRecargas / 2; x++) {
            int i = ThreadLocalRandom.current().nextInt(inicioMuroEsquerda.getX() + 1, finalMuroEsquerda.getX());
            int j = ThreadLocalRandom.current().nextInt(0, finalMuroEsquerda.getY());
            campo[i][j] = 'R';
            p = new Ponto(i, j, campo[i][j]);

            recargas.add(p);
        }

        for (int x = 0; x < quantRecargas / 2; x++) {
            int i = ThreadLocalRandom.current().nextInt(inicioMuroDireita.getX() + 1, finalMuroDireita.getX());
            int j = ThreadLocalRandom.current().nextInt(finalMuroDireita.getY(), tamanho - 1);
            campo[i][j] = 'R';
            p = new Ponto(i, j, campo[i][j]);

            recargas.add(p);
        }
    }

    private void colocaSujeiras() {
        Random r = new Random();
        int random = r.nextInt(tamanho - 1);
        int n = (tamanho * 3) - random;
        sujeiras = new ArrayList<Ponto>();
        Ponto p;
        for (int x = 0; x < n; x++) {
            int i = r.nextInt(tamanho);
            int j = r.nextInt(tamanho);
            campo[i][j] = 'S';
            p = new Ponto(i, j, campo[i][j]);

            sujeiras.add(p);
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

        inicioMuroEsquerda = new Ponto(i, j1 - 1, '.');
        inicioMuroDireita = new Ponto(i, j2 + 2, '.');

        while (i < limiteParaPintar) {

            campo[i][j1] = '*';
            campo[i][j2 + 1] = '*';
            i++;

        }

        campo[i - 1][j1 - 1] = '*';
        campo[i - 1][j2 + 2] = '*';

        finalMuroEsquerda = new Ponto(i - 1, j1 - 1, '.');
        finalMuroDireita = new Ponto(i - 1, j2 + 2, '.');
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
        System.out.println();
    }

    public boolean temDuasParedes(Ponto p) {
        return p.getY() + 2 >= tamanho ? false : campo[p.getX()][p.getY() + 1] == '*' && campo[p.getX()][p.getY() + 2] == '*';
    }

    public void setPosicaoAgente(int x, int y) {
        campo[posicaoAgente.getX()][posicaoAgente.getY()] = 'N';
        posicaoAgente.setPosicao(x, y);
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

    public ArrayList<Ponto> getSujeiras() {
        return sujeiras;
    }

    public boolean possoIrDireita(Ponto posicao) {
        return (posicao.getX() < tamanho && posicao.getY() + 1 < tamanho)
                ? campo[posicao.getX()][posicao.getY() + 1] != '*' && campo[posicao.getX()][posicao.getY() + 1] != 'L' && campo[posicao.getX()][posicao.getY() + 1] != 'R'
                : false;
    }

    public boolean possoIrEsquerda(Ponto posicao) {
        return (posicao.getX() < tamanho && (posicao.getY() - 1 < tamanho && posicao.getY() - 1 >= 0))
                ? campo[posicao.getX()][posicao.getY() - 1] != '*' && campo[posicao.getX()][posicao.getY() - 1] != 'L' && campo[posicao.getX()][posicao.getY() - 1] != 'R'
                : false;
    }

    boolean possoIrBaixo(Ponto posicao) {
        return (posicao.getX() + 1 < tamanho && (posicao.getY() < tamanho))
                ? campo[posicao.getX() + 1][posicao.getY()] != '*' && campo[posicao.getX() + 1][posicao.getY()] != 'L' && campo[posicao.getX() + 1][posicao.getY()] != 'R'
                : false;
    }

    boolean posicaoPermitida(int x, int y) {
        return ((x < tamanho && x >= 0) && (y >= 0 && y < tamanho))
                ? campo[x][y] != '*' && campo[x][y] != 'L' && campo[x][y] != 'R'
                : false;
    }

    boolean temParede(int x, int y) {
        return ((x < tamanho && x >= 0) && (y >= 0 && y < tamanho))
                ? campo[x][y] == '*' : true;
    }

    boolean temParedeDireita(int x, int y) {
        return ((x < tamanho && x >= 0) && (y + 1 >= 0 && y + 1 < tamanho))
                ? campo[x][y + 1] == '*' : true;
    }

    boolean temParedeEsquerda(int x, int y) {
        return ((x < tamanho && x >= 0) && (y - 1 >= 0 && y - 1 < tamanho))
                ? campo[x][y - 1] == '*' : true;
    }

    public Ponto getCampo(int x, int y) {

        return new Ponto(x, y, campo[x][y]);
    }

    public Ponto lixeiraMaisProxima(Ponto posicao) {
        Ponto p1 = null;
        int valorCorrente = 0;
        int valorMenor = 100000;

        for (int i = 0; i < lixeiras.size(); i++) {

            valorCorrente = (lixeiras.get(i).getX() - posicao.getX()) + (lixeiras.get(i).getY() - posicao.getY());

            if (valorCorrente < valorMenor) {
                valorMenor = valorCorrente;
                p1 = lixeiras.get(i);
            }
        }
        return p1;
    }

    public int heuristicaLixeiras(Ponto posicao, Ponto lixeira) {

        return (posicao.getX() - lixeira.getX()) + (posicao.getY() - lixeira.getY());

    }

}
