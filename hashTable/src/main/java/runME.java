public class runME {
    public static void main(String...args){
        hashTable<String> table = new hashTable<>(100);
        table.add(1001,"Блаблабла");
        table.add(1,"Ламберт, ламберт - хер моржовый, ламберт ламберт - вредный");
        table.add(1001,"oh shit here we going again");
        table.add(231321,"231321");
        table.add(5555,"5555");
        table.showTable();
        System.out.println("in 5555 "+ table.get(5555));
        System.out.println("in 25 "+ table.get(25));
        System.out.println("in 0 "+ table.get(0));
        table.delete(5555);
        table.delete(23);
        table.delete(2332);
        table.add(23,"23");
        table.showTable();
        table.delete(23);
        table.delete(23);
        table.showTable();
    }
}
