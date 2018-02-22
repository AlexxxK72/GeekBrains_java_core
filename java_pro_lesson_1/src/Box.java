import java.lang.reflect.Type;
import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> fruits;

    public Box() {
        fruits = new ArrayList<>();
    }

    public ArrayList<? extends T> getFruits() {
        return fruits;
    }

    public void setFruits(ArrayList<T> fruits) {
        this.fruits = fruits;
        //type = fruits.getClass().getTypeParameters()[0];
    }

    public boolean pourFruit(Box<T> srcBox){
        if(this != srcBox){
        for (T fruit : this.getFruits()) {
            srcBox.add(fruit);
        }
        this.getFruits().clear();
        }
        return false;
    }


    public void add(T fruit) {
            fruits.add(fruit);
    }

    public boolean compare(Box<? extends Fruit> srcBox) {
        return Math.abs(this.getWeight() - srcBox.getWeight()) < 0.001f;
    }

    public double getWeight(){
        if(fruits.size() > 0){
            if(fruits.get(0) instanceof Apple) return Apple.WEIGHT * fruits.size();
            if(fruits.get(0) instanceof Orange) return Orange.WEIGHT * fruits.size();
        }
        return 0.f;
    }

    public String getState(){
        if(fruits.size() > 0) return fruits.get(0).getClass().getName() + ": " + fruits.size() + " шт.";
        return "ящик пустой";
    }
}
