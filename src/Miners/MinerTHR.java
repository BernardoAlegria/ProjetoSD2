/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miners;

import BlockChain.Block;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernardo
 */
public class MinerTHR extends Thread{
    AtomicBoolean isDone;
    AtomicLong nounce;
    Block blk;

    public MinerTHR(AtomicBoolean isDone, AtomicLong nounce, Block blk) {
        this.isDone = isDone;
        this.nounce = nounce;
        this.blk = blk;
    }
    
    
    @Override
    public void run(){
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");
            String prefix = String.format("%0" + blk.getSize() + "d", 0);
            while( ! isDone.get() ){
                long num = nounce.getAndIncrement();
                
                String msg = blk.getFact().toString() + num;
                byte[] h = hasher.digest(msg.getBytes());
                String txtH = Base64.getEncoder().encodeToString(h);
                if( txtH.startsWith(prefix)){
                    nounce.set(num);
                    isDone.set(true);
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Miners.MinerTHR.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
