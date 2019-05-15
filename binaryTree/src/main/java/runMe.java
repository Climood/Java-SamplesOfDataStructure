public class runMe {
    public static void main (String...args){
        binaryTree<Character> tree=new binaryTree<Character>();
        tree.insert(0,'c');
        tree.insert(-2,'a');
        tree.insert(-1,'b');
        tree.insert(1,'d');
        tree.showAllSimmetric(tree.root);
        System.out.println();
        tree.showAllBackOrder(tree.root);
        System.out.println();
        tree.showAllRightOrder(tree.root);
        tree.delete(-2);
        tree.insert(32,'j');
        tree.insert(32,'d');
        tree.showAllSimmetric(tree.root);
        tree.delete(32);
        tree.insert(-23,'A');
        tree.insert(-20,'B');
        tree.insert(5,'t');
        tree.insert(-6,'x');
        tree.insert(90,'y');
        tree.insert(-50,'z');
        tree.insert(16,'i');
        tree.insert(13,'o');
        tree.insert(-89,'p');
        tree.insert(7,'m');
        tree.showAllSimmetric(tree.root);
        tree.delete(21);
        tree.delete(888);
        tree.delete(0);
        tree.delete(7);
        tree.delete(-20);
        tree.showAllSimmetric(tree.root);
        tree.recSearch(tree.root,1111);
        tree.recSearch(tree.root,0);
        tree.recSearch(tree.root,-6);



    }
}
