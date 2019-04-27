import java.util.Arrays;
public class runMe {
    public static void main (String...args){
        orderedDoubleLinkedList List= new orderedDoubleLinkedList();
        List.insert(11);
        List.insert(22);
        List.insert(33);
        System.out.println("------0-----");
        List.showList();
        List.insert(1);
        List.insert(55);
        List.insert(44);
        List.insert(55);
        System.out.println("------1-----");
        List.showList();
        List.insert(60);
        System.out.println("------2-----");
        List.showList();
        System.out.println("------3 deletes-----");
        List.remove(11);
        List.remove(60);
        List.remove(55);
        System.out.println("------4-----");
        List.showList();
        System.out.println("------5-----");
        int []backArray;
        int []Array;
        backArray = List.toArray(true);
        Array = List.toArray(false);
        System.out.println("backArray : "+ Arrays.toString(backArray));
        System.out.println("Array : "+ Arrays.toString(Array));
        System.out.println("MergeSorting of backArray: ");
        mergeSort.mergeSort(backArray);
        System.out.println("backArray : "+ Arrays.toString(backArray));
    }
}
