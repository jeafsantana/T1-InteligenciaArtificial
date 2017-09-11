package t1;

import java.util.ArrayList;

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
        
        if(flagDiagonal){
            if(sinalX == '+' && sinalY == '+'){
                if(ambiente.posicaoPermitida(posicao.getX()-1, posicao.getY()+1)){
                    posicao.setPosicao(posicao.getX()-1, posicao.getY()+1);
                    direcao = 1;
                }
            }
            else if(sinalX == '+' && sinalY == '-'){
                if(ambiente.posicaoPermitida(posicao.getX()-1, posicao.getY()-1)){
                    posicao.setPosicao(posicao.getX()-1, posicao.getY()-1);
                    direcao = -1;
                }
            }
            flagDiagonal = false;
            sinalX = '.';
            sinalY = '.';
        }
        else if(ambiente.temDuasParedes(posicao) && sentido == 1){
            if(ambiente.posicaoPermitida(posicao.getX()+1, posicao.getY()-1)){
                posicao.setPosicao(posicao.getX()+1, posicao.getY()-1);
                direcao = 1;
            }
            else{
                posicao.setPosicao(posicao.getX()+1, posicao.getY());
                direcao = 1;
            }
        }
        else if(direcao == 1 && ambiente.possoIrDireita(posicao)){
            posicao.setPosicao(posicao.getX(),posicao.getY()+1);
        }
        else if(direcao == 1 && !ambiente.temParede(posicao.getX(),posicao.getY()+1)){
            posicao.setPosicao(posicao.getX()+1, posicao.getY()+1);
            if(!ambiente.temParedeDireita(posicao.getX(), posicao.getY())){
                flagDiagonal = true;
                sinalX = '+';
                sinalY = '+';
            }
        }
        else if(direcao == -1 && ambiente.possoIrEsquerda(posicao)){
            posicao.setPosicao(posicao.getX(),posicao.getY()-1);
        }
        else if(direcao == -1 && !ambiente.temParede(posicao.getX(),posicao.getY()-1)){
            posicao.setPosicao(posicao.getX()+1, posicao.getY()-1);
            if(!ambiente.temParedeEsquerda(posicao.getX(), posicao.getY())){
                flagDiagonal = true;
                sinalX = '+';
                sinalY = '-';
            }
            else
                direcao = 1;
        }
        else if(sentido == 1 && ambiente.possoIrBaixo(posicao)){
            posicao.setPosicao(posicao.getX()+1,posicao.getY());
            direcao = -direcao;
        }
        else if(sentido == 1 && !ambiente.temParede(posicao.getX()+1,posicao.getY())){
            posicao.setPosicao(posicao.getX()+1, posicao.getY()+1);
            direcao = 1;
        }
        else if(sentido == 1 && ambiente.posicaoPermitida(posicao.getX()+1, posicao.getY()-1)){
            posicao.setPosicao(posicao.getX()+1, posicao.getY()-1);
            direcao = -1;
        }

//        if(temQuina(redor) && posicao.getX() > ambiente.getTamanho()/2 && sentido == 1){
//            posicao.setPosicao(p.getX()-1, p.getY()-1);
//            direcao = -1;
//        }
//        if(posicao.getY() > ambiente.getTamanho()/2 && posicao.getX() > ambiente.getTamanho()/2 && sentido == 1 && posicaoAcima(p) && p.getConteudo() != '*'){
//            posicao.setPosicao(p.getX(), p.getY());
//            direcao = -1;
//        }
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
        if(posicao.getX() != (ambiente.getTamanho() - 1) || posicao.getY() != (ambiente.getTamanho() - 1))
            varreAmbiente();
    }
    
    private void buscaPontoRecarga(Ponto p){}
    
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

    private void buscaLixeira(Ponto posicao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean temQuina(ArrayList<Ponto> redor) {
        int cont = 0;
        for(Ponto p : redor){
            if(p.getConteudo() == '*') cont++;
        }
        return cont == 2;
    }
}
