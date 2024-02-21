public class Proces {
    int arriveTime;
    int processPower;
    int time;

    public int getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(int arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getProcessPower() {
        return processPower;
    }

    public void setProcessPower(int processPower) {
        this.processPower = processPower;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public void subtractTime(int timeToSub)
    {
        this.time = Math.max(this.time - timeToSub, 0);
    }

    public Proces(int frequency, int processPower, int time) {
        this.arriveTime = frequency;
        this.processPower = processPower;
        this.time = time;
    }
    public static Proces copy(Proces procToCopy)
    {
        Proces copiedProc = new Proces(0,0,0);
        copiedProc.time=procToCopy.time;
        copiedProc.arriveTime=procToCopy.arriveTime;
        copiedProc.processPower=procToCopy.processPower;
        return copiedProc;
    }
}
