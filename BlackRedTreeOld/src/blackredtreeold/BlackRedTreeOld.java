/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackredtreeold;
enum Colors {BLACK,RED};
class Node{
    boolean isNull=false;
    Node Left,Right,Parent;
    Colors Color;
    int value;
    Node(int what){
        value=what;
        Color=Colors.RED;
        Left=new Node();
        Left.Parent=this;
        Right=new Node();
        Right.Parent=this;
    }
    Node(){
        isNull=true;
        Color=Colors.BLACK;
    }
}
class BRTree{
    Node root,current;
    BRTree(int RootValue){
        root=new Node(RootValue);
        root.Color=Colors.BLACK;
    }
    Node Search(int what){
        current=root;
        while(!current.isNull){
            if(what==current.value){
                System.out.println("Значение найдено, возвращаю ссылку на узел");
                return current;
            }else{
                if(what>current.value){
                    current=current.Right;
                }else{
                    current=current.Left;
                }
            }
        }
        System.out.println("Значение не найдено");
        return null;
    }
    Node Uncle(Node who){
        if(GrandParent(who)==null){
            return null;
        }else{
            if(who.Parent==GrandParent(who).Left){
                if(GrandParent(who).Right.isNull){
                    return null;
                }else{
                    return GrandParent(who).Right;
                }
            }else{
                if(GrandParent(who).Left.isNull){
                    return null;
                }else{
                    return GrandParent(who).Left;
                }
            }
        }
    }
    
    Node GrandParent(Node who){
        if(who.Parent!=null && who.Parent.Parent!=null){
            return who.Parent.Parent;
        }else{
            return null;
        }
    }
    void Insert(int what){
        Node AddedUzel;
        if(Search(what)!=null){
            System.out.println("Значение уже есть в древе!");
            return;
        }
        current=root;
        for(;;){
            if(what>current.value){
                if(current.Right.isNull){
                    current.Right=new Node(what);
                    AddedUzel=current.Right;
                    current.Right.Parent=current;
                    break;//added
                }else{
                    current=current.Right;
                }
            }else{
                if(current.Left.isNull){
                    current.Left=new Node(what);
                    AddedUzel=current.Left;
                    current.Left.Parent=current;
                    break;//added
                }else{
                    current=current.Left;
                }
            }
        }
        InsertCase1(AddedUzel); ///Запускаем цепочку проверок случаев
    }
    void InsertCase1(Node who){ //Случай вставки 1: добавляемый узел в корне дерева
        if(who.Parent==null){
            who.Color=Colors.BLACK;
        }else
            InsertCase2(who);
    }
    void InsertCase2(Node who){ //Случай вставки 2: Предок P добавляемого узла чёрный,
        if(who.Parent.Color==Colors.BLACK){
        }else
            InsertCase3(who);
    }
    void InsertCase3(Node who){ //Случай вставки 3: Если и родитель P и дядя U  добавляемого узла — красные, то они оба могут быть перекрашены в чёрный и дедушка G станет красным
        Node Unc=Uncle(who);
        Node GrandP=GrandParent(who);
        if(Unc!=null && Unc.Color==Colors.RED){
            who.Parent.Color=Colors.BLACK;
            Unc.Color=Colors.BLACK;
            GrandP.Color=Colors.BLACK;
            InsertCase1(GrandP);
        }else{
            InsertCase4(who);
        }
    }
    void InsertCase4(Node who){ //Случай вставки 4: Родитель P добавляемого узла является красным, но дядя U — чёрный. Также, текущий узел N — правый потомок P, а P в свою очередь — левый потомок своего предка G.
        Node GrandP=GrandParent(who);
        if(who.Parent.Right==who && who.Parent==GrandP.Left){
            LeftRotate(who.Parent);
            who=who.Left;
        }else{
            if(who.Parent.Left==who && who.Parent==GrandP.Right){
                RightRotate(who.Parent);
                who=who.Right;
            }
        }
        InsertCase5(who);
    }
    void InsertCase5(Node who){ //Случай вставки 5: Родитель P добавляемого узла является красным, но дядя U — чёрный, текущий узел N — левый потомок P и P — левый потомок G.
        Node GrandP=GrandParent(who);
        who.Parent.Color=Colors.BLACK;
        GrandP.Color=Colors.RED;
        if(who.Parent.Left==who && who.Parent==GrandP.Left){
            RightRotate(GrandP);
        }else{
            LeftRotate(GrandP);
        }
    }
    void showTree(Node begin){
        if(begin.isNull){
            return;
        }
        showTree(begin.Left);
        System.out.print(begin.value+" ");
        showTree(begin.Right);
    }
    void LeftRotate(Node who){
        Node temp=who.Right;
        temp.Parent=who.Parent;
        if(who.Parent!=null){
            if(who.Parent.Right==who){
                who.Parent.Right=temp;
            }else{
                who.Parent.Left=temp;
            }
        }
        who.Right=temp.Left;
        temp.Left.Parent=who;
        temp.Left=who;
        who.Parent=temp;
    }
    void RightRotate(Node who){
        Node temp=who.Left;
        temp.Parent=who.Parent;
        if(who.Parent!=null){
            if(who.Parent.Right==who){
                who.Parent.Right=temp;
            }else{
                who.Parent.Left=temp;
            }
        }
        who.Left=temp.Right;
        temp.Right.Parent=who;
        temp.Right=who;
        who.Parent=temp;
    }
    void DeleteValue(int what){
        Node DeletedNode=Search(what);
        if(DeletedNode==null){
            System.out.println("Такого элемента нету в дереве!");
            return;
        }
        if(DeletedNode.Parent==null){
            System.out.println("Удаление корня через этот метод невозможно");
            return;
        }
        if(DeletedNode.Left.isNull && DeletedNode.Right.isNull){
            if(DeletedNode.Parent.Left==DeletedNode){
                DeletedNode.Parent.Left=new Node();
            }else{
                DeletedNode.Parent.Right=new Node();
            }
        }
        if(DeletedNode.Left.isNull && !DeletedNode.Right.isNull){
            if(DeletedNode.Parent.Left==DeletedNode){
                DeletedNode.Right.Parent=DeletedNode.Parent;
                DeletedNode.Parent.Left=DeletedNode.Right;
            }else{
                DeletedNode.Right.Parent=DeletedNode.Parent;
                DeletedNode.Parent.Right=DeletedNode.Right;
            }
        }   
        if(!DeletedNode.Left.isNull && DeletedNode.Right.isNull){
            if(DeletedNode.Parent.Left==DeletedNode){
                DeletedNode.Left.Parent=DeletedNode.Parent;
                DeletedNode.Parent.Left=DeletedNode.Left;
            }else{
                DeletedNode.Left.Parent=DeletedNode.Parent;
                DeletedNode.Parent.Right=DeletedNode.Left;
            }
        }
        if(!DeletedNode.Left.isNull && !DeletedNode.Right.isNull){ ///Я выбрал нахождение минимального в правом поддреве
            Node PastNode=null,Temp=null;
            current=DeletedNode.Right;
            while(!current.isNull){
                PastNode=current;
                current=current.Left;
            }
            Temp=PastNode.Parent;
            PastNode.Parent=DeletedNode.Parent;
            if(DeletedNode.Parent.Left==DeletedNode){
                DeletedNode.Parent.Left=PastNode;
            }else{
                DeletedNode.Parent.Right=PastNode;
            }
            PastNode.Left=DeletedNode.Left;
            PastNode.Left.Parent=PastNode;
            if(DeletedNode.Right!=PastNode){
                Temp.Left=new Node();
                if(!PastNode.Right.isNull){
                    Temp.Right=PastNode.Right;
                    Temp.Right.Parent=Temp;
                }
                PastNode.Right=DeletedNode.Right;
                PastNode.Right.Parent=PastNode;
            }
        }
    }
    
}
/**
 *
 * @author Muduck
 */
public class BlackRedTreeNew {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BRTree test=new BRTree(10);
        test.Insert(5);
        test.Insert(15);
        test.Insert(20);
        test.Insert(60);
        test.Insert(-60);
        test.Insert(-187);
        test.Insert(-31);
        test.Insert(23);
        test.Insert(-65);
        test.Insert(73);
        test.Insert(100);
        test.Insert(200);
        test.Insert(-99);
        test.Insert(-12);
        test.Insert(11);
        test.Search(5);
        test.Search(12);
        test.DeleteValue(-60);
        test.showTree(test.root);
    }
    
}
