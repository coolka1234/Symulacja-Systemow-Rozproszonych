public class Main {
    public static void main(String[] args) {
        /*
        Założenia symulacji:
        Obciazenia losowo 1-70
        Czasy przybycia rozne od 1-8
        Czas przetwarzania 1-9
        w strategi 2 5 zapytan (tzn do skutku) jeden tik czasowy procesora
        Obciazenia zaokraglamy do 2 miejsc, odchylenia do 6 w gore
        Przejmowanie procesu jednego
         */
        System.out.println("Symulacja 1:###########################################");
        Symulacja sym1=new Symulacja(5,60,10,2,10, 30);
        System.out.println("Symulacja 2:###########################################");
        Symulacja sym2=new Symulacja(10,40,30,3,30,70);
        System.out.println("Symulacja 3:###########################################");
        Symulacja sym3=new Symulacja(40, 70,40,5,100,90);
    }
}