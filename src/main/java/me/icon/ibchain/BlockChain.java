package me.icon.ibchain;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * 区块链
 * Created by Lin on 2018/6/11.
 */
public class BlockChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficult = 5;

    public static void main(String[] args) {
        System.out.println("Trying to Mine block 1...");
        addBlock(new Block("Hi im the first block", "0"));

        System.out.println("Trying to Mine block 2...");
        addBlock(new Block("Hi im the second block", blockchain.get(blockchain.size() - 1).hash));

        System.out.println("Trying to Mine block 3...");
        addBlock(new Block("Hi im the third block", blockchain.get(blockchain.size() - 1).hash));

        System.out.println("\nBlockChain is Valid: " + isChainValid());

        String blockchainJsonString = JSON.toJSONString(blockchain, true);
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJsonString);
    }

    public static boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficult]).replace('\0', '0');

        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal.");
                return false;
            }

            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashed not equal.");
                return false;
            }
            if (!currentBlock.hash.substring(0, difficult).equals(hashTarget)) {
                System.out.println("This block hasn't been mined.");
                return false;
            }
        }
        return true;
    }


    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficult);
        blockchain.add(newBlock);
    }
}
