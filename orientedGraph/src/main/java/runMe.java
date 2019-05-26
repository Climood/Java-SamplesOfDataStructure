public class runMe {
    public static void main(String...args){
        orientedGraphWithTopologycSort graph = new orientedGraphWithTopologycSort();
        graph.addVertex("A Алгебра",777); //0
        graph.addVertex("D Высшая Алгебра",777); //1
        graph.addVertex("G Дипломная работа",777); //2
        graph.addVertex("H Ученая степень",777); //3
        graph.addVertex("B Геометрия",777); //4
        graph.addVertex("E Аналитическая Геометрия",777); //5
        graph.addVertex("C Литература",777);//6
        graph.addVertex("F Сравнительное литературоведение",777);//7
        graph.addEdge(0,1);
        graph.addEdge(0,5);
        graph.addEdge(1,2);
        graph.addEdge(5,2);
        graph.addEdge(4,5);
        graph.addEdge(2,3);
        graph.addEdge(6,7);
        graph.addEdge(7,3);
        System.out.println(graph.topologycSort());
    }
}
