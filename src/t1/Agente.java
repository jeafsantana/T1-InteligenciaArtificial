package t1;

import java.util.ArrayList;

public class Agente {

    private int bateria;
    private int repositorio;
    private Ponto posicao;
    private int cargaMax;
    private int repositorioMax;
    private Ambiente ambiente;
    private int direcao;

    public Agente(int carga, int repositorio, int n) {
        cargaMax = carga;
        repositorioMax = repositorio;
        bateria = carga;
        posicao = new Ponto(0, 0, '.');
        ambiente = new Ambiente(n);

    }

    private void varreAmbiente() {
        ArrayList<Ponto> redor = ambiente.getRedor(posicao.getX(), posicao.getY());
        
        for(Ponto p : redor){
            if(posicaoDireita(p) && p.getConteudo() != '*' && direcao == 1){
                posicao.setPosicao(p.getX(),p.getY());
                break;
            }
            if(posicaoAbaixo(p) && p.getConteudo() != '*'){
                posicao.setPosicao(p.getX(),p.getY());
                direcao = -direcao;
                break;
            }
            if(posicaoEsquerda(p) && p.getConteudo() != '*' && direcao == -1){
                posicao.setPosicao(p.getX(),p.getY());
                break;
            }
        }
        ambiente.setPosicaoAgente(posicao.getX(), posicao.getY());
        if(ambiente.getSujeiras().contains(posicao.getX()+","+posicao.getY())){
            repositorio++;
            ambiente.tiraSujeira(posicao);
        }
        if(repositorio == repositorioMax){
            buscaLixeira(posicao);
        }
        bateria--;
        if (bateria <= (bateria * 0.1)) {
            buscaPontoRecarga(posicao);
        }
        if(posicao.getX() != (ambiente.getTamanho() - 1) && posicao.getY() != (ambiente.getTamanho() - 1))
            varreAmbiente();
    }
    
    private void buscaPontoRecarga(Ponto p){}

    private boolean posicaoDireita(Ponto p) {
        return p.getX() > posicao.getX() && p.getY() == posicao.getY();
    }

    private boolean posicaoAbaixo(Ponto p) {
        return p.getX() == posicao.getX() && p.getY() > posicao.getY();
    }

    private boolean posicaoEsquerda(Ponto p) {
        return p.getX() < posicao.getX() && p.getY() == posicao.getY();
    }

    private void buscaLixeira(Ponto posicao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
