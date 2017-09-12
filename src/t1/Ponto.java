package t1;

public class Ponto {

    private int x, y;
    private char conteudo;
    public int valorHeuristica;
    public Ponto anterior;

    public Ponto(int x, int y, char conteudo) {
        this.x = x;
        this.y = y;
        this.conteudo = conteudo;
        anterior = null;
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

}
