public class Primes {
    public static void main(String[] args) {
        for (int i = 2; i < 101; i++) {
            if (isPrime(i)) {
                System.out.println(i);
            }
        }
    }

    // Проверка на то что число является простым
    //Данный цикл перебирает числа, начиная с 2 до n, проверяя существует ли
    //какое-либо значение, делящееся на n без остатка. Для этого используем
    // оператора остатка “%”. Если какая-либо переменная полностью делится на аргумент,
    //сработает оператор return false. Если же значение не делится на аргумент без
    //остатка, то это простое число, и оператор покажет return true
    public static boolean isPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n%i == 0) {
                return false;
            }
        }
        return true;
    }
}
