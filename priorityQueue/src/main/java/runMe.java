public class runMe {
    public static void main (String...args){
        priorityQueue<Character> queue = new priorityQueue<>();
        queue.insert('C',2);
        queue.insert('B',1);
        queue.insert('A',0);
        queue.insert('D',3);
        queue.showAllElementsInPriorityOrder();
        queue.pop();
        queue.showAllElementsInPriorityOrder();

    }
}
