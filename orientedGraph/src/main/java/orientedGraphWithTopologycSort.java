public class orientedGraphWithTopologycSort {
    private final int MAX_VERTEXS = 100;
    private int numVertex;
    private int[][] smezhMatrix;
    private Vertex[] vertexList;

    orientedGraphWithTopologycSort(){
        vertexList = new Vertex[MAX_VERTEXS];
        smezhMatrix = new int[MAX_VERTEXS][MAX_VERTEXS];
        numVertex = 0;
    }

    //--------------------------------------------------------------------------------------
    void addVertex(String StringValue, int intValue){
        vertexList[numVertex] = new Vertex(StringValue,intValue);
        vertexList[numVertex].index = numVertex;
        numVertex++;
    }

    //--------------------------------------------------------------------------------------
    void deleteVertex(int index){ //Метод для удаления вершины из графа ( можно использовать при топологической сортировке)
        for(int i = 0; i<numVertex; i++){ //Удаляем все возможные ребра, связанные с этой вершиной
            smezhMatrix[i][index] = 0;
            smezhMatrix[index][i] = 0;
        }
        while(index != numVertex - 1){
            vertexList[index] = vertexList[index+1];
            index ++;
        }
        numVertex--;
    }

    //--------------------------------------------------------------------------------------
    void addEdge(int from,int to){
        smezhMatrix[from][to] = 1;
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
        System.out.println("зашли в вершину "+vertexList[startVertexIndex].getStringValue()+" с ключом "+vertexList[startVertexIndex].getIntValue());

        while(verStack.numVertexs != 0){
            int vertexIndex = getNotMeetedVetrexIndex(verStack.peek().index);
            if(vertexIndex == -1){
                verStack.pop();
            }else{
                vertexList[vertexIndex].wasVisited = true;
                System.out.println("зашли в вершину "+vertexList[vertexIndex].getStringValue()+" с ключом "+vertexList[vertexIndex].getIntValue());
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
    void BFS(int startVertexIndex){
        Queue verQueue = new Queue();
        verQueue.add(vertexList[startVertexIndex]);
        vertexList[startVertexIndex].wasVisited = true;
        System.out.println("зашли в вершину "+vertexList[startVertexIndex].getStringValue()+" с ключом "+vertexList[startVertexIndex].getIntValue());
        int meetedVertexIndex;
        while(verQueue.numVertexs != 0){
            int vertexIndex = verQueue.pop().index;
            while((meetedVertexIndex = getNotMeetedVetrexIndex(vertexIndex)) != -1){
                vertexList[meetedVertexIndex].wasVisited = true;
                System.out.println("зашли в вершину "+vertexList[meetedVertexIndex].getStringValue()+" с ключом "+vertexList[meetedVertexIndex].getIntValue());
                verQueue.add(vertexList[meetedVertexIndex]);
            }
        }
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
                System.out.println("Строим ребро от "+currVertex.getStringValue()+" к "+vertexList[vertexIndex].getStringValue());
                verStack.add(vertexList[vertexIndex]);
            }
        }
        //После обхода надо обнулить флаги посещения для всех вершин
        for(int i=0 ; i<numVertex;i++){
            vertexList[i].wasVisited = false;
        }
    }

    //--------------------------------------------------------------------------------------
    //Должна применяться к направленным графам БЕЗ циклов!
    String topologycSort(){ //Вместо удаления вершин решил их помечать удаленными (не придется делать копию всего графа)
        int[][] savedSmezhMatrix = smezhMatrix; //бэкап матрицы смежности, в ней будут удаления, потом восстановим
        int savedNumVertex = numVertex; //сохраним изначальное число вершин, чтобы потом восстановить
        StringBuilder topology = new StringBuilder();

        while(numVertex != 0) {
            for (int i = 0; i < savedNumVertex; i++) {
                if(vertexList[i].wasDeleted) continue; //пропускаем псевдо-удаленные
                if (getPreemnik(vertexList[i]) == null) { //Если вершина не имеет преемников
                    topology.append(vertexList[i].getStringValue() +" -> ");
                    vertexList[i].wasDeleted = true;
                    numVertex--;
                    for(int j = 0; j<savedNumVertex; j++){ //Удаляем все возможные ребра, выходящие из этой вершины
                        smezhMatrix[i][j] = 0;
                    }
                }
            }
        }
        //После сортировки надо обнулить флаги удаления для всех вершин
        for(int i=0 ; i<numVertex;i++){
            vertexList[i].wasDeleted = false;
        }
        smezhMatrix = savedSmezhMatrix;
        numVertex = savedNumVertex;
        return topology.toString();
    }

    Vertex getPreemnik(Vertex targetVertex){ //Преемник -> вершина, имеющая ребро, указывающее к целевой вершине
        int targetVertexIndex = targetVertex.index;
        for(int i = 0; i<numVertex; i++){
            if(smezhMatrix[i][targetVertexIndex] == 1){
                return vertexList[i]; //нашли такую вершину
            }
        }
        return null;
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
class Vertex{
    private String StringValue;
    private int intValue;
    int index; //Это поле присваивается самим графом при добавлении каждой вершины
    boolean wasVisited; // используется для обходов
    boolean wasDeleted; // используется для топологической сортировки

    public String getStringValue() {
        return StringValue;
    }

    public void setCharValue(String StringValue) {
        this.StringValue = StringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    Vertex(String StringValue,int intValue){
        this.StringValue = StringValue;
        this.intValue = intValue;
    }

    void show(){
        System.out.print("["+StringValue+","+intValue+"]");
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