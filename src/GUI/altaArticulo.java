/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BD.Conexion;
import Clases.Articulo;
import Clases.Cotizacion;
import Clases.IVA;
import Clases.Proveedor;
import Clases.controladorBasura;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
/**
 *
 * @author joaco
 */
public class altaArticulo extends javax.swing.JFrame {
    private boolean modificar;
    private boolean EstaEnLaLista = false;
    private Articulo a;
    private JTable tabla;                   //por parametro
    private JList listaEnModificar;         //por parametro
    private boolean mostrarDeshabilitados;  //por parametro
    List<Articulo> articulosDeshabilitados; // por parametro
    /**
     * Creates new form altaProducto
     */
    public altaArticulo() {
        initComponents();
        modificar = false;
        setTitle("Alta Articulo");
        jButton1.setText("Crear");
        
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        this.jList1.setModel(listModel);

        List<IVA> ivas = Conexion.getInstance().getIVAS();
        for (IVA iva : ivas) {
            jComboBox1.addItem(iva);
        }

        AutoCompleteDecorator.decorate(this.jComboBox2);
        List<Proveedor> LProv = Conexion.getInstance().listadoProveedores();

        for (Proveedor p : LProv) {
            if(!p.isDeshabilitado())
            this.jComboBox2.addItem(p);
        }
        
        this.jLabel8.setVisible(false);
        this.jCheckBox1.setVisible(false);
        this.jCheckBox2.setVisible(false);


    }

    altaArticulo(Articulo a, JTable jTable1, JList listaEnModificar,boolean mostrarDeshab,List<Articulo> articulosDesha) {
        initComponents();
        this.mostrarDeshabilitados = mostrarDeshab;
        this.articulosDeshabilitados = articulosDesha;
        setTitle("Modificar Artículo");
        modificar = true;
        jButton1.setText("Modificar");
        this.a = a;
        this.tabla = jTable1;
        this.listaEnModificar = listaEnModificar;
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        buttonGroup1.add(jCheckBox1);
        buttonGroup1.add(jCheckBox2);
        
        //Seteo los atributos del artículo seleccionado en la tabla a sus correspondientes textfield.
        jTextField1.setText(a.getCodigo());
        jTextField1.setEnabled(false);
        jTextField1.setEditable(false);
        jTextField2.setText(a.getNombre());
        jTextArea1.setText(a.getDescripcion());
        if(a.isDeshabilitado()){
            jCheckBox1.setSelected(true);
        }else{
            jCheckBox2.setSelected(true);
        }
        
        AutoCompleteDecorator.decorate(this.jComboBox2);
        List<Proveedor> LProv = Conexion.getInstance().listadoProveedores();

        for (Proveedor p : LProv) {
            if (!p.isDeshabilitado()) {
                this.jComboBox2.addItem(p);
                for (Proveedor prov : a.getProveedores()) {
                    if (p.toString().equals(prov.toString())) {
                        this.jComboBox2.setSelectedItem(p);
                        //Relleno la lista con los proveedores que tiene el artículo actualmente
                        listModel.addElement(prov);
                    }
                }
            }

        }

        this.jList1.setModel(listModel);

        List<IVA> ivas = Conexion.getInstance().getIVAS();
        for (IVA iva : ivas) {
            jComboBox1.addItem(iva);
            if (iva.toString().equals(a.getIva().toString())) {
                this.jComboBox1.setSelectedItem(iva);
            }
        }
        
    }

    altaArticulo(Cotizacion c, JTable jTable1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Alta de producto");

        jLabel1.setText("Codigo:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Descripcion:");

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cerrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("IVA:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel5.setText("Proveedor:");

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jComboBox2.setEditable(true);
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jList1);

        jLabel7.setText("Proveedores: ");

        jButton3.setText("Agregar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Quitar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel8.setText("Deshabilitado:");

        jCheckBox1.setText("Si");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("No");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(356, 356, 356)
                        .addComponent(jLabel6)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel1))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4))
                            .addComponent(jLabel7)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(34, 34, 34)
                                                    .addComponent(jLabel8))
                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jCheckBox2)
                                                .addComponent(jCheckBox1)))))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel4))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jButton3))))
                                .addComponent(jSeparator4)))
                        .addGap(0, 13, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jCheckBox1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        IVA iva = (IVA) jComboBox1.getSelectedItem();
        Date fechaHoy = new Date();
        Date fechaRegirIVA = iva.getFechaRegir();

        if (modificar == false) {
            //ALTA ARTICULO.
            if (jTextField1.getText().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Debe ingresar el código del artículo.");
            } else if (jTextField2.getText().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del artículo.");
            } else if (jTextArea1.getText().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Debe ingresar el descripción del artículo.");
            } else if(fechaHoy.before(fechaRegirIVA)){
                javax.swing.JOptionPane.showMessageDialog(null, "Ha seleccionado un IVA que todavía no empezó a regir. Vuelva a intentarlo con otro IVA.");
            }else if (jComboBox2.getSelectedIndex() == -1) {
                javax.swing.JOptionPane.showMessageDialog(null, "Debe seleccionar el proveedor del artículo.");
            } else {
                boolean existeA = Conexion.getInstance().existeArticulo(jTextField1.getText());
                if (existeA) {
                    javax.swing.JOptionPane.showMessageDialog(null, "El artículo ya existe.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                } else {                   
                    Articulo art = new Articulo();
                    DefaultListModel listModel = (DefaultListModel) this.jList1.getModel();
                    List<Proveedor> provs = new ArrayList<>();
                    //Agrego los proveedores de la lista a un array de proveedores
                    for(int i = 0; i < listModel.getSize(); i++){
                        Proveedor p = (Proveedor)listModel.get(i);
                        p.getArticulos().add(a);
                        provs.add(p);                       
                    }
                    //Datos del artículo a crear//
                    art.setCodigo(jTextField1.getText());
                    art.setNombre(jTextField2.getText());
                    art.setDescripcion(jTextArea1.getText());
                    art.setIva(iva);
                    art.setProveedores(provs);
                    art.setUsuario(controladorBasura.getU());
                    boolean e = Conexion.getInstance().persist(art);
                    if (e) {
                        javax.swing.JOptionPane.showMessageDialog(null, "El artículo fue dado de alta exitosamente.", "Enhorabuena", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        this.jTextField1.setText("");
                        this.jTextField2.setText("");
                        this.jTextArea1.setText("");
                        this.jList1.clearSelection();
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Ha ocurrido un problema.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            //MODIFICAR ARTICULO.
            if (jTextField2.getText().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del artículo.");
            } else if (jTextArea1.getText().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Debe ingresar el descripción del artículo.");
            } else {
                //Datos del artículo a modificar----------------//
                a.setNombre(jTextField2.getText());
                a.setDescripcion(jTextArea1.getText());
                a.setIva((IVA) jComboBox1.getSelectedItem());
                a.setUsuario(controladorBasura.getU());
                if (this.jCheckBox1.isSelected()) {
                    a.setDeshabilitado(true);
                    
                } else {
                    a.setDeshabilitado(false);
                }
                
              
                //---------------------------------------------//

                //Agrego los proveedores de la lista a un array de proveedores y los seteo//
                DefaultListModel listModel = (DefaultListModel) this.jList1.getModel();
                List<Proveedor> provs = new ArrayList<>();

                for (int i = 0; i < listModel.getSize(); i++) {
                    Proveedor p = (Proveedor) listModel.get(i);
                    //Agrego el artículo a la lista de artículos del proveedor//
                    p.getArticulos().add(a);
                    provs.add(p);
                }
                a.setProveedores(provs);
                //-----------------------------------------------------------------------//

                Conexion.getInstance().merge(a);
                javax.swing.JOptionPane.showMessageDialog(null, "El artículo fue modificado exitosamente.", "Enhorabuena", javax.swing.JOptionPane.INFORMATION_MESSAGE);

//                //Actualizo los datos de la tabla----------------------------//
//                int fila = tabla.getSelectedRow();
//                DefaultTableModel m = (DefaultTableModel) tabla.getModel();
//                tabla.getModel().setValueAt(a.getNombre(), fila, 1);
//                tabla.getModel().setValueAt(a.getDescripcion(), fila, 2);
//                tabla.getModel().setValueAt(a.getIva().toString(), fila, 3);
//                tabla.repaint();
//                //-----------------------------------------------------------//
//
//                //Actualizo los datos de la lista--------------------------------------------//
//                DefaultListModel listModel2 = (DefaultListModel) listaEnModificar.getModel();
//                listModel2.removeAllElements();
//                listaEnModificar.setModel(listModel);
//                //---------------------------------------------------------------------------//

                DefaultTableModel dm = (DefaultTableModel) tabla.getModel();
                dm.getDataVector().removeAllElements();
                dm.fireTableDataChanged();

                Iterator<Articulo> it = Conexion.getInstance().listadoArticulos().iterator();
                if (mostrarDeshabilitados) {
                    while (it.hasNext()) {
                        Articulo next = it.next();
                        Object[] fila2 = new Object[5];
                        fila2[0] = next.getCodigo();
                        fila2[1] = next.getNombre();
                        fila2[2] = next.getDescripcion();
                        fila2[3] = next.getIva().toString();
                        fila2[4] = next;
                        dm.addRow(fila2);
                    }

                } else {

                    while (it.hasNext()) {
                        Articulo next = it.next();
                        Object[] fila3 = new Object[5];
                        if (!next.isDeshabilitado()) {
                            fila3[0] = next.getCodigo();
                            fila3[1] = next.getNombre();
                            fila3[2] = next.getDescripcion();
                            fila3[3] = next.getIva().toString();
                            fila3[4] = next;
                            dm.addRow(fila3);
                        } else {
                            Articulo art = (Articulo) next;

                        }
                    }

                }
  
            }
        }       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultListModel listModel;
        listModel = (DefaultListModel) this.jList1.getModel();
        Proveedor p = (Proveedor) this.jComboBox2.getSelectedItem();
        int proveedores = listModel.getSize();
        boolean existe = false;
        
        for (int i = 0; i < proveedores; i++) {
            Proveedor p2 = (Proveedor) listModel.get(i);
            if (p.toString().equals(p2.toString())) {
                existe = true;
                break;
                
            }            
        }

        if (!existe) {
            listModel.addElement(p);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "No se puede agregar el mismo proveedor 2 veces.");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        DefaultListModel listModel;
        listModel = (DefaultListModel) this.jList1.getModel();
        Proveedor p = (Proveedor) this.jList1.getSelectedValue();
        int proveedores = listModel.getSize();
        boolean existe = false;

        if (this.jList1.getSelectedIndex() == -1) {
            javax.swing.JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la lista.");
        } else {
            for (int i = 0; i < proveedores; i++) {
                Proveedor p2 = (Proveedor) listModel.get(i);
                if (p.toString().equals(p2.toString())) {
                    existe = true;
                    break;
                }
            }

            if (existe) {
                int input = javax.swing.JOptionPane.showConfirmDialog(null, "¿Desea continuar?", "Seleccione una opción",
                     javax.swing.JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    listModel.removeElement(p);
                }

            }

        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(altaArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(altaArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(altaArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(altaArticulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new altaArticulo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox<IVA> jComboBox1;
    private javax.swing.JComboBox<Proveedor> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList<Proveedor> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}