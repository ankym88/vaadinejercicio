package com.umg.app;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;


@Theme("mytheme")
public class MyUI extends UI {

    private Registro service = Registro.getInstance();
    private Grid<Docente> grid = new Grid<>(Docente.class);
    private TextField filterText = new TextField();
    private Formulario form = new Formulario(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {


        VerticalLayout content = new VerticalLayout();
        setContent(content);

        HorizontalLayout titleBar = new HorizontalLayout();
        titleBar.setWidth("100%");


        Label title = new Label("SISTEMA DE REGISTRO DE DOCENTES");
        titleBar.addComponent(title);
        titleBar.setExpandRatio(title, 1.0f); // Expand

        Label titleComment = new Label("Franky Mejia");
        titleComment.setSizeUndefined(); // Take minimum space
        titleBar.addComponent(titleComment);



        final VerticalLayout layoutt = new VerticalLayout();

         layoutt.addComponents(title, titleComment);





        final VerticalLayout layout = new VerticalLayout();

        final TextField name = new TextField();
        name.setCaption("Type your name here:");



        Button addCustomerBtn = new Button("Agregar Nuevo");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Docente());
        });

        HorizontalLayout toolbarr = new HorizontalLayout(title, titleComment);

        HorizontalLayout toolbar = new HorizontalLayout(addCustomerBtn);

        grid.setColumns("nombre", "apellido", "email","cursos","nacimiento");

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);

        layout.addComponents(toolbarr,toolbar, main);

        // fetch list of Customers from service and assign it to Grid
        updateList();

        setContent(layout);

        form.setVisible(false);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                form.setVisible(false);
            } else {
                form.setCustomer(event.getValue());
            }
        });
    }

    public void updateList() {
        List<Docente> customers = service.findAll(filterText.getValue());
        grid.setItems(customers);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
