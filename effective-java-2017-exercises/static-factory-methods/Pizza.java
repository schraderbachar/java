import java.io.Console;
import java.util.ArrayList;

enum Size {
    Small,
    Medium,
    Large,
    XL
}

public class Pizza {
    private Size size;
    ArrayList<String> toppings;
    boolean isGlutenFree;
    double price;

    private Pizza(Size size,ArrayList<String> toppings, boolean isGlutenFree){
        this.size = size;
        this.toppings = toppings;
        this.isGlutenFree = isGlutenFree;
        this.calculatePrice();
    }

    @Override
    public String toString() {
        return "Pizza size " + this.size.toString() + " with toppings " + this.toppings.toString() + " GF? " + this.isGlutenFree + " for a total of $" + this.price;
    }

    private void calculatePrice(){
        var price = 0;
        if (this.size == Size.Small){
            price = 6;
        }
       else if (this.size == Size.Medium){
            price = 8;
        }
        else if (this.size == Size.Large){
            price = 8;
        }
        else {
            price = 10;
        }
       price += this.toppings.size();

        this.price = price;
    }

    static Pizza createMargherita(Size size){
        ArrayList<String> toppings = new ArrayList<>();
        toppings.add("Cheese");
        toppings.add("Tomato");
        toppings.add("Basil");
        toppings.add("Olive Oil");
        return new Pizza(size,toppings, false);
    }
    static Pizza createPepperoni(Size size){
        ArrayList<String> toppings = new ArrayList<>();
            toppings.add("Cheese");
            toppings.add("Tomato");
            toppings.add("Pepperoni");
            return new Pizza(size, toppings, false);
    }
    static Pizza createVeggie(Size size){
        ArrayList<String> toppings = new ArrayList<>();
            toppings.add("Cheese");
            toppings.add("Tomato");
            toppings.add("Mushrooms");
            toppings.add("Peppers");
            toppings.add("Peppers");
            return new Pizza(size, toppings, true);
    }
    static Pizza createCustom(Size size, ArrayList<String> toppings){
        return new Pizza(size, toppings, false);
    }

    static Pizza createGlutenFree(Size size, ArrayList<String> toppings){
        return new Pizza(size, toppings, true);
    }

    public static void main(String[] args) {
        Pizza m = Pizza.createMargherita(Size.Large);
        Pizza p = Pizza.createPepperoni(Size.XL);
        Pizza v = Pizza.createVeggie(Size.XL);

         ArrayList<String> toppings = new ArrayList<>();
            toppings.add("Cheese");
            toppings.add("Tomato");
            toppings.add("Pineapple");
            toppings.add("Ham");
            toppings.add("Peppers");
        Pizza c = Pizza.createCustom(Size.Medium, toppings);

         ArrayList<String> toppingsGF = new ArrayList<>();
            toppingsGF.add("Cheese");
            toppingsGF.add("Pineapple");
        Pizza gf = Pizza.createGlutenFree(Size.Large, toppingsGF);

        System.out.println("Creating Margherita Pizza");
        System.out.println(m.toString());
        System.out.println("Creating Pepperoni Pizza");
        System.out.println(p.toString());
        System.out.println("Creating Veggie Pizza");
        System.out.println(v.toString());
        System.out.println("Creating Custom Pizza");
        System.out.println(c.toString());
        System.out.println("Creating Gluten Free Pizza");
        System.out.println(gf.toString());
    }



}
