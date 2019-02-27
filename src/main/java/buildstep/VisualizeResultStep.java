package buildstep;

public class VisualizeResultStep implements BuildStep {

    public VisualizeResultStep() {
    }

    @Override
    public void makeStep() {
        System.out.println("Visualization");
    }
}
