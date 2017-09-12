package t1;

import java.util.ArrayList;
import java.util.List;

public class Agente {

    private int bateria;
    private int repositorio;
    private Ponto posicao;
    private final int cargaMax;
    private final int repositorioMax;
    private Ambiente ambiente;
    private int direcao;
    private int sentido;
    private boolean flagDiagonal;
    private char sinalX, sinalY;

    public Agente(int carga, int repositorio, int n, int lixeiras, int pontos) {
        cargaMax = carga;
        repositorioMax = repositorio;
        bateria = carga;
        posicao = new Ponto(0, 0, '.');
        ambiente = new Ambiente(n, lixeiras, pontos);
        sentido = 1;
        direcao = 1;
    }

    public void varreAmbiente() {

        if (flagDiagonal) {
            if (sinalX == '+' && sinalY == '+') {
                direcao = 1;
                if (ambiente.posicaoPermitida(posicao.getX() - 1, posicao.getY() + 1)) {
                    posicao.setPosicao(posicao.getX() - 1, posicao.getY() + 1);
                }
            } else if (sinalX == '+' && sinalY == '-') {
                direcao = -1;
                if (ambiente.posicaoPermitida(posicao.getX() - 1, posicao.getY() - 1)) {
                    posicao.setPosicao(posicao.getX() - 1, posicao.getY() - 1);
                }
            }
            flagDiagonal = false;
            sinalX = '.';
            sinalY = '.';
        } else if (ambiente.temDuasParedes(posicao) && sentido == 1) {
            if (ambiente.posicaoPermitida(posicao.getX() + 1, posicao.getY() - 1)) {
                posicao.setPosicao(posicao.getX() + 1, posicao.getY() - 1);
                direcao = 1;
            } else {
                posicao.setPosicao(posicao.getX() + 1, posicao.getY());
                direcao = 1;
            }
        } else if (direcao == 1 && ambiente.possoIrDireita(posicao)) {
            posicao.setPosicao(posicao.getX(), posicao.getY() + 1);
        } else if (direcao == 1 && !ambiente.temParede(posicao.getX(), posicao.getY() + 1)) {
            posicao.setPosicao(posicao.getX() + 1, posicao.getY() + 1);
            if (!ambiente.temParedeDireita(posicao.getX(), posicao.getY())) {
                flagDiagonal = true;
                sinalX = '+';
                sinalY = '+';
            }
        } else if (direcao == -1 && ambiente.possoIrEsquerda(posicao)) {
            posicao.setPosicao(posicao.getX(), posicao.getY() - 1);
        } else if (direcao == -1 && !ambiente.temParede(posicao.getX(), posicao.getY() - 1)) {
            posicao.setPosicao(posicao.getX() + 1, posicao.getY() - 1);
            if (!ambiente.temParedeEsquerda(posicao.getX(), posicao.getY())) {
                flagDiagonal = true;
                sinalX = '+';
                sinalY = '-';
            } else {
                direcao = 1;
            }
        } else if (sentido == 1 && ambiente.possoIrBaixo(posicao)) {
            posicao.setPosicao(posicao.getX() + 1, posicao.getY());
            direcao = -direcao;
        } else if (sentido == 1 && !ambiente.temParede(posicao.getX() + 1, posicao.getY())) {
            posicao.setPosicao(posicao.getX() + 1, posicao.getY() + 1);
            direcao = 1;
        } else if (sentido == 1 && ambiente.posicaoPermitida(posicao.getX() + 1, posicao.getY() - 1)) {
            posicao.setPosicao(posicao.getX() + 1, posicao.getY() - 1);
            direcao = -1;
        }

//        if(posicao.getY() > ambiente.getTamanho()/2 && posicao.getX() > ambiente.getTamanho()/2 && sentido == 1 && posicaoAcima(p) && p.getConteudo() != '*'){
//            posicao.setPosicao(p.getX(), p.getY());
//            direcao = -1;
//        }
        ambiente.setPosicaoAgente(posicao.getX(), posicao.getY());
        if (ambiente.getSujeiras().contains(posicao.getX() + "," + posicao.getY())) {
            repositorio++;
            ambiente.tiraSujeira(posicao);
        }
        if (repositorio == repositorioMax) {
            buscaLixeira(posicao);
        }
        bateria--;
        if (bateria <= (bateria * 0.1)) {
            buscaPontoRecarga(posicao);
        }
        if (posicao.getX() != (ambiente.getTamanho() - 1) || posicao.getY() != (ambiente.getTamanho() - 1)) {
            varreAmbiente();
        }
    }

    private void buscaPontoRecarga(Ponto p) {
    }

    private boolean posicaoAcima(Ponto p) {
        return p.getX() == posicao.getX() && p.getY() < posicao.getY();
    }

    private boolean posicaoDireita(Ponto p) {
        return p.getX() == posicao.getX() && p.getY() > posicao.getY();
    }

    private boolean posicaoAbaixo(Ponto p) {
        return p.getX() > posicao.getX() && p.getY() == posicao.getY();
    }

    private boolean posicaoEsquerda(Ponto p) {
        return p.getX() == posicao.getX() && p.getY() < posicao.getY();
    }

    /**
     * The main A Star Algorithm in Java.
     *
     * finds an allowed path from start to goal coordinates on this map.
     * <p>
     * This method uses the A Star algorithm. The hCosts value is calculated in
     * the given Node implementation.
     * <p>
     * This method will return a LinkedList containing the start node at the
     * beginning followed by the calculated shortest allowed path ending with
     * the end node.
     * <p>
     * If no allowed path exists, an empty list will be returned.
     * <p>
     * <p>
     * x/y must be bigger or equal to 0 and smaller or equal to width/hight.
     *
     * @param oldX x where the path starts
     * @param oldY y where the path starts
     * @param newX x where the path ends
     * @param newY y where the path ends
     * @return the path as calculated by the A Star algorithm
     */
    private List<Ponto> buscaLixeira(int oldX, int oldY, int newX, int newY) {

        ArrayList<Ponto> openList = new ArrayList<Ponto>();
        ArrayList<Ponto> closedList = new ArrayList<Ponto>();
        Ponto lixeira = ambiente.getCampo(newX, newY);
        openList.add(ambiente.getCampo(oldX, oldY)); // add starting node to open list

        boolean done = false;
        Ponto current;
        while (!done) {
            current = lowestFInOpen(); // get node with lowest fCosts from openList provavelmente a heuristica vai aqui
            closedList.add(current); // add current node to closed list
            openList.remove(current); // delete current node from open list

            if ((current.getX() == newX) && (current.getY() == newY)) { // found goal
                return calcPath(nodes[oldX][oldY], current);
            }

            // for all adjacent nodes:
            ArrayList<Ponto> adjacentNodes = ambiente.getRedor(current.getX(), current.getY());
            for (int i = 0; i < adjacentNodes.size(); i++) {
                Ponto currentAdj = adjacentNodes.get(i);
                if (!openList.contains(currentAdj)) { // node is not in openList
                    currentAdj.setAnterior(current); // set current node as previous for this node
                    currentAdj.setValorHeuristica(ambiente.heuristicaLixeiras(currentAdj, lixeira)); // set h costs of this node (estimated costs to goal)
                    currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
                    openList.add(currentAdj); // add node to openList
                } else { // node is in openList
                    if (currentAdj.getgCosts() > currentAdj.calculategCosts(current)) { // costs from current node are cheaper than previous costs
                        currentAdj.setPrevious(current); // set current node as previous for this node
                        currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
                    }
                }
            }

            if (openList.isEmpty()) { // no path exists
                return new ArrayList<Ponto>(); // return empty list
            }
        }
        return null; // unreachable

    }

    private boolean temQuina(ArrayList<Ponto> redor) {
        int cont = 0;
        for (Ponto p : redor) {
            if (p.getConteudo() == '*') {
                cont++;
            }
        }
        return cont == 2;
    }
}
