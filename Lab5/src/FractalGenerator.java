import java.awt.geom.Rectangle2D;

/**
 Этот класс обеспечивает общий интерфейс и операции для фрактала
         генераторы, которые можно просмотреть в Fractal Explorer.
         */
public abstract class FractalGenerator {

    /**
     * Эта функция статического помощника принимает интеграмер координаты и преобразует его
     * в двухточное значение, соответствующее определенному диапазону. Так и есть
     * используется для преобразования пиксельных координат в двухточное значения для
     * вычисления фракталов и т.д.
     *
     * @param rangeMin минимальное значение диапазона с плавающей запятой
     * @param rangeMax максимальное значение диапазона с плавающей запятой
     *
     * @param size размер измерения, из которого берется координата пикселя.
     *  * Например, это может быть ширина изображения или высота изображения.
     *
     * @param coord координата для вычисления значения двойной точности.
     *  * Координата должна находиться в диапазоне [0, size].
     */
    public static double getCoord(double rangeMin, double rangeMax,
                                  int size, int coord) {

        assert size > 0;
        assert coord >= 0 && coord < size;

        double range = rangeMax - rangeMin;
        return rangeMin + (range * (double) coord / (double) size);
    }


    /**
     Устанавливает указанный прямоугольник, чтобы содержать начальный диапазон, пригодный для
     Фрактальный генерируется.
     */
    public abstract void getInitialRange(Rectangle2D.Double range);


    /**
     Обновляет текущий диапазон, который будет центрирован в указанных координатах,
     и быть увеличены или из указанного фактора масштабирования.
     */
    public void recenterAndZoomRange(Rectangle2D.Double range,
                                     double centerX, double centerY, double scale) {

        double newWidth = range.width * scale;
        double newHeight = range.height * scale;

        range.x = centerX - newWidth / 2;
        range.y = centerY - newHeight / 2;
        range.width = newWidth;
        range.height = newHeight;
    }

    /**
     С учетом координат хт;em'gt;x'lt;/em'gt;
     вычисляет и возвращает количество итераций перед фракталом
     Функция избегает области границ для этой точки. Точка, которая
     Не ускользаю до того, как будет указано ограничение итерации
     с результатом -1.
     */
    public abstract int numIterations(double x, double y);
}
