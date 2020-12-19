import java.awt.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

/**
 Этот класс позволяет обузаить различные части фрактала
 Создание и показ графического интерфейса Swing и обрабатывает события, вызванные различными
 Взаимодействия с пользователями.
 */

public class FractalExplorer {
    /*Размер дисплея Integer – это ширина и высота дисплея в пикселях. **/
    private int displaySize;

    /**
     - JImageDisplay ссылка на обновление дисплея из различных методов, как
     Фрактал вычисляется.
     */
    private JImageDisplay display;

    /**
     Объект FractalGenerator, использующий ссылку базового класса для
     других видов фракталов в будущем.
     */
    private FractalGenerator fractal;

    /**
     - Прямоугольник2D.Двойной объект, который определяет диапазон комплекса
     - то, что мы сейчас отображаем.
     */
    private Rectangle2D.Double range;

    /**
     Конструктор, который принимает размер дисплея, хранит его и
     Инициализирует диапазон и фрактальный генератор объектов.
     */
    public FractalExplorer(int size) {
        /*- размером с дисплей*/
        displaySize = size;

        /*Инициализирует фрактальный генератор и объекты диапазона. **/
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);

    }
    /**
     Этот метод intializes Swing GUI с JFrame проведения
     JImageDisplay объект и кнопка для сброса дисплея.
     */
    public void createAndShowGUI()
    {
        /*Установите рамку для использования java.awt.BorderLayout для ее содержимого. **/
        display.setLayout(new BorderLayout());
        JFrame myframe = new JFrame("Fractal Explorer");

        /**
         Добавляйте объект отображения изображений в BorderLayout.CENTER
         Позиция.
         */
        myframe.add(display, BorderLayout.CENTER);

        /*Создайте кнопку сброса. */
        JButton resetButton = new JButton("Reset Display");

        /*Экземпляр ResetHandler на кнопке сброса. */
        ResetHandler handler = new ResetHandler();
        resetButton.addActionListener(handler);

        /*Добавляйте кнопку сброса в положение BorderLayout.SOUTH. */
        myframe.add(resetButton, BorderLayout.SOUTH);

        /*Экземпляр MouseHandler на компоненте фрактального дисплея. */
        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        /*Установите операцию закрытия кадра по умолчанию, чтобы "выйти". */
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         Данные операции правильно разметят содержимое окна, сделают его
         видимым и затем запретят изменение размеров окна
         */
        myframe.pack();
        myframe.setVisible(true);
        myframe.setResizable(false);
    }

    /**
     - Частный метод помощников для отображения фрактала. Этот метод петли
     через каждый пиксель на дисплее и вычисляет количество
     итерации для соответствующих координат в фрактале
     - область отображения. Если количество итераций -1, установите цвет пикселя
     в черный цвет. В противном случае выберите значение, основанное на количестве итераций.
     Обновление дисплея цветом для каждого пикселя и перекраска
     JImageDisplay, когда все пиксели были нарисованы.
     */
    private void drawFractal()
    {
        /*Петля через каждый пиксель на дисплее */
        for (int x=0; x<displaySize; x++){
            for (int y=0; y<displaySize; y++){

                /**
                 Найти соответствующие координаты xCoord и yCoord
                 в области отображения фрактала.
                 */
                double xCoord = fractal.getCoord(range.x,
                        range.x + range.width, displaySize, x);
                double yCoord = fractal.getCoord(range.y,
                        range.y + range.height, displaySize, y);

                /**
                 Вычислить количество итераций для координат в
                 - область отображения фрактала.
                 */
                int iteration = fractal.numIterations(xCoord, yCoord);

                /*Если количество итераций составляет -1, установите пиксель на черный цвет. */
                if (iteration == -1){
                    display.drawPixel(x, y, 0);
                }

                else {
                    /**
                     В противном случае выберите значение оттенка на основе числа
                     итераций.
                     */
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    /*Обновление дисплея с цветом для каждого пикселя. */
                    display.drawPixel(x, y, rgbColor);
                }

            }
        }
        /**
         Когда все пиксели нарисованы, перекраска JImageDisplay в
         - текущее содержимое его изображения.
         */
        display.repaint();
    }
    /**
     Внутренний класс для обработки событий ActionListener с кнопки сброса.
     */
    private class ResetHandler implements ActionListener
    {
        /**
         Обработчик сбрасывает диапазон в внутренний диапазон,
         генератор, а затем рисует фрактальный.
         */
        public void actionPerformed(ActionEvent e)
        {
            fractal.getInitialRange(range);
            drawFractal();
        }
    }
    /**
     Внутренний класс для обработки событий MouseListener с дисплея.
     */
    private class MouseHandler extends MouseAdapter
    {
        /**
         Когда обработчик получает событие щелчка мыши, он отображает пиксель-
         координаты щелчка в область фрактала, который в настоящее время
         - отображается, а затем вызывает генератор recenterAnd'oomRange ()
         метод с нажатиями координат и шкалой 0,5.
         */
        @Override
        public void mouseClicked(MouseEvent e)
        {
            /*Получить x координаты области отображения щелчка мыши. */
            int x = e.getX();
            double xCoord = fractal.getCoord(range.x,
                    range.x + range.width, displaySize, x);

            /*- Получите y координаты области отображения щелчка мыши. */
            int y = e.getY();
            double yCoord = fractal.getCoord(range.y,
                    range.y + range.height, displaySize, y);

            /**
             генератор recenterAnd'oomRange () метод с
             координаты, которые были нажаты и 0,5 шкалы.
             */
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

            /**
             Перерисовка фрактала после того, как область
             Отображение изменилось.
             */
            drawFractal();
        }
    }

    /**
     Статический основной () метод запуска FractalExplorer. Инициализирует новый
     - Экземпляр FractalExplorer с размером дисплея 600
     СоздатьAndShowGUI () на объекте исследователя, а затем вызывает
     - нарисуйтеФрактал () на исследователе, чтобы увидеть исходное представление.
     */
    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(600);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}
