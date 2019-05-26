public class runMe {
    public static void main(String...args){
        weightGraph weightGraph = new weightGraph();
        weightGraph.addVertex("A",0);
        weightGraph.addVertex("B",1);
        weightGraph.addVertex("C",2);
        weightGraph.addVertex("D",3);
        weightGraph.addVertex("E",4);
        weightGraph.addVertex("F",5);
        weightGraph.addEdge(0,1,6);
        weightGraph.addEdge(0,3,4);
        weightGraph.addEdge(1,3,7);
        weightGraph.addEdge(3,2,8);
        weightGraph.addEdge(1,2,10);
        weightGraph.addEdge(1,4,7);
        weightGraph.addEdge(3,4,12);
        weightGraph.addEdge(4,2,5);
        weightGraph.addEdge(4,5,7);
        weightGraph.addEdge(2,5,6);
        System.out.println("Минимальное оставное древо для текущего графа:");
        weightGraph.getMSTTree(0);
    }
}
