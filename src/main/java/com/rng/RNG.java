package com.rng;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;
import java.util.Random;
import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;

class RNG {
    // declare variables
    private int start;
    private int end;
    private int MIN_NUMBER;
    private String clientSeed;
    private String serverSeed;
    private int nonce;

    // declare constructor
    public RNG(String clientSeed, String serverSeed, int nonce) {
        this.start = 0;
        this.end = 8;
        this.MIN_NUMBER = 1;
        this.clientSeed = clientSeed;
        this.serverSeed = serverSeed;
        this.nonce = nonce;
    }

    // declare method
    public long randomRollValue() {
        String factor = String.join("OB", clientSeed, serverSeed, String.valueOf(nonce));
        String hex = Hashing.sha256()
                .hashString(factor, StandardCharsets.UTF_8)
                .toString();
        // String hex = DigestUtils.sha256Hex(factor);
        String result = hex.substring(start, end);
        long hash = Long.parseLong(result, 16);
        return (hash % 100000000) + 1;
    }

    // main method
    /**
     * @param args
     */
    public static void main(String[] args) {
        RNG rng = new RNG("c", "s", 1);
        long rollval = rng.randomRollValue();
        System.out.println(rollval);
        seedpairGen();
        
        
    }

    //new method
    public static void plotHistogram(List<Long> rollvalues) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        double[] values = new double[rollvalues.size()];
        for (int p = 0; p < rollvalues.size(); p++) {
            values[p] = rollvalues.get(p);
        }
        dataset.addSeries("roll values", values, 1000, 1, 100000000);
        String plotTitle = "Histogram";
        String xaxis = "roll values";
        String yaxis = "frequency";
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        boolean show = false;
        boolean toolTips = false;
        boolean urls = false;
        JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis, dataset, orientation, show, toolTips,
                urls);
        int width = 640;
        int height = 480;
        try {
            ChartUtils.saveChartAsPNG(new File("./src/main/java/com/rng/histogram.png"), chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void seedpairGen(){
        List<List<String>> clientseeds = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/java/com/rng/seed1.csv"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                clientseeds.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<String>> serverseeds = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/java/com/rng/seed2.csv"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                serverseeds.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // a loop that gets the second item of every list in clientseeds
        List<String> rollvals = new ArrayList<>();
        rollvals.add("clientseed,serverseed,nonce,rollresult");

        // a list that store rollvals
            
        for (int i = 1000; i < 10000; i++) {
            
            // get the second item of every list in clientseeds
            String clientseed = clientseeds.get(i).get(1);
            // a loop that gets the second item of every list in serverseeds
            for (int j = 1000; j < 10000; j++) {
                Integer count10 = 0;
            Integer count20 = 0;
            Integer count30 = 0;
            Integer count40 = 0;
            Integer count50 = 0;
            Integer count60 = 0;
            Integer count70 = 0;
            Integer count80 = 0;
            Integer count90 = 0;
            Integer count100 = 0;
                List<Long> rollvalues = new ArrayList<>();
                // get the second item of every list in serverseeds
                String serverseed = serverseeds.get(j).get(1);
                // a loop that runs RNG 1000 times with different nonce values
                for (int k = 0; k < 1000; k++) {
                    
                    RNG rng = new RNG(clientseed, serverseed, k);
                    long rollval = rng.randomRollValue();
                    // join client seed, server seed, nonce, and roll value by comma
                    // result = String.join(",", clientseed, serverseed, String.valueOf(k),
                    // String.valueOf(rollval));
                    // add result to rollvals
                    // rollvals.add(result);
                    rollvalues.add(rollval);
                    // count the number of times each roll value appears
                    //use switch case
                    if (rollval >= 1 && rollval <= 10000000) {
                        count10++;
                    } else if (rollval >= 10000001 && rollval <= 20000000) {
                        count20++;
                    } else if (rollval >= 20000001 && rollval <= 30000000) {
                        count30++;
                    } else if (rollval >= 30000001 && rollval <= 40000000) {
                        count40++;
                    } else if (rollval >= 40000001 && rollval <= 50000000) {
                        count50++;
                    } else if (rollval >= 50000001 && rollval <= 60000000) {
                        count60++;
                    } else if (rollval >= 60000001 && rollval <= 70000000) {
                        count70++;
                    } else if (rollval >= 70000001 && rollval <= 80000000) {
                        count80++;
                    } else if (rollval >= 80000001 && rollval <= 90000000) {
                        count90++;
                    } else if (rollval >= 90000001 && rollval <= 100000000) {
                        count100++;
                    }
                }
                if (count10 < 85&&count20 < 90) {
                    System.out.println("client seed: " + clientseed);
                    System.out.println("server seed: " + serverseed);
                    System.out.println("count: " + count10);
                    System.out.println("count: " + count20);
                    // join client seed, server seed, nonce, and roll value by comma
                    String result = "";
                    result = String.join(",", String.valueOf(i),String.valueOf(j), String.valueOf(count10), String.valueOf(count20));
                    // add result to rollvals
                    rollvals.add(result);
                }
                // plot a histogram of roll values
                

                // if count is 1000, print client seed, server seed, and nonce
                /*
                 * if (count > 1000) {
                 * System.out.println("client seed: " + clientseed);
                 * System.out.println("server seed: " + serverseed);
                 * System.out.println("count: " + count);
                 * }
                 */
            }
            
        }
            
            
        // save rollvals to csv
        
         Path path = Paths.get("./src/main/java/com/rng/rollvals2.csv");
         try {
          
          Files.write(path, rollvals);
          } catch (IOException e) {
          e.printStackTrace();
          }
    }
}
