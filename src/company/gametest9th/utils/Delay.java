package company.gametest9th.utils;

public class Delay {
    private int count;
    private int countLimit;
    private boolean isPause;
    private boolean isLoop;

    public Delay(int countLimit) {
        this.countLimit = countLimit;
        count = 0;
        isPause = true;
        isLoop = false;
    }

    //將第一次攻擊無Delay
    public void firstNoDelay(){
        count=countLimit-1;
    }

    public void resetDelay(){
        count=0;
    }

    public void setLimit(int limit) {
        this.countLimit = limit;
    }

    public void stop() {
        isPause = true;
        count = 0;
    }

    public void play() {
        isPause = false;
        isLoop = false;
    }

    public void loop() {
        isPause = false;
        isLoop = true;
    }

    public boolean count() {
        if (isPause) {
            return false;
        }
        if (count >= countLimit) {
            if (isLoop) {
                count = 0;
            }
            else {
                stop();
            }
            return true;
        }

        count++;
        return false;
    }
}
