package t1;

import java.util.ArrayList;
import java.util.Random;

public class Ambiente {
    private char[][] campo;
    private int tamanho;
    private ArrayList<String> sujeiras, lixeiras, recargas;
    
    public Ambiente(int  n){
        tamanho = n;
        campo = new char[tamanho][tamanho];
        init();
    }
    
     private void init() {
         
        for(int i = 0 ; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                campo[i][j] = 'N';
            }
        }
        
        colocaLixeiras();
        //colocaRecargas();
        //colocaSujeiras();
        imprimeAmbiente();
    }
    
    
    public boolean passouBorda(int i, int j){
        return i >= tamanho || i < 0 || j >= tamanho || j < 0;
    }
    
    private void colocaLixeiras() {
        Random r = new Random();
        int random = r.nextInt(tamanho - 5);
        int n = (tamanho) - random;
        lixeiras = new ArrayList<String>();
       
        for(int x = 0; x < n; x++){            
            int i = r.nextInt(tamanho);
            int j = r.nextInt(tamanho);
            campo[i][j] = 'L';
            lixeiras.add(i + "," + j);
        }
    }

    private void colocaRecargas() {
        Random r = new Random();
        int n = (tamanho) - r.nextInt();
        recargas = new ArrayList<String>();
        for(int x = 0; x < n; x++){
            int i = r.nextInt(tamanho);
            int j = r.nextInt(tamanho);
            campo[i][j] = 'R';
            recargas.add(i + "," + j);
        }
    }

    private void colocaSujeiras() {
        Random r = new Random();
        int n = (tamanho * tamanho) - r.nextInt();
        sujeiras = new ArrayList<String>();
        for(int x = 0; x < n; x++){
            int i = r.nextInt(tamanho);
            int j = r.nextInt(tamanho);
            campo[i][j] = 'S';
            sujeiras.add(i + "," + j);
        }
    }   
    
    public ArrayList<Ponto> getRedor(int i, int j){
        ArrayList<Ponto> pontos = new ArrayList<>();
        if(!passouBorda(i+1, j))
            pontos.add(new Ponto(i + 1, j, campo[i+1][j]));
        if(!passouBorda(i+1, j+1))
            pontos.add(new Ponto(i + 1, j + 1, campo[i+1][j+1]));
        if(!passouBorda(i+1,j-1))
            pontos.add(new Ponto(i + 1, j - 1, campo[i+1][j-1]));
        if(!passouBorda(i-1,j))
            pontos.add(new Ponto(i - 1, j, campo[i-1][j]));
        if(!passouBorda(i-1,j+1))
            pontos.add(new Ponto(i - 1, j + 1, campo[i-1][j+1]));
        if(!passouBorda(i-1, j-1))
            pontos.add(new Ponto(i - 1, j - 1, campo[i-1][j-1]));
        if(!passouBorda(i,j-1))
            pontos.add(new Ponto(i, j - 1, campo[i][j-1]));
        if(!passouBorda(i, j+1))
            pontos.add(new Ponto(i , j + 1, campo[i][j+1]));
        
        return pontos;
    }
    
    private void imprimeAmbiente(){
        
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){            
                    System.out.print(" " + campo[i][j] + " ");            
                }   
            System.out.println();
            }  
    
    }
    
}
