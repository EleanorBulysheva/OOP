import java.util.HashMap;
import java.util.HashSet;


/**
 Этот класс содержит реализацию алгоритма поиска траекторий А*.
 Алгоритм реализован как статический метод, так как алгоритм поиска пути
 на самом деле не нужно поддерживать какое-либо состояние между призывами
 Алгоритм.
 */
public class AStarPathfinder {
    /**
     Эта константа содержит максимальный предел отсечения для стоимости путей. Если
     - конкретная точка пути, превышающая этот предел затрат,
     Отбрасывается.
     **/
    public static final float COST_LIMIT = 1e6f;


    /**
     Попытки вычислить путь, который перемещается между началом и концом
     - расположение указанной карты. Если путь может быть найден,<em>final</em> шаг на пути возвращается;
     таким образом, точка может быть
     Используется для ходьбы назад к отправной точке. Если путь не найден,
     * <code>null</code>  возвращается.
     **/
    public static Waypoint computePath(Map2D map)
    {
        // Переменные, необходимые для поиска А*.
        AStarState state = new AStarState(map);
        Location finishLoc = map.getFinish();

        // Настройка отправной точки для запуска поиска А*.
        Waypoint start = new Waypoint(map.getStart(), null);
        start.setCosts(0, estimateTravelCost(start.getLocation(), finishLoc));
        state.addOpenWaypoint(start);

        Waypoint finalWaypoint = null;
        boolean foundPath = false;

        while (!foundPath && state.numOpenWaypoints() > 0)
        {
            // Найти "лучший" (т.е. самый дешевый) путь до сих пор.
            Waypoint best = state.getMinOpenWaypoint();

            // Если лучшее расположение - место финиша, то мы закончили!
            if (best.getLocation().equals(finishLoc))
            {
                finalWaypoint = best;
                foundPath = true;
            }

            // Добавление/обновление всех соседей текущего наилучшего местоположения. Это
            // эквивалентно попытке всех "следующих шагов" из этого места.
            takeNextStep(best, state);

            // Наконец, переместите это место из "открытого" списка в "закрытый"
            // Список.
            state.closeWaypoint(best.getLocation());
        }

        return finalWaypoint;
    }

    /**
     Этот статический метод помощник принимает точку пути, и генерирует все действительные "следующий
     шаги" с этой точки зрения. Новые точки пути добавляются в "открытые
     - waypoints" коллекция пройденного объекта состояния А*.
     **/
    private static void takeNextStep(Waypoint currWP, AStarState state)
    {
        Location loc = currWP.getLocation();
        Map2D map = state.getMap();

        for (int y = loc.yCoord - 1; y <= loc.yCoord + 1; y++)
        {
            for (int x = loc.xCoord - 1; x <= loc.xCoord + 1; x++)
            {
                Location nextLoc = new Location(x, y);

                // Если "следующее местоположение" находится за пределами карты, пропустите его.
                if (!map.contains(nextLoc))
                    continue;

                // Если "следующее местоположение" это место, пропустить его.
                if (nextLoc == loc)
                    continue;

                // Если это местоположение уже находится в "закрытом" наборе
                // затем продолжить следующее место.
                if (state.isLocationClosed(nextLoc))
                    continue;

                // Сделайте точку пути для этого "следующего местоположения".

                Waypoint nextWP = new Waypoint(nextLoc, currWP);

                // Хорошо, мы обманываем и используем смету расходов для вычисления фактического
                // стоимость от предыдущей ячейки. Затем мы добавляем в стоимость от
                // ячейка карты, на которую мы наступить, чтобы включить барьеры и т.д.

                float prevCost = currWP.getPreviousCost() +
                        estimateTravelCost(currWP.getLocation(),
                                nextWP.getLocation());

                prevCost += map.getCellValue(nextLoc);

                // Пропустите это "следующее место", если это слишком дорого.
                if (prevCost >= COST_LIMIT)
                    continue;

                nextWP.setCosts(prevCost,
                        estimateTravelCost(nextLoc, map.getFinish()));

                // Добавьте точку пути к набору открытых точек пути. Если есть
                // оказывается уже точкой пути для этого места, новые
                // точка пути заменяет старую точку пути только в том случае, если она является менее дорогостоящей
                // чем старый.
                state.addOpenWaypoint(nextWP);
            }
        }
    }

    /**
     Оценивает стоимость проезда между двумя указанными местами.
     Фактическая стоимость, вычисленная, является лишь прямым расстоянием между
     Два места.
     **/
    private static float estimateTravelCost(Location currLoc, Location destLoc)
    {
        int dx = destLoc.xCoord - currLoc.xCoord;
        int dy = destLoc.yCoord - currLoc.yCoord;

        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
