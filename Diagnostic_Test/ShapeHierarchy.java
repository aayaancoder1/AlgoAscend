//Shape Hierarchy (Python or Java):
// Create a base class Shape with a method area().
// Subclass Rectangle (with length and width)
// Subclass Circle (with radius)
// Write a program that creates objects of both and prints their areas.


interface Shape
{
    public abstract double area(int x, int y);
}

class Rectangle implements Shape
{
    public double area(int l, int b){
        return l * b;
        return 0;
    }
}

class ShapeHierarchy extends Shape  //circle
{
    double area(int r){
        return (3.14 * r * r);
    }

    public static void main (String arg[]){
        Rectangle r = new Rectangle();
        System.out.println(r.area(10, 20));

        ShapeHierarchy s = new ShapeHierarchy();
        System.out.println(s.area(10));
    }
}