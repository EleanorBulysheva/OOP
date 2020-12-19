import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 Простое приложение Swing для демонстрации алгоритма поиска траекторий АЗ.
 Пользователю представлена карта, содержащая начало и конец местоположения. Пользователь
 может нарисовать или устранить препятствия на карте, а затем нажать на кнопку, чтобы вычислить
 Путь от начала до конца с помощью алгоритма поиска пути АЗ. Если путь
 найдено, он отображается зеленым цветом.
 **/

public class AStarApp {

    /*Количество ячеек сетки в направлении X. **/
    private int width;

    /*Количество ячеек сетки в направлении Y. **/
    private int height;

    /*Место, откуда начинается путь. **/
    private Location startLoc;

    /*Место, где должен закончиться путь. **/
    private Location finishLoc;

    /**
     Это 2D массив компонентов пользовательского интерфейса, которые обеспечивают отображение и манипуляции
     - ячеек на карте.
     ***/
    private JMapCell[][] mapCells;


    /**
     Этот внутренний класс обрабатывает события мыши в основной сетке ячеек карты,
     - изменение ячеек на основе состояния кнопки мыши и первоначального редактирования
     Это было выполнено.
     **/
    private class MapCellHandler implements MouseListener
    {
        /**
         Это значение будет верно, если кнопка мыши была нажата, и мы
         В настоящее время в разгар операции модификации.
         **/
        private boolean modifying;

        /**
         Это значение записывает, делаем ли мы ячейки проходимыми или
         непроходимый. Что зависит от исходного состояния ячейки
         что операция была начата внутри.
         **/
        private boolean makePassable;

        /*Инициирует операцию модификации. **/
        public void mousePressed(MouseEvent e)
        {
            modifying = true;

            JMapCell cell = (JMapCell) e.getSource();

            // Если текущая ячейка проходима, то мы делаем их
            // непроходимым; если это непроходимо, то мы делаем их проходимыми.

            makePassable = !cell.isPassable();

            cell.setPassable(makePassable);
        }

        /*Завершает операцию модификации. **/
        public void mouseReleased(MouseEvent e)
        {
            modifying = false;
        }

        /**
         Если мышь была нажата, это продолжает модификацию
         операции в новую ячейку.
         **/
        public void mouseEntered(MouseEvent e)
        {
            if (modifying)
            {
                JMapCell cell = (JMapCell) e.getSource();
                cell.setPassable(makePassable);
            }
        }

        /*Не нужен для этого обработчика. **/
        public void mouseExited(MouseEvent e)
        {
            // Это тот, который мы игнорируем.
        }

        // Это тот, который мы игнорируем.
        public void mouseClicked(MouseEvent e)
        {
            // И этот тоже.
        }
    }


    /**
     Создает новый экземпляр AStarApp с указанной шириной карты и
     высота.
     **/
    public AStarApp(int w, int h) {
        if (w <= 0)
            throw new IllegalArgumentException("w must be > 0; got " + w);

        if (h <= 0)
            throw new IllegalArgumentException("h must be > 0; got " + h);

        width = w;
        height = h;

        startLoc = new Location(2, h / 2);
        finishLoc = new Location(w - 3, h / 2);
    }


    /**
     Простой метод помощников для настройки пользовательского интерфейса Swing. Это называется
     - из потока обработчика событий Swing, чтобы быть потоковым.
     **/
    private void initGUI()
    {
        JFrame frame = new JFrame("Pathfinder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        contentPane.setLayout(new BorderLayout());

        // Используйте GridBagLayout, потому что он на самом деле уважает предпочтительный размер
        // указаны компоненты, которые он излагает.

        GridBagLayout gbLayout = new GridBagLayout();
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.BOTH;
        gbConstraints.weightx = 1;
        gbConstraints.weighty = 1;
        gbConstraints.insets.set(0, 0, 1, 1);

        JPanel mapPanel = new JPanel(gbLayout);
        mapPanel.setBackground(Color.GRAY);

        mapCells = new JMapCell[width][height];

        MapCellHandler cellHandler = new MapCellHandler();

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                mapCells[x][y] = new JMapCell();

                gbConstraints.gridx = x;
                gbConstraints.gridy = y;

                gbLayout.setConstraints(mapCells[x][y], gbConstraints);

                mapPanel.add(mapCells[x][y]);
                mapCells[x][y].addMouseListener(cellHandler);
            }
        }

        contentPane.add(mapPanel, BorderLayout.CENTER);

        JButton findPathButton = new JButton("Find Path");
        findPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { findAndShowPath(); }
        });

        contentPane.add(findPathButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        mapCells[startLoc.xCoord][startLoc.yCoord].setEndpoint(true);
        mapCells[finishLoc.xCoord][finishLoc.yCoord].setEndpoint(true);
    }


    /*Выбивает приложение. Вызывается из метода {@link #main}. **/
    private void start()
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { initGUI(); }
        });
    }


    /**
     Этот метод помощников пытается вычислить путь с помощью текущей карты
     состояние. Реализация идет довольно медленно; новый объект@link Map2D»
     создан и инициализирован из текущего состояния приложения. Затем АЗ
     - следопыт называется, и если путь найден, дисплей обновляется до
     Показать путь, который был найден. (Лучшее решение будет использовать модель
     Посмотреть шаблон проектирования контроллера.)
     **/
    private void findAndShowPath()
    {
        // Создайте объект Map2D, содержащий текущее состояние пользовательского ввода.

        Map2D map = new Map2D(width, height);
        map.setStart(startLoc);
        map.setFinish(finishLoc);

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                mapCells[x][y].setPath(false);

                if (mapCells[x][y].isPassable())
                    map.setCellValue(x, y, 0);
                else
                    map.setCellValue(x, y, Integer.MAX_VALUE);
            }
        }

        // Попробуйте вычислить путь. Если можно вычислить, отметь все ячейки в путь.

        Waypoint wp = AStarPathfinder.computePath(map);

        while (wp != null)
        {
            Location loc = wp.getLocation();
            mapCells[loc.xCoord][loc.yCoord].setPath(true);

            wp = wp.getPrevious();
        }
    }


    /**
     Пункт входа для приложения. Аргументы командной строки не являются
     - признано в это время.
     **/
    public static void main(String[] args) {
        AStarApp app = new AStarApp(40, 30);
        app.start();
    }
}
