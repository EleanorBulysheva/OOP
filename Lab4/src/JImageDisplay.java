import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

/**
 Этот класс позволяет нам отображать наши фракталы.
 Он происходит от javax.swing.JComponent.
 */
public class JImageDisplay extends JComponent{
    /**
     Экземпляр буферного изображения.
     Класс BufferedImage управляет
     изображением, содержимое которого можно записать
     */
    private BufferedImage displayImage;

    /**
     Конструктор JImageDisplay принимает целочисленные
     значения ширины и высоты, и инициализирует объект BufferedImage новым
     изображением с этой шириной и высотой, и типом изображения
     TYPE_INT_RGB
     */
    public JImageDisplay(int width, int height) {
        displayImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);

        /**
         Вызов набора родительского классаPreferredSize () метод
         с данной шириной и высотой.
         */
        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);

    }
    /**
     - Реализация Superclass paintComponent (g) называется так границами и
     Функции нарисованы правильно. Затем изображение втягивается в компонент.
     (Мы передаем значение null для ImageObserver, поскольку данная
     функциональность не требуется)
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(displayImage, 0, 0, displayImage.getWidth(),
                displayImage.getHeight(), null);
    }
    /**
     Устанавливает все пиксели в данных изображения на черный цвет.
     */
    public void clearImage()
    {
        int[] blankArray = new int[getWidth() * getHeight()];
        displayImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
    }
    /**
     Устанавливает пиксель к определенному цвету.
     */
    public void drawPixel(int x, int y, int rgbColor)
    {
        displayImage.setRGB(x, y, rgbColor);
    }
}
