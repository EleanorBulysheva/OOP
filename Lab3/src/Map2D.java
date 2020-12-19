/**
 Этот класс представляет собой простую двумерную карту, состоящую из квадратных ячеек.
 Каждая ячейка определяет стоимость прохождения этой ячейки.
 **/
public class Map2D {
    /*Ширина карты. **/
    private int width;

    /*Высота карты. **/
    private int height;

    /**
     Фактические данные карты, которые алгоритм поиска пути должен перемещаться.
     **/
    private int[][] cells;

    /**Стартовое место для выполнения пути A*. **/
    private Location start;

    /** Окончание местоположения для выполнения пути A. **/
    private Location finish;


    /**Создает новую 2D-карту с указанной шириной и высотой. **/
    public Map2D(int width, int height)
    {
        if (width <= 0 || height <= 0)
        {
            throw new IllegalArgumentException(
                    "width and height must be positive values; got " + width +
                            "x" + height);
        }

        this.width = width;
        this.height = height;

        cells = new int[width][height];

        // Составить некоторые координаты для начала и завершения.
        start = new Location(0, height / 2);
        finish = new Location(width - 1, height / 2);
    }


    /**
     Этот метод помощника проверяет указанные координаты, чтобы увидеть,
     В пределах границ карты. Если координаты не находятся на карте
     - затем метод бросает хт;код»gt;IllegalArgumentException»lt;/code
     **/
    private void checkCoords(int x, int y)
    {
        if (x < 0 || x > width)
        {
            throw new IllegalArgumentException("x must be in range [0, " +
                    width + "), got " + x);
        }

        if (y < 0 || y > height)
        {
            throw new IllegalArgumentException("y must be in range [0, " +
                    height + "), got " + y);
        }
    }

    /**Возвращает ширину карты. **/
    public int getWidth()
    {
        return width;
    }

    /**Возвращает высоту карты. **/
    public int getHeight()
    {
        return height;
    }

    /**
     Возвращает верно, если указанные координаты содержатся на карте
     Площадь.
     **/
    public boolean contains(int x, int y)
    {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }


    /**Возвращает верно, если местоположение содержится в области карты. **/
    public boolean contains(Location loc)
    {
        return contains(loc.xCoord, loc.yCoord);
    }

    /**Возвращает сохраненную стоимость для указанной ячейки. **/
    public int getCellValue(int x, int y)
    {
        checkCoords(x, y);
        return cells[x][y];
    }

    /**Возвращает сохраненную стоимость для указанной ячейки. **/
    public int getCellValue(Location loc)
    {
        return getCellValue(loc.xCoord, loc.yCoord);
    }

    /**Устанавливает стоимость для указанной ячейки. **/
    public void setCellValue(int x, int y, int value)
    {
        checkCoords(x, y);
        cells[x][y] = value;
    }

    /**
     Возвращает исходное место для карты. Именно здесь генерируется
     Путь начнется с.
     **/
    public Location getStart()
    {
        return start;
    }

    /**
     Устанавливает исходное место для карты. Это где сгенерированный путь
     И начнется с.
     **/
    public void setStart(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc cannot be null");

        start = loc;
    }

    /**
     Возвращает место окончания для карты. Именно здесь генерируется
     Путь прекратится.
     **/
    public Location getFinish()
    {
        return finish;
    }

    /**
     Устанавливает место окончания для карты. Это где сгенерированный путь
     Вопрос прекратится.
     **/
    public void setFinish(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc cannot be null");

        finish = loc;
    }
}
