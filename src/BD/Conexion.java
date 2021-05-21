/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import Clases.Administrador;
import Clases.Articulo;
import Clases.Comprobante;
import Clases.Cotizacion;
import Clases.Factura;
import Clases.Empleado;
import Clases.Historial;
import Clases.IVA;
import Clases.Proveedor;
import Clases.Recibo;
import Clases.Usuario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author joaco
 */
public class Conexion {

    private Conexion() {
    }

    public static Conexion getInstance() {
        return ConexionHolder.INSTANCE;
    }

 private static class ConexionHolder {

        private static final Conexion INSTANCE = new Conexion();
        private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Tres_Flores_-_Proveedores_2.0PU");
        private static final EntityManager em = emf.createEntityManager();
    }

    public EntityManager getEntity() {
        return ConexionHolder.em;
    }

    public boolean persist(Object object) {
        EntityManager em = getEntity();
        em.getTransaction().begin();
        boolean retorno = false;
        try {
            em.persist(object);
            em.getTransaction().commit();
            retorno = true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            retorno = false;
        }
        return retorno;
    }

    public void merge(Object object) {
        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public void delete(Object object) {
        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public void refresh(Object object) {
        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            em.refresh(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
    
    public void remove(Object object) {
        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
       
    public List<Articulo> listadoArticulos() {
        EntityManager em = getEntity();
        List<Articulo> listaProductos = null;
        em.getTransaction().begin();
        try {
            listaProductos = em.createNativeQuery("SELECT * FROM articulo", Articulo.class).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return listaProductos;
    }

    public boolean existeArticulo(String codigo) {
        EntityManager em = getEntity();
        Articulo a;
        em.getTransaction().begin();
        boolean retorno = false;
        try {
            a = (Articulo) em.createNativeQuery("SELECT * FROM articulo WHERE codigo = :codigo", Articulo.class)
                    .setParameter("codigo", codigo)
                    .getSingleResult();
            em.getTransaction().commit();
            if (a != null) {
                retorno = true;
            } else {
                retorno = false;
            }

        } catch (Exception e) {
            em.getTransaction().rollback();

        }
        return retorno;
    }

      public List<Proveedor> listadoProveedores(){
        EntityManager em = getEntity();
        List<Proveedor> listaProveedor = null;
        em.getTransaction().begin();
        try{
            listaProveedor = em.createNativeQuery("SELECT * FROM proveedor", Proveedor.class).getResultList();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return listaProveedor;
    }
 
    public Proveedor getProveedor(int codigo) {
        Proveedor p = new Proveedor();

        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            p = (Proveedor) em.createNativeQuery("SELECT * FROM proveedor WHERE codigo = :codigo", Proveedor.class)
                    .setParameter("codigo", codigo)
                    .getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }

        return p;
    }
    
    public Proveedor getProveedorxRazonSocial(String razonSocial) {
        Proveedor p = new Proveedor();

        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            p = (Proveedor) em.createNativeQuery("SELECT * FROM proveedor WHERE razonSocial = :razonSocial", Proveedor.class)
                    .setParameter("razonSocial", razonSocial)
                    .getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }

        return p;
    }

    public List<IVA> getIVAS() {
        List<IVA> ivas = null;
        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            ivas = em.createNativeQuery("SELECT * FROM iva", IVA.class).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return ivas;
    }
    
    public List<Articulo> getArticuloxNombre_Descripcion(String texto){
        
        texto = "%" + texto + "%";
        List<Articulo> listaA = null;
        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            listaA = em.createNativeQuery("SELECT * FROM articulo\n" +
                                                "WHERE nombre LIKE :texto\n" +
                                                "   OR descripcion LIKE :texto", Articulo.class)
                    .setParameter("texto", texto)
                    .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return listaA;
    }
    
    public boolean existeUsuario(String cedula) {
        boolean retorno = false;
        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            Usuario u = (Usuario) em.createNativeQuery("SELECT * FROM usuario WHERE cedula = :cedula", Usuario.class)
                    .setParameter("cedula", cedula)
                    .getSingleResult();
            em.getTransaction().commit();

            if (u != null) {
                retorno = true;
            } else {
                retorno = false;
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return retorno;
    }

    public Usuario loginUsuarioAdmin(String cedula) {
        EntityManager em = getEntity();
        em.getTransaction().begin();
        Usuario u = null;
        try {
            Administrador a = (Administrador) em.createNativeQuery("SELECT U.*, A.superAdmin FROM usuario AS U INNER JOIN administrador AS A "
                    + "ON U.cedula = A.cedula WHERE A.cedula = :cedula", Administrador.class)
                    .setParameter("cedula", cedula)
                    .getSingleResult();
            em.getTransaction().commit();

            if (a != null) {
                u = (Administrador) a;
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return u;
    }

    public Usuario loginUsuarioEmpleado(String cedula) {
        EntityManager em = getEntity();
        em.getTransaction().begin();
        Usuario u = null;
        Empleado emp = null;
        try {
             emp = (Empleado) em.createNativeQuery("SELECT U.cedula, U.nombre, U.apellido, U.contrasenia FROM usuario AS U INNER JOIN empleado AS E "
                    + "ON U.cedula = E.cedula WHERE E.cedula = :cedula", Empleado.class)
                    .setParameter("cedula", cedula)
                    .getSingleResult();
            em.getTransaction().commit();
            
            if(emp != null)
                u = (Empleado)emp;

        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return u;
    }

    public IVA getIva_de_Articulo(Articulo art){
        EntityManager em = getEntity();
        IVA iva = null;
        em.getTransaction().begin();
        try {
            iva = (IVA) em.createNativeQuery("SELECT `iva`.* FROM `iva`, `articulo` WHERE `articulo`.`codigo`=:codigo AND `articulo`.`iva_id`=`iva`.`id`", Articulo.class)
                    .setParameter("codigo", art.getCodigo())
                    .getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

        }
        return iva;
    }
    
    public List<Factura> ListarFacturas(Proveedor p){
        EntityManager em = getEntity();
        List<Factura> listaFacturas = null;
        em.getTransaction().begin();
        try{
            listaFacturas = em.createNativeQuery("SELECT factura.*, comprobante.* FROM factura INNER JOIN comprobante WHERE factura.serieComprobante = comprobante.serieComprobante "
                    + "AND factura.nroComprobante = comprobante.nroComprobante AND comprobante.proveedor_codigo = :codigo", Factura.class)
                    .setParameter("codigo", p.getCodigo()).getResultList();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return listaFacturas;
    }

    public List<Factura> ListarFacturasPorFecha(int codigoProveedor, Date fechaDesde, Date fechaHasta) {
        EntityManager em = getEntity();
        List<Factura> listaFacturas = null;
        em.getTransaction().begin();              
        try {
            listaFacturas = em.createNativeQuery("SELECT factura.*, comprobante.* FROM factura INNER JOIN comprobante WHERE factura.serieComprobante = comprobante.serieComprobante "
                    + "AND factura.nroComprobante = comprobante.nroComprobante AND comprobante.proveedor_codigo = :codigo "
                    + "AND comprobante.fecha >= :fechaDesde AND comprobante.fecha <= :fechaHasta ORDER BY comprobante.fecha ASC", Factura.class)
                    .setParameter("codigo", codigoProveedor)
                    .setParameter("fechaDesde", fechaDesde)
                    .setParameter("fechaHasta", fechaHasta)
                    .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return listaFacturas;
    }
    
    public List<Factura> ListarFacturasBienSencillo(){
        EntityManager em = getEntity();
        List<Factura> listaFacturas = null;
        em.getTransaction().begin();
        try{
            listaFacturas = em.createNativeQuery("SELECT * FROM factura", Factura.class).getResultList();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return listaFacturas;
    }

    public List<Recibo> listarRecibos(int codigo) {
        EntityManager em = getEntity();
        List<Recibo> recibos = null;
        em.getTransaction().begin();
        try {
            recibos = em.createNativeQuery("SELECT recibo.*, comprobante.* FROM recibo INNER JOIN comprobante WHERE recibo.serieComprobante = comprobante.serieComprobante "
                    + "AND recibo.nroComprobante = comprobante.nroComprobante AND comprobante.proveedor_codigo = :codigo", Recibo.class)
                    .setParameter("codigo", codigo).getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return recibos;
    }

    public List<Recibo> listarRecibosPorFecha(int codigo, Date fechaDesde, Date fechaHasta) {
        EntityManager em = getEntity();
        List<Recibo> listaRecibos = null;
        em.getTransaction().begin();
        try {
            listaRecibos = em.createNativeQuery("SELECT recibo.*, comprobante.* FROM recibo INNER JOIN comprobante WHERE recibo.serieComprobante = comprobante.serieComprobante "
                    + "AND recibo.nroComprobante = comprobante.nroComprobante AND comprobante.proveedor_codigo = :codigo "
                    + "AND comprobante.fecha >= :fechaDesde AND comprobante.fecha <= :fechaHasta ORDER BY comprobante.fecha ASC", Recibo.class)
                    .setParameter("codigo", codigo)
                    .setParameter("fechaDesde", fechaDesde)
                    .setParameter("fechaHasta", fechaHasta)
                    .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return listaRecibos;
    }

    public List<Comprobante> listarComprobantes(int codigo) {
        EntityManager em = getEntity();
        List<Comprobante> listaComprobantes = new ArrayList<>();
        em.getTransaction().begin();
        try {
            listaComprobantes = em.createNativeQuery("SELECT comprobante.* FROM comprobante"
                    + " WHERE proveedor_codigo = :codigo ORDER BY fecha ASC", Comprobante.class)
                    .setParameter("codigo", codigo)
                    .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }

        return listaComprobantes;
    }
    
    
    public List<Comprobante> listarComprobantesPorFecha(int codigo, Date fechaDesde, Date fechaHasta) {
        EntityManager em = getEntity();
        List<Comprobante> listaComprobantes = null;
        em.getTransaction().begin();
        try {
            listaComprobantes = em.createNativeQuery("SELECT comprobante.* FROM comprobante"
                    + " WHERE proveedor_codigo = :codigo AND comprobante.fecha >= :fechaDesde AND comprobante.fecha <= :fechaHasta"
                    + " ORDER BY fecha ASC", Comprobante.class)
                    .setParameter("codigo", codigo)
                    .setParameter("fechaDesde", fechaDesde)
                    .setParameter("fechaHasta", fechaHasta)
                    .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }

        return listaComprobantes;
    }
   
    public List<Historial> listarHistorialArticulo(String codigo) {
        List<Historial> lista  = null;

        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            lista =  em.createNativeQuery("SELECT * FROM historial WHERE articulo_codigo = :codigo", Historial.class)
                    .setParameter("codigo", codigo)
                    .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }

        return lista;
    }

    public List<Factura> ListarFacturasCredito(Proveedor p){
        EntityManager em = getEntity();
        List<Factura> listaFacturas = null;
        em.getTransaction().begin();
        try{
            listaFacturas = em.createNativeQuery("SELECT factura.*, comprobante.* FROM factura INNER JOIN comprobante WHERE factura.serieComprobante = comprobante.serieComprobante "
                    + "AND factura.nroComprobante = comprobante.nroComprobante AND factura.tipo = 1 AND comprobante.proveedor_codigo = :codigo", Factura.class)
                    .setParameter("codigo", p.getCodigo())
                    .getResultList();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return listaFacturas;
    }

    public boolean existeFecha(Date fechaConvertida) {
        boolean retorno;
        Cotizacion cotizacion;
       
        SimpleDateFormat getAnioFormato = new SimpleDateFormat("yyyy");
        SimpleDateFormat getMesFormato = new SimpleDateFormat("MM");
        SimpleDateFormat getDiaFormato = new SimpleDateFormat("dd");

        int anio = Integer.parseInt(getAnioFormato.format(fechaConvertida));
        int mes = Integer.parseInt(getMesFormato.format(fechaConvertida));
        int dia = Integer.parseInt(getDiaFormato.format(fechaConvertida));

        String fecha = "'" + anio + "-" + mes + "-" + dia + "'";

        EntityManager em = getEntity();
        em.getTransaction().begin();
        try {
            cotizacion = (Cotizacion) em.createNativeQuery("SELECT * FROM cotizacion WHERE DATE(cotizacion.fecha) = "  + fecha + "", Cotizacion.class)
                    .getSingleResult();
            em.getTransaction().commit();

            if (cotizacion != null) {
                retorno = true;
            } else {
                retorno = false;
            }

        } catch (Exception e) {
            retorno = false;
            em.getTransaction().rollback();

        }
        return retorno;

    }

    public List<Cotizacion> listarCotizaciones() {
        EntityManager em = getEntity();
        List<Cotizacion> listaCotizaciones = null;
        em.getTransaction().begin();
        try {
            listaCotizaciones = em.createNativeQuery("SELECT * FROM cotizacion ORDER BY fecha ASC", Cotizacion.class)
                    .getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return listaCotizaciones;
    }

    public void comprobarFechaCotizacion() {
        
    }
    
    public List<Proveedor> listarProveedoresArticulo(String codigo){
        EntityManager em = getEntity();
        List<Proveedor> listaProveedor = null;
        em.getTransaction().begin();
        try{
            listaProveedor = em.createNativeQuery("SELECT p.* FROM proveedor as p,articulo_proveedor as ap WHERE ap.proveedores_codigo = p.codigo AND p.deshabilitado = false AND ap.articulos_codigo = :codigo", Proveedor.class)
                    .setParameter("codigo", codigo)
                    .getResultList();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return listaProveedor;
    
    }
    
    public List<Historial> listarHistorialProveedorArticulo(String codigoArt,int codigoPro){
        EntityManager em = getEntity();
        List<Historial> listaHistorial = null;
        em.getTransaction().begin();
        try{
            listaHistorial = em.createNativeQuery("SELECT * FROM historial WHERE articulo_codigo = :codigoArt AND proveedor_codigo = :codigoPro ", Historial.class)
                    .setParameter("codigoArt", codigoArt)
                    .setParameter("codigoPro", codigoPro)
                    .getResultList();
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }
        return listaHistorial;
    
    }

}

