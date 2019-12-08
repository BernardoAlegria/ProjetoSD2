/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import GameClasses.Card;
import GameClasses.CardManager;
import GameClasses.Player;
import java.util.ArrayList;

/**
 *
 * @author Bernardo
 */
public class Test {
    public static void main(String[] args) throws Exception {
        BlockChain bc = new BlockChain();
        //cartas para teste
        
        Card[] cards = new Card[10];
        Player[] players = new Player[4];
        for (int i = 0; i < 10; i++) {
            cards[i] = new Card(i, "carta num"+i, 1, i/3);
        }
        //Players para teste
        for (int i = 0; i < 4; i++) {
            players[i] = new Player(i, "User"+i);
        }
        //adicionar cartas aos jogadores
        for (int i = 0; i < 10; i++) {
            players[i/3].addCard(cards[i]);
        }
        //****************testa a geração dos dados para teste********************
       for (int i = 0; i < 4; i++) {
           ArrayList<Card> cartas = players[i].getColecao();
           for (int j = 0; j < cartas.size(); j++) {
               System.out.println("\n"+cartas.get(j).toString());
           }
       }
        
        CardManager.tradeCards(players[0], players[1], cards[0], cards[3], bc);
        CardManager.tradeCards(players[0], players[2], cards[1], cards[6], bc);
        CardManager.tradeCards(players[1], players[2], cards[0], cards[1], bc);
        
        for (int i = 0; i < 4; i++) {
           ArrayList<Card> cartas = players[i].getColecao();
           for (int j = 0; j < cartas.size(); j++) {
               System.out.println("\n"+cartas.get(j).toString());
           }
       }
        bc.list();
    }
}
