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

        tablita2.getColumnModel().getColumn(4).setMinWidth(0);
        tablita2.getColumnModel().getColumn(4).setMaxWidth(0);
        tablita2.getColumnModel().getColumn(4).setWidth(0);

        this.setTitle(a.getCodigo() + " - " + a.getNombre().toUpperCase());

        Iterator<Historial> it = Conexion.getInstance().listarHistorialArticulo(a.getCodigo()).iterator();
        List<Historial> l = new ArrayList(); //LISTA PARA GUARDAR LOS PRECIOS DIFERENTES 

        //FILTRADO DE LISTA DE PRECIOS
        while (it.hasNext()) {

            Historial h = (Historial) it.next();
            String proveedor = h.getProveedor().getRUT();
            String codigoP = a.getCodigo();
            float precio = h.getPrecio();
            Date date = h.getFecha();

            if (!existeProveedor(l, proveedor)) {//SI EL PROVEEDOR NO EXISTE EN LA LISTA LO INGRESO CON DICHO PRODUCTO.
                l.add(h);
                Collections.sort(l);
            } else {
                if (!existePrecioProveedor(l, proveedor, codigoP, precio)) {//SI EXISTE INGRESO SUS PRODUCOS CON DIFERENTES PRECIOS.
                    l.add(h);
                    Collections.sort(l);
                } else if (!existeProductoIgualFechaPrecioProv(l,h)){
                    if (!insertarPrimero(l, h)) {
                        if (!insertarFinal(l, h)) {
                            insertarMedio(l, h);
                        }
                    }
                }
            }
            Collections.sort(l);
        }

        int shrek = 0;
        //PINTAR TABLA

        DefaultTableModel mdl = (DefaultTableModel) tablita2.getModel();
        Iterator<Historial> it2 = l.iterator();

        while (it2.hasNext()) {

            Historial historia = it2.next();

            if (!a.isDeshabilitado()) {

                Object[] fila = new Object[8];
                fila[0] = historia.getProveedor().getRUT();
                fila[1] = historia.getProveedor().getRazonSocial();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date f = historia.getFecha();
                sdf.format(f);

                fila[2] = f;
                fila[3] = historia.getPrecio();
                fila[4] = historia;
                mdl.addRow(fila);
            }
        }

    }
    
    private boolean existeProductoIgualFechaPrecioProv(List lista, Historial h){
        boolean existe = false;
        
        String RUT = h.getProveedor().getRUT();
        Date date = h.getFecha();
        float precio = h.getPrecio();
        
        Iterator it = lista.iterator();
        while(it.hasNext()){
            Historial h2 = (Historial)it.next();
            String RUT2 = h2.getProveedor().getRUT();
            Date date2 = h2.getFecha();
            float precio2 = h2.getPrecio();
            
            if(date.equals(date2) && RUT.equals(RUT2) && precio == precio2){
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

    private boolean existePrecioProveedor(List<Historial> lista, String rut, String codigoP, float precio) {

        Iterator it = lista.iterator();
        boolean existe = false;

        while (it.hasNext()) {

            Historial h = (Historial) it.next();
            Proveedor p = h.getProveedor();
            String codigoPLista2 = h.getProducto().getCodigo();

            if (p.getRUT().equals(rut) && codigoPLista2.equals(codigoP) && h.getPrecio() == precio) {
                    
                existe = true;
                break;
            }
        }
        return existe;
    }

    private boolean insertarPrimero(List lista, Historial h) {

        boolean primero = false;
        
        Date date = h.getFecha();
        String RUT = h.getProveedor().getRUT();
        float precio = h.getPrecio();

        Historial h2 = (Historial) lista.get(0);
        Date date2 = h2.getFecha();
        String RUT2 = h2.getProveedor().getRUT();
        float precio2 = h2.getPrecio();
        
        if (date.equals(date2) && precio2 == precio ) {
            if (!verificarProveedoresIgualFecha(lista, h)) {
                lista.add(h);
                primero = true;
            }
        } else if (date.before(date2) && RUT.equals(RUT2) && precio == precio2) {
            lista.remove(h2);
            lista.add(h);
            primero = true;
        } else if (date.before(date2) && RUT.equals(RUT2) && precio != precio2) {
            lista.add(h);
            primero = true;
        } else if (date.before(date2) && !RUT.equals(RUT2)) {
            lista.add(h);
            primero = true;
        }
        return primero;
    }
    
    private boolean insertarFinal(List lista, Historial h) {
        boolean ultimo = false;

        Date date = h.getFecha();
        String RUT = h.getProveedor().getRUT();
        float precio = h.getPrecio();

        
        int tamLista = lista.size();

        Historial h2 = (Historial) lista.get(lista.size() - 1);
        Date date2 = h2.getFecha();
        String RUT2 = h2.getProveedor().getRUT();
        float precio2 = h2.getPrecio();

        if (date.equals(date2)) {
            if (!verificarProveedoresIgualFecha(lista, h)) {
                lista.add(h);
                ultimo = true;
            }
        } else if (date.after(date2) && RUT.equals(RUT2) && precio != precio2) {
            lista.add(h);
            ultimo = true;

        } else if (date.after(date2) && !RUT.equals(RUT2)) {
            if (verificarConFechaAnteriorConIgualPro(lista, h)) {
                lista.add(h);
                ultimo = true;
            }
        } else if (tamLista >= 2) {
            Historial h3 = (Historial) lista.get(lista.size() - 2);
            Date date3 = h3.getFecha();
            String RUT3 = h3.getProveedor().getRUT();
            float precio3 = h3.getPrecio();
            
            if ((date.after(date3) || date.equals(date3)) && date.before(date2) && RUT.equals(RUT2) && precio == precio2) {
                lista.remove(h2);
                lista.add(h);
                ultimo = true;
            }


        }

        return ultimo;
    }

    private boolean verificarProveedoresIgualFecha(List lista, Historial h) {
        boolean repetido = false;

        String RUT = h.getProveedor().getRUT();
        Date date = h.getFecha();
        float precio = h.getPrecio();

        Iterator it = lista.iterator();
        while (it.hasNext()) {
            Historial h2 = (Historial) it.next();
            String RUT2 = h2.getProveedor().getRUT();
            Date date2 = h2.getFecha();
            float precio2 = h2.getPrecio();

            if (date.equals(date2) && RUT.equals(RUT2) && precio == precio2) {
                repetido = true;
                break;
            }
        }
        return repetido;
    }

    private boolean verificarConFechaAnteriorConIgualPro(List lista, Historial h) {
        boolean ingresa = false;
        boolean mayor;

        String RUT = h.getProveedor().getRUT();
        Date date = h.getFecha();
        float precio = h.getPrecio();

        Historial h2 = null;
        String RUT2;
        Date date2;
        float precio2;

        Iterator it = lista.iterator();
        while (it.hasNext()) {            //BUSCO LA MAYOR FECHA PARA ESE PROVEEDOR CON MET. BURBUJA DEA

            h2 = (Historial) it.next();
            RUT2 = h2.getProveedor().getRUT();
            date2 = h2.getFecha();
            mayor = true;

            Iterator it2 = lista.iterator();
            while (it2.hasNext()) {
                Historial h3 = (Historial) it2.next();
                String RUT3 = h3.getProveedor().getRUT();
                Date date3 = h3.getFecha();

                if (date2.before(date3) && RUT2.equals(RUT3)) {
                    mayor = false;
                    break;
                }
            }
            if (mayor) {
                break;
            }
        }
        precio2 = h2.getPrecio();
        if (precio2 != precio) {
            ingresa = true;
        }
        return ingresa;
    }

    private void insertarMedio(List lista, Historial h) {

        ListIterator it = lista.listIterator();
        Historial h2 = null;
        Historial h3 = null;
        int maximo = lista.size() - 1;
        int indice = 1;
        while (it.hasNext()) {
            h2 = (Historial) it.next();

            if (it.hasNext()) {
                h3 = (Historial) it.next();
                it.previous();
            } else {
                break;
            }
            Date f = h.getFecha();
            String RUT = h.getProveedor().getRUT();
            float p = h.getPrecio();

            Date f2 = h2.getFecha();
            String RUT2 = h2.getProveedor().getRUT();
            float p2 = h2.getPrecio();

            Date f3 = h3.getFecha();
            String RUT3 = h3.getProveedor().getRUT();
            float p3 = h3.getPrecio();
                
            if (f.after(f2) && f.before(f3) && RUT.equals(RUT2) && RUT.equals(RUT3) && p != p2 && p != p3) {
                lista.add(h);
                break;

            }else if( (f.after(f2)|| f.equals(f2)) && f.before(f3) && RUT.equals(RUT2) && RUT.equals(RUT3) && p == p3){
                lista.remove(h3);
                lista.add(h);
                break;
            }
        }

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablita2;
    // End of variables declaration//GEN-END:variables

    
    

    
}
