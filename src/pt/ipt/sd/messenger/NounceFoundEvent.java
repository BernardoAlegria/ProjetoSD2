/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipt.sd.messenger;

import BlockChain.Block;

/**
 *
 * @author Bernardo
 */
public interface NounceFoundEvent {
    public void onNounceFound(Block blk);
}
