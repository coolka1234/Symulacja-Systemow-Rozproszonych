import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Symulacja {
    int N; //liczba CPU
    int p; //prog obciazenia, pytaj o wysylanie jak przekraczasz
    int r; //pyta czy mu nie ulzyc jak ma wiecej niz p
    int z; //ile razy pytac
    List<List<Proces>> listForAllCPUs=new ArrayList<>();
    List<CPU> cpuList=new ArrayList<>();
    final int maxTime;

    public Symulacja(int n, int p, int r, int z, int howManyToGen, int range) {
        N = n;
        this.p = p;
        this.r = r;
        this.z = z;
        generateList(howManyToGen, range);
        this.maxTime=findMaxTime();
        symulacja();
    }
    public void generateList(int howMany,int range)
    {
        Generator.generateCPUs(N);
        cpuList=Generator.listOfCPU;
        for (int i = 0; i < N; i++) {
            Generator.generateProceses(howMany,range);
            listForAllCPUs.add(Generator.procesList);

        }
    }
    public int findMaxTime()
    {
        int max=0;
        for (List<Proces> forAllCPUs : listForAllCPUs) {
            if (forAllCPUs.get(forAllCPUs.size() - 1).arriveTime + forAllCPUs.get(forAllCPUs.size() - 1).time > max) {
                max = forAllCPUs.get(forAllCPUs.size() - 1).arriveTime + forAllCPUs.get(forAllCPUs.size() - 1).time;
            }
        }
        return max;
    }

    public void strategia1()
    {
        int currentTime=0;
        List<List<Proces>> copyList = new ArrayList<>(listForAllCPUs.size());
        copyList(copyList,listForAllCPUs);
        boolean ifSent=false;
        Random random=new Random();
        while (allProcDone(copyList))
        {
            //dodajemy kazdemu procesowi proces jesli juz jest
            for (int i = 0; i < N; i++) {
                if(copyList.get(i).size()==0)
                {
                    continue;
                }
                for (int k = 0; k < copyList.get(i).size(); k++) {
                    if(copyList.get(i).get(k).arriveTime==currentTime)
                    {
                        for (int j = 0; j < z; j++) {
                            int chosenNum=random.nextInt(1,N+1); //losowyProces
                            cpuList.get(i).numOfAsked++;
                            if(cpuList.get(chosenNum-1).load<p)
                            {
                                addProces(chosenNum-1, copyList.get(i),k);
                                k--;
                                cpuList.get(chosenNum-1).numOfMigrated++;
                                ifSent=true;
                                break;
                            }
                        }
                        if(!ifSent) //jak nie znalezlismy takiego to sam robi
                        {
                            addProces(i, copyList.get(i),k);
                            k--;
                        }
                        ifSent=false;
                        //cpuList.get(i).procesList.add(listForAllCPUs.get(i).get(0));
                    }
                }
                cpuList.get(i).doProcesses(); //przerabiamy procesy
            }
            currentTime++;

        }
    }
    public void strategia2()
    {
        int currentTime=0;
        int doProcessesChecker=0;
        List<List<Proces>> copyList = new ArrayList<>(listForAllCPUs.size());
        copyList(copyList,listForAllCPUs);
        boolean ifSent=false;
        Random r=new Random();
        while (allProcDone(copyList))
        {
            //dodajemy kazdemu procesowi proces jesli juz jest
            for (int i = 0; i < N; i++) {
                if(copyList.get(i).size()==0)
                {
                    continue;
                }
                for (int k = 0; k < copyList.get(i).size(); k++) {
                    if(copyList.get(i).get(k).arriveTime==currentTime)
                    {
                        if(cpuList.get(i).load>p)
                        {
                            while(!ifSent)
                            {
                                int chosenNum=r.nextInt(0,N); //losowyProces
                                cpuList.get(i).numOfAsked++;
                                doProcessesChecker++;
                                if(cpuList.get(chosenNum).load<p)
                                {
                                    addProces(chosenNum, copyList.get(i),k);
                                    k--;
                                    cpuList.get(chosenNum).numOfMigrated++;
                                    ifSent=true;
                                } else if (doProcessesChecker%5==0) {
                                    for (CPU cpu : cpuList) {
                                        cpu.doProcesses();
                                    }
                                }
                            }
                        }
                        else
                        {
                            addProces(i, copyList.get(i),k);
                            k--;
                        }
                        ifSent=false;
                    }
                }
                cpuList.get(i).doProcesses(); //przerabiamy procesy
                doProcessesChecker=0;
            }
            currentTime++;

        }
    }
    public void strategia3()
    {
        int currentTime=0;
        int doProcessesChecker=0;
        List<List<Proces>> copyList = new ArrayList<>(listForAllCPUs.size());
        copyList(copyList,listForAllCPUs);
        boolean ifSent=false;
        Random random=new Random();
        while (allProcDone(copyList))
        {
            //dodajemy kazdemu procesowi proces jesli juz jest
            for (int i = 0; i < N; i++) {
                if(copyList.get(i).size()==0)
                {
                    continue;
                }
                for (int k = 0; k < copyList.get(i).size(); k++) {
                    if(copyList.get(i).get(k).arriveTime==currentTime)
                    {
                        if(cpuList.get(i).load>p)
                        {
                            while(!ifSent)
                            {
                                int chosenNum=random.nextInt(0,N); //losowyProces
                                cpuList.get(i).numOfAsked++;
                                doProcessesChecker++;
                                if(cpuList.get(chosenNum).load<p)
                                {
                                    addProces(chosenNum, copyList.get(i),k);
                                    k--;
                                    cpuList.get(chosenNum).numOfMigrated++;
                                    ifSent=true;
                                    //teraz niech zapyta inny procesor
                                    if(cpuList.get(chosenNum).load<r)
                                    {
                                        int chosenNum2=random.nextInt(0,N); //losowyProces
                                        while (chosenNum!=chosenNum2) //zeby siebe nie wylosowal
                                        {
                                            chosenNum2= random.nextInt(0,N);
                                        }
                                        cpuList.get(chosenNum).numOfAsked++;
                                        if(cpuList.get(chosenNum2).load>p)
                                        {
                                            if(cpuList.get(chosenNum2).procesList.size()>0)
                                            {
                                                addProces(chosenNum,copyList.get(chosenNum2),k);
                                                cpuList.get(chosenNum).numOfMigrated++;
                                                k--;
                                            }
                                        }

                                    }
                                } else if (doProcessesChecker%5==0) {
                                    for (CPU cpu : cpuList) {
                                        cpu.doProcesses();
                                    }
                                }
                            }
                        }
                        else
                        {
                            addProces(i, copyList.get(i),k);
                            k--;
                        }
                        ifSent=false;
                    }
                }
                cpuList.get(i).doProcesses(); //przerabiamy procesy
                doProcessesChecker=0;
            }
            currentTime++;

        }
    }
    public boolean allProcDone(List<List<Proces>> list)
    {
        for (List<Proces> proces : list) {
            if (proces.size() != 0) {
                return true;
            }
        }
        return false;
    }

    private void addProces(int i, List<Proces> proces,int j) {
        cpuList.get(i).procesList.add(proces.get(j));
        cpuList.get(i).load+= proces.get(j).processPower;
        proces.remove(j);
    }
    public void copyList(List<List<Proces>> dest,List<List<Proces>> orig)
    {
        for (List<Proces> proces : orig) {
            List<Proces> nowaLista = new ArrayList<>();
            for (Proces proce : proces) {
                nowaLista.add(Proces.copy(proce));
            }
            dest.add(nowaLista);
        }
    }
    public void resetCPUs()
    {
        for (int i = 0; i < N; i++) {
            cpuList.get(i).numOfMigrated=0;
            cpuList.get(i).procesList=new ArrayList<>();
            cpuList.get(i).load=0;
            cpuList.get(i).numOfAsked=0;
            cpuList.get(i).averageLoad=0;
            cpuList.get(i).averageLoadVariance=0;
            cpuList.get(i).numOfAsked=0;

        }
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void symulacja()
    {
        System.out.println("Strategia 1");
        strategia1();
        int roundingPlaces = 2;
        for (int i = 0; i < N; i++) {
            cpuList.get(i).averageLoad();
            cpuList.get(i).averageLoadVariance();
            System.out.println("Procesor nr: "+(i+1)+" miał średnie obciążenie na poziomie "+round(cpuList.get(i).averageLoad, roundingPlaces)+" o średnim odchyleniu ±"+round(cpuList.get(i).averageLoadVariance,5)+" zapytal o migracje: "+cpuList.get(i).numOfAsked+" i otrzymal nieswoich : "+cpuList.get(i).numOfMigrated);
        }
        resetCPUs();
        System.out.println("Strategia 2");
        strategia2();
        for (int i = 0; i < N; i++) {
            cpuList.get(i).averageLoad();
            cpuList.get(i).averageLoadVariance();
            System.out.println("Procesor nr: "+(i+1)+" miał średnie obciążenie na poziomie "+round(cpuList.get(i).averageLoad, roundingPlaces)+" o średnim odchyleniu ±"+round(cpuList.get(i).averageLoadVariance,5)+" zapytal o migracje: "+cpuList.get(i).numOfAsked+" i otrzymal nieswoich : "+cpuList.get(i).numOfMigrated);
        }
        resetCPUs();
        System.out.println("Strategia 3");
        strategia3();
        for (int i = 0; i < N; i++) {
            cpuList.get(i).averageLoad();
            cpuList.get(i).averageLoadVariance();
            System.out.println("Procesor nr: "+(i+1)+" miał średnie obciążenie na poziomie "+round(cpuList.get(i).averageLoad, roundingPlaces)+" o średnim odchyleniu ±"+round(cpuList.get(i).averageLoadVariance,5)+" zapytal o migracje: "+cpuList.get(i).numOfAsked+" i otrzymal nieswoich : "+cpuList.get(i).numOfMigrated);
        }
        resetCPUs();

    }
}
