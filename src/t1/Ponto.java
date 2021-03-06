package t1;

public class Ponto {

    private int x, y;
    private char conteudo;
    public int valorHeuristica, valorG;
    public Ponto anterior;

    public Ponto(int x, int y, char conteudo) {
        this.x = x;
        this.y = y;
        this.conteudo = conteudo;
        anterior = null;
    }

    public void setValorHeuristica(int valor) {
        valorHeuristica = valor;
    }

    public void setAnterior(Ponto p) {
        anterior = p;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getConteudo() {
        return conteudo;
    }

    public void setConteudo(char conteudo) {
        this.conteudo = conteudo;
    }

    void setPosicao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void setValorG(int valor) {
        valorG = valor;
    }
    
    int getValorG(){
        return valorG;
    }

    @Override
    public boolean equals(Object obj) {
        final Ponto other = (Ponto) obj;
        return this.x == other.x && this.y == other.y;
    }
}
