package com.umg.app;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class Formulario extends FormLayout {

    private TextArea title = new TextArea("Agregar Docente");
    private TextField nombre = new TextField("Nombre");
    private TextField apellido = new TextField("Apellido");
    private TextField email = new TextField("Email");
    private NativeSelect<Curso> cursos = new NativeSelect<>("curso");
    private DateField birthdate = new DateField("Fecha Nacimiento");
    private DateField nacimiento = new DateField("Nacimiento");
    private Button save = new Button("Guardar");
    private Button delete = new Button("Borrar");

    private Registro service = Registro.getInstance();
    private Docente customer;
    private MyUI myUI;
    private Binder<Docente> binder = new Binder<>(Docente.class);

    public Formulario(MyUI myUI) {

        VerticalLayout content = new VerticalLayout();


        HorizontalLayout titleBar = new HorizontalLayout();
        titleBar.setWidth("100%");


        Label title = new Label("Formulario Docente");
        titleBar.addComponent(title);
        titleBar.setExpandRatio(title, 1.0f); // Expand

        this.myUI = myUI;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(title,nombre, apellido, email,cursos,nacimiento, buttons);


        cursos.setItems(Curso.values());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(KeyCode.ENTER);

        binder.bindInstanceFields(this);

        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
    }

    public void setCustomer(Docente customer) {
        this.customer = customer;
        binder.setBean(customer);

        // Show delete button for only customers already in the database
        delete.setVisible(customer.isPersisted());
        setVisible(true);
        nombre.selectAll();
    }

    private void delete() {
        service.delete(customer);
        myUI.updateList();
        setVisible(false);
    }

    private void save() {
        service.save(customer);
        myUI.updateList();
        setVisible(false);
    }

}
