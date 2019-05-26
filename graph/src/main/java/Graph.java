
public class Graph { //пример с матрицей смежности, вместо списка смежности //Граф НЕнаправленный
    private final int MAX_VERTEXS = 100;
    private int numVertex; //так же служит индексом, т.е. если берем как индекс, учитываем -1
    private int smezhMatrix [][];
    private Vertex []vertexList;

    Graph(){
        vertexList = new Vertex[MAX_VERTEXS];
        smezhMatrix = new int[MAX_VERTEXS][MAX_VERTEXS];
        numVertex = 0;
    }

    //--------------------------------------------------------------------------------------
    void addVertex(char charValue, int intValue){
        vertexList[numVertex] = new Vertex(charValue,intValue);
        vertexList[numVertex].index = numVertex;
        numVertex++;
    }

    //--------------------------------------------------------------------------------------
    void addEdge(int from,int to){
        smezhMatrix[from][to] = 1;
        smezhMatrix[to][from] = 1;
    }

    void showVertex(int index){
        vertexList[index].show();
    }

    //--------------------------------------------------------------------------------------
    void showSmezhMatrix(){
        for(int i=0;i<MAX_VERTEXS;i++){
            for(int j=0;j<MAX_VERTEXS;j++){
                System.out.print(smezhMatrix[i][j]);
            }
            System.out.println();
        }
    }

    //--------------------------------------------------------------------------------------
    void DFS(int startVertexIndex){ //Обход графа в глубину с выбранной вершины
        Stack verStack = new Stack();
        verStack.add(vertexList[startVertexIndex]);
        vertexList[startVertexIndex].wasVisited = true;
        System.out.println("зашли в вершину "+vertexList[startVertexIndex].getCharValue()+" с ключом "+vertexList[startVertexIndex].getIntValue());

        while(verStack.numVertexs != 0){
            int vertexIndex = getNotMeetedVetrexIndex(verStack.peek().index);
            if(vertexIndex == -1){
                verStack.pop();
            }else{
                vertexList[vertexIndex].wasVisited = true;
                System.out.println("зашли в вершину "+vertexList[vertexIndex].getCharValue()+" с ключом "+vertexList[vertexIndex].getIntValue());
                verStack.add(vertexList[vertexIndex]);
            }
        }
        //После обхода надо обнулить флаги посещения для всех вершин
        for(int i=0 ; i<numVertex;i++){
            vertexList[i].wasVisited = false;
        }
    }

    //--------------------------------------------------------------------------------------
    int getNotMeetedVetrexIndex(int startVertex){
        for(int i = 0;i<numVertex;i++) { //Ищем смежную с текущей вершиной,  не посещенную ранее вершину
            if(smezhMatrix[startVertex][i] == 1 && !vertexList[i].wasVisited){ //Если есть смежная с текущей и не посещенная ранее
                return i;
            }
        }
        return -1; //непосещенных смежных со startVertex вершин не осталось
    }

    //--------------------------------------------------------------------------------------
    void BFS(int startVertexIndex){ //Обход графа в ширину //TODO вынести обход в отдельный метод, который работает одинаково но с разными структурами (очередь, стэк) => он будет работать сразу как 2 обхода (избежим дублирование кода)
        /*Queue verQueue = new Queue();
        verQueue.add(vertexList[startVertexIndex]);
        vertexList[startVertexIndex].wasVisited = true;
        System.out.println("зашли в вершину "+vertexList[startVertexIndex].getCharValue()+" с ключом "+vertexList[startVertexIndex].getIntValue());

        while(verQueue.numVertexs != 0){
            int vertexIndex = getNotMeetedVetrexIndex(verQueue.peek().index);
            if(vertexIndex == -1){
                verQueue.pop();
            }else{
                vertexList[vertexIndex].wasVisited = true;
                System.out.println("зашли в вершину "+vertexList[vertexIndex].getCharValue()+" с ключом "+vertexList[vertexIndex].getIntValue());
                verQueue.add(vertexList[vertexIndex]);
            }
        }*/

        Queue verQueue = new Queue();
        verQueue.add(vertexList[startVertexIndex]);
        vertexList[startVertexIndex].wasVisited = true;
        System.out.println("зашли в вершину "+vertexList[startVertexIndex].getCharValue()+" с ключом "+vertexList[startVertexIndex].getIntValue());
        int meetedVertexIndex;
        while(verQueue.numVertexs != 0){
            int vertexIndex = verQueue.pop().index;
            while((meetedVertexIndex = getNotMeetedVetrexIndex(vertexIndex)) != -1){
                vertexList[meetedVertexIndex].wasVisited = true;
                System.out.println("зашли в вершину "+vertexList[meetedVertexIndex].getCharValue()+" с ключом "+vertexList[meetedVertexIndex].getIntValue());
                verQueue.add(vertexList[meetedVertexIndex]);
            }
        }
        //После обхода надо обнулить флаги посещения для всех вершин
        for(int i=0 ; i<numVertex;i++){
            vertexList[i].wasVisited = false;
        }
    }

    //--------------------------------------------------------------------------------------
    void getMSTTree(int startVertexIndex){ //метод для получения минимального остовного деревья (реализация базируется на обходе в глубину) //обход в глубину делает MST сам, надо лишь запомнить ребра
        Stack verStack = new Stack();
        verStack.add(vertexList[startVertexIndex]);
        Vertex currVertex;
        vertexList[startVertexIndex].wasVisited = true;
        while(verStack.numVertexs != 0){
            currVertex = verStack.peek();
            int vertexIndex = getNotMeetedVetrexIndex(verStack.peek().index);
            if(vertexIndex == -1){
                verStack.pop();
            }else{
                vertexList[vertexIndex].wasVisited = true;
                System.out.println("Строим ребро от "+currVertex.getCharValue()+" к "+vertexList[vertexIndex].getCharValue());
                verStack.add(vertexList[vertexIndex]);
            }
        }
        //После обхода надо обнулить флаги посещения для всех вершин
        for(int i=0 ; i<numVertex;i++){
            vertexList[i].wasVisited = false;
        }
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
class Vertex{
    private char charValue;
    private int intValue;
    int index;
    boolean wasVisited;

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    Vertex(char charValue,int intValue){
        this.charValue = charValue;
        this.intValue = intValue;
    }

    void show(){
        System.out.print("["+charValue+","+intValue+"]");
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////
class Stack{ //FILO
    int numVertexs;
    int index = -1;
    Vertex []vertexs = new Vertex[30];

    void add(Vertex vertex){
        if(numVertexs == 30){
            return;
        }
        vertexs[++index] = vertex;
        numVertexs++;
    }

    Vertex pop(){
        if(numVertexs == 0){
            return null;
        }
        numVertexs--;
        return vertexs[index--];
    }

    Vertex peek(){
        if(numVertexs == 0){
            return null;
        }
        return vertexs[index];
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////
class Queue{ //FIFO
    int numVertexs;
    int addIndex = -1;
    int popIndex = 0;
    Vertex []vertexs = new Vertex[30];

    void add(Vertex vertex){
        if(addIndex == vertexs.length -1){
            addIndex = -1;
        }
        vertexs[++addIndex] = vertex;
        numVertexs++;
    }

    Vertex pop(){
        Vertex tmp = vertexs[popIndex++];
        if(popIndex == vertexs.length){
            popIndex = 0;
        }
        numVertexs--;
        return tmp;
    }

    Vertex peek(){
        return vertexs[popIndex];
    }
}