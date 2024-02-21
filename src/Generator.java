import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    static List<Proces> procesList=new ArrayList<>();
    static List<CPU> listOfCPU=new ArrayList<>();
    public static void generateProceses(int howMany, int range)
    {
        procesList=new ArrayList<>();
        int arriveTime = 0;
        Random r=new Random();
        for (int i = 0; i < howMany; i++)
        {
            arriveTime=arriveTime+r.nextInt(1,8);
            procesList.add(new Proces(arriveTime,r.nextInt(1,range),r.nextInt(1,9)));
        }
    }
    public static void generateCPUs(int N)
    {

        for (int i = 1; i <= N; i++) {
            listOfCPU.add(new CPU(null,0,i,0,0));
        }
    }
}
