/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameClasses;

/**
 *
 * @author Bernardo
 */
public class Card {
    private int iD;
    private String nome;
    private int edicao;
    private int dono;

    public Card(int iD, String nome, int edicao, int dono) {
        this.iD = iD;
        this.nome = nome;
        this.edicao = edicao;
        this.dono = dono;
    }

    public int getiD() {
        return iD;
    }

    public String getNome() {
        return nome;
    }

    public int getEdicao() {
        return edicao;
    }

    public int getDono() {
        return dono;
    }

    public void setDono(int dono) {
        this.dono = dono;
    }

    
    @Override
    public String toString() {
        return "Card{" + "iD=" + iD + ", nome=" + nome + ", edicao=" + edicao + ", dono=" + dono + '}';
    }
    
    
}
