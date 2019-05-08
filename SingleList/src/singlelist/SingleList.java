/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singlelist;

/**
 *
 * @author Muduck
 */
class Node{
    Node left=null;
    int value;
    Node(){
        this.value=0;
    }
    Node(int value){
        this.value=value;
    }
    void get_value(){
        System.out.print(value+" ");
    }
}
class List{
    Node root=null;
    Node Selected=null;
    List(){
        root=new Node();
        Selected=root;
    }
    List(int start_value){
        root=new Node(start_value);
        Selected=root;
    }
    void put(int what){
        if(root==null){ System.out.println("List is empty!"); }
        while(Selected.left!=null){
            Selected=Selected.left;
        }
        Selected.left=new Node(what);
        Selected=root;
    }
    void get(int index){
        if(root==null){ System.out.println("List is empty!"); }
        for(int i=0;i<index;i++){
            if(Selected.left==null) { System.out.println("Node with this index not found"); return; }
            Selected=Selected.left;
        }
        Selected.get_value();
        Selected=root;
    }
    void reverse(){
        Node Prev=root;
        Node Next=root.left;
        root.left=null;
        while(Next!=null){
            Node Temp=Next.left;
            Next.left=Prev;
            Prev=Next;
            Next=Temp;
        }
        Selected=root=Prev;
    }
    void show_all(){
        
        while(Selected!=null){
            Selected.get_value();
            Selected=Selected.left;
        }
        Selected=root;
    }
}
public class SingleList {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List Test=new List(5);
        Test.put(6);
        Test.put(9);
        Test.put(12);
        Test.show_all();
        System.out.println(" _________ ");
        Test.get(3);
        System.out.println(" _________ ");
        Test.reverse();
        Test.show_all();
    }
    
}
