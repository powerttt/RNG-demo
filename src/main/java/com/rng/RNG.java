package com.rng;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;


class RNG {
    //declare variables
    private int start;
    private int end;
    private int MIN_NUMBER;
    private String clientSeed;
    private String serverSeed;
    private int nonce;

    //declare constructor
    public RNG(String clientSeed, String serverSeed, int nonce) {
        this.start = 0;
        this.end = 8;
        this.MIN_NUMBER = 1;
        this.clientSeed = clientSeed;
        this.serverSeed = serverSeed;
        this.nonce = nonce;
    }

    //declare method
    public long randomRollValue() {
        String factor = String.join("OB", clientSeed, serverSeed, String.valueOf(nonce));
        String hex = Hashing.sha256()
                .hashString(factor, StandardCharsets.UTF_8)
                .toString();
        //String hex = DigestUtils.sha256Hex(factor);
        String result = hex.substring(start, end);
        long hash = Long.parseLong(result, 16);
        return (hash % 100000000) + 1;
    }

    //main method
    public static void main(String[] args) {
        RNG rng = new RNG("client", "server", 2);
        long rollval = rng.randomRollValue();
        System.out.println(rollval);
    }

}
