public interface Shape {
    abstract double area();
    abstract String getType();
     static Circle createCircle(double radius){
        return new Circle(radius);
     }

     static Rectangle createRectangle(double width, double height){
        return new Rectangle(width,height);
     }

     static Triangle createTriangle(double base, double height){
        return new Triangle(3, 4);
     }

     public static void main(String[] args) {
        Circle c = Shape.createCircle(5);
        Rectangle r = Shape.createRectangle(3, 4);
        Triangle t = Shape.createTriangle(3, 4);

        System.out.println(c.area());
        System.out.println(c.getType());
        System.out.println(r.area());
        System.out.println(r.getType());
        System.out.println(t.area());
        System.out.println(t.getType());
    }
}



 class Circle implements Shape{
    private double radius;

    Circle(double radius){
    this.radius = radius;
    }
    @Override
    public double area() {
        return Math.PI * Math.pow(radius,2);
    }

    @Override
    public String getType() {
       return "Circle";
    }

}

class Rectangle implements Shape {

    private double width;
    private double height;

    Rectangle(double width, double height){
        this.width = width;
        this.height = height;
    }


    @Override
    public double area() {
       return width * height;
    }

    @Override
    public String getType() {
        return "Rectangle";
    }

}

class Triangle implements Shape {

    private double base;
    private double height;

    Triangle(double base, double height){
        this.base = base;
        this.height = height;
    }


    @Override
    public double area() {
       return base * height / 2;
    }

    @Override
    public String getType() {
        return "Triangle";
    }

}
