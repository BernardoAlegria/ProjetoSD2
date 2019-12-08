/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlockChain;

import Miners.Miner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author Bernardo
 */
public class Block {
    long nounce;
    private int size;
    protected String hash;
    private String previous;
    private Transaction fact;
    
    public Block(Transaction data, String previousHash) throws NoSuchAlgorithmException, Exception{
        this.previous = previousHash;
        this.fact = data;
        this.size=4;
        Miner.mine(this);
      
    }
    public boolean isValid(){
        //TODO
        return true;
    }
    
    public String calculateHash() throws NoSuchAlgorithmException{
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        String msg = (previous+ fact + nounce);
        byte[] hbytes = hasher.digest(msg.getBytes());
        return Base64.getEncoder().encodeToString(hbytes);
        
    }
    
    public String getHash(){
        return this.hash;
    }
    
    //getters

    public int getSize() {
        return size;
    }

    public String getPreviousHash() {
        return previous;
    }

    public Transaction getFact() {
        return fact;
    }

    public long getNounce() {
        return nounce;
    }

    //setters 
    
    public void setHash(String hash) {
        this.hash = hash; 
    }

    public void setNonce(long nonce) {
        this.nounce = nonce;
    }

    @Override
    public String toString() {
        return "hash=" + hash;
    }
    
    
}
