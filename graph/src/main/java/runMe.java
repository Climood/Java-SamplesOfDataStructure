public class runMe {
    public static void main(String...args){
        Graph graph= new Graph();
        //graph.showSmezhMatrix();
        graph.addVertex('A',13);
        graph.addVertex('B',13);
        graph.addVertex('F',13);
        graph.addVertex('H',13);
        graph.addVertex('C',13);
        graph.addVertex('D',13);
        graph.addVertex('G',13);
        graph.addVertex('I',13);
        graph.addVertex('E',13);
        graph.addEdge(0,1);
        graph.addEdge(1,2);
        graph.addEdge(2,3);
        graph.addEdge(0,4);
        graph.addEdge(0,5);
        graph.addEdge(5,6);
        graph.addEdge(6,7);
        graph.addEdge(0,8);
        graph.DFS(0);
        graph.BFS(0);
        graph.getMSTTree(0);
    }
}
