package VISTA;

import ENTIDAD.persona;
import ENTIDAD.producto;
import MODELO.detalle_reciboDao;
import MODELO.generar_cod_recibo;
import MODELO.personaDao;
import MODELO.productoDao;
import MODELO.reciboDao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class recibo_cliente extends javax.swing.JFrame {

    MODELO.productoDao mp = new productoDao();
    MODELO.personaDao mper = new personaDao();
    MODELO.reciboDao mr = new reciboDao();
    MODELO.detalle_reciboDao mdr = new detalle_reciboDao();
    ENTIDAD.producto ep = new producto();
    ENTIDAD.persona eper = new persona();
    DefaultTableModel m2;
    Object[] obj = new Object[4];
    double monto;int num=0;
    
    public recibo_cliente() {
        initComponents();
        this.setLocationRelativeTo(null);
        cargarTabla();
        tablaRecibo.setRowHeight(25);
        monto = home_cliente.monto;
        txtmonto.setText("$. " + monto + "0");
    }
    
    void cargarTabla(){
        m2 = (DefaultTableModel) tablaRecibo.getModel();
        for (int i = 0; i < home_cliente.filas; i++) {
            obj[0] = home_cliente.m1.getValueAt(i, 0);
            obj[1] = home_cliente.m1.getValueAt(i, 1);
            obj[2] = home_cliente.m1.getValueAt(i, 2);
            obj[3] = home_cliente.m1.getValueAt(i, 3);
            m2.addRow(obj);
        }
    }
    
    //SE OBTIENE UN NUMERO PARA SIMULAR CUANTAS VECES SE PUEDE IMPRIMIR EL RECIBO ANTES QUE SE QUEDE SIN PAPEL O SE TRABE
    int numTope(){
        int a = 0;
        String num = ""+mr.maxIdRecibo();
        char r;
        if(num==null){
            r = 1;
        }else if(mr.maxIdRecibo()<10){
            r = num.charAt(0);
        }else if(mr.maxIdRecibo()<100 && mr.maxIdRecibo()>=10){
            r = num.charAt(1);
        }else{
            r = num.charAt(2);
        }
        a = Integer.parseInt(""+r);
        return a;
    }
    
    //este metodo genera el codigo de los recibos
    String generarCod(){
        String cod_rec = "";
        String serie = mr.maxCodRecibo();
        int j;
        if(serie == null){
            cod_rec = "REC001";
        }else{
            char r1 = serie.charAt(3);
            char r2 = serie.charAt(4);
            char r3 = serie.charAt(5);
            String r="";
            r=""+r1+r2+r3;
            
            j=Integer.parseInt(r);
            MODELO.generar_cod_recibo gen = new generar_cod_recibo();
            gen.generar(j);
            serie = "REC" + gen.serie();
            cod_rec = serie;
        }
        return cod_rec;
    }
    
    void ingresar() throws MessagingException{
        if(txtdni.getText().equals("") || txtnombre.getText().equals("") || txtdireccion.getText().equals("") || txtemail.getText().equals("") || txtapellido.getText().equals("") ||txtnacimiento.getDate()==null){
            JOptionPane.showMessageDialog(null, "Campos de textos vacios");
            txtdni.requestFocus();
        }else{
            if(numTope()!=0){
                metodoParaIngresar();
            }else{
                int input = JOptionPane.showConfirmDialog(null,"¿Ya arreglo la hoja atorada o repuso mas papeles?", "Seleccione una opcion...",JOptionPane.YES_NO_OPTION);

                // 0=yes, 1=no
                if(input == 0){
                   metodoParaIngresar();
                }else if(input == 1){
                   JOptionPane.showMessageDialog(null, "Ingrese hoja o mire si hay hojas atascadas");
                }
            }
        }
    }
    
    void metodoParaIngresar() throws MessagingException{
        //INGRESAR PERSONA
        Date f = txtnacimiento.getDate();
        DateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
        String fechana = f2.format(f);

        String dni = txtdni.getText();
        String nom = txtnombre.getText();
        String ape = txtapellido.getText();
        String email = txtemail.getText();
        String dir = txtdireccion.getText();
        int id_tper = 1;

        Object[] ob = new Object[7];

        ob[0] = dni;
        ob[1] = nom;
        ob[2] = ape;
        ob[3] = email;
        ob[4] = dir;
        ob[5] = fechana;
        ob[6] = id_tper;

        int id_per = 0; 
        
        if(mper.obtenerIdPersonaPorDNI(dni)==0){
            int r1 = mper.addPersona(ob);

            if(r1>0){
                JOptionPane.showMessageDialog(null, "Datos de empleado ingresados correctamente");
            }
            
            id_per = mper.idPersona();
        }else{
            id_per = mper.obtenerIdPersonaPorDNI(dni);
        }

        //INGRESAR RECIBO
        String cod_rec = generarCod();
        DateTimeFormatter fechitaa = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String fec_rec = ""+fechitaa.format(LocalDateTime.now());
        double mon_rec = monto;
        int est_rec = 1;

        Object[] ob2 = new Object[5];

        ob2[0] = cod_rec;
        ob2[1] = fec_rec;
        ob2[2] = mon_rec;
        ob2[3] = est_rec;
        ob2[4] = id_per;

        int r2 = mr.addRecibo(ob2);

        if(r2>0){
            JOptionPane.showMessageDialog(null, "Datos de recibo ingresados correctamente");
        }

        //INGRESAR DETALLE RECIBO
        for(int i=0;i<tablaRecibo.getRowCount();i++){
            int id_pro = Integer.parseInt(tablaRecibo.getValueAt(i, 0).toString());
            int id_rec = mr.idRecibo();
            int cant_drec = Integer.parseInt(tablaRecibo.getValueAt(i, 2).toString());
            double sub_drec = Double.parseDouble(tablaRecibo.getValueAt(i, 3).toString());

            Object[] ob3 = new Object[4];

            ob3[0] = id_pro;
            ob3[1] = id_rec;
            ob3[2] = cant_drec;
            ob3[3] = sub_drec;

            mdr.addRecibo(ob3);

        }
        JOptionPane.showMessageDialog(null, "Datos de recibo ingresados correctamente");

        eper = mper.datosPersona_id(id_per);
        
        JOptionPane.showMessageDialog(null, "Abriendo Recibo de Pago","Mensaje",1);
        try {
            pdf(cod_rec, fec_rec, eper.getDni_per(), eper.getNom_per(), eper.getApe_per(), eper.getDir_per(), email, mon_rec);
        } catch (Exception e) {
        }
        //abre el pdf generado
        abrirPDF(cod_rec);

        enviarRecibo(cod_rec);

        //AUMENTAMOS EL NUM++ PARA CONTAR CUANTAS VECES SE ESTA IMPRIMIENDO LOS RECIBOS
        num++;

        //REGRESAR AL MAIN
        main main = new main();
        main.setVisible(true);
        dispose();
    }
    
    //metodo que me crea un pdf del recibo
    public void pdf(String codigo, String fecha, String dni, String nombre, String ape, String dir, String email, double pago) throws FileNotFoundException, DocumentException {
        FileOutputStream archivo = new FileOutputStream(codigo+".pdf");
        Document documento = new Document();
        PdfWriter.getInstance(documento, archivo);
        documento.open();
                
        try {
            Font negrita1 = new Font(Font.FontFamily.HELVETICA,14,Font.BOLD,BaseColor.BLACK);
            Font negrita3 = new Font(Font.FontFamily.HELVETICA,12,Font.BOLD,BaseColor.BLACK);
            Font negrita5 = new Font(Font.FontFamily.HELVETICA,12,Font.BOLD,BaseColor.WHITE);
            Font negrita2 = new Font(Font.FontFamily.HELVETICA,22,Font.BOLD,BaseColor.BLACK);
            Font negrita4 = new Font(Font.FontFamily.HELVETICA,16,Font.BOLD,BaseColor.BLACK);

            PdfPTable encabezado = new PdfPTable(1);
            encabezado.setWidthPercentage(100);
            encabezado.getDefaultCell().setBorder(0);
            float[] clumnasEncabezado = new float[]{100f};
            encabezado.setWidths(clumnasEncabezado);
            encabezado.setHorizontalAlignment(Element.ALIGN_CENTER);

            Paragraph parrafo = new Paragraph("Recicla Pucallpa",negrita2);
            parrafo.setAlignment(1);
            documento.add(parrafo);
            
            
            Paragraph parrafo2 = new Paragraph("\nSalvemos el Planeta",negrita4);
            parrafo2.setAlignment(1);
            documento.add(parrafo2);
            
            Paragraph parrafo3 = new Paragraph("Telefono: (061) 263219",negrita4);
            parrafo3.setAlignment(1);
            documento.add(parrafo3);
            
            Paragraph titulo = new Paragraph("\nRecibo",negrita4);
            titulo.setAlignment(1);
            documento.add(titulo);
            Paragraph titulo2 = new Paragraph(codigo,negrita1);
            titulo2.setAlignment(1);
            documento.add(titulo2);
            
            //datos de la empresa
            documento.add(new Paragraph("\n_______________________________________________________"));
            documento.add(new Paragraph("Fecha:                   " + fecha));
            documento.add(new Paragraph("Empresa:              Recicladora Pucallpa"));
            documento.add(new Paragraph("Telefono:              (061) 263219"));
            
            //datos del cliente
            documento.add(new Paragraph("\n_______________________________________________________"));
            documento.add(new Paragraph("DNI:                       " + dni));
            documento.add(new Paragraph("Cliente:                  " + nombre + " " + ape));
            documento.add(new Paragraph("Email:                     " + email));
            documento.add(new Paragraph("Direccion:               " + dir));
            
            Paragraph titulo5 = new Paragraph("\nDetalles",negrita4);
            titulo5.setAlignment(1);
            documento.add(titulo5);
            
            //pago
            documento.add(new Paragraph("\n_______________________________________________________"));
            documento.add(new Paragraph("Pago:              $. " + pago + "0"));
            
            //prodcutos
            documento.add(new Paragraph("\nProductos",negrita3));
            documento.add(new Paragraph("\n"));
            PdfPTable tablaP = new PdfPTable(4);
            tablaP.setWidthPercentage(100);
            tablaP.getDefaultCell().setBorder(0);
            float[] clumnasP = new float[]{15f, 60f, 20f, 20f};
            tablaP.setWidths(clumnasP);
            tablaP.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell p1 = new  PdfPCell(new Phrase("Nro",negrita5));
            PdfPCell p2 = new  PdfPCell(new Phrase("Producto",negrita5));
            PdfPCell p3 = new  PdfPCell(new Phrase("Cantidad",negrita5));
            PdfPCell p4 = new  PdfPCell(new Phrase("Subtotal",negrita5));
            p1.setBorder(0);
            p2.setBorder(0);
            p3.setBorder(0);
            p4.setBorder(0);
            p1.setBackgroundColor(BaseColor.DARK_GRAY);
            p2.setBackgroundColor(BaseColor.DARK_GRAY);
            p3.setBackgroundColor(BaseColor.DARK_GRAY);
            p4.setBackgroundColor(BaseColor.DARK_GRAY);
            tablaP.addCell(p1);
            tablaP.addCell(p2);
            tablaP.addCell(p3);
            tablaP.addCell(p4);
            for(int i=0;i<tablaRecibo.getRowCount();i++){
                String des = tablaRecibo.getValueAt(i, 1).toString();
                int cant = Integer.parseInt(tablaRecibo.getValueAt(i, 2).toString());
                double sub = Double.parseDouble(tablaRecibo.getValueAt(i, 3).toString());
                int nroo= i+1;
                String nro = "" + nroo;
                String cantidad = "" + cant;
                String subtotal = "" + sub;
                
                tablaP.addCell(nro);
                tablaP.addCell(des);
                tablaP.addCell(cantidad);
                tablaP.addCell(subtotal);
            }
            
            documento.add(tablaP);
            
            Paragraph footer = new Paragraph("\n\nRecicla y cuida el medio ambiente");
            footer.setAlignment(1);
            documento.add(footer);
            
            Paragraph finall = new Paragraph("\n\n\nGracias", negrita3);
            finall.setAlignment(Element.ALIGN_CENTER);
            documento.add(finall);
            
        } catch (BadElementException ex) {
            System.out.println("" + ex);
        }
        documento.close();
        
    }
    
    //metodo que me abre el pdf generado
    public void abrirPDF(String codigo){
        try {
            File path = new File(codigo + ".pdf");
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            System.out.println("Error al abrir el pdf " + e);
        }
    }
    
    //ENVIAR SU RECIBO A SU CORREO
    void enviarRecibo(String cod) throws AddressException, MessagingException{
        String correo = "recicla.prueba.purcallpa@gmail.com";
        String contraa = "bkxmwanmbmpvuceq";
        String correoD = txtemail.getText();
        
        Properties p = new Properties();
        p.put("mail.smtp.host","smtp.gmail.com");
        p.setProperty("mail.smtp.starttls.enable", "true");
        p.put("mal.smtp.ssl.trust", "smtp.gmail.com");
        p.setProperty("mail.smtp.port", "587");
        p.setProperty("mail.smtp.user", correo);
        p.setProperty("mail.smtp.auth", "true");
        
        Session s = Session.getDefaultInstance(p);
        
        BodyPart texto = new MimeBodyPart();
        texto.setText("Hola, saludos cordiales. Muchas gracias por reciclar tus productos, aqui le adjuntamos su recibo de pago.");
        BodyPart adjunto = new MimeBodyPart();
        adjunto.setDataHandler(new DataHandler(new FileDataSource("C:\\Users\\jamt_\\Documents\\NetBeansProjects\\CASO2\\"+cod+".pdf")));
        adjunto.setFileName(cod + ".pdf");
        
        MimeMultipart m = new MimeMultipart();
        m.addBodyPart(texto);
        m.addBodyPart(adjunto);
        
        MimeMessage mensaje = new MimeMessage(s);
        mensaje.setFrom(new InternetAddress(correo));
        mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(correoD));
        mensaje.setSubject("Recibo de Pago - " + cod);
        mensaje.setContent(m);
        
        Transport t = s.getTransport("smtp");
        t.connect(correo,contraa);
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
        
        JOptionPane.showMessageDialog(null, "Recibo enviado al correo: " + correoD + " satisfactoriamente");
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnsalir = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRecibo = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        txtmonto = new javax.swing.JLabel();
        btngenerarrecibo = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtdni = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtapellido = new javax.swing.JTextField();
        txtemail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtdireccion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtnacimiento = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 720));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("SF Pro Text", 0, 12)); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(1200, 720));
        jPanel1.setMinimumSize(new java.awt.Dimension(1200, 720));

        jPanel5.setBackground(new java.awt.Color(6, 55, 58));
        jPanel5.setMaximumSize(new java.awt.Dimension(1200, 60));
        jPanel5.setMinimumSize(new java.awt.Dimension(1200, 60));

        btnsalir.setBackground(new java.awt.Color(31, 95, 91));
        btnsalir.setMaximumSize(new java.awt.Dimension(200, 50));
        btnsalir.setMinimumSize(new java.awt.Dimension(200, 50));
        btnsalir.setRequestFocusEnabled(false);
        btnsalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnsalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnsalirMouseExited(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SF Pro", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Salir");

        javax.swing.GroupLayout btnsalirLayout = new javax.swing.GroupLayout(btnsalir);
        btnsalir.setLayout(btnsalirLayout);
        btnsalirLayout.setHorizontalGroup(
            btnsalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnsalirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnsalirLayout.setVerticalGroup(
            btnsalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsalirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(994, Short.MAX_VALUE)
                    .addComponent(btnsalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addGap(5, 5, 5)
                    .addComponent(btnsalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jSeparator1.setBackground(new java.awt.Color(6, 55, 58));
        jSeparator1.setForeground(new java.awt.Color(6, 55, 58));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel1.setFont(new java.awt.Font("SF Pro Text", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(6, 55, 58));
        jLabel1.setText("Informacion del cliente");

        jLabel4.setFont(new java.awt.Font("SF Pro Text", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(6, 55, 58));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Recibo");

        jLabel12.setFont(new java.awt.Font("SF Pro Text", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(6, 55, 58));
        jLabel12.setText("Productos");

        tablaRecibo.setFont(new java.awt.Font("SF Pro", 0, 14)); // NOI18N
        tablaRecibo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PRODUCTO", "CANTIDAD", "SUBTOTAL"
            }
        ));
        tablaRecibo.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tablaRecibo);
        if (tablaRecibo.getColumnModel().getColumnCount() > 0) {
            tablaRecibo.getColumnModel().getColumn(0).setPreferredWidth(10);
            tablaRecibo.getColumnModel().getColumn(1).setPreferredWidth(130);
            tablaRecibo.getColumnModel().getColumn(2).setPreferredWidth(10);
            tablaRecibo.getColumnModel().getColumn(3).setPreferredWidth(10);
        }

        jLabel13.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(6, 55, 58));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Total:");

        txtmonto.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        txtmonto.setForeground(new java.awt.Color(6, 55, 58));
        txtmonto.setText("Monto");

        btngenerarrecibo.setBackground(new java.awt.Color(31, 95, 91));
        btngenerarrecibo.setMaximumSize(new java.awt.Dimension(200, 50));
        btngenerarrecibo.setMinimumSize(new java.awt.Dimension(200, 50));
        btngenerarrecibo.setRequestFocusEnabled(false);
        btngenerarrecibo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngenerarreciboMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btngenerarreciboMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btngenerarreciboMouseExited(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("SF Pro", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Generar Recibo");

        javax.swing.GroupLayout btngenerarreciboLayout = new javax.swing.GroupLayout(btngenerarrecibo);
        btngenerarrecibo.setLayout(btngenerarreciboLayout);
        btngenerarreciboLayout.setHorizontalGroup(
            btngenerarreciboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btngenerarreciboLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );
        btngenerarreciboLayout.setVerticalGroup(
            btngenerarreciboLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btngenerarreciboLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        jLabel2.setText("DNI:");

        txtdni.setFont(new java.awt.Font("SF Pro", 0, 14)); // NOI18N
        txtdni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtdniKeyTyped(evt);
            }
        });

        txtnombre.setFont(new java.awt.Font("SF Pro", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        jLabel3.setText("Nombres:");

        jLabel6.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        jLabel6.setText("Apellidos:");

        txtapellido.setFont(new java.awt.Font("SF Pro", 0, 14)); // NOI18N

        txtemail.setFont(new java.awt.Font("SF Pro", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        jLabel7.setText("Email:");

        jLabel8.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        jLabel8.setText("Direccion:");

        txtdireccion.setFont(new java.awt.Font("SF Pro", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        jLabel9.setText("Fecha de Nacimiento:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(100, 100, 100)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtdni, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtnombre)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtnacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(98, 98, 98)
                        .addComponent(txtdireccion))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(99, 99, 99)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtemail)
                            .addComponent(txtapellido))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btngenerarrecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtmonto, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtdni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(txtapellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(txtnacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtmonto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                        .addComponent(btngenerarrecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnsalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsalirMouseClicked
        main main = new main();
        main.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnsalirMouseClicked

    private void btnsalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsalirMouseEntered
        btnsalir.setBackground(new Color(6, 26, 35));
    }//GEN-LAST:event_btnsalirMouseEntered

    private void btnsalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsalirMouseExited
        btnsalir.setBackground(new Color(31,95,91));
    }//GEN-LAST:event_btnsalirMouseExited

    private void btngenerarreciboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngenerarreciboMouseClicked
        try {
            // TODO add your handling code here:
            ingresar();
        } catch (MessagingException ex) {
            Logger.getLogger(recibo_cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btngenerarreciboMouseClicked

    private void btngenerarreciboMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngenerarreciboMouseEntered
        // TODO add your handling code here:
        btngenerarrecibo.setBackground(new Color(6, 26, 35));
    }//GEN-LAST:event_btngenerarreciboMouseEntered

    private void btngenerarreciboMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngenerarreciboMouseExited
        // TODO add your handling code here:
        btngenerarrecibo.setBackground(new Color(31,95,91));
    }//GEN-LAST:event_btngenerarreciboMouseExited

    private void txtdniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtdniKeyTyped
        // TODO add your handling code here:
        if(txtdni.getText().length() >= 8){
            evt.consume();
        }
        
        int key = evt.getKeyChar();

        boolean numeros = key >= 48 && key <= 57;

        if (!numeros){
            evt.consume();
        }
    }//GEN-LAST:event_txtdniKeyTyped

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
            java.util.logging.Logger.getLogger(recibo_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(recibo_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(recibo_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(recibo_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new recibo_cliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btngenerarrecibo;
    private javax.swing.JPanel btnsalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tablaRecibo;
    private javax.swing.JTextField txtapellido;
    private javax.swing.JTextField txtdireccion;
    private javax.swing.JTextField txtdni;
    private javax.swing.JTextField txtemail;
    private javax.swing.JLabel txtmonto;
    private com.toedter.calendar.JDateChooser txtnacimiento;
    private javax.swing.JTextField txtnombre;
    // End of variables declaration//GEN-END:variables
}
