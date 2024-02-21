import java.util.ArrayList;
import java.util.List;

public class CPU {
    List<Proces> procesList=new ArrayList<>();
    int load;
    int number;
    double averageLoad;
    double averageLoadVariance;
    int timeUsed;
    int totalLoad;
    List<Integer> loadForVariance=new ArrayList<>();
    public int numOfAsked;
    public int numOfMigrated;

    public int getTotalLoad() {
        return totalLoad;
    }

    public void setTotalLoad(int totalLoad) {
        this.totalLoad = totalLoad;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public CPU(List<Proces> procesList, int load, int number, double averageLoad, double averageLoadVariance) {
        this.procesList = new ArrayList<>();
        this.load = load;
        this.number = number;
        this.averageLoad = averageLoad;
        this.averageLoadVariance = averageLoadVariance;
        this.numOfAsked=0;
        this.numOfMigrated=0;
    }
    public CPU()
    {
        this.procesList = null;
        this.load = 0;
        this.number = 0;
        this.averageLoad = 0;
        this.averageLoadVariance = 0;
        this.numOfAsked=0;
        this.numOfMigrated=0;
    }
    public void sentProces(CPU otherCpu, Proces proces) //wyslanie procesow do innego procesora, nie sprawdza czy moze
    {
        otherCpu.procesList.add(0,proces);
    }
    public void doProcesses() //wykonywanie wszystkich procesow
    {
        if(procesList!=null) {
            for (Proces p :
                    procesList) {
                p.subtractTime(1);
                totalLoad+= p.processPower;
                loadForVariance.add(p.processPower);
            }
            markProceses();
        }
        timeUsed++;
    }
    public void markProceses()
    {
        for (int i = 0; i < procesList.size(); i++) {
            if(procesList.get(i).time<=0)
            {
                load=Math.max(0,load-procesList.get(i).processPower);
                procesList.remove(i);
                i--;
            }
        }
    }

    public List<Proces> getProcesList() {
        return procesList;
    }

    public void setProcesList(List<Proces> procesList) {
        this.procesList = procesList;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getAverageLoad() {
        return averageLoad;
    }

    public void setAverageLoad(double averageLoad) {
        this.averageLoad = averageLoad;
    }

    public double getAverageLoadVariance() {
        return averageLoadVariance;
    }

    public void setAverageLoadVariance(double averageLoadVariance) {
        this.averageLoadVariance = averageLoadVariance;
    }
    public void addTotalLoad(int toAdd)
    {
        this.totalLoad+=toAdd;
    }
    public void averageLoad()
    {
        this.averageLoad=(double)totalLoad/(double)this.timeUsed;
    }
    public void averageLoadVariance()
    {
        for (int procLoad:
             loadForVariance) {
            averageLoadVariance+=Math.abs(procLoad-averageLoad);
        }
        averageLoadVariance=averageLoad/timeUsed;
    }
}
