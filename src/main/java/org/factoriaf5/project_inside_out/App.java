package org.factoriaf5.project_inside_out;

import org.factoriaf5.project_inside_out.controllers.MomentController;
import org.factoriaf5.project_inside_out.repositories.InMemoryMomentRepository;
import org.factoriaf5.project_inside_out.repositories.MomentRepository;
import org.factoriaf5.project_inside_out.services.MomentService;
import org.factoriaf5.project_inside_out.views.ConsoleMenu;

public class App {
    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        MomentRepository repository = new InMemoryMomentRepository();
        MomentService service = new MomentService(repository);
        MomentController controller = new MomentController(menu, service);
        
        controller.run();
    }
}