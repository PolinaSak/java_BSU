// 6. Определить класс Точка в пространстве, координаты которой задаются с помощью Рациональной Дроби. Создать методы по определению расстояния между точками и расстояния до начала координат. Проверить для трех точек возможность нахождения на одной прямой.

package mycomp;

public class App 
{
    public static void main( String[] args )
    {
        Point a = new Point(new RationalFraction(0, 1), new RationalFraction(0, 1), new RationalFraction(0, 1));
Point b = new Point(new RationalFraction(1, 2), new RationalFraction(1, 2), new RationalFraction(1, 2));
Point c = new Point(new RationalFraction(1, 1), new RationalFraction(1, 1), new RationalFraction(1, 1));

System.out.println("Расстояние A-B: " + a.distanceTo(b));
System.out.println("Расстояние B до начала координат: " + b.distanceToO());
System.out.println("На одной прямой: " + Point.areCollinear(a, b, c));
    }
}
