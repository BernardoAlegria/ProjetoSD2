    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameClasses;

import java.util.ArrayList;

/**
 *
 * @author Bernardo
 */
public class Player {
    
    private int iD;
    private ArrayList<Card> colecao = new ArrayList<>();
    private String userName;
    private String password;
    //TODO dinheiro do player

    public Player(int ID, String userName) {
        this.iD = ID;
        this.userName = userName;
    }
    
    public void addCard(Card card){
        //no futuro, como vão haver poucas escritas e muitas leituras, seria interessante colocar as cartas novas
        //de forma organizada
        colecao.add(card);
    }
    public Card removeCard(int id){
        //corre a coleção até encontrar uma carta com o id desejado, retira, retorna a carta e termina a pesquisa
        Card card = null;
        for (int i = 0; i < colecao.size(); i++) {
            if(colecao.get(i).getiD() == id){
                card = colecao.get(i);
                colecao.remove(i);
                break;
            }
        }
        return card;
    }

    public ArrayList<Card> getColecao() {
        return colecao;
    }

    public int getiD() {
        return iD;
    }
    
    
}
