/** ************************************************************************** */
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
import Miners.DistributedMiner;
import java.io.IOException;
import pt.ipt.sd.demo.rmi.Hello.*;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import myUtils.Crypt;
import myUtils.Serializer;

/**
 *
 * @author manso
 */
public class RemoteNetNode extends UnicastRemoteObject implements IRemoteNetNode {
    public static final String NAME = "NET_NODE";
    
    int port;
    String host = "unknown";
    NetworkNode gui;
    
    KeyPair rsa;
    Key aes;
    
    CopyOnWriteArraySet<IRemoteNetNode> nodeList;
    BlockChain myBlockChain;
    DistributedMiner miner = new DistributedMiner();
    
    public RemoteNetNode(int port,BlockChain bc, NetworkNode gui) throws Exception{
        super(port);
        this.port=port;
        this.host = InetAddress.getLocalHost().getHostName();
        nodeList = new CopyOnWriteArraySet<>();
        
        this.gui=gui;
        rsa = Crypt.generateKeyPair("RSA");
        aes = Crypt.generateKey("AES");
        this.myBlockChain=bc;
    }
    
    @Override
    public void sendMessage(String txt) throws RemoteException{
        String client = "";
        try {            
            client = RemoteServer.getClientHost();           
        } catch (ServerNotActiveException ex) {
            client = "unknown";
        }
        
        this.gui.displayMessage(client, txt);
    }
    /*String host = "unknow";
    int port;
    
    

    public RemoteNetNode(int port) throws RemoteException {
        super(port);
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
        }
    }

    public String getMessage() throws RemoteException {
       String client = "";
        try {
           client = RemoteServer.getClientHost();
            System.out.println("CLIENT : " +client);
           
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(RemoteNetNode.class.getName()).log(Level.SEVERE, null, ex);
        }
         return client + " Hello from " + host;
    }

    @Override
    public String getMessage(String txt) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */
    @Override
    public void addNode(IRemoteNetNode node) throws RemoteException {
        //adiciona o node à sua lista
        nodeList.add(node);
        //Envia o node para todos os seus nodes que não o tenham.
        for (IRemoteNetNode iRemoteNetNode : nodeList) {
            if(!iRemoteNetNode.getNodes().contains(node))
                iRemoteNetNode.addNode(node);
        }
        //atualiza a lista de nodos
        gui.displayNodes();
    }

    @Override
    public List<IRemoteNetNode> getNodes() throws RemoteException {
      return new ArrayList<>(nodeList);
    }

    @Override
    public String getName() throws RemoteException {
        return host+": "+port;
    }

    @Override
    public byte[] getAES(PublicKey pub) throws RemoteException {
        try {
            return Crypt.encrypt(Serializer.objectToByteArray(aes), pub);
        } catch (IOException ex) {
            Logger.getLogger(RemoteNetNode.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    

    @Override
    public void sendString(byte[] txt) throws RemoteException {
        byte[] plain = Crypt.decrypt(txt, aes);
        sendMessage(new String(plain));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RemoteNetNode other = (RemoteNetNode) obj;
        if (this.port != other.port) {
            return false;
        }
        if(!Objects.equals(this.host, other.host)){
            return false;
        }
        return true;
    }

    @Override
    public void addBlock(Block blk) throws RemoteException {
        try {
            
            gui.displayMessage("blockChain: ", "adicionando o bloco");
            //adiciona o bloco
            myBlockChain.addBlock(blk);
            gui.displayMessage("blockChain: ", "bloco adicionado");
            //update na lista
            gui.displayLastNode();
        } catch (Exception ex) {
            gui.displayLog("add Block Error", ex);
            Logger.getLogger(RemoteNetNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BlockChain getBlockChain() throws RemoteException {
        return myBlockChain;
    }

    @Override
    public void mine(Block b) throws RemoteException {
        try {
            //verifica se o miner já está a trabalhar
            if (miner.isWorking()){
                gui.displayMessage("miner: ", "miner busy");
                return;
            }
            gui.setWorking(true);
           //minar de forma assincrona
            new Thread(
                //lambda expression
                () -> {
                    try {
                        //enviar bloco para minagem para a rede
                        for (IRemoteNetNode node : nodeList) {
                            node.mine(b);
                        }

                        //começar a minar
                        //setnounce?
                        miner.mine(b, gui);
                    } catch (Exception ex) {
                        Logger.getLogger(RemoteNetNode.class.getName()).log(Level.SEVERE, null, ex);
                        gui.displayLog(host, ex);
                    }

                }
        ).start();
            
            //b.setNonce(miner.getNounce());
        } catch (Exception ex) {
            gui.displayLog("Miner error", ex);
            Logger.getLogger(RemoteNetNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void stopMining(Block blk) throws RemoteException{
        gui.setWorking(false);
        miner.stopMining();
        //dou o bloco a minar à rede
        System.out.println("chainSize: " + myBlockChain.getBlockchain().size());
        //se ja adicionei o bloco à minha lista
        if (myBlockChain.getBlockchain().contains(blk)) {
            return;
        }
        //se não tenho de adicionar e enviar aos meus nodes
        myBlockChain.getBlockchain().add(blk);
        for (IRemoteNetNode node : nodeList) {
            node.stopMining(blk);
        }
    }
    
}
