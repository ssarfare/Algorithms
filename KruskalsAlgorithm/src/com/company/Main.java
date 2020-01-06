package com.company;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try{
            //Input File Path
            File inputFile = new File("E:\\Sem I\\Algo and Data Structures\\Programs\\KruskalsAlgorithm\\src\\com\\company\\Input3.txt");
            Scanner scanner = new Scanner(inputFile);

            String[] firstLine = scanner.nextLine().split("\\s");
            int numberOfVertex =Integer.parseInt(firstLine[0]);
            int numberOfEdges=Integer.parseInt(firstLine[1]);
            String typeOfGraph=firstLine[2];
            boolean isDirected=typeOfGraph.equals("D")?true:false;

            String source;
            Graph myGraph=new Graph(isDirected);

            while(scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split("\\s");
                if(line.length == 3)
                    myGraph.addEdge(line[0], line[1], Integer.parseInt(line[2]));
                else source=line[0];
            }

            KruskalAlgorithm algo= new KruskalAlgorithm();

            long startTime = System.currentTimeMillis();
            List<Edge> minimumTree =algo.minimumSpanningTree(myGraph);
            long endTime = System.currentTimeMillis();
            int cost=0;
            for(Edge e:minimumTree){
                System.out.println( e.source + " to " + e.destination + " -> " + e.weight);
                cost=cost+e.weight;
            }
            System.out.println("Running time for Kruskal's Algorithm to find the minimum spanning tree for input graph is : "+ (endTime - startTime) + " ms");

            System.out.println("TotalCost of minimum spanning trees is : "+cost);

        }catch(Exception ex){
            System.out.println(ex);
        }
    }


    public static class Graph {
        private HashMap<String, LinkedList<Edge>> graph = new HashMap<>();
        private Set<Edge> listOfEdges = new HashSet<>();

        boolean isDirected = false;

        public Graph(boolean isDirected) {
            this.isDirected = isDirected;
        }

        // This function adds a new vertex to the graph
        public void addVertex(String vertex) {
            graph.put(vertex, new LinkedList());
        }

        public void addEdge(String source, String destination, int weight) {
            Edge edge = new Edge(source, destination, weight);

            if (graph.containsKey(source)) {
                graph.get(source).addLast(edge);
            } else {
                LinkedList<Edge> edgeList = new LinkedList<>();
                edgeList.addLast(edge);
                graph.put(source, edgeList);
            }

            if (graph.containsKey(destination)) {
                if (!this.isDirected) {
                    graph.get(destination).addLast(edge);
                }
            } else {
                LinkedList<Edge> edgeList = new LinkedList<>();
                if (!this.isDirected)
                    edgeList.addLast(edge);
                graph.put(destination, edgeList);
            }

            if (!listOfEdges.contains(edge)) {
                listOfEdges.add(edge);
            }
        }

        public Set<String> getAllVertices() {
            return graph.keySet();
        }
        public Set<Edge> getListOfEdges(){
            return listOfEdges;
        }

    }


    public static class Edge {
        String source;
        String destination;
        int weight;

        public Edge(String s, String d, int weight) {
            this.source = s;
            this.destination = d;
            this.weight = weight;
        }

        public String getSource() {
            return source;
        }

        public String getDestination() {
            return destination;
        }
        public int getWeight() {
            return weight;
        }


    }


    public static class KruskalAlgorithm {

        public List<Edge> minimumSpanningTree(Graph g) {
            List<Edge> minimumSpanningTree = new ArrayList<>();
            List<Edge> listOfEdges = new ArrayList(g.getListOfEdges());
            DisjointSet set = new DisjointSet();

            Collections.sort(listOfEdges, new Comparator<Edge>() {
                @Override
                public int compare(Edge o1, Edge o2) {
                    return o1.weight-o2.weight;
                }
            });

            for (String vertex : g.getAllVertices()) {
                set.createSet(vertex);
            }

            for (Edge edge : listOfEdges) {
                String parent1 = set.findParent(edge.source);
                String parent2 = set.findParent(edge.destination);

                if (parent1 == parent2) {
                    continue;
                } else {
                    minimumSpanningTree.add(edge);
                    set.union(parent1, parent2);
                }
            }
            return minimumSpanningTree;
        }

        public class DisjointSet {
            private Map<String, NodeVertex> setMap = new HashMap<>();

            class NodeVertex {
                int rank;
                String data;
                NodeVertex parent;
            }

            public void createSet(String data) {
                NodeVertex node = new NodeVertex();
                node.data = data;
                node.rank = 0;
                node.parent = node;
                setMap.put(data, node);
            }

            public void union(String vertex1, String vertex2) {
                NodeVertex parent1 = findSet(setMap.get(vertex1));
                NodeVertex parent2 = findSet(setMap.get(vertex2));
                if(parent1 !=parent2){
                    if (parent1.rank >= parent2.rank) {
                        parent1.rank = (parent1.rank == parent2.rank) ? parent1.rank + 1 : parent1.rank;
                        parent2.parent = parent1;
                    } else {
                        parent1.parent = parent2;
                    }
                }
            }

            public String findParent(String vertex) {
                return findSet(setMap.get(vertex)).data;
            }

            private NodeVertex findSet(NodeVertex node) {
                NodeVertex parent = node.parent;
                if (parent == node) {
                    return node;
                }
                node.parent = findSet(parent);
                return node.parent;
            }
        }
        }
}
