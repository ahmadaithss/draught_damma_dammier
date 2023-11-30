package damme.jeu.com.jeudamme.models;

public class PositionValue {
    private PositionValue originePosition;
    private int x;
    private int y;
    private int value;

    public PositionValue(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public void setOriginePosition(PositionValue originePosition) {
        this.originePosition = originePosition;
    }

    public PositionValue getOriginePosition() {
        return originePosition;
    }

    @Override
    public String toString() {
        return "("+this.x+","+this.y+")="+this.getValue();
    }
}
