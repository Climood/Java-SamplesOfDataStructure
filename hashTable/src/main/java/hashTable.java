class orderedList<typeOfObject> {
    private Link<typeOfObject> first;

    orderedList(){
        first = null;
    }
    //-------------------------------------------------------------------------------------
    Link insert(typeOfObject what,int key){
        if(first == null){
            first = new Link<typeOfObject>(what,key);
            System.out.println("Элемент со значением "+what+" и ключом "+key+" добавлен.");
            return first;
        }
        Link prev = null;
        Link curr = first;
        while(curr !=null && key > curr.key){
            prev = curr;
            curr = curr.next;
        }
        if(prev == null){ //insert in the begining of queue
            first = new Link<typeOfObject>(what,key);
            System.out.println("Элемент со значением "+what+" и ключом "+key+" добавлен.");
            first.next = curr;
            return first;
        }else{
            prev.next = new Link<typeOfObject>(what,key);
            System.out.println("Элемент со значением "+what+" и ключом "+key+" добавлен.");
            prev.next.next = curr;
            return prev.next;
        }

    }

    //-------------------------------------------------------------------------------------
    Link<typeOfObject> search(int key){
        Link curr = first;
        while(curr != null && curr.key <= key){
            if(curr.key == key){
                System.out.println("Элемент с ключом "+key+" найден.");
                return curr;
            }
            curr = curr.next;
        }
        System.out.println("Элемент с ключом "+key+" не найден.");
        return null;
    }

    //-------------------------------------------------------------------------------------
    Link<typeOfObject> delete(int key){
        Link<typeOfObject> prevLink = null;
        Link<typeOfObject> deletedLink = null;
        Link<typeOfObject> curr = first;
        while(curr != null && curr.key <= key){
            if(curr.key == key){
                System.out.println("Элемент с ключом "+key+" найден.");
                deletedLink = curr;
                break;
            }
            prevLink=curr;
            curr=curr.next;
        }
        if(deletedLink == null){
            System.out.println("Удаление невозможно. Элемент с ключом "+key+" не найден.");
            return null;
        }else{
            if(deletedLink == first){
                first = deletedLink.next;
                return deletedLink;
            }else{
                prevLink.next = deletedLink.next;
                return deletedLink;
            }
        }
    }

    //-------------------------------------------------------------------------------------
    typeOfObject getValue(int key){
        Link<typeOfObject> findedLink = search(key);
        if(findedLink == null){
            System.out.println("Получение данных невозможно. Элемент с ключом "+key+" не найден.");
            return null;
        }else{
            return findedLink.getValue();
        }
    }

    //-------------------------------------------------------------------------------------
    void showList(){
        Link<typeOfObject> curr = first;
        while(curr !=null){
            System.out.print("["+curr.getValue()+","+curr.key+"] ");
            curr=curr.next;
        }
    }


}

//////////////////////////////////////////////////////////////////////////////////
class Link<typeOfObject> {
    Link<typeOfObject> next;
    int key;
    private typeOfObject value;

    public typeOfObject getValue() {
        return value;
    }

    public void setValue(typeOfObject value) {
        this.value = value;
    }

    Link(typeOfObject value,int priority){
        this.value=value;
        this.key=priority;
    }
}

//////////////////////////////////////////////////////////////////////////////////
public class hashTable<typeOfObject>{ //для коллизий используется метод цепочек
    private orderedList []lists;
    private int hashTableSize;

    hashTable(int size){
        lists = new orderedList[size];
        hashTableSize = size;
        for(int i=0; i<hashTableSize;i++){
            lists[i] = new orderedList<typeOfObject>();
        }
    }

    //-------------------------------------------------------------------------------------
    void showTable(){
        for(int i=0; i<hashTableSize;i++){
            System.out.print(i+" ");
            lists[i].showList();
            System.out.println();
        }
    }

    //-------------------------------------------------------------------------------------
    int hashFunc(int key){
        return key % hashTableSize;
    }

    //-------------------------------------------------------------------------------------
    void add(int key,typeOfObject what){
        int hashIndex = hashFunc(key);
        lists[hashIndex].insert(what,hashIndex);
    }

    //-------------------------------------------------------------------------------------
    typeOfObject get(int key){
        int hashIndex = hashFunc(key);
        typeOfObject value = (typeOfObject) lists[hashIndex].getValue(hashIndex);
        if(value == null){
            System.out.println("Элемента с таким ключом нет в хеш таблице.");
        }
        return value;
    }

    //-------------------------------------------------------------------------------------
    typeOfObject delete(int key){
        int hashIndex = hashFunc(key);
        Link deletedLink = lists[hashIndex].delete(hashIndex);
        if(deletedLink == null){
            System.out.println("Элемента с таким ключом нет в хеш таблице.");
            return null;
        }else{
            return (typeOfObject) deletedLink.getValue();
        }
    }
}
