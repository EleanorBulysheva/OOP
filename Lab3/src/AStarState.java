import java.util.*;

/**
 * Этот класс хранит базовое состояние, необходимое алгоритму A* для вычисления a
 * путь по карте. Это состояние включает коллекцию "открытых путевых точек" и
 * другую коллекцию "закрытых путевых точек"." Кроме того, этот класс обеспечивает
 * основные операции, необходимые алгоритму поиска пути A* для выполнения его
 * обработка.
 **/
public class AStarState {
    /** Это ссылка на карту, по которой перемещается алгоритм A*. **/
    private Map2D map;
    private Map<Location, Waypoint> Opened = new java.util.HashMap<Location, Waypoint>();
    private Map<Location, Waypoint> Closed = new java.util.HashMap<Location, Waypoint>();

    /**
     * Инициализируйте новый объект состояния для использования алгоритма поиска пути A*.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");
        this.map = map;
    }

    /** Возвращает карту, по которой перемещается навигатор A*. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * Этот метод сканирует все открытые путевые точки и возвращает путевую точку
     * с минимальными общими затратами. Если открытых путевых точек нет, то этот метод
     * возвращает <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (Opened.size() == 0) return null;
        ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>(Opened.values());
        float mincost = waypoints.get(0).getTotalCost();
        Waypoint min = waypoints.get(0);
        for (int i = 1; i < waypoints.size(); i++) {
            if (waypoints.get(i).getTotalCost() < mincost) {
                min = waypoints.get(i);
                mincost = min.getTotalCost();
            }
        }
        return min;
    }

    /**
     * Этот метод добавляет путевую точку к уже существующей (или потенциально обновляет ее
     * в) коллекция "открытые путевые точки". Если там еще нет открытого
     * путевая точка в местоположении новой путевой точки, то новая путевая точка просто
     * добавлено в коллекцию. Однако если в
     местоположении * new waypoint уже есть путевая точка, то новая путевая точка заменяет только старую <em>
     * если</em> значение "предыдущей стоимости" новой путевой точки меньше текущей
     * значение путевой точки "предыдущая стоимость".
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        if (Opened.get(newWP.getLocation()) == null ) {
            Opened.put(newWP.getLocation(), newWP);
            return true;
        }
        else
        {
            if (Opened.get(newWP.getLocation()).getPreviousCost() >
                    newWP.getPreviousCost()) {
                Opened.put(newWP.getLocation(), newWP);
                return true;
            }
        }
        return false;
    }

    /** Возвращает текущее количество открытых путевых точек. **/
    public int numOpenWaypoints()
    {
        return Opened.size();
    }

    /**
     * Этот метод перемещает путевую точку в указанном месте из
     открытого списка в закрытый список.
     **/
    public void closeWaypoint(Location loc)
    {
        Closed.put(loc, Opened.remove(loc));
    }

    /**
     * Возвращает true, если коллекция закрытых путевых точек содержит путевую точку
     * для указанного местоположения.
     **/
    public boolean isLocationClosed(Location loc)
    {
        if (Closed.containsKey(loc)) return true;
        return false;
    }
}
