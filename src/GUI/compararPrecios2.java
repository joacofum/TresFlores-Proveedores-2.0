/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BD.Conexion;
import Clases.Articulo;
import Clases.Historial;
import Clases.Proveedor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Leo
 */
public class compararPrecios2 extends javax.swing.JFrame {
    
    private Articulo article;
    private List<Proveedor> listaProveedors;
    /**
     * Creates new form compararPrecios2
     */
    public compararPrecios2() {
        initComponents();

        tablita2.getColumnModel().getColumn(4).setMinWidth(0);
        tablita2.getColumnModel().getColumn(4).setMaxWidth(0);
        tablita2.getColumnModel().getColumn(4).setWidth(0);
    }

    public compararPrecios2(Articulo a) {
        initComponents();
        article = a;

        tablita2.getColumnModel().getColumn(4).setMinWidth(0);
        tablita2.getColumnModel().getColumn(4).setMaxWidth(0);
        tablita2.getColumnModel().getColumn(4).setWidth(0);

        this.setTitle(article.getCodigo() + " - " + article.getNombre().toUpperCase());
        listaProveedors = Conexion.getInstance().listarProveedoresArticulo(article.getCodigo());
        Iterator it3 = listaProveedors.iterator();
        jComboBox1.addItem("Todos");
        
        while(it3.hasNext()){
            Proveedor p = (Proveedor)it3.next();
            jComboBox1.addItem(p.getRUT());
        }
        
        //mostrarTodosProveedores();
    }
    
    private boolean existeProductoIgualFechaPrecioProv(List lista, Historial h){
        boolean existe = false;
        
        Date date = h.getFecha();
        float precio = h.getPrecio();
        Iterator it = lista.iterator();
        
        while(it.hasNext()){
            Historial h2 = (Historial)it.next();
            Date date2 = h2.getFecha();
            float precio2 = h2.getPrecio();
            
            if(date.equals(date2) && precio == precio2){
                existe = true;
                break;
            }
        }
        
        return existe;
    }

    private boolean existeProveedor(List<Historial> lista, String rut) {
        boolean existe = false;
        if (lista.size() == 0) {

            return false;
        } else {

            Iterator it = lista.iterator();

            while (it.hasNext()) {

                Historial h = (Historial) it.next();
                Proveedor p = h.getProveedor();

                if (p.getRUT() == rut) {
                    existe = true;
                    break;
                }
            }
        }
        return existe;
    }

    private boolean existePrecioProveedor(List<Historial> lista,float precio) {

        Iterator it = lista.iterator();
        boolean existe = false;

        while (it.hasNext()) {

            Historial h = (Historial) it.next();
            if (h.getPrecio() == precio) {

                existe = true;
                break;
            }
        }
        return existe;
    }
/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablita2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablita2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RUT", "RAZÓN SOCIAL", "FECHA", "PRECIO", "objeto"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablita2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tablita2);

        jButton1.setText("Cerrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Seleccionar Proveedor");

        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private List mostrarTodosProveedores(){
        List<Historial> lista = new ArrayList<>();
        List<List> listas = new ArrayList<>();
        List<Historial> l = null;
        
        Iterator it = listaProveedors.iterator();
        
        while(it.hasNext()){
            Proveedor p = (Proveedor)it.next();
            int codigoPro = p.getCodigo();
            l = mostrarPreciosProveedor(codigoPro,article);
            listas.add(l);
        }
        
        Iterator it2 = listas.iterator();
        while(it2.hasNext()){
            List l2 = (List)it2.next();
            Iterator it3 = l2.iterator();
            while(it3.hasNext()){
                Historial h = (Historial)it3.next();
                lista.add(h);
            }
        }
        
        Collections.sort(lista);
        return lista;
    }
    
    private int getCodigoProveedor(String RUT){
        int codigo = 0;
        Iterator it = listaProveedors.iterator();
        while(it.hasNext()){
            Proveedor p = (Proveedor)it.next();
            String RUT2 = p.getRUT();
            if(RUT2.equals(RUT)){
                codigo = p.getCodigo();
                break;
            }
                
        }
        return codigo;
    }
    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
        String opcion = jComboBox1.getSelectedItem().toString();
        DefaultTableModel model = (DefaultTableModel) this.tablita2.getModel();
        model.setRowCount(0);
        List l = null;
        if (opcion.equals("Todos")) {
           l =  mostrarTodosProveedores();
        } else {
            int codigo = getCodigoProveedor(opcion);
            l = mostrarPreciosProveedor(codigo, article);
        }
        pintarTabla(l);
    }//GEN-LAST:event_jComboBox1ItemStateChanged
    
    
    private void eliminarFechaMayorSiExiste(List lista,Historial h){
        boolean teEncontre = false;
        boolean borrar = false;
        Historial h2 = null;
        Date f = h.getFecha();
        float p = h.getPrecio();
        
        Iterator it = lista.iterator();
        
        while(it.hasNext()){
            h2 = (Historial)it.next();
            Date f2 = h2.getFecha();
            float p2 = h2.getPrecio();
            
            if(teEncontre){
                if(f.before(f2) && p == p2){
                    borrar = true;
                    break;
                }
            }
            
            if(f.equals(f2) && p == p2){
                teEncontre = true;
            }         
        }
        
        if(borrar){
            lista.remove(h2);
        }
    }
    
    private List mostrarPreciosProveedor(int codigo, Articulo articulo) {
        List<Historial> listaHistorial = Conexion.getInstance().listarHistorialProveedorArticulo(articulo.getCodigo(),codigo);

        Iterator<Historial> it = listaHistorial.iterator();
        List<Historial> l = new ArrayList(); //LISTA PARA GUARDAR LOS PRECIOS DIFERENTES 

        //FILTRADO DE LISTA DE PRECIOS
        while (it.hasNext()) {

            Historial h = (Historial) it.next();
            float precio = h.getPrecio();
            
            if (l.size() == 0) {//SI EL PROVEEDOR NO EXISTE EN LA LISTA LO INGRESO CON DICHO PRODUCTO.
                l.add(h);
            } else {
                if (!existePrecioProveedor(l, precio)) {//SI EXISTE INGRESO SUS PRODUCOS CON DIFERENTES PRECIOS.
                    l.add(h);
                } else if (!existeProductoIgualFechaPrecioProv(l, h)) {
                    if (ingresarPrecio(l, h)) {
                        l.add(h);
                        Collections.sort(l);
                        eliminarFechaMayorSiExiste(l, h);
                    }
                }
            }
            Collections.sort(l);
        }
        return l;
    }

    private boolean ingresarPrimero(List lista, Historial h) {

        Historial h2 = (Historial) lista.get(0);
        Date date = h.getFecha();
        Date date2 = h2.getFecha();
        float precio = h.getPrecio();
        float precio2 = h2.getPrecio();
        
        if(date.before(date2) || (date.equals(date2) && precio != precio2)){
            return true;
        }
        return false;
    }
    
    private boolean ingresarFinal(List lista, Historial h) {
        int tamLista = lista.size();
        Historial h2 = (Historial) lista.get(lista.size() - 1);
        Date date = h.getFecha();
        Date date2 = h2.getFecha();
        float precio = h.getPrecio();
        float precio2 = h2.getPrecio();

        if (tamLista == 1) {
            if(date.after(date2) && precio != precio){
                return true;
            }

        } else if (tamLista >= 2) {
            Historial h3 = (Historial) lista.get(lista.size() - 2);
            Date date3 = h3.getFecha();
            
            if(date.after(date3) && date.before(date2) && precio == precio2){
                return true;
            }else if( date.after(date2) && precio != precio2){
                return true;
            } 
        }
        return false;
    }
    
    private boolean ingresarMedio(List lista,Historial h){
        boolean medio = false;
        ListIterator it = lista.listIterator();
        Historial h2 = null;
        Historial h3 = null;

        Date f = h.getFecha();
        float p = h.getPrecio();
        
        while (it.hasNext()) {
            h2 = (Historial) it.next();
            
            if (it.hasNext()) {
                h3 = (Historial) it.next();
                it.previous();
            } else {
                break;
            }
            
            Date f2 = h2.getFecha();
            Date f3 = h3.getFecha();
            float p2 = h2.getPrecio();
            float p3 = h3.getPrecio();
            
            if(f.after(f2) && f.before(f3) && p != p2 && p != p3){
                medio = true;
                break;
            }
            
            if(f.after(f2) && f.before(f3) && p != p2 && p == p3){
                medio = true;
                break;
            }
        }
        return medio;
    }
    
    
    private boolean ingresarPrecio(List l,Historial h){
        
        if (ingresarPrimero(l, h)) {
            return true;
        }
        if (ingresarFinal(l, h)) {
            return true;
        }
        if (ingresarMedio(l, h)) {
            return true;
        }
        return false;
    }
    
    private void pintarTabla(List l){
        
            //PINTAR TABLA
            DefaultTableModel mdl = (DefaultTableModel) tablita2.getModel();
            Iterator<Historial> it2 = l.iterator();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            while (it2.hasNext()) {

                Historial historia = it2.next();

                if (!article.isDeshabilitado()) {

                    Object[] fila = new Object[8];
                    fila[0] = historia.getProveedor().getRUT();
                    fila[1] = historia.getProveedor().getRazonSocial();

                    
                    Date f = historia.getFecha();
                    sdf.format(f);

                    fila[2] = f;
                    fila[3] = historia.getPrecio();
                    fila[4] = historia;
                    mdl.addRow(fila);
                }
            }
    }
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
            java.util.logging.Logger.getLogger(compararPrecios2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(compararPrecios2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(compararPrecios2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(compararPrecios2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new compararPrecios2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablita2;
    // End of variables declaration//GEN-END:variables

    

    
    

    
}
