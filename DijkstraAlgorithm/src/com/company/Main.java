package com.company;

import java.io.File;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here

        try{
            //Input File Path
            File inputFile = new File("E:\\Sem I\\Algo and Data Structures\\Project2_AlgoDS\\DijkstraAlgorithm\\src\\com\\company\\Input4.txt");
            Scanner scanner = new Scanner(inputFile);

            String[] firstLine = scanner.nextLine().split("\\s");
            int numberOfVertex =Integer.parseInt(firstLine[0]);
            int numberOfEdges=Integer.parseInt(firstLine[1]);
            String typeOfGraph=firstLine[2];
            boolean isDirected=typeOfGraph.equals("D")?true:false;

            String source="";
            Graph myGraph=new Graph(isDirected);

            while(scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split("\\s");
                if(line.length == 3)
                    myGraph.addEdge(line[0], line[1], Integer.parseInt(line[2]));
                else source=line[0];
            }

            DijkstraAlgorithm algo= new DijkstraAlgorithm();

            if(source.equals("")){
                source=(String) myGraph.graph.keySet().toArray()[0];
            }


            long startTime = System.currentTimeMillis();
            Map<String, Integer> shortestPath =algo.shortestPath(myGraph,source);
            long endTime = System.currentTimeMillis();
            System.out.println("Running time for Dijkstra's Algorithm to find the shortest distance from the source node is "+(endTime - startTime) + " ms");
            for(Map.Entry entry:shortestPath.entrySet()){
                System.out.println("Shortest distance from Source "+source+" to " +entry.getKey()+" is " +entry.getValue() );
            }

        }catch(Exception ex){
            System.out.println(ex);
        }











    }
    public static class DijkstraAlgorithm {
        public Map<String, Integer> shortestPath(Graph graph, String source) {
            VectorBasedHeapForDijkstraAlgorithm heap = new VectorBasedHeapForDijkstraAlgorithm();
            Map<String, Integer> distanceVerticesMap = new HashMap<>();
            Map<String, String> ancestors = new HashMap<>();

            for (String vertex : graph.getAllVertices()) {
                heap.add(Integer.MAX_VALUE, vertex);
            }

            heap.updateKey(source, 0);

            distanceVerticesMap.put(source, 0);

            ancestors.put(source, null);

            while (!heap.isEmpty()) {
                VectorBasedHeapForDijkstraAlgorithm.Node minNode = heap.extractMin();
                String currentVertex = minNode.item;

                distanceVerticesMap.put(currentVertex, minNode.key);

                for (Edge edge : graph.getAllEdgesOfAVertex(currentVertex)) {
                    String adjacentVertex = getVertexForEdge(currentVertex, edge);

                    if (!heap.contains(adjacentVertex)) {
                        continue;
                    }

                    int newDistance = distanceVerticesMap.get(currentVertex) + edge.getWeight();

                    if (heap.getWeight(adjacentVertex) > newDistance) {
                        heap.updateKey(adjacentVertex, newDistance);
                        ancestors.put(adjacentVertex, currentVertex);
                    }

                }
            }

            return distanceVerticesMap;
        }

        private String getVertexForEdge(String v, Edge e) {
            return (e.getSource().equals(v) ? e.getDestination() : e.getSource());
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
        public LinkedList<Edge> getAllEdgesOfAVertex(String node) {
            return graph.get(node);
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

    public static class VectorBasedHeapForDijkstraAlgorithm{
        List<Node> nodeList;
        HashMap<String, Integer> itemPosition;

        public VectorBasedHeapForDijkstraAlgorithm() {
            nodeList = new ArrayList<>();
            itemPosition = new HashMap<>();

            Node temp = new Node();
            nodeList.add(temp);
        }

        public boolean contains(String item) {
            return itemPosition.containsKey(item);
        }

        public Integer getWeight(String key) {
            Integer itemPosition = this.itemPosition.get(key);
            if (itemPosition == null) {
                return null;
            } else {
                return nodeList.get(itemPosition).key;
            }
        }

        public void add(int key, String item) {
            Node node = new Node(key, item);
            itemPosition.put(item, nodeList.size());
            nodeList.add(node);
            upHeap(nodeList.size() - 1);
        }


        public Node extractMin() {
            Node minNode = new Node();
            minNode.key = nodeList.get(1).key;
            minNode.item = nodeList.get(1).item;
            int lastNodeKey = nodeList.get(nodeList.size() - 1).key;
            String lastNodeItem = nodeList.get(nodeList.size() - 1).item;

            nodeList.get(1).item = lastNodeItem;
            nodeList.get(1).key = lastNodeKey;
            nodeList.remove(nodeList.size() - 1);

            itemPosition.remove(minNode.item);
            if (!isEmpty())
                itemPosition.put(nodeList.get(1).item, 1);

            downHeap(1);

            return minNode;
        }

        public boolean isEmpty() {
            return (nodeList.size() == 1);
        }

        public void updateKey(String item, int key) {
            Integer current = itemPosition.get(item);
            if (current == null) {
                System.out.println("No such element found");
                return;
            }

            int nodeKey = nodeList.get(current).key;
            nodeList.get(current).key = key;
            if (key < nodeKey) {
                upHeap(current);
            } else if (key > nodeKey) {
                downHeap(current);
            }
        }

        private void upHeap(int current) {
            while (current > 1 && (nodeList.get(current / 2).key > nodeList.get(current).key)) {
                swapItems(nodeList.get(current / 2), nodeList.get(current));
                updateItemPosition(nodeList.get(current / 2).item, nodeList.get(current).item, current / 2, current);
                current = current / 2;
            }
        }

        private void downHeap(int n) {
            int parent = n;
            while (parent < nodeList.size()) {
                int child1 = 2 * parent;
                int child2 = 2 * parent + 1;
                if (child2 < nodeList.size()) {
                    if (nodeList.get(parent).key <= nodeList.get(child1).key && nodeList.get(parent).key <= nodeList.get(child2).key) {
                        break;
                    } else {
                        if (nodeList.get(child1).key < nodeList.get(child2).key) {
                            swapItems(nodeList.get(parent), nodeList.get(child1));
                            updateItemPosition( nodeList.get(parent).item, nodeList.get(child1).item, parent, child1);
                            parent = child1;
                        } else {
                            swapItems(nodeList.get(parent), nodeList.get(child2));
                            updateItemPosition(nodeList.get(parent).item,nodeList.get(child2).item, parent, child2);
                            parent = child2;
                        }
                    }
                } else {
                    if (child1 < nodeList.size() && nodeList.get(child1).key < nodeList.get(parent).key) { //check if node has one child
                        swapItems(nodeList.get(parent), nodeList.get(child1));
                        updateItemPosition(nodeList.get(parent).item, nodeList.get(child1).item, parent, child1);
                        parent = child1;
                    }
                    parent++;
                }
            }
        }

        private void swapItems(Node node1, Node node2) {
            int tempKey = node1.key;
            String tempItem = node1.item;
            node1.key = node2.key;
            node1.item = node2.item;
            node2.key = tempKey;
            node2.item = tempItem;
        }

        private void updateItemPosition(String s, String d, int pos1, int pos2) {
            itemPosition.remove(s);
            itemPosition.remove(d);
            itemPosition.put(s, pos1);
            itemPosition.put(d, pos2);
        }


        public class Node {
            public int key;
            public String item;

            public Node() {
            }

            public Node(int key, String item) {
                this.key = key;
                this.item = item;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Node node = (Node) o;
                return key == node.key &&
                        Objects.equals(item, node.item);
            }

            @Override
            public int hashCode() {
                return Objects.hash(key, item);
            }
        }

    }
}

