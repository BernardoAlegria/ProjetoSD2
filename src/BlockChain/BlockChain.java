/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import BlockChain.Block;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernardo
 */
public class BlockChain {

    private ArrayList<Block> blockchain = new ArrayList<>();

    public BlockChain() {

    }

    public ArrayList<Block> getBlockchain() {
        return blockchain;
    }

    //deprecated?
    public void insert(Transaction fact) throws NoSuchAlgorithmException, Exception {
        //e se não tiver nada ainda?
        if (blockchain.isEmpty()) {
            String previousHash = "0";
            Block block = new Block(fact, previousHash);
            blockchain.add(block);
        } else {
            String previousHash = blockchain.get(blockchain.size() - 1).getHash();
            Block block = new Block(fact, previousHash);
            blockchain.add(block);
        }
    }

    public void list() {
        for (int i = 0; i < blockchain.size(); i++) {
            String previousHash = blockchain.get(i).getPreviousHash() + " ";
            String value = blockchain.get(i).getFact().toString();
            String hash = blockchain.get(i).getHash() + "";

            System.out.println(previousHash + "fact: " + hash);
        }
    }

    @Override
    public String toString() {
        String lista = "";
        for (int i = 0; i < blockchain.size(); i++) {

            String previousHash = blockchain.get(i).getPreviousHash() + " ";
            String value = blockchain.get(i).getFact().toString();
            String hash = blockchain.get(i).getHash() + "";

            //ve se não foi alterado
            if (!blockchain.get(i).isValid()) {
                lista = lista.concat(previousHash + "fact: " + hash + " erro" + "\n");
            } else {
                lista = lista.concat(previousHash + "fact: " + " " + hash + "\n");
            }
        }
        return lista;
    }
    
    public String getLastBlock(){
        if(blockchain.isEmpty()){
            return "";
        }
        int size = blockchain.size();
        return this.blockchain.get(size-1).hash;
    }
    
    public Block getNewBlock(Transaction tr) throws Exception{
        String prev = getLastBlock();
        return new Block(tr, prev);
    }
    
    public void addBlock(Transaction tr) throws Exception{
        String previousHash = blockchain.get(blockchain.size() - 1).getHash();
        Block blk = new Block(tr, previousHash);
        if(blk.isValid()){
            blockchain.add(blk);          
        }
    }
}
