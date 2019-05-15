public class binaryTree<typeOfValue> {    //Dublicates not allowed in this realization
    Node root = null;

    //------------------------------------------------------------------------------------
    Node search(int key){
        Node current = root;
        while(current != null){
            if(current.getKey() == key){
                System.out.println("Узел с ключом "+key+" найден. Его значение = "+current.getValue());
                return current;
            }
            if(current.getKey() > key){
                current=current.leftChild;
            }else{
                current=current.rightChild;
            }
        }
        System.out.println("Узла с таким ключом("+key+ ") нету.");
        return null;
    }

    //------------------------------------------------------------------------------------
    Node recSearch(Node curr,int what){
        if(curr == null){
            System.out.println("Узла с таким ключом("+what+ ") нету.");
            return null;
        }
        if(curr.getKey() == what ){
            System.out.println("Узел с ключом "+what+" найден. Его значение = "+curr.getValue());
            return curr;
        }
        if(what > curr.getKey()){
            recSearch(curr.rightChild,what);
        }else{
            recSearch(curr.leftChild,what);
        }
        return null;
    }
    //------------------------------------------------------------------------------------
    Node insert(int key, typeOfValue what){
        if(root == null){
            root = new Node<typeOfValue>(key,what);
            System.out.println("Узел с ключом "+key+" и значением = "+what+" вставлен.");
            return root;
        }
        Node current = root;
        Node futureParent;
        while(true){
            if(current.getKey() == key){
                System.out.println("Узел с ключом "+key+" уже есть. Его значение = "+current.getValue());
                return current;
            }
            if(key < current.getKey()){
                futureParent=current;
                current=current.leftChild;
                if(current == null){
                    futureParent.leftChild = new Node<typeOfValue>(key,what);
                    futureParent.leftChild.parent=futureParent;
                    System.out.println("Узел с ключом "+key+" и значением = "+what+" вставлен.");
                    return futureParent.leftChild;
                }
            }else{
                futureParent=current;
                current=current.rightChild;
                if(current == null){
                    futureParent.rightChild = new Node<typeOfValue>(key,what);
                    futureParent.rightChild.parent=futureParent;
                    System.out.println("Узел с ключом "+key+" и значением = "+what+" вставлен.");
                    return futureParent.rightChild;
                }
            }
        }
    }
    //------------------------------------------------------------------------------------
    Node getPreemnik(Node deletedNode){ //method need to case 3 of deleting of node
        Node current = deletedNode.rightChild;
        Node Preemnik = current;
        if(current.leftChild == null){ //case 1 right child of deleted node dont nave a left Child
            return  current;
        }else{                         //case 2 right child of deleted node have a left Child
            while(current != null){
                Preemnik = current;
                current = current.leftChild;
            }
            if(Preemnik.rightChild != null){ //Preemnik have right Child, whos become Child of preemnik's parent
                Preemnik.parent.leftChild = Preemnik.rightChild;
                Preemnik.rightChild = deletedNode.rightChild;
            }
            return Preemnik;
        }
    }
    //------------------------------------------------------------------------------------
    boolean delete(int key){
        Node deletedNode = search(key);
        if(deletedNode == null){
            System.out.println("Узла со значением ключа "+key+" нету, удаление невозможно.");
            return false;
        }
        Node parent = deletedNode.parent; //For better undestanding of code

        if(deletedNode.leftChild == null && deletedNode.rightChild == null){ //Case 1: deleted node dont have childs
            if (deletedNode == root){
                root = null;
                System.out.println("Узел с ключом "+key+" удален. Его значение = "+deletedNode.getValue());
                return true;
            }
            if(deletedNode == parent.leftChild){
                parent.leftChild = null;
            }else{
                parent.rightChild = null;
            }
        }else if(deletedNode.rightChild == null){ //Case 2: deleted node have one Child (left Child)
            if (deletedNode == root){
                root = deletedNode.leftChild;
                System.out.println("Узел с ключом "+key+" удален. Его значение = "+deletedNode.getValue());
                return true;
            }
            if(deletedNode == parent.leftChild){
                parent.leftChild = deletedNode.leftChild;
            }else{
                parent.rightChild = deletedNode.leftChild;
            }
        }else if(deletedNode.leftChild == null){ //Case 2: deleted node have one Child (right Child)
            if (deletedNode == root){
                root = deletedNode.rightChild;
                System.out.println("Узел с ключом "+key+" удален. Его значение = "+deletedNode.getValue());
                return true;
            }
            if(deletedNode == parent.leftChild){
                parent.leftChild = deletedNode.rightChild;
            }else{
                parent.rightChild = deletedNode.rightChild;
            }
        }else{     //Case 3: deleted node have both Childs
            Node Preemnik = getPreemnik(deletedNode);
            if (deletedNode == root){
                root = Preemnik;
                Preemnik.leftChild = deletedNode.leftChild;
                System.out.println("Узел с ключом "+key+" удален. Его значение = "+deletedNode.getValue());
                return true;
            }
            if(Preemnik == deletedNode.rightChild){ //Right child of deleted Node become preemnik
                if(deletedNode == parent.leftChild){
                    parent.leftChild = Preemnik;
                }else{
                    parent.rightChild = Preemnik;
                }
                Preemnik.leftChild = deletedNode.leftChild;
            }else{                                   //Preemnik is one of left Child of deleted's node's Right Child
                Preemnik.rightChild = deletedNode.rightChild;
                if(deletedNode == parent.leftChild){
                    parent.leftChild = Preemnik;
                }else{
                    parent.rightChild = Preemnik;
                }
                Preemnik.leftChild = deletedNode.leftChild;
            }
        }
        System.out.println("Узел с ключом "+key+" удален. Его значение = "+deletedNode.getValue());
        return  true;
    }
    //------------------------------------------------------------------------------------
    void showAllSimmetric(Node root){ //print ordered stream of value
         if(root != null){
             showAllSimmetric(root.leftChild);
             System.out.print("["+root.getKey()+","+root.getValue()+"] ");
             showAllSimmetric(root.rightChild);
         }
    }
    //------------------------------------------------------------------------------------
    void showAllRightOrder(Node root){        //useful for prefix arithmetic expression
        if(root != null){
            System.out.print("["+root.getKey()+","+root.getValue()+"] ");
            showAllSimmetric(root.leftChild);
            showAllSimmetric(root.rightChild);
        }
    }
    //------------------------------------------------------------------------------------
    void showAllBackOrder(Node root){         //useful for postfix arithmetic expression
        if(root != null){
            showAllSimmetric(root.leftChild);
            showAllSimmetric(root.rightChild);
            System.out.print("["+root.getKey()+","+root.getValue()+"] ");
        }
    }
    //------------------------------------------------------------------------------------
    Node getMinimumKeyNode(){
        Node current = root;
        Node last = current;
        while(current !=null){
            last = current;
            current = current.leftChild;
        }
        return last;
    }
    //------------------------------------------------------------------------------------
    Node getMaximumKeyNode(){
        Node current = root;
        Node last = current;
        while(current !=null){
            last = current;
            current = current.rightChild;
        }
        return last;
    }
}

///////////////////////////////////////////////////////////////////////////////////////////
class Node<typeOfValue> {
    Node parent = null;
    Node rightChild = null;
    Node leftChild = null;

    private int key;
    private typeOfValue value;


    //------------------------------------------------------------------------------------
    public int getKey() {
        return key;
    }
    //------------------------------------------------------------------------------------
    public void setKey(int key){
        this.key = key;
    }
    //------------------------------------------------------------------------------------
    Node(int key,typeOfValue value){
        this.key = key;
        this.value = value;
    }
    //------------------------------------------------------------------------------------
    Node(){
        this.key = 0;
    }
    //------------------------------------------------------------------------------------
    public typeOfValue getValue(){
        return value;
    }
    //------------------------------------------------------------------------------------
    public void setValue(typeOfValue value){
        this.value = value;
    }
}


