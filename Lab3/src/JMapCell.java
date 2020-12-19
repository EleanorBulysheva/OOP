import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


/**
 Этот класс представляет собой пользовательский компонент Swing для представления одной ячейки карты в
 2D-карта. Ячейка имеет несколько различных видов состояния, но самые основные
 Состояние заключается в том, проходима ячейка или нет.
 */

public class JMapCell extends JComponent{
    private static final Dimension CELL_SIZE = new Dimension(12, 12);

    /**Правда указывает на то, что ячейка является конечной точкой, либо начать или закончить. **/
    boolean endpoint = false;


    /**Правда указывает на то, что ячейка проходима; ложным означает, что это не так.*/
    boolean passable = true;

    /**
     Правда указывает на то, что эта ячейка является частью пути между началом и концом.
     **/
    boolean path = false;

    /**
     Создать новую ячейку карты с указанной "проходимостью". Входные данные
     верно означает, что клетка проходима.
     **/
    public JMapCell(boolean pass)
    {
        // Установите предпочтительный размер ячейки, чтобы управлять первоначальным размером окна.
        setPreferredSize(CELL_SIZE);

        setPassable(pass);
    }

    /**Создать новую ячейку карты, которая будет проходима по умолчанию. **/
    public JMapCell()
    {
        // Позвоните другому конструктору, указав верное для "проходимого".
        this(true);
    }

    /**Помечает эту ячейку либо как стартовую, либо как концовку ячейки. **/
    public void setEndpoint(boolean end)
    {
        endpoint = end;
        updateAppearance();
    }

    /**
     Устанавливает эту ячейку, чтобы быть проходимой или не проходимой. Ввод истинных знаков
     - ячейка как проходимая; ввод ложных знаков его как не проходимый.
     **/
    public void setPassable(boolean pass)
    {
        passable = pass;
        updateAppearance();
    }

    /**Возвращает верно, если эта ячейка проходима, или ложно иначе. **/
    public boolean isPassable()
    {
        return passable;
    }

    /**Переключает текущее "проходимое" состояние ячейки карты. **/
    public void togglePassable()
    {
        setPassable(!isPassable());
    }

    /**Отметки этой ячейки как часть пути, обнаруженного алгоритмом А*. **/
    public void setPath(boolean path)
    {
        this.path = path;
        updateAppearance();
    }

    /**
     Этот метод помощников обновляет цвет фона в соответствии с текущим
     Внутреннее состояние ячейки.
     **/
    private void updateAppearance()
    {
        if (passable)
        {
            // Проходимая клетка. Укажите его состояние с границей.
            setBackground(Color.WHITE);

            if (endpoint)
                setBackground(Color.CYAN);
            else if (path)
                setBackground(Color.GREEN);
        }
        else
        {
            // Непроходимая клетка. Сделай все это красным.
            setBackground(Color.RED);
        }
    }

    /**
     Реализация метода краски для рисования фонового цвета в
     Карта ячейки.
     **/
    protected void paintComponent(Graphics g)
    {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}
