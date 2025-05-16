package client;

import domain.AgeGroup;
import domain.Trial;

public class Main {
    private static final TrialClient client = new TrialClient();

    public static void main(String[] args) {
        Trial t = new Trial(null, "Obstacle Run", AgeGroup.NINEELEVEN);
        try {
            System.out.println("Adding a new trial " + t);
            Trial tt = client.create(t);
            t.setId(tt.getId());
            System.out.println("Added trial " + t + " (id: " + t.getId() + ")");

            System.out.println("\nPrinting all trials ...");
            for (Trial trial : client.get())
                System.out.println(trial + " (id: " + trial.getId() + ")");

            System.out.println("\nInfo for trial");
            final Long id = t.getId();
            System.out.println(client.getById(id));

            t.setName("Updated Trial");
            client.modify(t);
            System.out.println(client.getById(id));

            System.out.println("\nDeleting trial with id=" + t.getId());
            client.delete(id);

            System.out.println("\nPrinting all trials ...");
                for (Trial trial : client.get())
                    System.out.println(trial + " (id: " + trial.getId() + ")");
        } catch (Exception ex) {
            System.out.println("Exception ... " + ex.getMessage());
        }
    }
}
