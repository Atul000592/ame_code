package nic.ame.app.test.controller;

import java.util.ArrayList;
import java.util.List;

public class StringChunker {
    public static void main(String[] args) {
        String inputString = "Your long string goes here, and it can be of any length. This is just an example to demonstrate how to split a string into smaller parts.";

        // Get the list of string chunks
        List<String> chunks = divideStringIntoChunks(inputString, 30);
        System.out.println(chunks.size());
        // Print the chunks
       int count=0;
        for (String chunk : chunks) {
            System.out.println(chunk);
            count=count+1;
            if(count>2)
            	break;
        }
    }

    public static List<String> divideStringIntoChunks(String input, int chunkSize) {
        List<String> chunkList = new ArrayList<>();
        int length = input.length();
        
        for (int i = 0; i < length; i += chunkSize) {
            // Calculate the end index for the current chunk
            int end = Math.min(i + chunkSize, length);
            String chunk = input.substring(i, end);
            chunkList.add(chunk);
        }
        
        return chunkList;
    }
}
