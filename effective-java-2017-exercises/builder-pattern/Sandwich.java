public class Sandwich{
    private final String bread;

    private final String meat;
    private final String cheese;
    private final boolean lettuce;
    private final boolean tomato;
    private final boolean onions;
    private final String sauce;

    public static class Builder {
        private final String bread;

         private  String meat = "";
        private  String cheese = "";
        private  boolean lettuce = false;
        private  boolean tomato = false;
        private  boolean onions = false;
        private  String sauce = "";

        public Builder(String bread){
            this.bread = bread;

        }

        public Builder meat(String meat){
            this.meat = meat;
            return this;
        }
        public Builder cheese(String cheese){
            this.cheese = cheese;
            return this;
        }
        public Builder lettuce(boolean lettuce){
            this.lettuce = lettuce;
            return this;
        }
        public Builder tomato(boolean tomato){
            this.tomato = tomato;
            return this;
        }
        public Builder onions(boolean onions){
            this.onions = onions;
            return this;
        }
        // Could make this into an array list to allow for multiple sauces- could do the same with meat as well
        public Builder sauce(String sauce){
            this.sauce = sauce;
            return this;
        }
        public Sandwich build(){
            return new Sandwich(this);
        }
    }

    private Sandwich(Builder builder){
                this.bread = builder.bread;
        this.meat = builder.meat;
        this.cheese = builder.cheese;
        this.lettuce = builder.lettuce;
        this.tomato = builder.tomato;
        this.onions = builder.onions;
        this.sauce = builder.sauce;
    }

    @Override
    public String toString() {

        return "Bread " + this.bread + " meat " + this.meat + " cheese " + this.cheese + " tomato? " + this.tomato + " lettuce? " + this.lettuce + " onions?" + this.onions  + " sauce " + this.sauce ;
    }

    public static void main(String[] args) {
        System.out.println("A simple sandwich (just bread and meat)");
        Sandwich meat = new Sandwich.Builder("white").meat("ham").build();
        System.out.println(meat.toString());
        System.out.println("A vegetarian sandwich (no meat, but lots of veggies)");
        Sandwich veggie = new Sandwich.Builder("white").cheese("cheddar").lettuce(true).onions(true).tomato(true).sauce("ranch").build();
        System.out.println(veggie.toString());

         System.out.println("A fully loaded sandwich");
        Sandwich all = new Sandwich.Builder("white").meat("ham").meat("turkey").cheese("cheddar").lettuce(true).onions(true).tomato(true).sauce("ranch").build();
        System.out.println(all.toString());

    }

}