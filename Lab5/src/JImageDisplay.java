import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;

public class JImageDisplay extends JComponent{

    private BufferedImage image;

    /**
     Конструктор берет интеграную ширину и высоту и инициализирует
     - его объект BufferedImage будет новым изображением с такой шириной высоты
     - изображения типа TYPE_INT_RGB.
     */
    public JImageDisplay(int width, int height) {
        image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        /**
         Вызов набора родительского классаPreferredSize () метод
         с данной шириной и высотой.
         */
        Dimension size = new Dimension(width, height);
        super.setPreferredSize(size);
    }
    /**
     - Реализация Superclass paintComponent (g) называется так границами и
     Функции нарисованы правильно. Затем изображение втягивается в компонент.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Мы передаем значение null для ImageObserver
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }
    /**
     Устанавливает пиксель к определенному цвету.
     */
    public void drawPixel(int x, int y, int rgbColor) {
        image.setRGB(x, y, rgbColor);
    }
    /**
     Устанавливает все пиксели в данных изображения на черный цвет.
     */
    public void clearImage() {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                drawPixel(i, j, 0);
            }
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
