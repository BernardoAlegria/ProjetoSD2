/*****************************************************************************/
///****     Copyright (C) 2010                                             ****/
///****     António Manuel Rodrigues Manso                                  ****/
///****     e-mail: manso@ipt.pt                                            ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt              ****/
///****     Instituto Politécnico de Tomar                                  ****/
///****     Escola Superior de Tecnologia de Tomar                          ****/
///****                                                                     ****/
///*****************************************************************************/
///****     This software was build with the purpose of learning.           ****/
///****     Its use is free and is not provided any guarantee               ****/
///****     or support.                                                     ****/
///****     If you met bugs, please, report them to the author              ****/
///****                                                                     ****/
///*****************************************************************************/
///*****************************************************************************/

package pt.ipt.sd.messenger;


import BlockChain.Block;
import BlockChain.BlockChain;
import BlockChain.Transaction;
import pt.ipt.sd.demo.rmi.Hello.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manso
 */
public interface IRemoteNetNode  extends Remote{
    
    public void sendMessage(String txt) throws RemoteException;
    
    public String getName() throws RemoteException;
    
    public byte[] getAES(PublicKey pub) throws RemoteException;
    
    public void sendString(byte[] txt) throws RemoteException;
    
    public void addNode(IRemoteNetNode node) throws RemoteException;
    
    public List<IRemoteNetNode> getNodes() throws RemoteException;
    
    //metodos Blockchain
    
    public void addBlock(Transaction tr) throws RemoteException;
    
    public BlockChain getBlockChain() throws RemoteException;
    
    public void Mine(Block b) throws RemoteException;
}
