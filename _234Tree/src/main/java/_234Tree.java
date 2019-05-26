public class _234Tree { //Dublicats not allowed here :(
    _234Node root = new _234Node();

    //------------------------------------------------------------------------------------
    DataItem search(int value){
        _234Node currNode = root;
        int childNumber;
        while(true){
            if((childNumber = currNode.searchItem(value)) != -1){
                return currNode.getItem(childNumber); //нашли нужный узел
            }else if(currNode.isLeaf()){
                return null; //дошли до листа, совпадений нету
            }else{
                currNode = getNextChild(currNode,value); //спускаемся дальше по древу
            }
        }
    }

    //------------------------------------------------------------------------------------
    _234Node insert(int value){
        DataItem tmpItem = new DataItem(value);
        _234Node currNode = root;
        while(true){
            if(currNode.isFull()){ //Текущий узел полный , требуется разбиение
                split(currNode);
                currNode = currNode.getParent(); //переходим на предка разбитого и продолжаем поиск с него
                currNode = getNextChild(currNode,value); //от предка идем к нужному
            }else if(currNode.isLeaf()){ //Текущий узел лист, прекращаем проходку
                break;
            }else{ //текущий узел не лист и не полный, просто переходим по нужному индексу
                currNode = getNextChild(currNode,value);
            }
        }
        currNode.insertItem(tmpItem); //Вставляем новый итем в выбранный узел
        return currNode;
    }

    //------------------------------------------------------------------------------------
    void split(_234Node splittedNode){ //Разбиваем полный узел
        DataItem itemB, itemC; //временные ссылки для переходящих итемов
        _234Node parent, child2, child3;
        int itemIndex;
        itemB = splittedNode.deleteItem();
        itemC = splittedNode.deleteItem(); //поочередно удаляем крайние правые итемы и сохраняем ссылки на них
        child2 = splittedNode.disconnectChild(2);
        child3 = splittedNode.disconnectChild(3); //Отсоединяем так же крайние два потомка
        _234Node newRightNode = new _234Node(); //Новый правый "брат" разбиваемого узла.

        if(splittedNode == root){//Case 1 : разбиваемый - корень
            root = new _234Node();
            parent = root;
            root.connectChild(0,splittedNode); //Создали новый корень и в самого левого потомка кинули ему старый
        }else{ //Case 2:  Разбиваемый не корень
            parent = splittedNode.getParent();
        }
        itemIndex = parent.insertItem(itemB); //присоеднияем средний итем к родителю
        int numParentItems = parent.getNumItems();
        for(int i = numParentItems-1; i>itemIndex; i--){ //Перемещение потомков родителей,идущих после вставялемого в родителя узла, на 1 вправо
            _234Node tmpNode = parent.disconnectChild(i);
            parent.connectChild(i+1,tmpNode);
        }
        parent.connectChild(itemIndex+1,newRightNode);
        newRightNode.insertItem(itemC); //Далее вставляем итем в брата и кидаем ему потомков
        newRightNode.connectChild(0,child2);
        newRightNode.connectChild(1,child3);
    }

    //------------------------------------------------------------------------------------
    _234Node getNextChild(_234Node parentNode, int value){ //Получаем нужного потомка при поиске значения , узел не должен быть пустым\полным\листом
        int i;
        int nItems = parentNode.getNumItems();
        for(i = 0; i<nItems; i++){
            if(value < parentNode.getItem(i).getValue()){
                return parentNode.getChild(i); //искомое значение ниже значения в текущем итеме узла, переход надо делать по нему
            }
        }
        return parentNode.getChild(i); //Вставляемое больше всех итемов, возвращаем для перехода самый крайний
    }

    //------------------------------------------------------------------------------------
    void showTree(){
        recShowTree(root,0,0);
    }
    //------------------------------------------------------------------------------------
    void recShowTree(_234Node currNode, int level,int childIndex){
        System.out.print("Уровень = "+level+ " потомок ="+childIndex+" ");
        currNode.showNode();

        int numItems = currNode.getNumItems(); //рекурсивно вызываем показ для каждого потомка текущего узла
        for(int i = 0;i<numItems+1;i++){
            _234Node nextNode = currNode.getChild(i);
            if(nextNode != null){
                recShowTree(nextNode,level+1,i);
            }else{
                return;
            }
        }
    }
}

///////////////////////////////////////////////////////////////////////////////////////////
class _234Node {
    private int nItems;
    private _234Node parent;
    private _234Node []childArray = new _234Node[4]; // [*<A,A<*<B,B<*<C,C<*]
    private DataItem []dataArray = new DataItem[3]; // [A,B,C]


    //------------------------------------------------------------------------------------
    int insertItem(DataItem dataItem) { // данные всегда будут упорядочены , возвращаем индекс только что вставленного элемента
        //Узел должен быть не пуст
        nItems++;
        for (int i = 2; i >= 0; i--) { //смотрим поочередно все итемы текущего узла справа
            if (dataArray[i] == null) {
                continue;
            } else {
                if (dataItem.getValue() < dataArray[i].getValue()) {
                    dataArray[i + 1] = dataArray[i]; //Сдвигаем вправо , если новый ключ меньше
                } else {
                    dataArray[i + 1] = dataItem;
                    return i + 1;
                }
            }
        }
        dataArray[0] = dataItem; //Если все итемы в текущем узле оказались пусты
        return 0;

    }

    //------------------------------------------------------------------------------------
    int searchItem(int value){ //возвращает индекс -1, если данного с этим значением нету в массиве данных, иначе индекс данного с выбранным значением
        for(int i =0; i<3; i++){
            if(dataArray[i] == null) break;
            else if(dataArray[i].getValue() == value) return i;
        }
        return -1;
    }

    //------------------------------------------------------------------------------------
    DataItem deleteItem(){ //Удаляем всегда наибольший итем (самый правый)
        DataItem tmpItem = dataArray[nItems-1];
        dataArray[nItems-1] = null;
        nItems--;
        return tmpItem;
    }

    //------------------------------------------------------------------------------------
    void connectChild(int childIndex, _234Node child) {
        childArray[childIndex] = child;
        if (child != null) child.parent = this;
    }

    //------------------------------------------------------------------------------------
    _234Node disconnectChild(int childIndex){
        _234Node tmpNode = childArray[childIndex];
        childArray[childIndex] = null;
        return tmpNode;
    }

    //------------------------------------------------------------------------------------
    boolean isFull(){
        return nItems == 3;
    }

    //------------------------------------------------------------------------------------
    _234Node getChild(int childIndex){
        return childArray[childIndex];
    }

    //------------------------------------------------------------------------------------
    _234Node getParent(){
        return parent;
    }

    int getNumItems(){
        return nItems;
    }
    //------------------------------------------------------------------------------------
    boolean isLeaf(){
        return (childArray[0] == null);
    }

    //------------------------------------------------------------------------------------
    DataItem getItem(int itemIndex){
        return dataArray[itemIndex];
    }

    //------------------------------------------------------------------------------------
    void showNode(){
        for(int i=0;i<nItems;i++){
            dataArray[i].showItem();
            System.out.print("|");
        }
        System.out.println();
    }
}

///////////////////////////////////////////////////////////////////////////////////////////
class DataItem{
    private int value;

    DataItem(int value){
        this.value = value;
    }

    void showItem(){
        System.out.print(" "+value+" ");
    }

    int getValue(){
        return value;
    }

    void setValue(int value){
        this.value = value;
    }
}