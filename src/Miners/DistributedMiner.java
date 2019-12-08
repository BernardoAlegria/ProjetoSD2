/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miners;

import BlockChain.Block;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Bernardo
 */
public class DistributedMiner {
    
    long nounce=0;
    AtomicBoolean isWorking;

    public DistributedMiner() {
        isWorking = new AtomicBoolean(false);
    }
    
    public void mine(Block blk) throws Exception{
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");
            String prefix = String.format("%0" + blk.getSize() + "d", 0);
            isWorking.set(true);
            while( isWorking() ){
                long num = ThreadLocalRandom.current().nextLong();
                
                String msg = blk.getFact().toString() + num;
                byte[] h = hasher.digest(msg.getBytes());
                String txtH = Base64.getEncoder().encodeToString(h);
                if( txtH.startsWith(prefix)){
                    nounce = num;
                    break;
                }
            }
    }
    
    public void stopMining(){
        isWorking.set(false);
    }
    
    public boolean isWorking(){
        return isWorking.get();
    }
    
    public long getNounce(){
        return nounce;
    }
}
