/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameClasses;

import BlockChain.BlockChain;
import BlockChain.Transaction;

/**
 *
 * @author Bernardo
 */
public class CardManager {
    
    /*
    public static void addCard(Player player, Card card) {
        player.addCard(card);
    } 
    private static void removeCard(Player player) {    
    }
    **************função na classe Player***************    */ 
    
    //qual a melhor maneira de aceder à blockChain? passa-la por parametro ou esta funçao devolver a transaction
    //para se usar depois?
    public static Transaction tradeCards(Player player1,Player player2, Card card1, Card card2, BlockChain bc) throws Exception {
        //retirar ambas as cartas dos jogadores
        card1 = player1.removeCard(card1.getiD());
        card2 = player2.removeCard(card2.getiD());
        //trocar o id do dono das cartas
        card1.setDono(player2.getiD());
        card2.setDono(player1.getiD());
        //fazer a troca
        player1.addCard(card2);
        player2.addCard(card1);
        // TODO: criar transaction para criar bloco para adicionar à blockchain.
        Transaction tr = new Transaction("troca", player1, player2, card1, card2);
        bc.insert(tr);
        return tr;
        
    }
    public static void buyCard(Player giver,Player receiver, int value){
        
    }
    public static void createCard(){
        
    }
    public static void openPack(Player player){
        //create card x vezes + inserir na coleção do jogador
    }
}
