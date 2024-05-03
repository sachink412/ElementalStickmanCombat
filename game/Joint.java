package game;

public class Joint {

    protected Part Part0 = null;
    protected Part Part1 = null;
    protected TFrame C0 = null;

    public Joint() {
        C0 = new TFrame();
    }

    public void setPart0(Part part) {
        this.Part0 = part;
    }

    public void setPart1(Part part) {
        this.Part1 = part;
    }
}