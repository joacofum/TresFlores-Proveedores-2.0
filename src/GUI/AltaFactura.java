/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BD.Conexion;
import Clases.Articulo;
import Clases.F_P;
import Clases.Factura;
import Clases.Historial;
import Clases.Proveedor;
import Clases.tipoComprobante;
import Clases.tipoIVA;
import Clases.tipoMoneda;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author emiliano
 */
public class AltaFactura extends javax.swing.JFrame {

    List<Articulo> ListaArticulo = new ArrayList<Articulo>();

    Articulo articulo_seleccionado = null;

    Factura f;

    boolean buscarart = false;
    
    float monedaCotizacion;
    
    public AltaFactura() {
        initComponents();
        this.jCBTipoComprobante.setModel(new DefaultComboBoxModel(tipoComprobante.values()));
        this.jCBMoneda.setModel(new DefaultComboBoxModel(tipoMoneda.values()));
        this.jTextSubTotal.setEditable(false);
        this.jTextTOTAL.setEditable(false);
        this.jTextIVAminimo.setEditable(false);
        this.jTextDireccion.setEditable(false);
        this.jTextRut.setEditable(false);
        this.jTextSubTotalArt.setEditable(false);
        this.jTextIVAbasico.setEditable(false);

        //agregar listado de proveedores
        AutoCompleteDecorator.decorate(this.jCBProveedor);
        List<Proveedor> LProv = Conexion.getInstance().listadoProveedores();
        LProv.forEach((p) -> {
            if(!p.isDeshabilitado())
            this.jCBProveedor.addItem(p);
        });

        this.setLocationRelativeTo(null);

        if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Contado")) {
            this.ListaFacparaNC(false);
        } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Crédito")) {
            this.ListaFacparaNC(false);
        } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Devolución Contado")) {
            this.ListaFacparaNC(false);
        } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Nota de crédito")) {
            this.ListaFacparaNC(true);
        }

        this.jMenuBar1.setVisible(false);
//        this.jButtonModificar.setVisible(false);
//        this.jButtonCerrarMod.setVisible(false);
        this.jPanelModificar.setVisible(false);
    }

    public AltaFactura(Factura fac) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.jLabel17.setVisible(false);
        this.jListFacturaspNC.setVisible(false);
        this.jButtonModificar.setVisible(false);
        this.jPanelModificar.setVisible(false);
        this.jButtonIngresar.setVisible(false);
        this.jPanelSetArticulo.setVisible(false);
        this.f = fac;

        this.jCBTipoComprobante.setModel(new DefaultComboBoxModel(tipoComprobante.values()));
        this.jCBMoneda.setModel(new DefaultComboBoxModel(tipoMoneda.values()));

        this.jCBProveedor.addItem(fac.getProveedor());
        this.jTextDireccion.setText(fac.getProveedor().getDireccion());
        this.jTextRut.setText(fac.getProveedor().getRUT());
        this.jTextSerie.setText(fac.getSerieComprobante());
        this.jTextNumeroFact.setText(String.valueOf(fac.getNroComprobante()));
        this.jTextTOTAL.setText(String.valueOf(fac.getTotal()));

        if (fac.getMoneda() == tipoMoneda.$U) {
            this.jCBMoneda.setSelectedIndex(0);
        } else if (fac.getMoneda() == tipoMoneda.US$) {
            this.jCBMoneda.setSelectedIndex(1);
        }

        if (fac.getTipo() == tipoComprobante.Credito) {
            this.jCBTipoComprobante.setSelectedIndex(1);
        } else if (fac.getTipo() == tipoComprobante.Contado) {
            this.jCBTipoComprobante.setSelectedIndex(0);
        } else if (fac.getTipo() == tipoComprobante.DevolucionContado) {
            this.jCBTipoComprobante.setSelectedIndex(2);
        } else if (fac.getTipo() == tipoComprobante.NotaCredito) {
            this.jCBTipoComprobante.setSelectedIndex(3);
            this.jLabel17.setVisible(true);
            this.jListFacturaspNC.setVisible(true);
        }

        this.jDateChooser.setDate(fac.getFecha());

        List<F_P> ListF_P = fac.getFp_s();
        DefaultTableModel model = (DefaultTableModel) this.jTableArticulos.getModel();

        for (int i = 0; i < ListF_P.size(); i++) {
            String nombre_desc = ListF_P.get(i).getArticulo().getNombre() + " - " + ListF_P.get(i).getArticulo().getDescripcion();

            float subtotal = (ListF_P.get(i).getCantidad() * ListF_P.get(i).getPrecio()) - ListF_P.get(i).getDescuento();

            model.addRow(new Object[]{nombre_desc, ListF_P.get(i).getCantidad(), ListF_P.get(i).getPrecio(), ListF_P.get(i).getDescuento(), subtotal});

            this.ListaArticulo.add(ListF_P.get(i).getArticulo());
        }

        this.jCBProveedor.setEnabled(false);
        this.jTextRut.setEditable(false);
        this.jTextDireccion.setEditable(false);
        this.jTextSerie.setEditable(false);
        this.jTextNumeroFact.setEditable(false);
        this.jTextSubTotal.setEditable(false);
        this.jTextIVAminimo.setEditable(false);
        this.jTextIVAbasico.setEditable(false);
        this.jTextTOTAL.setEditable(false);
        this.jDateChooser.setEnabled(false);
        this.jCBTipoComprobante.setEnabled(false);
        this.jCBMoneda.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableArticulos = new javax.swing.JTable();
        jButtonIngresar = new javax.swing.JButton();
        jButtonCerrar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jPanelSetArticulo = new javax.swing.JPanel();
        jButtonAgregarArticulo = new javax.swing.JButton();
        jTextSubTotalArt = new javax.swing.JTextField();
        jTextDescuento = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextUnitario = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextCantidad = new javax.swing.JTextField();
        jTextArticulo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextSubTotal = new javax.swing.JTextField();
        jTextTOTAL = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextIVAbasico = new javax.swing.JTextField();
        jCheckBoxIvaInc = new javax.swing.JCheckBox();
        jTextIVAminimo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListFacturaspNC = new javax.swing.JList<>();
        jLabel17 = new javax.swing.JLabel();
        jCBProveedor = new javax.swing.JComboBox<>();
        jDateChooser = new com.toedter.calendar.JDateChooser();
        jCBMoneda = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextRut = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextDireccion = new javax.swing.JTextField();
        jCBTipoComprobante = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextSerie = new javax.swing.JTextField();
        jTextNumeroFact = new javax.swing.JTextField();
        jPanelModificar = new javax.swing.JPanel();
        jButtonCerrarMod = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        labelCotizacion = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuEdicion = new javax.swing.JMenu();
        jMenuItemModificar = new javax.swing.JMenuItem();
        jMenuItemEliminar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Agregar Comprobante"));

        jTableArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Articulo", "Cantidad", "Unitario", "Descuento", "SubTotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableArticulos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableArticulosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableArticulos);
        if (jTableArticulos.getColumnModel().getColumnCount() > 0) {
            jTableArticulos.getColumnModel().getColumn(0).setPreferredWidth(530);
            jTableArticulos.getColumnModel().getColumn(1).setPreferredWidth(70);
            jTableArticulos.getColumnModel().getColumn(2).setPreferredWidth(70);
            jTableArticulos.getColumnModel().getColumn(3).setPreferredWidth(70);
            jTableArticulos.getColumnModel().getColumn(4).setPreferredWidth(120);
        }

        jButtonIngresar.setText("Ingresar");
        jButtonIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngresarActionPerformed(evt);
            }
        });

        jButtonCerrar.setText("Cerrar");
        jButtonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarActionPerformed(evt);
            }
        });

        jLabel13.setText("SubTotal");

        jPanelSetArticulo.setBorder(javax.swing.BorderFactory.createTitledBorder("Agregar articulo"));

        jButtonAgregarArticulo.setText("+");
        jButtonAgregarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarArticuloActionPerformed(evt);
            }
        });

        jTextSubTotalArt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextSubTotalArtKeyPressed(evt);
            }
        });

        jTextDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDescuentoActionPerformed(evt);
            }
        });
        jTextDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextDescuentoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextDescuentoKeyTyped(evt);
            }
        });

        jLabel11.setText("Descuento");

        jLabel12.setText("SubTotal");

        jTextUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextUnitarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextUnitarioKeyTyped(evt);
            }
        });

        jLabel10.setText("Unitario");

        jLabel9.setText("Cantidad");

        jTextCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextCantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextCantidadKeyTyped(evt);
            }
        });

        jTextArticulo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArticuloFocusLost(evt);
            }
        });
        jTextArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArticuloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArticuloKeyTyped(evt);
            }
        });

        jLabel8.setText("Articulo");

        javax.swing.GroupLayout jPanelSetArticuloLayout = new javax.swing.GroupLayout(jPanelSetArticulo);
        jPanelSetArticulo.setLayout(jPanelSetArticuloLayout);
        jPanelSetArticuloLayout.setHorizontalGroup(
            jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSetArticuloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jTextArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jTextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSetArticuloLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel10))
                    .addGroup(jPanelSetArticuloLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jTextUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanelSetArticuloLayout.createSequentialGroup()
                        .addComponent(jTextSubTotalArt, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAgregarArticulo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelSetArticuloLayout.setVerticalGroup(
            jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSetArticuloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelSetArticuloLayout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelSetArticuloLayout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanelSetArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonAgregarArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextSubTotalArt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanelSetArticuloLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSetArticuloLayout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jTextDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelSetArticuloLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel14.setText("IVA Minimo");

        jLabel15.setText("Total");

        jLabel16.setText("IVA Basico");

        jCheckBoxIvaInc.setText("Precio unitario con IVA incluido");
        jCheckBoxIvaInc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxIvaIncItemStateChanged(evt);
            }
        });
        jCheckBoxIvaInc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxIvaIncActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jListFacturaspNC);

        jLabel17.setText("Corresponde a la(s) factura(s):");

        jCBProveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBProveedorItemStateChanged(evt);
            }
        });
        jCBProveedor.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jCBProveedorComponentShown(evt);
            }
        });
        jCBProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBProveedorActionPerformed(evt);
            }
        });
        jCBProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCBProveedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCBProveedorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jCBProveedorKeyTyped(evt);
            }
        });

        jDateChooser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jDateChooserFocusLost(evt);
            }
        });
        jDateChooser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateChooserKeyPressed(evt);
            }
        });

        jCBMoneda.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBMonedaItemStateChanged(evt);
            }
        });

        jLabel5.setText("Moneda");

        jLabel4.setText("Fecha");

        jLabel1.setText("Proveedor");

        jLabel2.setText("Rut");

        jLabel3.setText("Direccion");

        jCBTipoComprobante.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBTipoComprobanteItemStateChanged(evt);
            }
        });

        jLabel6.setText("Comprobante");

        jLabel7.setText("Numero");

        jTextSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextSerieKeyPressed(evt);
            }
        });

        jTextNumeroFact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextNumeroFactKeyPressed(evt);
            }
        });

        jPanelModificar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 153)));

        jButtonCerrarMod.setText("x");
        jButtonCerrarMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCerrarModActionPerformed(evt);
            }
        });

        jButtonModificar.setText("Modificar");
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelModificarLayout = new javax.swing.GroupLayout(jPanelModificar);
        jPanelModificar.setLayout(jPanelModificarLayout);
        jPanelModificarLayout.setHorizontalGroup(
            jPanelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelModificarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCerrarMod)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelModificarLayout.setVerticalGroup(
            jPanelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelModificarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelModificarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonModificar)
                    .addComponent(jButtonCerrarMod))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jCBProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jTextRut, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jTextDireccion)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 37, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCBMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelCotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(307, 307, 307)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextNumeroFact, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jCBTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jPanelModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(303, 303, 303)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextTOTAL, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel16)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel14)
                                                .addGap(4, 4, 4)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextIVAminimo)
                                            .addComponent(jTextIVAbasico, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButtonIngresar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonCerrar))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jCheckBoxIvaInc)
                            .addComponent(jPanelSetArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCBProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(jCBMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelCotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCBTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextNumeroFact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jPanelSetArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxIvaInc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextIVAminimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(6, 6, 6)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextIVAbasico, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextTOTAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelModificar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonIngresar)
                        .addComponent(jButtonCerrar)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jMenuEdicion.setText("Edición");

        jMenuItemModificar.setText("Modificar");
        jMenuItemModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemModificarActionPerformed(evt);
            }
        });
        jMenuEdicion.add(jMenuItemModificar);

        jMenuItemEliminar.setText("Deshabilitar");
        jMenuItemEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEliminarActionPerformed(evt);
            }
        });
        jMenuEdicion.add(jMenuItemEliminar);

        jMenuBar1.add(jMenuEdicion);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDescuentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDescuentoActionPerformed

    private void jButtonIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngresarActionPerformed
        Factura fac = new Factura();
        if (this.jTextSerie.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Debe ingresar la serie del numero de factura.");
        } else if (this.jTextNumeroFact.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Debe ingresar el numero de factura.");
        } else if (this.jDateChooser.getDate() == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Debe ingresar la fecha de la factura.");
        } else if (this.ListaArticulo.size() == 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Es necesario ingresar minimo un articulo.");
        } else {
            if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Contado")) {
                fac.setTipo(tipoComprobante.Contado);
                fac.setCotizacion(45);
                fac.setSerieComprobante(this.jTextSerie.getText());
                fac.setNroComprobante(Integer.parseInt(this.jTextNumeroFact.getText()));
                fac.setPendiente(0);
                fac.setTotal(Float.parseFloat(this.jTextTOTAL.getText()));
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date dat = this.jDateChooser.getDate();
                sdf.format(dat);
                fac.setFecha(dat);
                fac.setProveedor((Proveedor) this.jCBProveedor.getSelectedItem());

                if (this.jCBMoneda.getSelectedItem() == tipoMoneda.$U) {
                    fac.setMoneda(tipoMoneda.$U);
                } else if (this.jCBMoneda.getSelectedItem() == tipoMoneda.US$) {
                    fac.setMoneda(tipoMoneda.US$);
                }

                List<F_P> listaf_p = new ArrayList<F_P>();
                DefaultTableModel modelo = (DefaultTableModel) jTableArticulos.getModel();
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    F_P f_p = new F_P();
                    f_p.setFactura(fac);
                    f_p.setArticulo(this.ListaArticulo.get(i));

                    String c = modelo.getValueAt(i, 1).toString();
                    float cant = Float.parseFloat(c);
                    f_p.setCantidad(cant);

                    String p = modelo.getValueAt(i, 2).toString();
                    float precio = Float.parseFloat(p);
                    f_p.setPrecio(precio);

                    String d = modelo.getValueAt(i, 3).toString();
                    float descuento = Float.parseFloat(d);
                    f_p.setDescuento(descuento);
 
                    this.ListaArticulo.get(i).addF_P(f_p);

                    listaf_p.add(f_p);
                    
                    //HISTORIAL DE PRECIOS DEL PRODUCTO - INICIO
                    
                    Historial h = new Historial();
                    h.setProveedor((Proveedor) this.jCBProveedor.getSelectedItem());
                    h.setPrecio(precio);
                    h.setProducto(this.ListaArticulo.get(i));
                    h.setFecha(this.jDateChooser.getDate());
                    this.ListaArticulo.get(i).getHistoriales().add(h);
                    Conexion.getInstance().persist(h);
                    
                    //HISTORIAL DE PRECIOS DEL PRODUCTO - FIN
                    
                }
                fac.setFp_s(listaf_p);

            } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Crédito")) {
                fac.setTipo(tipoComprobante.Credito);
                fac.setCotizacion(45);
                fac.setSerieComprobante(this.jTextSerie.getText());
                fac.setNroComprobante(Integer.parseInt(this.jTextNumeroFact.getText()));
                fac.setPendiente(Float.parseFloat(this.jTextTOTAL.getText()));
                fac.setTotal(Float.parseFloat(this.jTextTOTAL.getText()));
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date dat = this.jDateChooser.getDate();
                sdf.format(dat);
                fac.setFecha(dat);
                
                fac.setProveedor((Proveedor) this.jCBProveedor.getSelectedItem());

                if (this.jCBMoneda.getSelectedItem() == tipoMoneda.$U) {
                    fac.setMoneda(tipoMoneda.$U);
                } else if (this.jCBMoneda.getSelectedItem() == tipoMoneda.US$) {
                    fac.setMoneda(tipoMoneda.US$);
                }

                List<F_P> listaf_p = new ArrayList<F_P>();
                DefaultTableModel modelo = (DefaultTableModel) jTableArticulos.getModel();
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    F_P f_p = new F_P();
                    f_p.setFactura(fac);
                    f_p.setArticulo(this.ListaArticulo.get(i));

                    String c = modelo.getValueAt(i, 1).toString();
                    float cant = Float.parseFloat(c);
                    f_p.setCantidad(cant);

                    String p = modelo.getValueAt(i, 2).toString();
                    float precio = Float.parseFloat(p);
                    f_p.setPrecio(precio);

                    String d = modelo.getValueAt(i, 3).toString();
                    float descuento = Float.parseFloat(d);
                    f_p.setDescuento(descuento);

                    this.ListaArticulo.get(i).addF_P(f_p);

                    listaf_p.add(f_p);
                    
                    //HISTORIAL DE PRECIOS DEL PRODUCTO - INICIO
                    
                    Historial h = new Historial();
                    h.setProveedor((Proveedor) this.jCBProveedor.getSelectedItem());
                    h.setPrecio(precio);
                    h.setProducto(this.ListaArticulo.get(i));
                    h.setFecha(this.jDateChooser.getDate());
                    this.ListaArticulo.get(i).getHistoriales().add(h);
                    Conexion.getInstance().persist(h);
                    
                    //HISTORIAL DE PRECIOS DEL PRODUCTO - FIN
                }
                fac.setFp_s(listaf_p);
            } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Devolución Contado")) {
                fac.setTipo(tipoComprobante.DevolucionContado);
                fac.setCotizacion(45);
                fac.setSerieComprobante(this.jTextSerie.getText());
                fac.setNroComprobante(Integer.parseInt(this.jTextNumeroFact.getText()));
                fac.setPendiente(0);
                fac.setTotal(Float.parseFloat(this.jTextTOTAL.getText()));
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date dat = this.jDateChooser.getDate();
                sdf.format(dat);
                fac.setFecha(dat);
                fac.setProveedor((Proveedor) this.jCBProveedor.getSelectedItem());

                if (this.jCBMoneda.getSelectedItem() == tipoMoneda.$U) {
                    fac.setMoneda(tipoMoneda.$U);
                } else if (this.jCBMoneda.getSelectedItem() == tipoMoneda.US$) {
                    fac.setMoneda(tipoMoneda.US$);
                }

                List<F_P> listaf_p = new ArrayList<F_P>();
                DefaultTableModel modelo = (DefaultTableModel) jTableArticulos.getModel();
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    F_P f_p = new F_P();
                    f_p.setFactura(fac);
                    f_p.setArticulo(this.ListaArticulo.get(i));

                    String c = modelo.getValueAt(i, 1).toString();
                    float cant = Float.parseFloat(c);
                    f_p.setCantidad(cant);

                    String p = modelo.getValueAt(i, 2).toString();
                    float precio = Float.parseFloat(p);
                    f_p.setPrecio(precio);

                    String d = modelo.getValueAt(i, 3).toString();
                    float descuento = Float.parseFloat(d);
                    f_p.setDescuento(descuento);

                    this.ListaArticulo.get(i).addF_P(f_p);

                    listaf_p.add(f_p);
                    
                    //HISTORIAL DE PRECIOS DEL PRODUCTO - INICIO
                    
                    Historial h = new Historial();
                    h.setProveedor((Proveedor) this.jCBProveedor.getSelectedItem());
                    h.setPrecio(precio);
                    h.setProducto(this.ListaArticulo.get(i));
                    Date dat2 = this.jDateChooser.getDate();
                    sdf.format(dat2);
                    h.setFecha(dat2);
                    this.ListaArticulo.get(i).getHistoriales().add(h);
                    Conexion.getInstance().persist(h);
                    
                    //HISTORIAL DE PRECIOS DEL PRODUCTO - FIN

                }
                fac.setFp_s(listaf_p);
            } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Nota de crédito")) {
                fac.setTipo(tipoComprobante.NotaCredito);
                
                
                //HISTORIAL DE PRECIOS DEL PRODUCTO - INICIO
                    
//                    Historial h = new Historial();
//                    h.setProveedor((Proveedor) this.jCBProveedor.getSelectedItem());
//                    h.setPrecio(precio);
//                    h.setProducto(this.ListaArticulo.get(i));
//                    h.setFecha(this.jDateChooser.getDate());
//                    this.ListaArticulo.get(i).getHistoriales().add(h);
//                    Conexion.getInstance().persist(h);
                    
                    //HISTORIAL DE PRECIOS DEL PRODUCTO - FIN
            }
            //fac.setUsuario(usuario);

            boolean f = Conexion.getInstance().persist(fac);
            List<F_P> lf_p = fac.getFp_s();
            for (int i = 0; i < lf_p.size(); i++) {
                boolean pf = Conexion.getInstance().persist(lf_p.get(i));
                if (!pf) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Ha ocurrido un problema.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
            if (f) {
                javax.swing.JOptionPane.showMessageDialog(null, "Factura fue ingresada exitosamente.", "Enhorabuena", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Ha ocurrido un problema.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_jButtonIngresarActionPerformed

    private void jTextArticuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArticuloKeyPressed
         if (evt.getKeyCode()==KeyEvent.VK_BACK_SPACE) {
            this.articulo_seleccionado = null;
        }
        
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && this.articulo_seleccionado == null) {
            buscarart = true;
            articulosBuscador AB = new articulosBuscador(this.jTextArticulo.getText(), this);
            if (AB.sizeconsulta > 1) {
                AB.show();
                this.jTextArticulo.transferFocus();
            } else if (AB.sizeconsulta == 1) {
                this.jTextArticulo.transferFocus();
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Ese articulo no existe", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                this.jTextArticulo.setText("");
            }
        }else if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.jTextArticulo.transferFocus();
        }else{
            this.articulo_seleccionado = null;
        }
    }//GEN-LAST:event_jTextArticuloKeyPressed

    public void setTextArticulo(String nombre) {
        this.jTextArticulo.setText(nombre);
    }

    public Proveedor getProveedor() {
        return (Proveedor) this.jCBProveedor.getSelectedItem();
    }

    private void jButtonAgregarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarArticuloActionPerformed
        if (this.jTextArticulo.getText().length() == 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Debe ingresar un articulo.", "Precaucion!", javax.swing.JOptionPane.WARNING_MESSAGE);
        } else if (this.jTextCantidad.getText().length() == 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Falta ingresar la cantidad.", "Precaucion!", javax.swing.JOptionPane.WARNING_MESSAGE);
        } else if (this.jTextUnitario.getText().length() == 0) {
            javax.swing.JOptionPane.showMessageDialog(null, "Falta ingresar el precio unitario del articulo.", "Precaucion!", javax.swing.JOptionPane.WARNING_MESSAGE);
        } else {

            float descuento;
            if (this.jTextDescuento.getText().length() == 0) {
                descuento = 0;
            } else {
                descuento = Float.parseFloat(this.jTextDescuento.getText());
            }

            DefaultTableModel model = (DefaultTableModel) this.jTableArticulos.getModel();
            float cantidad = Float.parseFloat(this.jTextCantidad.getText());
            float unitario = Float.parseFloat(this.jTextUnitario.getText());

            float subtotalar = unitario * cantidad - descuento;
            model.addRow(new Object[]{this.jTextArticulo.getText(), cantidad, unitario, descuento, subtotalar});
            this.ListaArticulo.add(articulo_seleccionado);
            this.articulo_seleccionado = null;
            this.jTextArticulo.setText("");
            this.jTextCantidad.setText("");
            this.jTextUnitario.setText("");
            this.jTextDescuento.setText("");
            this.jTextSubTotalArt.setText("");
            if (this.jCheckBoxIvaInc.isSelected()) {
                this.CalcularTotales_conIVA_inc();
            } else {
                this.CalcularTotales_sinIVA_inc();
            }

        }
    }//GEN-LAST:event_jButtonAgregarArticuloActionPerformed

    private void jCBProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBProveedorActionPerformed

    }//GEN-LAST:event_jCBProveedorActionPerformed

    private void jCBProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBProveedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

        }
    }//GEN-LAST:event_jCBProveedorKeyPressed

    private void jCBProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBProveedorKeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //this.jDateChooser.requestFocus();
        }
    }//GEN-LAST:event_jCBProveedorKeyTyped

    private void jCBProveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBProveedorItemStateChanged
        Proveedor p = (Proveedor) this.jCBProveedor.getSelectedItem();
        this.jTextRut.setText(p.getRUT());
        this.jTextDireccion.setText(p.getDireccion());
    }//GEN-LAST:event_jCBProveedorItemStateChanged

    private void jCBProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCBProveedorKeyReleased

    }//GEN-LAST:event_jCBProveedorKeyReleased

    private void jCBProveedorComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jCBProveedorComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBProveedorComponentShown

    private void jDateChooserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooserKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.jDateChooser.transferFocus();
        }
    }//GEN-LAST:event_jDateChooserKeyPressed

    private void jTextSerieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextSerieKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.jTextSerie.transferFocus();
        }
    }//GEN-LAST:event_jTextSerieKeyPressed

    private void jTextNumeroFactKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNumeroFactKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.jTextNumeroFact.transferFocus();
        }
    }//GEN-LAST:event_jTextNumeroFactKeyPressed

    private void jTextCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCantidadKeyPressed
        char caracter = evt.getKeyChar();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.jTextCantidad.transferFocus();
        } else if (!((caracter < '0') || (caracter > '9'))) {
            int des = 0, un = 0;
            if (this.jTextDescuento.getText().length() > 0) {
                des = Integer.parseInt(this.jTextDescuento.getText());
            }
            if (this.jTextUnitario.getText().length() > 0) {
                un = Integer.parseInt(this.jTextUnitario.getText());
            }

            int sub = Integer.parseInt(Character.toString(caracter)) * un - des;
            this.jTextSubTotalArt.setText(Integer.toString(sub));
        }
    }//GEN-LAST:event_jTextCantidadKeyPressed

    private void jTextUnitarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextUnitarioKeyPressed
        char caracter = evt.getKeyChar();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.jTextUnitario.transferFocus();
        } else if (((caracter < '0')
                || (caracter > '9'))
                && (caracter != '\b')) {
            evt.consume();  // ignorar el evento de teclado
        } else if (!((caracter < '0') || (caracter > '9'))) {
            int cant = 0, des = 0;
            if (this.jTextCantidad.getText().length() > 0) {
                cant = Integer.parseInt(this.jTextCantidad.getText());
            }
            if (this.jTextDescuento.getText().length() > 0) {
                des = Integer.parseInt(this.jTextDescuento.getText());
            }

            int sub = cant * Integer.parseInt(Character.toString(caracter)) - des;
            this.jTextSubTotalArt.setText(Integer.toString(sub));
        }
    }//GEN-LAST:event_jTextUnitarioKeyPressed

    private void jTextDescuentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDescuentoKeyPressed
        char caracter = evt.getKeyChar();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (this.jTextArticulo.getText().length() == 0) {
                javax.swing.JOptionPane.showMessageDialog(null, "Debe ingresar un articulo.", "Precaucion!", javax.swing.JOptionPane.WARNING_MESSAGE);
            } else if (this.jTextCantidad.getText().length() == 0) {
                javax.swing.JOptionPane.showMessageDialog(null, "Falta ingresar la cantidad.", "Precaucion!", javax.swing.JOptionPane.WARNING_MESSAGE);
            } else if (this.jTextUnitario.getText().length() == 0) {
                javax.swing.JOptionPane.showMessageDialog(null, "Falta ingresar el precio unitario del articulo.", "Precaucion!", javax.swing.JOptionPane.WARNING_MESSAGE);
            } else {

                float descuento;
                if (this.jTextDescuento.getText().length() == 0) {
                    descuento = 0;
                } else {
                    descuento = Float.parseFloat(this.jTextDescuento.getText());
                }

                DefaultTableModel model = (DefaultTableModel) this.jTableArticulos.getModel();
                float cantidad = Float.parseFloat(this.jTextCantidad.getText());
                float unitario = Float.parseFloat(this.jTextUnitario.getText());

                float subtotalar = unitario * cantidad - descuento;
                model.addRow(new Object[]{this.jTextArticulo.getText(), cantidad, unitario, descuento, subtotalar});
                this.ListaArticulo.add(articulo_seleccionado);
                this.articulo_seleccionado = null;
                this.jTextArticulo.setText("");
                this.jTextCantidad.setText("");
                this.jTextUnitario.setText("");
                this.jTextDescuento.setText("");
                this.jTextSubTotalArt.setText("");
                this.jTextArticulo.requestFocus();
                if (this.jCheckBoxIvaInc.isSelected()) {
                    this.CalcularTotales_conIVA_inc();
                } else {
                    this.CalcularTotales_sinIVA_inc();
                }
            }
        } else if (((caracter < '0')
                || (caracter > '9'))
                && (caracter != '\b')) {
            evt.consume();  // ignorar el evento de teclado
        } else if (!((caracter < '0') || (caracter > '9'))) {
            int cant = 0, un = 0;
            if (this.jTextCantidad.getText().length() > 0) {
                cant = Integer.parseInt(this.jTextCantidad.getText());
            }
            if (this.jTextUnitario.getText().length() > 0) {
                un = Integer.parseInt(this.jTextUnitario.getText());
            }

            int sub = cant * un - Integer.parseInt(Character.toString(caracter));
            this.jTextSubTotalArt.setText(Integer.toString(sub));
        }
    }//GEN-LAST:event_jTextDescuentoKeyPressed

    private void jTextSubTotalArtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextSubTotalArtKeyPressed

    }//GEN-LAST:event_jTextSubTotalArtKeyPressed

    private void jButtonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCerrarActionPerformed

    private void jTextUnitarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextUnitarioKeyTyped
        char caracter = evt.getKeyChar();
        if (((caracter < '0')
                || (caracter > '9'))
                && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }//GEN-LAST:event_jTextUnitarioKeyTyped

    private void jTextCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCantidadKeyTyped
        char caracter = evt.getKeyChar();
        if (((caracter < '0')
                || (caracter > '9'))
                && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }//GEN-LAST:event_jTextCantidadKeyTyped

    private void jTextDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDescuentoKeyTyped
        char caracter = evt.getKeyChar();
        if (((caracter < '0')
                || (caracter > '9'))
                && (caracter != '\b' /*corresponde a BACK_SPACE*/)) {
            evt.consume();  // ignorar el evento de teclado
        }
    }//GEN-LAST:event_jTextDescuentoKeyTyped

    private void jCheckBoxIvaIncItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxIvaIncItemStateChanged
        if (this.jCheckBoxIvaInc.isSelected()) {
            this.CalcularTotales_conIVA_inc();
        } else {
            this.CalcularTotales_sinIVA_inc();
        }
    }//GEN-LAST:event_jCheckBoxIvaIncItemStateChanged

    private void jCBTipoComprobanteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBTipoComprobanteItemStateChanged
        if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Contado")) {
            this.ListaFacparaNC(false);
        } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Crédito")) {
            this.ListaFacparaNC(false);
        } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Devolución Contado")) {
            this.ListaFacparaNC(false);
        } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Nota de crédito")) {
            this.ListaFacparaNC(true);
        }
    }//GEN-LAST:event_jCBTipoComprobanteItemStateChanged

    private void jMenuItemModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemModificarActionPerformed
        this.jTextSerie.setEditable(true);
        this.jTextNumeroFact.setEditable(true);
        this.jDateChooser.setEnabled(true);
        this.jCBTipoComprobante.setEnabled(true);
        this.jCBMoneda.setEnabled(true);
        this.jButtonModificar.setVisible(true);
        this.jPanelSetArticulo.setVisible(true);

        this.jPanelModificar.setVisible(true);
    }//GEN-LAST:event_jMenuItemModificarActionPerformed

    private void jMenuItemEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemEliminarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed

        Factura fac = this.f;

        if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Contado")) {
            fac.setTipo(tipoComprobante.Contado);
            fac.setCotizacion(45);
            fac.setSerieComprobante(this.jTextSerie.getText());
            fac.setNroComprobante(Integer.parseInt(this.jTextNumeroFact.getText()));
            fac.setPendiente(0);
            fac.setTotal(Float.parseFloat(this.jTextTOTAL.getText()));
            fac.setFecha(this.jDateChooser.getDate());
            fac.setProveedor(this.getProveedor());

            if (this.jCBMoneda.getSelectedItem() == tipoMoneda.$U) {
                fac.setMoneda(tipoMoneda.$U);
            } else if (this.jCBMoneda.getSelectedItem() == tipoMoneda.US$) {
                fac.setMoneda(tipoMoneda.US$);
            }

            fac.getFp_s().clear();

//          List<F_P> listaf_p = new ArrayList<F_P>();
            DefaultTableModel modelo = (DefaultTableModel) jTableArticulos.getModel();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                F_P f_p = new F_P();
                f_p.setFactura(fac);
                f_p.setArticulo(this.ListaArticulo.get(i));

                String c = modelo.getValueAt(i, 1).toString();
                float cant = Float.parseFloat(c);
                f_p.setCantidad(cant);

                String p = modelo.getValueAt(i, 2).toString();
                float precio = Float.parseFloat(p);
                f_p.setPrecio(precio);

                String d = modelo.getValueAt(i, 3).toString();
                float descuento = Float.parseFloat(d);
                f_p.setDescuento(descuento);

                this.ListaArticulo.get(i).addF_P(f_p);

                fac.getFp_s().add(f_p);
            }
//            fac.setFp_s(listaf_p);

        } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Crédito")) {
            fac.setTipo(tipoComprobante.Credito);
            fac.setCotizacion(45);
            fac.setSerieComprobante(this.jTextSerie.getText());
            fac.setNroComprobante(Integer.parseInt(this.jTextNumeroFact.getText()));
            fac.setPendiente(Float.parseFloat(this.jTextTOTAL.getText()));
            fac.setTotal(Float.parseFloat(this.jTextTOTAL.getText()));
            fac.setFecha(this.jDateChooser.getDate());
            fac.setProveedor(this.getProveedor());

            if (this.jCBMoneda.getSelectedItem() == tipoMoneda.$U) {
                fac.setMoneda(tipoMoneda.$U);
            } else if (this.jCBMoneda.getSelectedItem() == tipoMoneda.US$) {
                fac.setMoneda(tipoMoneda.US$);
            }

            fac.getFp_s().clear();

//          List<F_P> listaf_p = new ArrayList<F_P>();
            DefaultTableModel modelo = (DefaultTableModel) jTableArticulos.getModel();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                F_P f_p = new F_P();
                f_p.setFactura(fac);
                f_p.setArticulo(this.ListaArticulo.get(i));

                String c = modelo.getValueAt(i, 1).toString();
                float cant = Float.parseFloat(c);
                f_p.setCantidad(cant);

                String p = modelo.getValueAt(i, 2).toString();
                float precio = Float.parseFloat(p);
                f_p.setPrecio(precio);

                String d = modelo.getValueAt(i, 3).toString();
                float descuento = Float.parseFloat(d);
                f_p.setDescuento(descuento);

                this.ListaArticulo.get(i).addF_P(f_p);

                fac.getFp_s().add(f_p);
            }
//            fac.setFp_s(listaf_p);
        } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Devolución Contado")) {
            fac.setTipo(tipoComprobante.DevolucionContado);
            fac.setCotizacion(45);
            fac.setSerieComprobante(this.jTextSerie.getText());
            fac.setNroComprobante(Integer.parseInt(this.jTextNumeroFact.getText()));
            fac.setPendiente(0);
            fac.setTotal(Float.parseFloat(this.jTextTOTAL.getText()));
            fac.setFecha(this.jDateChooser.getDate());
            fac.setProveedor(this.getProveedor());

            if (this.jCBMoneda.getSelectedItem() == tipoMoneda.$U) {
                fac.setMoneda(tipoMoneda.$U);
            } else if (this.jCBMoneda.getSelectedItem() == tipoMoneda.US$) {
                fac.setMoneda(tipoMoneda.US$);
            }

            fac.getFp_s().clear();

//          List<F_P> listaf_p = new ArrayList<F_P>();
            DefaultTableModel modelo = (DefaultTableModel) jTableArticulos.getModel();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                F_P f_p = new F_P();
                f_p.setFactura(fac);
                f_p.setArticulo(this.ListaArticulo.get(i));

                String c = modelo.getValueAt(i, 1).toString();
                float cant = Float.parseFloat(c);
                f_p.setCantidad(cant);

                String p = modelo.getValueAt(i, 2).toString();
                float precio = Float.parseFloat(p);
                f_p.setPrecio(precio);

                String d = modelo.getValueAt(i, 3).toString();
                float descuento = Float.parseFloat(d);
                f_p.setDescuento(descuento);

                this.ListaArticulo.get(i).addF_P(f_p);

                fac.getFp_s().add(f_p);
            }
//            fac.setFp_s(listaf_p);
        } else if (this.jCBTipoComprobante.getSelectedItem().toString().equals("Nota de crédito")) {
            fac.setTipo(tipoComprobante.NotaCredito);
        }
        //fac.setUsuario(usuario);
        Conexion.getInstance().merge(fac);
        javax.swing.JOptionPane.showMessageDialog(null, "Factura fue modificada exitosamente.", "Enhorabuena", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        this.jTextSerie.setEditable(false);
        this.jTextNumeroFact.setEditable(false);
        this.jDateChooser.setEnabled(false);
        this.jCBTipoComprobante.setEnabled(false);
        this.jCBMoneda.setEnabled(false);
//        this.jButtonModificar.setVisible(true);
        this.jPanelSetArticulo.setVisible(false);

        this.jPanelModificar.setVisible(false);
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jTableArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableArticulosMouseClicked
        DefaultTableModel modelo = (DefaultTableModel) jTableArticulos.getModel();
        this.jTextArticulo.setText(modelo.getValueAt(this.jTableArticulos.getSelectedRow(), 0).toString());
        this.jTextCantidad.setText(modelo.getValueAt(this.jTableArticulos.getSelectedRow(), 1).toString());
        this.jTextUnitario.setText(modelo.getValueAt(this.jTableArticulos.getSelectedRow(), 2).toString());
        this.jTextDescuento.setText(modelo.getValueAt(this.jTableArticulos.getSelectedRow(), 3).toString());
        this.articulo_seleccionado = this.ListaArticulo.get(this.jTableArticulos.getSelectedRow());
        this.ListaArticulo.remove(this.jTableArticulos.getSelectedRow());
        modelo.removeRow(this.jTableArticulos.getSelectedRow());
    }//GEN-LAST:event_jTableArticulosMouseClicked

    private void jButtonCerrarModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCerrarModActionPerformed
        this.jTextSerie.setEditable(false);
        this.jTextNumeroFact.setEditable(false);
        this.jDateChooser.setEnabled(false);
        this.jCBTipoComprobante.setEnabled(false);
        this.jCBMoneda.setEnabled(false);
//        this.jButtonModificar.setVisible(true);
        this.jPanelSetArticulo.setVisible(false);

        this.jPanelModificar.setVisible(false);
    }//GEN-LAST:event_jButtonCerrarModActionPerformed

    private void jTextArticuloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArticuloFocusLost
        if (this.jTextArticulo.getText().length() > 0 && this.articulo_seleccionado == null && !buscarart) {
            articulosBuscador AB = new articulosBuscador(this.jTextArticulo.getText(), this);
            if (AB.sizeconsulta > 1) {
                AB.show();
                this.jTextArticulo.transferFocus();
            } else if (AB.sizeconsulta == 1) {
                this.jTextArticulo.transferFocus();
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Ese articulo no existe", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                this.jTextArticulo.setText("");
            }
        }
        buscarart = false;
    }//GEN-LAST:event_jTextArticuloFocusLost

    private void jTextArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArticuloKeyTyped

    }//GEN-LAST:event_jTextArticuloKeyTyped

    private void jDateChooserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDateChooserFocusLost
        //Datos de entrada al web service.      
    }//GEN-LAST:event_jDateChooserFocusLost

    private void jCheckBoxIvaIncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxIvaIncActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxIvaIncActionPerformed

    private void jCBMonedaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBMonedaItemStateChanged
        // TODO add your handling code here:
        Date fechaSeleccionada = this.jDateChooser.getDate();
        //A la fecha seleccionada hay que restarle 1 día.
        Conexion.getInstance().comprobarFechaCotizacion();
    }//GEN-LAST:event_jCBMonedaItemStateChanged

    private void CalcularTotales_sinIVA_inc() {
        float subtotal = 0, iva_minimo = 0, iva_basico = 0, total = 0;

        DefaultTableModel modelo = (DefaultTableModel) jTableArticulos.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String v = modelo.getValueAt(i, 4).toString();
            float val = Float.parseFloat(v);
            subtotal = subtotal + val;

            Articulo a = this.ListaArticulo.get(i);
            if (a.getIva().getTipo() == tipoIVA.Minimo) {
                float iv = (val / 100) * a.getIva().getPorcentaje();
                iva_minimo = iva_minimo + iv;
            } else if (a.getIva().getTipo() == tipoIVA.Basico) {
                float iv = (val / 100) * a.getIva().getPorcentaje();
                iva_basico = iva_basico + iv;
            }

        }
        total = subtotal + iva_minimo + iva_basico;
        this.jTextIVAminimo.setText(String.valueOf(iva_minimo));
        this.jTextIVAbasico.setText(String.valueOf(iva_basico));
        this.jTextSubTotal.setText(String.valueOf(subtotal));
        this.jTextTOTAL.setText(String.valueOf(total));
    }

    private void CalcularTotales_conIVA_inc() {
        float subtotal = 0, iva_minimo = 0, iva_basico = 0, total = 0;
        
        //Subtotal excento, basico y minimo.
        float subtotalExcento = 0;
        float subtotalBasico = 0;
        float subtotalMinimo = 0;
        
        DefaultTableModel modelo = (DefaultTableModel) jTableArticulos.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Articulo a = this.ListaArticulo.get(i);
            String v = modelo.getValueAt(i, 4).toString();
            float val = Float.parseFloat(v);

            if (a.getIva().getTipo() == tipoIVA.Minimo) {               
                float porcentajeIVA = a.getIva().getPorcentaje();
                subtotalMinimo = subtotalMinimo + (val / (1 + (porcentajeIVA / 100)));
                iva_minimo = iva_minimo + (subtotalMinimo * a.getIva().getPorcentaje() / 100);
            } else if (a.getIva().getTipo() == tipoIVA.Basico) {
                float porcentajeIVA = a.getIva().getPorcentaje();
                subtotalBasico = subtotalBasico + (val / (1 + (porcentajeIVA / 100)));
                iva_basico = iva_basico + (subtotalBasico * a.getIva().getPorcentaje() / 100);
            } else if (a.getIva().getTipo() == tipoIVA.Excento) {
                subtotalExcento = subtotalExcento + val;
            }

        }

        subtotal = subtotalExcento + subtotalMinimo + subtotalBasico;
        total = subtotal + iva_minimo + iva_basico;

        this.jTextIVAminimo.setText(String.valueOf(iva_minimo));
        this.jTextIVAbasico.setText(String.valueOf(iva_basico));
        this.jTextSubTotal.setText(String.valueOf(subtotal));
        this.jTextTOTAL.setText(String.valueOf(total));
    }

    private void ListaFacparaNC(boolean b) {
        this.jLabel17.setVisible(b);
        this.jListFacturaspNC.setVisible(b);
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
            java.util.logging.Logger.getLogger(AltaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AltaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AltaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AltaFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AltaFactura().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButtonAgregarArticulo;
    private javax.swing.JButton jButtonCerrar;
    private javax.swing.JButton jButtonCerrarMod;
    private javax.swing.JButton jButtonIngresar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JComboBox<String> jCBMoneda;
    private javax.swing.JComboBox<Proveedor> jCBProveedor;
    private javax.swing.JComboBox<String> jCBTipoComprobante;
    private javax.swing.JCheckBox jCheckBoxIvaInc;
    private com.toedter.calendar.JDateChooser jDateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jListFacturaspNC;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdicion;
    private javax.swing.JMenuItem jMenuItemEliminar;
    private javax.swing.JMenuItem jMenuItemModificar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelModificar;
    private javax.swing.JPanel jPanelSetArticulo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableArticulos;
    private javax.swing.JTextField jTextArticulo;
    private javax.swing.JTextField jTextCantidad;
    private javax.swing.JTextField jTextDescuento;
    private javax.swing.JTextField jTextDireccion;
    private javax.swing.JTextField jTextIVAbasico;
    private javax.swing.JTextField jTextIVAminimo;
    private javax.swing.JTextField jTextNumeroFact;
    private javax.swing.JTextField jTextRut;
    private javax.swing.JTextField jTextSerie;
    private javax.swing.JTextField jTextSubTotal;
    private javax.swing.JTextField jTextSubTotalArt;
    private javax.swing.JTextField jTextTOTAL;
    private javax.swing.JTextField jTextUnitario;
    private javax.swing.JLabel labelCotizacion;
    // End of variables declaration//GEN-END:variables
}
