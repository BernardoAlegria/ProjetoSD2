/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import GameClasses.Card;
import GameClasses.Player;

/**
 *
 * @author Bernardo
 */
public class Transaction {
    
    //2 tipos de transação: entre jogadores (normal) e entre jogador e sistema (geracao)
    private String type;
    private Player giver;
    private Player receiver;
    private Card card1;
    private Card card2;
    
    public Transaction(String type, Player giver, Player receiver, Card card1, Card card2) {
        this.type = type;
        this.giver = giver;
        this.receiver = receiver;
        this.card1 = card1;
        this.card2 = card2;
    }
    //TODO   
    @Override
    public String toString(){
        //para usar, por exemplo, para fazer as Hash.
        return ""+type+giver+receiver+card1+card2;
    }
    
}
