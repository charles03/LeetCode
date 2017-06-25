package com.leetcode.design;

import java.util.*;

/**
 * Created by charles on 6/5/17.
 * Design an in-memory file system to simulate the following functions:

 ls: Given a path in string format. If it is a file path, return a list that only contains this file's name. If it is a directory path, return the list of file and directory names in this directory. Your output (file and directory names together) should in lexicographic order.

 mkdir: Given a directory path that does not exist, you should make a new directory according to the path. If the middle directories in the path don't exist either, you should create them as well. This function has void return type.

 addContentToFile: Given a file path and file content in string format. If the file doesn't exist, you need to create that file containing given content. If the file already exists, you need to append given content to original content. This function has void return type.

 readContentFromFile: Given a file path, return its content in string format.

 Example:
 Input:
 ["FileSystem","ls","mkdir","addContentToFile","ls","readContentFromFile"]
 [[],["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"],["/a/b/c/d"]]
 Output:
 [null,[],null,null,["a"],"hello"]
 Note:
 You can assume all file or directory paths are absolute paths which begin with / and do not end with / except that the path is just "/".
 You can assume that all operations will be passed valid parameters and users will not attempt to retrieve file content or list a directory or file that does not exist.
 You can assume that all directory names and file names only contain lower-case letters, and same names won't exist in the same directory.
 */
public class DesignInMemoryFileSystem_588 {

    /**
     * A problem with the current design could occur if we want to list only the directories(and not the files), on any given path. In this case,
     * we need to traverse over the whole contents of the current directory, check for each entry, whether it is a file or a directory, and then extract the required data
     *
     * besides: If the number of directories is very large, we waste redundant space for isfile and content, which wasn't needed in the first design
     */
    class File {
        boolean isFile = false;
        Map<String, File> fileMap = new HashMap<>();
        String content = "";
    }
    private File root;
    public DesignInMemoryFileSystem_588() {
        root = new File();
    }

    /**
     * Time complexity
     * ls command is O(m+n+klog(k)
     * m refers to the length of the input string. We need to scan the input string once to split it and determine the various levels.
     * n refers to the depth of the last directory level in the given input for ls, This factor is taken because we need to enter nn levels of the tree structure to reach the last level.
     * k refers to the number of entries(files+subdirectories) in the last level directory(in the current input). We need to sort these names giving a factor of klog(k)klog(k).
     */
    public List<String> ls(String path) {
        File tmp = root;
        List<String> res = new ArrayList<>();
        if (!path.equals("/")) {
            String[] dirs = path.split("/");
            for (int i = 1; i < dirs.length; i++) {
                tmp = tmp.fileMap.get(dirs[i]);
            }
            if (tmp.isFile) {
                res.add(dirs[dirs.length - 1]);
                return res;
            }
        }
        List<String> fileList = new ArrayList<>(tmp.fileMap.keySet());
        Collections.sort(fileList);
        return fileList;
    }

    /**
     * mkdir command is O(m+n).
     * m refers to the length of the input string. We need to scan the input string once to split it and determine the various levels
     * n refers to the depth of the last directory level in the mkdir input,  This factor is taken because we need to enter nn levels of the tree structure to reach the last level
     */
    public void mkdir(String path) {
        String[] dirs = path.split("/");
        creatIfNotExist(root, dirs, dirs.length);
    }
    private File creatIfNotExist(File fRoot, String[] dirs, int level) {
        File tmp = fRoot;
        for (int i = 1; i < level; i++) {
            if (!tmp.fileMap.containsKey(dirs[i])) {
                tmp.fileMap.put(dirs[i], new File());
            }
            tmp = tmp.fileMap.get(dirs[i]);
        }
        return tmp;
    }

    /**
     * Time complexity : O(m + n)
     * m refers to the length of the input string. We need to scan the input string once to split it and determine the various levels.
     * n refers to the depth of the file name in the current input. This factor is taken because we need to enter nn levels of the tree structure to reach the level where the files's contents need to be added/read from.
     */
    public void addContentToFile(String filePath, String content) {
        String[] dirs = filePath.split("/");
        int lastLevel = dirs.length - 1;
        File curr = creatIfNotExist(root, dirs, lastLevel);
        if (!curr.fileMap.containsKey(dirs[lastLevel])) {
            curr.fileMap.put(dirs[lastLevel], new File());
        }
        curr = curr.fileMap.get(dirs[lastLevel]);
        curr.isFile = true;
        curr.content = content;
    }

    /**
     * Time complexity : O(m + n)
     * m refers to the length of the input string. We need to scan the input string once to split it and determine the various levels.
     * n refers to the depth of the file name in the current input. This factor is taken because we need to enter nn levels of the tree structure to reach the level where the files's contents need to be added/read from.
     */
    public String readContentFromFile(String filePath) {
        File tmp = root;
        String[] dirs = filePath.split("/");
        for (int i = 1; i < dirs.length - 1; i++) {
            tmp = tmp.fileMap.get(dirs[i]);
        }
        return tmp.fileMap.get(dirs[dirs.length - 1]).content;
    }
}
