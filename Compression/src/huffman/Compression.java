package huffman;

import java.util.HashMap;
import java.io.*;
import java.math.BigInteger;
import java.util.*; 
import java.nio.ByteBuffer;


/**
 *
 * @author yuya
 */
public class Compression {
    //fields
    //mapping list
    //x = {1111, 1110, 1101, 1011}, y = {1100, 1010, 1001, 1000} (256+50=306)
    private static final String[] MAPPING_LIST_1 = {
        "1111", "1110", "1101", "1011", //x
        "11001111", "11001110", "11001101", "11001011", "10101111", "10101110", "10101101", "10101011",
        "10011111", "10011110", "10011101", "10011011", "10001111", "10001110", "10001101", "10001011", //yx
        "110011001111", "110011001110", "110011001101", "110011001011", "110010101111", "110010101110", "110010101101", "110010101011",
        "110010011111", "110010011110", "110010011101", "110010011011", "110010001111", "110010001110", "110010001101", "110010001011", //y[1]yx
        "101011001111", "101011001110", "101011001101", "101011001011", "101010101111", "101010101110", "101010101101", "101010101011",
        "101010011111", "101010011110", "101010011101", "101010011011", "101010001111", "101010001110", "101010001101", "101010001011", //y[2]yx
        "100111001111", "100111001110", "100111001101", "100111001011", "100110101111", "100110101110", "100110101101", "100110101011",
        "100110011111", "100110011110", "100110011101", "100110011011", "100110001111", "100110001110", "100110001101", "100110001011", //y[3]yx
        "100011001111", "100011001110", "100011001101", "100011001011", "100010101111", "100010101110", "100010101101", "100010101011",
        "100010011111", "100010011110", "100010011101", "100010011011", "100010001111", "100010001110", "100010001101", "100010001011", //y[4]yx
        "1100110011001111", "1100110011001110", "1100110011001101", "1100110011001011", "1100110010101111", "1100110010101110", "1100110010101101", "1100110010101011",
        "1100110010011111", "1100110010011110", "1100110010011101", "1100110010011011", "1100110010001111", "1100110010001110", "1100110010001101", "1100110010001011",
        "1100101011001111", "1100101011001110", "1100101011001101", "1100101011001011", "1100101010101111", "1100101010101110", "1100101010101101", "1100101010101011",
        "1100101010011111", "1100101010011110", "1100101010011101", "1100101010011011", "1100101010001111", "1100101010001110", "1100101010001101", "1100101010001011",
        "1100100111001111", "1100100111001110", "1100100111001101", "1100100111001011", "1100100110101111", "1100100110101110", "1100100110101101", "1100100110101011",
        "1100100110011111", "1100100110011110", "1100100110011101", "1100100110011011", "1100100110001111", "1100100110001110", "1100100110001101", "1100100110001011",
        "1100100011001111", "1100100011001110", "1100100011001101", "1100100011001011", "1100100010101111", "1100100010101110", "1100100010101101", "1100100010101011",
        "1100100010011111", "1100100010011110", "1100100010011101", "1100100010011011", "1100100010001111", "1100100010001110", "1100100010001101", "1100100010001011", //y[1]yyx
        "1010110011001111", "1010110011001110", "1010110011001101", "1010110011001011", "1010110010101111", "1010110010101110", "1010110010101101", "1010110010101011",
        "1010110010011111", "1010110010011110", "1010110010011101", "1010110010011011", "1010110010001111", "1010110010001110", "1010110010001101", "1010110010001011",
        "1010101011001111", "1010101011001110", "1010101011001101", "1010101011001011", "1010101010101111", "1010101010101110", "1010101010101101", "1010101010101011",
        "1010101010011111", "1010101010011110", "1010101010011101", "1010101010011011", "1010101010001111", "1010101010001110", "1010101010001101", "1010101010001011",
        "1010100111001111", "1010100111001110", "1010100111001101", "1010100111001011", "1010100110101111", "1010100110101110", "1010100110101101", "1010100110101011",
        "1010100110011111", "1010100110011110", "1010100110011101", "1010100110011011", "1010100110001111", "1010100110001110", "1010100110001101", "1010100110001011",
        "1010100011001111", "1010100011001110", "1010100011001101", "1010100011001011", "1010100010101111", "1010100010101110", "1010100010101101", "1010100010101011",
        "1010100010011111", "1010100010011110", "1010100010011101", "1010100010011011", "1010100010001111", "1010100010001110", "1010100010001101", "1010100010001011", //y[2]yyx
        "1001110011001111", "1001110011001110", "1001110011001101", "1001110011001011", "1001110010101111", "1001110010101110", "1001110010101101", "1001110010101011",
        "1001110010011111", "1001110010011110", "1001110010011101", "1001110010011011", "1001110010001111", "1001110010001110", "1001110010001101", "1001110010001011",
        "1001101011001111", "1001101011001110", "1001101011001101", "1001101011001011", "1001101010101111", "1001101010101110", "1001101010101101", "1001101010101011",
        "1001101010011111", "1001101010011110", "1001101010011101", "1001101010011011", "1001101010001111", "1001101010001110", "1001101010001101", "1001101010001011",
        "1001100111001111", "1001100111001110", "1001100111001101", "1001100111001011", "1001100110101111", "1001100110101110", "1001100110101101", "1001100110101011",
        "1001100110011111", "1001100110011110", "1001100110011101", "1001100110011011", "1001100110001111", "1001100110001110", "1001100110001101", "1001100110001011",
        "1001100011001111", "1001100011001110", "1001100011001101", "1001100011001011", "1001100010101111", "1001100010101110", "1001100010101101", "1001100010101011",
        "1001100010011111", "1001100010011110", "1001100010011101", "1001100010011011", "1001100010001111", "1001100010001110", "1001100010001101", "1001100010001011", //y[3]yyx
        "1000110011001111", "1000110011001110", "1000110011001101", "1000110011001011", "1000110010101111", "1000110010101110", "1000110010101101", "1000110010101011",
        "1000110010011111", "1000110010011110", "1000110010011101", "1000110010011011", "1000110010001111", "1000110010001110", "1000110010001101", "1000110010001011",
        "1000101011001111", "1000101011001110", "1000101011001101", "1000101011001011", "1000101010101111", "1000101010101110", "1000101010101101", "1000101010101011",
        "1000101010011111", "1000101010011110", "1000101010011101", "1000101010011011", "1000101010001111", "1000101010001110", "1000101010001101", "1000101010001011",
        "1000100111001111", "1000100111001110", "1000100111001101", "1000100111001011", "1000100110101111", "1000100110101110", "1000100110101101", "1000100110101011",
        "1000100110011111", "1000100110011110", "1000100110011101", "1000100110011011", "1000100110001111", "1000100110001110", "1000100110001101", "1000100110001011",
        "1000100011001111", "1000100011001110", "1000100011001101", "1000100011001011", "1000100010101111", "1000100010101110", "1000100010101101", "1000100010101011",
        "1000100010011111", "1000100010011110", "1000100010011101", "1000100010011011", "1000100010001111", "1000100010001110", "1000100010001101", "1000100010001011", //y[4]yyx
    };
            
    //x = {1111, 1110}, y = {1101, 1011, 1100}
    private static final String[] MAPPING_LIST_2 = {
        "1111", "1110", //x
        "11011111", "11011110", "10111111", "10111110", "11001111", "11001110", //yx
        "110111011111", "110111011110", "110110111111", "110110111110", "110111001111", "110111001110",
        "101111011111", "101111011110", "101110111111", "101110111110", "101111001111", "101111001110",
        "110011011111", "110011011110", "110010111111", "110010111110", "110011001111", "110011001110", //yyx
        "1101110111011111", "1101110111011110", "1101110110111111", "1101110110111110", "1101110111001111", "1101110111001110",
        "1101101111011111", "1101101111011110", "1101101110111111", "1101101110111110", "1101101111001111", "1101101111001110",
        "1101110011011111", "1101110011011110", "1101110010111111", "1101110010111110", "1101110011001111", "1101110011001110", //y[1]yyx
        "1011110111011111", "1011110111011110", "1011110110111111", "1011110110111110", "1011110111001111", "1011110111001110",
        "1011101111011111", "1011101111011110", "1011101110111111", "1011101110111110", "1011101111001111", "1011101111001110",
        "1011110011011111", "1011110011011110", "1011110010111111", "1011110010111110", "1011110011001111", "1011110011001110", //y[2]yyx
        "1100110111011111", "1100110111011110", "1100110110111111", "1100110110111110", "1100110111001111", "1100110111001110",
        "1100101111011111", "1100101111011110", "1100101110111111", "1100101110111110", "1100101111001111", "1100101111001110",
        "1100110011011111", "1100110011011110", "1100110010111111", "1100110010111110", "1100110011001111", "1100110011001110", //y[3]yyx
    };
    
    //x = {1111, 1110, 1101, 1011, 0111, 1100}, y = {1010, 0110, 0101, 0011, 1001}
    private static final String[] MAPPING_LIST_3 = {
        "1111", "1110", "1101", "1011", "0111", "1100", //x
        "10101111", "10101110", "10101101", "10101011", "10100111", "10101100",
        "01101111", "01101110", "01101101", "01101011", "01100111", "01101100",
        "01011111", "01011110", "01011101", "01011011", "01010111", "01011100",
        "00111111", "00111110", "00111101", "00111011", "00110111", "00111100",
        "10011111", "10011110", "10011101", "10011011", "10010111", "10011100", //yx
        "101010101111", "101010101110", "101010101101", "101010101011", "101010100111", "101010101100",
        "101001101111", "101001101110", "101001101101", "101001101011", "101001100111", "101001101100",
        "101001011111", "101001011110", "101001011101", "101001011011", "101001010111", "101001011100",
        "101000111111", "101000111110", "101000111101", "101000111011", "101000110111", "101000111100",
        "101010011111", "101010011110", "101010011101", "101010011011", "101010010111", "101010011100", //y[1]yx
        "011010101111", "011010101110", "011010101101", "011010101011", "011010100111", "011010101100",
        "011001101111", "011001101110", "011001101101", "011001101011", "011001100111", "011001101100",
        "011001011111", "011001011110", "011001011101", "011001011011", "011001010111", "011001011100",
        "011000111111", "011000111110", "011000111101", "011000111011", "011000110111", "011000111100",
        "011010011111", "011010011110", "011010011101", "011010011011", "011010010111", "011010011100", //y[2]yx
        "010110101111", "010110101110", "010110101101", "010110101011", "010110100111", "010110101100",
        "010101101111", "010101101110", "010101101101", "010101101011", "010101100111", "010101101100",
        "010101011111", "010101011110", "010101011101", "010101011011", "010101010111", "010101011100",
        "010100111111", "010100111110", "010100111101", "010100111011", "010100110111", "010100111100",
        "010110011111", "010110011110", "010110011101", "010110011011", "010110010111", "010110011100", //y[3]yx
        "001110101111", "001110101110", "001110101101", "001110101011", "001110100111", "001110101100",
        "001101101111", "001101101110", "001101101101", "001101101011", "001101100111", "001101101100",
        "001101011111", "001101011110", "001101011101", "001101011011", "001101010111", "001101011100",
        "001100111111", "001100111110", "001100111101", "001100111011", "001100110111", "001100111100",
        "001110011111", "001110011110", "001110011101", "001110011011", "001110010111", "001110011100", //y[4]yx
        "100110101111", "100110101110", "100110101101", "100110101011", "100110100111", "100110101100",
        "100101101111", "100101101110", "100101101101", "100101101011", "100101100111", "100101101100",
        "100101011111", "100101011110", "100101011101", "100101011011", "100101010111", "100101011100",
        "100100111111", "100100111110", "100100111101", "100100111011", "100100110111", "100100111100",
        "100110011111", "100110011110", "100110011101", "100110011011", "100110010111", "100110011100", //y[5]yx
        "1010101010101111", "1010101010101110", "1010101010101101", "1010101010101011", "1010101010100111", "1010101010101100",
        "1010101001101111", "1010101001101110", "1010101001101101", "1010101001101011", "1010101001100111", "1010101001101100",
        "1010101001011111", "1010101001011110", "1010101001011101", "1010101001011011", "1010101001010111", "1010101001011100",
        "1010101000111111", "1010101000111110", "1010101000111101", "1010101000111011", "1010101000110111", "1010101000111100",
        "1010101010011111", "1010101010011110", "1010101010011101", "1010101010011011", "1010101010010111", "1010101010011100", //y[1]yyx
        "1010011010101111", "1010011010101110", "1010011010101101", "1010011010101011", "1010011010100111", "1010011010101100",
        "1010011001101111", "1010011001101110", "1010011001101101", "1010011001101011", "1010011001100111", "1010011001101100",
        "1010011001011111", "1010011001011110", "1010011001011101", "1010011001011011", "1010011001010111", "1010011001011100",
        "1010011000111111", "1010011000111110", "1010011000111101", "1010011000111011", "1010011000110111", "1010011000111100",
        "1010011010011111", "1010011010011110", "1010011010011101", "1010011010011011", "1010011010010111", "1010011010011100", //y[2]yyx
        "1010010110101111", "1010010110101110", "1010010110101101", "1010010110101011", "1010010110100111", "1010010110101100",
        "1010010101101111", "1010010101101110", "1010010101101101", "1010010101101011", "1010010101100111", "1010010101101100",
        "1010010101011111", "1010010101011110", "1010010101011101", "1010010101011011", "1010010101010111", "1010010101011100",
        "1010010100111111", "1010010100111110", "1010010100111101", "1010010100111011", "1010010100110111", "1010010100111100",
        "1010010110011111", "1010010110011110", "1010010110011101", "1010010110011011", "1010010110010111", "1010010110011100", //y[3]yyx
        "1010001110101111", "1010001110101110", "1010001110101101", "1010001110101011", "1010001110100111", "1010001110101100",
        "1010001101101111", "1010001101101110", "1010001101101101", "1010001101101011", "1010001101100111", "1010001101101100",
        "1010001101011111", "1010001101011110", "1010001101011101", "1010001101011011", "1010001101010111", "1010001101011100",
        "1010001100111111", "1010001100111110", "1010001100111101", "1010001100111011", "1010001100110111", "1010001100111100",
        "1010001110011111", "1010001110011110", "1010001110011101", "1010001110011011", "1010001110010111", "1010001110011100", //y[4]yyx
        "1010100110101111", "1010100110101110", "1010100110101101", "1010100110101011", "1010100110100111", "1010100110101100",
        "1010100101101111", "1010100101101110", "1010100101101101", "1010100101101011", "1010100101100111", "1010100101101100",
        "1010100101011111", "1010100101011110", "1010100101011101", "1010100101011011", "1010100101010111", "1010100101011100",
        "1010100100111111", "1010100100111110", "1010100100111101", "1010100100111011", "1010100100110111", "1010100100111100",
        "1010100110011111", "1010100110011110", "1010100110011101", "1010100110011011", "1010100110010111", "1010100110011100", //y[5]yyx
    };
    
    //constructor
    public Compression() {

    }
    
    /*
    * encoder
    */
    public void compress(String fileName) {
        
        //read file and convert it into byte array
        byte[] fileBytes = readFile(fileName);
        
        
        //list of encoding distributions
        LinkedList<LinkedHashMap<Short, String>> enDistList = new LinkedList<>();
        
        
        //execute compression using mapping list 1
        //find sorted frequency distribution
        LinkedHashMap<Short, Integer> freqDist1 = findFreqDist(fileBytes);
        
        //make mapping based on the frequency distribution
        LinkedHashMap<Short, String> enDist1 = new LinkedHashMap<>();
        Iterator hmIterator = freqDist1.entrySet().iterator();
        int mListIndex = 0;
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) hmIterator.next();
            enDist1.put((short) mapElement.getKey(), MAPPING_LIST_3[mListIndex]);
            mListIndex ++;
        }
        
        /*
        //debug
        System.out.println();
        System.out.println("Here");
        System.out.println(fileBytes.length);
        System.out.println("freqdist");
        System.out.println(freqDist1);
        System.out.println("mapping dist");
        System.out.println(enDist1);
        //debug
        */
        
        //add encoding mapping distribution to the linked list
        enDistList.addLast(enDist1);
        
        //substitute byte array into compressed byte array
        //byte[] compressedBytes1 = new byte[(int) fileBytes.length];
        byte[] compressedBytes1 = new byte[2 * fileBytes.length];
        int comFileIndex = 0;
        String bitStr = "";
        
        for (int i = 0; i < fileBytes.length; i += 2) {
            //two bytes case
            ByteBuffer bb = ByteBuffer.allocate(2);
            bb.put(0, fileBytes[i]);
            bb.put(1, fileBytes[i + 1]);
            short shortValue = bb.getShort(0);
            
            if (enDist1.containsKey(shortValue)) {
                bitStr += enDist1.get(shortValue);
                
                //convert string into byte
                while (bitStr.length() >= 8) {
                    byte b = new BigInteger(bitStr.substring(0, 8), 2).byteValue();
                    compressedBytes1[comFileIndex] = b;
                    comFileIndex ++;
                    bitStr = bitStr.substring(8);
                }
                continue;
            }
            
            //one byte case, first byte and second byte
            bitStr += enDist1.get((short) fileBytes[i]);
            bitStr += enDist1.get((short) fileBytes[i + 1]);
            
            //convert string into byte
            while (bitStr.length() >= 8) {
                byte b = new BigInteger(bitStr.substring(0, 8), 2).byteValue();
                compressedBytes1[comFileIndex] = b;
                comFileIndex ++;
                bitStr = bitStr.substring(8);
            }
        }
        System.out.println();
        System.out.println("Here");
        System.out.println();
        //deal with the case when the last is 4 bytes
        if (bitStr.length() == 4) {
            for (int i = 0; i < 4; i ++) {
                bitStr += "0";
            }
            byte b = new BigInteger(bitStr.substring(0, 8), 2).byteValue();
            compressedBytes1[comFileIndex] = b;
            comFileIndex ++;
        }
            
        //execute compression using mapping list 2
        //while size is still big, keep compressing
        String bitStr2 = "";
        byte[] compressedBytes2 = new byte[2 * comFileIndex];
        while (comFileIndex >= 100) {
            //find sorted frequency distribution
            LinkedHashMap<Short, Integer> freqDist = findFreqDist(compressedBytes1);
        
            //make mapping based on the frequency distribution
            LinkedHashMap<Short, String> enDist2 = new LinkedHashMap<>();
            Iterator hmIterator2 = freqDist.entrySet().iterator();
            int mListIndex2 = 0;
            System.out.println("AAAAAAAAA");
            while (hmIterator2.hasNext() == true) {
                Map.Entry mapElement = (Map.Entry) hmIterator2.next();
                enDist2.put((short) mapElement.getKey(), MAPPING_LIST_1[mListIndex2]);
                mListIndex2 ++;
            }
            
            //add encoding list to the linked list
            enDistList.addLast(enDist2);
            
            //substitute byte array into compressed byte array based on mapping list 2
            //reset size of compressed file
            comFileIndex = 0;
            for (int i = 0; i < compressedBytes1.length; i += 2) {
                //two bytes case
                ByteBuffer bb = ByteBuffer.allocate(2);
                bb.put(0, compressedBytes1[i]);
                bb.put(1, compressedBytes1[i + 1]);
                short shortValue = bb.getShort(0);
                
                if (enDist2.containsKey(shortValue)) {
                    bitStr2 += enDist2.get(shortValue);
                    //convert string into byte
                    while (bitStr2.length() >= 8) {
                        byte b = new BigInteger(bitStr2.substring(0, 8), 2).byteValue();
                        compressedBytes2[comFileIndex] = b;
                        comFileIndex ++;
                        bitStr2 = bitStr2.substring(8);
                    }
                    continue;
                }
            
                //one byte case, first byte and second byte
                bitStr2 += enDist2.get((short) compressedBytes1[i]);
                bitStr2 += enDist2.get((short) compressedBytes1[i + 1]);
            
                //convert string into byte
                while (bitStr2.length() >= 8) {
                    byte b = new BigInteger(bitStr2.substring(0, 8), 2).byteValue();
                    compressedBytes2[comFileIndex] = b;
                    comFileIndex ++;
                    bitStr2 = bitStr2.substring(8);
                }              
                
            }
            
            //deal with the case when the last is 4 bytes
            if (bitStr2.length() == 4) {
                for (int i = 0; i < 4; i ++) {
                    bitStr2 += "0";
                }
                byte b = new BigInteger(bitStr2.substring(0, 8), 2).byteValue();
                compressedBytes1[comFileIndex] = b;
                comFileIndex ++;
            }
            
            //reset binary string
            bitStr2 = "";
            //reset others
            compressedBytes1 = compressedBytes2;
            compressedBytes2 = new byte[2 * comFileIndex];
            
            //debug
            System.out.println();
            System.out.println("seond compression");
            System.out.println("size: " + comFileIndex);
            //debug
        }
        
        /*
        //write file based on the byte array and encoding distribution
        //build information of encoding distributions list, iterating over the list
        String decodeInfoStr = "";
        for (int j = 0; j < enDistList.size(); j ++) {
            HashMap<Short, String> enDist = enDistList.get(j);
            short distSize = (short) enDist.size();
            decodeInfoStr += String.format("%016d", Integer.parseInt(Integer.toBinaryString(distSize)));
            //get information of 8 or 16
            String eightOrSixteen = "";
            for (Map.Entry mapElement : enDist.entrySet()) {
                String value = (String) mapElement.getValue();
                if (value.length() == 8) {
                    eightOrSixteen += "0";
                } else if (value.length() == 16) {
                    eightOrSixteen += "1";
                } else {
                    //debug
                    System.out.println();
                }
            }
            //add zeros to make it byte unit
            for (int k = 0; k < ((distSize + (comFileIndex % 8)) / 8) - distSize; k ++) {
                eightOrSixteen += 0;
            }
            decodeInfoStr += eightOrSixteen;
        }
        
        //create byte array
        byte[] decodeInfoBytes = new byte[decodeInfoStr.length()];
        for (int j = 0; j < decodeInfoStr.length(); j += 8) {
            byte b = Byte.parseByte(decodeInfoStr.substring(j, j + 8), 2);
            decodeInfoStr = decodeInfoStr.substring(8);
        }
        */
    }

    
    /*
    *　read file and return byte array
    */
    private byte[] readFile(String inputFile) {
        //read file with input stream
        try  (InputStream inputStream = new FileInputStream(inputFile);) {
            long fileSize = new File(inputFile).length();
            byte[] allBytes = new byte[(int) fileSize];
            inputStream.read(allBytes);
            //debug
            System.out.println(fileSize);
            //debug
            return allBytes;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /*
    *　find frequency distribution
    */
    private LinkedHashMap<Short, Integer> findFreqDist(byte[] byteArray) {
        
        //build short array
        short[] shortArray = new short[byteArray.length / 2];
        for (int i = 0; i < byteArray.length; i += 2) {
            ByteBuffer bb = ByteBuffer.allocate(2);
            bb.put(0, byteArray[i]);
            bb.put(1, byteArray[i + 1]);
            short shortValue = bb.getShort(0);
            shortArray[i / 2] = shortValue;
        }


        //frequency distribution
        LinkedHashMap<Short, Integer> freqDist = new LinkedHashMap<>();
        Integer count;
        
        //find frequency distribution based on two bytes
        for (int i = 0; i < shortArray.length; i ++) {
            if (freqDist.containsKey(shortArray[i]) == false) {
                count = 1;
            } else {
                count = freqDist.get(shortArray[i]) + 1;
            }
            freqDist.put(shortArray[i], count);
        }
        
        //sort frequency distribution
        freqDist = sortByValue(freqDist);
        
        //limit by top 50
        limitDist(freqDist, 50);
        
        //find frequency distribution based on one byte
        for (int i = 0; i < byteArray.length; i += 2) {
            //check two bytes
            ByteBuffer bb = ByteBuffer.allocate(2);
            bb.put(0, byteArray[i]);
            bb.put(1, byteArray[i + 1]);
            short shortValue = bb.getShort(0);
            if (freqDist.containsKey(shortValue)) {
                continue;
            }

            //first byte
            if (freqDist.containsKey((short) byteArray[i]) == false) {
                count = 1;
            } else {
                count = freqDist.get((short) byteArray[i]) + 1;
            }
            freqDist.put((short) byteArray[i], count);
            
            //second byte
            if (freqDist.containsKey((short) byteArray[i + 1]) == false) {
                count = 1;
            } else {
                count = freqDist.get((short) byteArray[i + 1]) + 1;
            }
            freqDist.put((short) byteArray[i + 1], count);
        }
        
        //sort distribution map
        freqDist = sortByValue(freqDist);
        
        //return distribution
        return freqDist;
    }
    
    /*
    * sort hashmap by values in descending order
    */ 
    private LinkedHashMap<Short, Integer> sortByValue(LinkedHashMap<Short, Integer> hm) { 
        //create a list from elements of hm
        List<Map.Entry<Short, Integer>> list = new LinkedList<>(hm.entrySet());
        
        //sort the list
        Collections.sort(list, (Map.Entry<Short, Integer> o1, Map.Entry<Short, Integer> o2) -> (o1.getValue()).compareTo(o2.getValue()));
        
        //put data from sorted list to hash map
        LinkedHashMap<Short, Integer> temp = new LinkedHashMap<>();
        for (int i = list.size(); i-- > 0; ) {
            temp.put(list.get(i).getKey(), list.get(i).getValue());
        }
        
        return temp;
    }
    
    /*
    * limit the number of frequency distribution
    */
    private LinkedHashMap<Short, Integer> limitDist(LinkedHashMap<Short, Integer> hm, int limit) {
        // Create a list from elements of HashMap 
        List<Map.Entry<Short, Integer>> list = new LinkedList<>(hm.entrySet());
        
        //delete after limit
        int i = 0;
        for (Map.Entry<Short, Integer> element : list) { 
            if (i >= limit) {
                hm.remove(element.getKey());
            }
            i ++;
        } 
        return hm; 
    }
    
    //decoder
    public void decompress(String fileName) {
        //not yet
    }
}