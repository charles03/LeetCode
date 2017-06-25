package com.system.design.googleFileSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by charles on 9/18/16.
 *
 * Implement a simple client for GFS (Google File System, a distributed file system), it provides the following methods:

 read(filename). Read the file with given filename from GFS.
 write(filename, content). Write a file with given filename & content to GFS.
 There are two private methods that already implemented in the base class:

 readChunk(filename, chunkIndex). Read a chunk from GFS.
 writeChunk(filename, chunkIndex, chunkData). Write a chunk to GFS.
 To simplify this question, we can assume that the chunk size is chunkSize bytes.
 (In a real world system, it is 64M).
 The GFS Client's job is splitting a file into multiple chunks (if need) and save to the remote GFS server.
 chunkSize will be given in the constructor.
 You need to call these two private methods to implement read & write methods.

 Example
 GFSClient(5)
 read("a.txt")
 >> null
 write("a.txt", "World")
 >> You don't need to return anything, but you need to call writeChunk("a.txt", 0, "World") to write a 5 bytes chunk to GFS.
 read("a.txt")
 >> "World"
 write("b.txt", "111112222233")
 >> You need to save "11111" at chink 0, "22222" at chunk 1, "33" at chunk 2.
 write("b.txt", "aaaaabbbbb")
 read("b.txt")
 >> "aaaaabbbbb"
 */

/**
 * Reference:
 * https://github.com/Charles0429/papers/blob/master/doc/gfs.md
 * http://clouddbs.blogspot.com/2010/11/gfs-google-file-system-in-199-lines-of.html
 * https://github.com/laboshinl/gfs
 * https://github.com/yaojingguo/computer-systems/wiki/GFS
 * https://github.com/laveroni/TinyFS/blob/master/src/master/TFSMaster.java
 */
public class GoogleFileSystemClient extends BaseGoogleFileSystemClient{
    private int chunkSize;
    /** key is file name, value is numbers of chunk */
    private Map<String, Integer> chunkMap;

    public GoogleFileSystemClient(int chunkSize) {
        // initialize your data structure here
        this.chunkSize = chunkSize;
        this.chunkMap = new HashMap<>();
    }
    // @param filename a file name
    // @return content of the file given from GFS
    public String read(String filename) {
        if (!chunkMap.containsKey(filename)) {
            return null;
        }
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < chunkMap.get(filename); i++) {
            // thus i is chunk index
            String subContent = readChunk(filename, i);
            if (subContent != null) {
                content.append(subContent);
            }
        }
        return content.toString();
    }

    // @param filename a file name
    // @param content a string
    // @return void
    public void write(String filename, String content) {
        int len = content.length();
        int chunkNum = (len - 1) / chunkSize + 1;
        chunkMap.put(filename, chunkNum);

        int start = 0, end = 0;
        for (int i = 0; i < chunkNum; i++) {
            start = i * chunkSize;
            if (i == chunkNum - 1) {
                end = len;
            } else {
                end = (i+1) * chunkSize;
            }
            String subContent = content.substring(start, end);
            writeChunk(filename, i, subContent);
        }
    }

}

class BaseGoogleFileSystemClient {
    private Map<String, String> chunk_list;
    public BaseGoogleFileSystemClient() {}

    public String readChunk(String filename, int chunkIndex) {
        // read a chunk from GFS
        return null;
    }

    public void writeChunk(String filename, int chunkIndex, String content) {
        // write a chunk to GFS
        return;
    }

}
