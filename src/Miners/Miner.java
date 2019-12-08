/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Miners;

import BlockChain.Block;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author Bernardo
 */
public class Miner {
    
    
    public static void mine(Block b) throws Exception{
        AtomicBoolean isDone = new AtomicBoolean(false);
        AtomicLong nounce = new AtomicLong(0);
        
        int procs =Runtime.getRuntime().availableProcessors();
        Miners.MinerTHR[] miner = new Miners.MinerTHR[procs];
        
        for (int i = 0; i < miner.length; i++) {
            miner[i] = new Miners.MinerTHR(isDone, nounce,b);
            miner[i].start();
        }        
        
        for (int i = 0; i < miner.length; i++) {
            miner[i].join();
        }
        b.setNonce(nounce.get()); 
        //Hash para o bloco (era aqui o problema!!!!!!!!!!!!!!!!)
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        String msg = b.getFact().toString() + nounce.get();
        byte[] h = hasher.digest( msg.getBytes() );
        b.setHash(Base64.getEncoder().encodeToString(h));
        
        }

        
}
