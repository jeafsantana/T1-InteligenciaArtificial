package t1;


public class Ponto {
    private int x, y;
    private char conteudo;
    
    public Ponto(int x, int y, char conteudo){
        this.x = x;
        this.y = y;
        this.conteudo = conteudo;
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
    
    
}
