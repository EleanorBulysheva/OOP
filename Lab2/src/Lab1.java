import java.util.Scanner;

public class Lab1 {
    public static void main(String[] args)
    {
        int check = 0;


        Scanner in = new Scanner(System.in);

        System.out.print("Введите по очереди координаты для первой точки: " + "\n");
        double a = in.nextInt();
        double b = in.nextInt();
        double c = in.nextInt();
        System.out.print("Введите по очереди координаты для второй точки: " + "\n");
        double a1 = in.nextInt();
        double b1 = in.nextInt();
        double c1 = in.nextInt();
        System.out.print("Введите по очереди координаты для третьей точки: " + "\n");
        double a2 = in.nextInt();
        double b2 = in.nextInt();
        double c2 = in.nextInt();

        Point3d p1 = new Point3d(a,b,c);
        Point3d p2 = new Point3d(a1,b1,c1);
        Point3d p3 = new Point3d(a2,b2,c2);
        in.close();

        System.out.println("Площадь треугольника: " + computeArea(p1, p2, p3));
    }

    public static double computeArea(Point3d p1, Point3d p2, Point3d p3) {
        // Если одна из точек равна другой то площадь не вычисляется
        //Метод equals() используется для проверки равенства двух объектов.
        // Реализация этого метода проверяет по ссылкам два объекта на предмет их эквивалентности.
        if (p1.isEqual(p2) || p2.isEqual(p3) || p3.isEqual(p1)) {
            return -1;
        } else {
            double a = p1.distanceTo(p2);
            double b = p2.distanceTo(p3);
            double c = p3.distanceTo(p1);
            double p = (a+b+c)/2;
            return (Math.sqrt(p*(p-a)*(p-b)*(p-c)));
        }
    }
}
