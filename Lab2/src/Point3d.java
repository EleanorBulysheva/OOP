public class Point3d extends Point3d{

    private double yCoord;
    /** координата Z**/
    private double zCoord;
    /** Конструктор инициализации**/
    public Point3d( double z){

        zCoord = z;
    }
    /** Конструктор по умолчанию**/
    public Point3d(){
        this(0,0,0);
    }

    /** Возвращение координаты Z**/
    public double getZ(){
        return zCoord;
    }

    /** Установка значения координаты Z**/
    public void setZ(double z){
        zCoord = z;
    }
    /** Сравнение значений двух объектов**/
    public boolean isEquals(Point3d obj){
        if( this.xCoord == obj.xCoord &&
                this.yCoord == obj.yCoord &&
                this.zCoord == obj.zCoord)
            return true;
        else
            return false;
    }
    /** Расстояние между двумя точками с точностью до двух знаков**/
    public double distanceTo(Point3d obj){
//какой-то код с точностью до 2 знаков после запятой.
        double tempX = this.xCoord - obj.xCoord;
        double tempY = this.yCoord - obj.yCoord;
        double tempZ = this.zCoord - obj.zCoord;
        return Math.sqrt( Math.pow(tempX,2)+Math.pow(tempY,2)+ Math.pow(tempZ,2));
    }
}