package instrumentos.presentation.tipos;

import instrumentos.Application;
import instrumentos.logic.TipoInstrumento;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JPanel panel;
    private JTextField searchNombre;
    private JButton search;
    private JButton save;
    private JTable list;
    private JButton delete;
    private JLabel searchNombreLbl;
    private JButton report;
    private JTextField codigo;
    private JTextField nombre;
    private JTextField unidad;

    public JTextField getCodigo() {
        return codigo;
    }

    public JButton getDelete() {
        return delete;
    }

    public void setCodigoText(String codigo) {
        this.codigo.setText(codigo);
    }
    public void setNombreText(String nombre) {
        this.nombre.setText(nombre);
    }
    public void setUnidadText(String unidad) {
        this.unidad.setText(unidad);
    }

    private JLabel codigoLbl;
    private JLabel nombreLbl;
    private JLabel unidadLbl;
    private JButton clear;

    public View() {
        delete.setEnabled(false);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TipoInstrumento filter= new TipoInstrumento();
                    filter.setNombre(searchNombre.getText());
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow();
                controller.edit(row);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!model.getCurrent().equals(new TipoInstrumento()) ) {
                    controller.delete();
                    controller.clear();
                }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isValid()){
                    if(model.getMode() == Application.MODE_CREATE){
                        controller.create(take());
                    }
                    else{
                        controller.update(take());
                    }
                }
                else{
                    JOptionPane.showMessageDialog(panel,"Favor llenar todos los campos.","Información",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable updatedModel, Object properties) {
        int changedProps = (int) properties;
        if ((changedProps & Model.LIST) == Model.LIST) {
            int[] cols = {TableModel.CODIGO, TableModel.NOMBRE, TableModel.UNIDAD};
            list.setModel(new TableModel(cols, model.getList()));
            list.setRowHeight(30);
            TableColumnModel columnModel = list.getColumnModel();
            columnModel.getColumn(2).setPreferredWidth(200);
        }
        if ((changedProps & Model.CURRENT) == Model.CURRENT) {
            codigo.setText(model.getCurrent().getCodigo());
            nombre.setText(model.getCurrent().getNombre());
            unidad.setText(model.getCurrent().getUnidad());
        }
        if(model.getMode() == Application.MODE_EDIT){
            codigo.setEnabled(false);
            delete.setEnabled(true);
        }
        else{
            codigo.setEnabled(true);
            delete.setEnabled(false);
        }
        this.panel.revalidate();
    }

    boolean isValid(){
        boolean valid = true;
        if(codigo.getText().isEmpty()){
            valid = false;
            codigoLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
            codigo.setToolTipText("codigo requerido");
        }
        else{
            codigoLbl.setBorder(BorderFactory.createMatteBorder(0,0,0,0, Color.RED));
            codigo.setToolTipText(null);
        }
        if(nombre.getText().isEmpty()){
            valid = false;
            nombreLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
            nombre.setToolTipText("codigo requerido");
        }
        else{
            nombreLbl.setBorder(BorderFactory.createMatteBorder(0,0,0,0, Color.RED));
            nombre.setToolTipText(null);
        }
        if(unidad.getText().isEmpty()){
            valid = false;
            unidadLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
            unidad.setToolTipText("codigo requerido");
        }
        else{
            unidadLbl.setBorder(BorderFactory.createMatteBorder(0,0,0,0, Color.RED));
            unidad.setToolTipText(null);
        }/*
        try{
            Integer.parseInt(amount.getText());
            amount.setBackground(Color.white);
            amount.setToolTipText(null);
        }catch(Exception e){
            amount.setBackground(Color.red);
            amount.setToolTipText("monto requerido");
        }
        if(typeGroup.getSelection()==null){
            radioButton1.setBackground(Color.red);
            radioButton2.setBackground(Color.red);
            radioButton1.setToolTipText("Tipo requerido");
            radioButton2.setToolTipText("Tipo requerido");
            valid = false;
        }
        else{
            radioButton1.setBackground(Color.white);
            radioButton1.setToolTipText(null);
            radioButton2.setBackground(Color.white);
            radioButton2.setToolTipText(null);
        }*/
        return valid;
    }
    public TipoInstrumento take(){
        TipoInstrumento intrumento = new TipoInstrumento();
        intrumento.setCodigo(codigo.getText());
        intrumento.setNombre(nombre.getText());
        intrumento.setUnidad(unidad.getText());
        return intrumento;
    }
}
