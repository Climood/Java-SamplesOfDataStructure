public class weightGraph { //Пример взвешенного, но НЕнаправленного графа
    private final int MAX_VERTEXS = 100;
    private int numVertex;
    private int[][] smezhMatrix;
    private Vertex[] vertexList;

    weightGraph(){
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
    void addEdge(int from,int to,int edgeWeight){
        smezhMatrix[from][to] = edgeWeight;
        smezhMatrix[to][from] = edgeWeight;
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
    void UorhsellAlgorithm(){ //Позволяет узнать за О(1) возможен ли переход от одной вершины к другой, используя построенную таблицу (видоизмененную матрицу смежностей)
        //граф построенный на такой изменненной матрице -> транзитивное замыкание исходного графа
        //идея простая: Если от вершины А можно перейти к Б, а от Б к С => от А можно перейти к С
        int[][] modifiedSmezhMatrix = smezhMatrix;
        int fromVertexIndex;
        int toVertexIndex;
        for(fromVertexIndex = 0;fromVertexIndex<numVertex;fromVertexIndex++){
            for(toVertexIndex = 0;toVertexIndex<numVertex;toVertexIndex++){
                if(modifiedSmezhMatrix[fromVertexIndex][toVertexIndex] == 1){ //существует ребро от 1ой вершины ко 2ой
                    addNewEdgeToModifiedSmezhMatrix(modifiedSmezhMatrix,fromVertexIndex,toVertexIndex); //модифицируем матрицу смежности методом ниже
                }
            }
        }
        //Выведем модифицированную матрицу смежности (в укороченном варианте :) )
        for(int i=0;i<numVertex;i++){
            for(int j=0;j<numVertex;j++){
                System.out.print(modifiedSmezhMatrix[i][j]+"-");
            }
            System.out.println();
        }
        //чтобы понять, что возможен переход за O(1) просто смотрим есть ли ребро от начальной вершины до конечной в модифицированной матрице
    }


    void addNewEdgeToModifiedSmezhMatrix(int[][] modSmezhMatx,int fromVertexIndex,int toVertexIndex){
        //Метод смотрит на то, какие вершины имеют ребро к fromVertexIndex вершине, и проставляет им новое ребро до toVertexIndex
        for(int i = 0;i<numVertex ; i++){ //значит ищем все вершины, имеющие ребро в 1ую и ставим, что от них можно так же дойти и до 2ой
            if(modSmezhMatx[i][fromVertexIndex] == 1){
                modSmezhMatx[i][toVertexIndex] = 1;
            }
        }
    }

    //--------------------------------------------------------------------------------------
    void getTableOfSvyznosti(){ //Отображает последовательно для каждой вершины список вершин, в которые можно прийти, начав с выбранной
        //Все основано на DFS
        for(int i=0 ; i< numVertex;i++){
            System.out.println("Обход с вершины "+vertexList[i].getStringValue());
            DFS(i);
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
            if(smezhMatrix[startVertex][i] >0 && !vertexList[i].wasVisited){ //Если есть смежная с текущей и не посещенная ранее
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
    void getMSTTree(int startVertexIndex){ //MST древо во взвешенных ненаправленных графах должно учитывать вес ребер и в итоге сумма веса ребер должна быть минимально возможной
        Stack verStack = new Stack();
        edgePriorityQueue edgeQueue = new edgePriorityQueue();//отсортированная очередь для ребер (в порядке возрастания веса)
        verStack.add(vertexList[startVertexIndex]);
        vertexList[startVertexIndex].isInMSTTree = true;
        System.out.println("Ключили в MST древо вершину "+vertexList[startVertexIndex].getStringValue());
        Vertex currVertex;
        while(verStack.numVertexs < numVertex){ //Пока не включили все вершины в MST древо
            currVertex = verStack.peek();
            int vertexIndex;
            for(int i = 0; i < numVertex; i++){ //вставляем в очередь ребра , смежные с currVert
                //посещаем их и заносим стоимость ребер в очередь
                if(i == currVertex.index) continue;
                if(vertexList[i].isInMSTTree) continue; //пропускаем , если уже в древе
                if(smezhMatrix[currVertex.index][i] > 0) { //если ребро существует, то заносим
                    edgeQueue.insert(currVertex.index, i, smezhMatrix[currVertex.index][i]);
                }
            }
            //на вершине очереди ребро с наименьшей стоимостью, заносим его в MST древо и вершину к которой оно ведет тоже
            verStack.add(vertexList[edgeQueue.peek().toVertexIndex]);
            vertexList[edgeQueue.peek().toVertexIndex].isInMSTTree = true;
            System.out.println("Ключили в MST древо вершину "+vertexList[edgeQueue.peek().toVertexIndex].getStringValue());
            System.out.println("Построено ребро: "+vertexList[currVertex.index].getStringValue()+" ----> "+vertexList[edgeQueue.peek().toVertexIndex].getStringValue() +" вес:"+edgeQueue.peek().edgeWeight);
            //Теперь исключим из очереди все ребра, которые ведут к только что добавленной вершине
            edgeQueue.deleteAllEdgesWithTargetVertex(edgeQueue.peek().toVertexIndex);
        }
        //После построения древа, обнулим флаги
        for(int i=0 ; i<numVertex;i++){
            vertexList[i].isInMSTTree= false;
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

    //---------------------------------------------------------------------------------------
    Vertex getPreemnik(Vertex targetVertex){ //Преемник -> вершина, имеющая ребро, указывающее к целевой вершине
        int targetVertexIndex = targetVertex.index;
        for(int i = 0; i<numVertex; i++){
            if(smezhMatrix[i][targetVertexIndex] > 0){
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
    boolean isInMSTTree; //используется для построения MST древа

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
//////////////////////////////////////////////////////////////////////////////////////////////
class edgePriorityQueue{ //приоритетная очередь ребер для построения MST древа
    private edgeLink first;

    edgePriorityQueue(){
        first = null;
    }
    //-------------------------------------------------------------------------------------
    edgeLink insert(int fromVertexIndex,int toVertexIndex,int edgeWeight){
        if(first == null){
            first = new edgeLink(fromVertexIndex,toVertexIndex,edgeWeight);
            return first;
        }
        edgeLink prev = null;
        edgeLink curr = first;
        while(curr !=null && edgeWeight > curr.edgeWeight){
            prev = curr;
            curr = curr.next;
        }
        if(prev == null){ //insert in the begining of queue
            first = new edgeLink(fromVertexIndex,toVertexIndex,edgeWeight);
            first.next = curr;
            return first;
        }else{
            prev.next = new edgeLink(fromVertexIndex,toVertexIndex,edgeWeight);
            prev.next.next = curr;
            return prev.next;
        }

    }

    //-------------------------------------------------------------------------------------
    edgeLink pop(){
        if(first == null){
            return null;
        }
        edgeLink tmp = first;
        first=first.next;
        return tmp;
    }

    //-------------------------------------------------------------------------------------
    edgeLink peek(){
        if(first == null){
            return null;
        }
        return first;
    }

    //-------------------------------------------------------------------------------------
    void deleteAllEdgesWithTargetVertex(int targetVertexIndex){
        edgeLink prev = null;
        edgeLink curr = first;
        while(curr != null){
            if(curr.toVertexIndex == targetVertexIndex){ //если нашли ребро, ведущее к выбранной вершине
                if(curr == first){//если удаляем первое ребро в очереди
                    first = curr.next;
                }else //если не первое
                prev.next = curr.next;
            }
            prev = curr;
            curr = curr.next;
        }
    }
    //-------------------------------------------------------------------------------------
    void showAllElementsInPriorityOrder(){
        edgeLink curr = first;
        while(curr !=null){
            System.out.print("["+curr.fromVertexIndex+","+curr.toVertexIndex+","+curr.edgeWeight+"] ");
            curr=curr.next;
        }
    }


}

//////////////////////////////////////////////////////////////////////////////////
class edgeLink{//использую для помещения ребер в приоритетную очередь при построении MST древа
    int fromVertexIndex;
    int toVertexIndex;
    int edgeWeight;

    edgeLink next;

    edgeLink(int fromVertexIndex,int toVertexIndex,int edgeWeight){
        this.fromVertexIndex = fromVertexIndex;
        this.toVertexIndex = toVertexIndex;
        this.edgeWeight = edgeWeight;
    }
}
