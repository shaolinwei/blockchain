package me.icon.ibchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 区块
 * Created by Lin on 2018/6/11.
 */
public class Block implements Serializable {
    private static final long serialVersionUID = -2927217422117176773L;
    public String hash;
    public String previousHash;
    private String data; // our data will be a simple message.
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
    private long timeStamp; // as number of milliseconds since 1/1/1970.
    private int nonce;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash();
    }

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.sha256(previousHash
                + Long.toString(timeStamp)
                + Integer.toString(nonce)
                + data);
    }

    public void mineBlock(int difficulty) {
        String target = StringUtil.getDifficultyString(difficulty);
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block MINED!!! : " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) return false;
        if ((!"0".equals(previousHash))) {
            if ((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
