import java.awt.geom.Rectangle2D;

/**
 Этот класс является подклассом FractalGenerator. Он используется для вычисления
 - фрактальный мандельброт.
 */
public class Mandelbrot extends FractalGenerator
{
    /**
     Константа для количества максимальных итераций.
     */
    public static final int MAX_ITERATIONS = 2000;

    /**
     Этот метод позволяет фрактальный генератор указать, какая часть
     - комплексная плоскость является наиболее интересной для фрактала.
     Он передается прямоугольным объектом и метод изменяет
     поля прямоугольника, чтобы показать правильный начальный диапазон для фрактала.
     Эта реализация устанавливает начальный диапазон (-2 - 1.5i) - (1 и 1.5i)
     или х-2, й-1,5, ширина -3.
     */
    public void getInitialRange(Rectangle2D.Double range)
    {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    /**
     Этот метод реализует итеративную функцию для фрактала Мандельброта.
     Это займет два двойника для реальных и мнимых частей комплекса
     плоскости и возвращает количество итераций для соответствующего
     Координировать.
     */
    public int numIterations(double x, double y)
    {
        /*Начните с итераций в 0. */
        int iteration = 0;
        /*Инициализировать zreal и zimaginary. */
        double zreal = 0;
        double zimaginary = 0;

        /**
         * Вычислить Zn = Zn-1^2 + c где значения являются сложными числами, представленными zreal и zimaginary,
         * Z0=0, и c является особым моментом в
         *  - фрактал, который мы отображаем (с учетом x и y). Это итерированный
         *  До тех пор, пока Z^2 > 4 (абсолютное значение z больше, чем 2) или максимум
         *  Количество итераций достигнуто.
         */
        while (iteration < MAX_ITERATIONS &&
                zreal * zreal + zimaginary * zimaginary < 4)
        {
            double zrealUpdated = zreal * zreal - zimaginary * zimaginary + x;
            double zimaginaryUpdated = 2 * zreal * zimaginary + y;
            zreal = zrealUpdated;
            zimaginary = zimaginaryUpdated;
            iteration += 1;
        }

        /**
         В случае, если алгоритм дошел до значения MAX_ITERATIONS нужно
         вернуть -1, чтобы показать, что точка не выходит за границы
         */
        if (iteration == MAX_ITERATIONS)
        {
            return -1;
        }

        return iteration;
    }

}
