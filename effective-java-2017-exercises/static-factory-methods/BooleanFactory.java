

class BooleanFactory {
    private static final BooleanFactory TRUE = new BooleanFactory(true);
    private static final BooleanFactory FALSE = new BooleanFactory(false);

    private final boolean value;

    private BooleanFactory(boolean value){
        this.value = value;
    }

    public boolean getValue(){
        return this.value;
    }

    public static BooleanFactory valueOf(boolean value){
        return value ? TRUE : FALSE;
    }

    @Override
    public String toString() {
        return this.value ? "true" : "false";
    }

    public static void main(String[] args) {
        BooleanFactory trueValue = BooleanFactory.valueOf(true);
        BooleanFactory trueValue2 = BooleanFactory.valueOf(true);

        System.out.println("Same object? " + (trueValue == trueValue2));
        System.out.println("Object 1: " + trueValue.toString());
        System.out.println("Object 2: " + trueValue2.toString());

        BooleanFactory true1 = BooleanFactory.valueOf(true);
        BooleanFactory false1 = BooleanFactory.valueOf(false);

        System.out.println("Same object? " + (true1 == false1));  // true!
        System.out.println("Object 1: " + true1.toString());
        System.out.println("Object 2: " + false1.toString());
    }

}