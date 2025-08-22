package org.factoriaf5.project_inside_out.controllers;

import org.factoriaf5.project_inside_out.models.Emotion;
import org.factoriaf5.project_inside_out.models.Moment;
import org.factoriaf5.project_inside_out.models.MomentDTO;
import org.factoriaf5.project_inside_out.services.MomentService;
import org.factoriaf5.project_inside_out.views.ConsoleMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

class MomentControllerTest {

    private ConsoleMenu menu;
    private MomentService service;
    private MomentController controller;

    @BeforeEach
    void setUp() {
        menu = mock(ConsoleMenu.class);
        service = mock(MomentService.class);
        controller = new MomentController(menu, service);
    }

    @Test
    void run_ShouldExitOnOption6() {
        when(menu.readInt())
                .thenReturn(6); // primera lectura -> opción salir

        controller.run();

        verify(menu).showMomentMenu();
    }

    @Test
    void addMoment_ShouldSaveMomentAndPrintMessage() {
        when(menu.readInt("Seleccione una emoción: ")).thenReturn(1);
        when(menu.readInt("¿Es un buen momento (1) o un mal momento (2)?: ")).thenReturn(1);
        when(menu.readLine("Ingrese el título: ")).thenReturn("Viaje");
        when(menu.readLine("Ingrese la descripción: ")).thenReturn("Playa");
        when(menu.readLine("Ingrese la fecha (dd/mm/yyyy): ")).thenReturn("01/01/2024");

        // Se asegura que la primera llamada a readInt() en el run sea opción 1 (addMoment)
        when(menu.readInt())
                .thenReturn(1) // opción "añadir momento"
                .thenReturn(6); // luego salir del bucle

        controller.run();

        verify(service).addMoment(any(MomentDTO.class));
        verify(menu).printMessage("Momento añadido correctamente.");
    }

    @Test
    void showAllMoments_ShouldPrintAll() {
        when(menu.readInt())
                .thenReturn(2) // opción mostrar todos
                .thenReturn(6); // salir
        when(service.getAllMoments()).thenReturn(List.of(new Moment("Test", "Desc", Emotion.ALEGRIA, LocalDate.now(), true)));

        controller.run();

        verify(menu).printMoments(anyList());
    }

    @Test
    void deleteMoment_ShouldCallServiceDelete() {
        when(menu.readInt())
                .thenReturn(3) // opción eliminar
                .thenReturn(6); // salir
        when(menu.readInt("Ingresa el ID del momento: ")).thenReturn(42);

        controller.run();

        verify(service).deleteMoment(42);
        verify(menu).printMessage("Momento eliminado correctamente.");
    }

    @Test
    void filterByEmotion_ShouldCallServiceFilter() {
        when(menu.readInt())
                .thenReturn(4) // opción filtrar
                .thenReturn(6); // salir
        when(menu.readInt()).thenReturn(1); // submenú -> filtro por emoción
        when(menu.readInt("Seleccione una emoción: ")).thenReturn(2); // emoción tristeza válida

        controller.run();

        verify(service).filterByEmotion(Emotion.fromOption(2));
        verify(menu).printMoments(anyList());
    }

    @Test
    void filterByDate_ShouldCallServiceFilter() {
        when(menu.readInt())
                .thenReturn(4) // opción filtrar
                .thenReturn(6); // salir
        when(menu.readInt()).thenReturn(2); // submenú -> filtro por fecha
        when(menu.readLine("Ingrese la fecha (dd/mm/yyyy): ")).thenReturn("01/01/2025");

        controller.run();

        verify(service).filterByDate(any(LocalDate.class));
        verify(menu).printMoments(anyList());
    }

    @Test
    void filterByType_ShouldCallServiceFilter() {
        when(menu.readInt())
                .thenReturn(4) // opción filtrar
                .thenReturn(6); // salir
        when(menu.readInt()).thenReturn(3); // submenú -> filtro por tipo
        when(menu.readInt("¿Es un buen momento (1) o un mal momento (2)?: ")).thenReturn(1);

        controller.run();

        verify(service).filterByType(true);
        verify(menu).printMoments(anyList());
    }

    @Test
    void exportMoments_ShouldCallServiceExport() throws Exception {
        when(menu.readInt())
                .thenReturn(5) // opción exportar
                .thenReturn(6); // salir
        when(menu.readLine("Nombre del archivo CSV: ")).thenReturn("testfile.csv");

        controller.run();

        verify(service).exportToCSV("testfile.csv");
        verify(menu).printMessage("Exportado correctamente en: exports/testfile.csv");
    }

    @Test
    void invalidOption_ShouldShowError() {
        when(menu.readInt())
                .thenReturn(99) // opción inválida
                .thenReturn(6); // salir

        controller.run();

        verify(menu).printError("Opción inválida.");
    }
}
