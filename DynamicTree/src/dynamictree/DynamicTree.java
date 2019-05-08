/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamictree;
class Data{
    Data nextRight=null;
    Data nextLeft=null;
    int value1;
    Data(){
        value1=0;
    }
    Data(int x){
        value1=x;
    }
}
class Tree{
    Data root;
    Data current;
    Tree(){
        root=new Data();
    }
    Tree(int a){
        root=new Data(a);
    }
    Data search(int what){
        current=root;
        if(what<current.value1){
            while(current.nextLeft!=null){
                current=current.nextLeft;
                if(what==current.value1){
                    System.out.println("Хранилище с элементом найдено, возвращаю ссылку на это хранилище");
                    return current;
                }
            }
        }
        if(what>current.value1){
            while(current.nextRight!=null){
                current=current.nextRight;
                if(what==current.value1){
                    System.out.println("Хранилище с элементом найдено, возвращаю ссылку на это хранилище");
                    return current;
                }
            }
        }
        System.out.println("Хранилища с таким элементом не найдено. Возвращаю нулевую ссылку");
        return null;
    }
    void put(int what){
        current=root;
        Data temp=null;
        if(search(what)!=null){
            System.out.println("Хранилище с таким элементом уже есть в древе!");
            return;
        }
        System.out.println("Добавляю "+what+" в древо.");
        if(what<current.value1){
            while(current.nextLeft!=null && current.nextLeft.value1>what){
                current=current.nextLeft;
            }
            if(current.nextLeft!=null)temp=current.nextLeft;
            current.nextLeft=new Data(what);
            current.nextLeft.nextRight=current;
            if(temp!=null)current.nextLeft.nextLeft=temp;
        }
        if(what>current.value1){
            while(current.nextRight!=null && current.nextRight.value1<what){
                current=current.nextRight;
            }
            if(current.nextRight!=null)temp=current.nextRight;
            current.nextRight=new Data(what);
            current.nextRight.nextLeft=current;
            if(temp!=null)current.nextRight.nextRight=temp;
        }
        
    }
    void showAllElements(){
        current=root;
        while(current.nextLeft!=null){
            current=current.nextLeft;
        }
        System.out.print(current.value1+" ");
        while(current.nextRight!=null){
            current=current.nextRight;
            System.out.print(current.value1+" ");
        }
    }
    void showLikeTree(){
    }
    void ClearBefore(int what){
        Data p;
        Data temp;
        p=search(what);
        if(root.value1<what)root=p;
        //p.nextLeft=null; //Обрубание ветви без чистки памяти сборкой мусора
        current=p;
        while(current.nextLeft!=null){
            current=current.nextLeft;
        }
        while(current!=p){ //объекты будут вычищены сборкой мусора, т.к. нет больше ссылок на них
            current.nextLeft=null;
            temp=current.nextRight;
            current.nextRight=null;
            current=temp;
        }
    }
    void ClearAfter(int what){
        Data p;
        Data temp;
        p=search(what);
        if(root.value1>what)root=p;
        //p.nextRight=null; //Обрубание ветви без чистки памяти сборкой мусора
        current=p;
        while(current.nextRight!=null){
            current=current.nextRight;
        }
        while(current!=p){ //объекты будут вычищены сборкой мусора, т.к. нет больше ссылок на них
            current.nextRight=null;
            temp=current.nextLeft;
            current.nextLeft=null;
            current=temp;
        }
    }
}
/**
 *
 * @author Muduck
 */
public class DynamicTree {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Data p;
        Tree Tree1=new Tree(3);
        Tree1.put(4);
        Tree1.put(1);
        Tree1.search(5);
        Tree1.put(23);
        Tree1.put(-1);
        Tree1.put(-4);
        Tree1.put(6);
        Tree1.put(7);
        p=Tree1.search(4);
        System.out.println(p.value1);
        Tree1.showAllElements();
        Tree1.ClearBefore(1); //Будут ли откинутые объекты почищены сборкой мусора? имхо они еще остались в памяти, хоть и к ним теперь не подойти через древо
        Tree1.showAllElements(); 
        Tree1.put(-3); 
        Tree1.put(-6); 
        Tree1.put(-9); 
        Tree1.put(-11);
        Tree1.ClearAfter(1);///Убедимся далее, что древо с новым корнем работает правильно
        Tree1.search(-6);
        Tree1.put(-6);
        Tree1.showAllElements();
    }
    
}
