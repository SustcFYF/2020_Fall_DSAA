//package Homework4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {
    public boolean isDirected; // the graph is directed or not
    public int[][] a; // the input matrix
    public int[][] matrix; // the adjacency matrix
    public int n; // the number of the vertexes

    public void readGraphFile(String strFile) throws IOException {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(strFile));
            String readline;
            String[] line = new String[1000];
            int i = 0;
            // read the txt file line by line
            while ((readline = br.readLine()) != null) {
                line[i] = readline;
                i++;
            }
            // determine whether the graph is directed or not
            if (line[0].equals("0")) {
                isDirected = false;
            } else if (line[0].equals("1")) {
                isDirected = true;
            }
            // use a string array to store the data
            String[][] arr = new String[i - 1][];
            a = new int[i - 1][];
            for (int j = 0; j < i - 1; j++) {
                arr[j] = line[j + 1].split(" "); // ignore the first row in the line matrix
                a[j] = new int[arr[j].length];
                // transfer the string array into an int array
                for (int k = 0; k < a[j].length; k++) {
                    a[j][k] = Integer.parseInt(arr[j][k]);
                }
            }
            printAdjacencyMatrix(); // get adjacency matrix when access to the input matrix
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }


    // print each vertex in a line with other vertices and their weight in ascending order according to vertices number
    public String printAdjacencyList() {
        StringBuilder strList = new StringBuilder();
        // generate the adjacency list
        String[] list = new String[n];
        for (int i = 0; i < list.length; i++) {
            list[i] = i + ":";
        }
        // use adjacency matrix to generate the adjacency list
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    list[j] += "(" + j + "," + i + ":" + matrix[i][j] + ")";
                }
            }
        }
        // return in format
        for (String s : list) {
            strList.append(s).append("\n");
        }
        return strList.toString();// each line prints the start vertex and other vertices numbers and weights of edges ascending
    }


    // print each weight in matrix with vertices number as row and col in ascending order according to vertices number
    public String printAdjacencyMatrix() {
        StringBuilder strMatrix = new StringBuilder();
        // find the size of the adjacency matrix
        n = 0;
        for (int[] input : a) {
            for (int j = 0; j < 2; j++) {
                if (input[j] > n) {
                    n = input[j];
                }
            }
        }
        n++;
        // generate the adjacency matrix
        matrix = new int[n][n];
        if (isDirected) {
            for (int[] input : a) {
                matrix[input[1]][input[0]] = input[2];
            }
        } else {
            for (int[] input : a) {
                matrix[input[0]][input[1]] = input[2];
                matrix[input[1]][input[0]] = input[2];
            }
        }
        // return the adjacency matrix in format
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                strMatrix.append(matrix[i][j]).append(" ");
            }
            strMatrix.append("\n");
        }
        return strMatrix.toString();// each row and each line print the vertex numbers and weights ascending
    }


    // print the shortest path from startVertex to other vertices, except itself
    // output format: [shortestPathLength]v1,v2,...,vn
    // if no path from v1 to vn, print [0]null
    public String ShortestPath(int startVertex) {
        StringBuilder strPath = new StringBuilder();
        int[] length = new int[n]; // the shortest length
        String[] path = new String[n]; // the shortest path
        boolean[] isVisited = new boolean[n];
        isVisited[startVertex] = true; // the start vertex is visited
        int min;
        int key;
        // start from the first vertex
        for (int i = 0; i < n; i++) {
            path[i] = startVertex + "";
            if (matrix[i][startVertex] != 0) {
                length[i] = matrix[i][startVertex]; // store temporary shortest length
                path[i] += "," + i; // store temporary shortest path
                isVisited[i] = false;
            }
        }
        // visit the nearest vertex one by one
        for (int i = 0; i < n; i++) {
            if (i != startVertex) {
                min = Integer.MAX_VALUE; // to store the minimum length between two vertexes
                key = startVertex;
                for (int j = 0; j < n; j++) {
                    if ((!isVisited[j]) && (length[j] != 0) && (min > length[j])) {
                        min = length[j]; // update the minimum length
                        key = j; // store the vertex with smallest length
                    }
                }
                isVisited[key] = true; // set this vertex visited
                for (int j = 0; j < n; j++) {
                    if (!isVisited[j]) {
                        // find a shorter path than the temporary shortest path
                        // use the new key vertex to generate a new shortest path
                        if (matrix[j][key] != 0 && (length[j] > min + matrix[j][key] || length[j] == 0)) {
                            length[j] = min + matrix[j][key]; // update the shortest length
                            path[j] = path[key] + "," + j; // update the shortest path
                        }
                    }
                }
            }
        }
        // output in format
        for (int i = 0; i < n; i++) {
            if (i != startVertex) { // the path from the start vertex to itself is not needed
                if (length[i] == 0) {
                    path[i] = "null"; // output null if it cannot find a path
                }
                strPath.append("[").append(length[i]).append("]").append(path[i]).append("\n");
            }
        }
        return strPath.toString();
    }

    // overload the method ShortestPath
    // input both startVertex and endVertex to find a shortest path
    public String ShortestPath(int startVertex, int endVertex) {
        String strPath = "";
        String[] path = ShortestPath(startVertex).split("\n"); // use known String
        if (startVertex > endVertex) { // find the specific row
            strPath = path[endVertex];
        }
        if (startVertex < endVertex) {
            strPath = path[endVertex - 1];
        }
        return strPath;
    }
}
