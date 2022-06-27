package VISTA;

import ENTIDAD.producto;
import MODELO.productoDao;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class home_cliente extends javax.swing.JFrame {

    MODELO.productoDao mp = new productoDao();
    ENTIDAD.producto ep = new producto();
    DefaultTableModel m = new DefaultTableModel();
    int random;
    int cant_lata=1;
    int cant_botella=1;
    int cant_carton=1;
    public static double monto=0, precio;
    public static int filas=0;
    public static TableModel m1;
    
    public home_cliente() {
        initComponents();
        this.setLocationRelativeTo(null);
        tablaRecibo.setRowHeight(25);
        monto=0;
    }

    int generarRandom(){
        int r = (int) (Math.random()*(5-1))+1;

        return r;
    }
    
    void mostrarProducto(){
        random = generarRandom();
        if(random!=4){
            ep = mp.datosProducto_id(random);
            txtproducto.setText("");
            txtproducto.setText(ep.getDes_pro());
            txtprecio.setText("");
            txtprecio.setText("$. "+ep.getPrec_pro()+"0");
            precio = ep.getPrec_pro();
            switch (random) {
                case 1:
                    txtImgen.setText("");
                    txtImgen.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/lata.jpg")));
                    break;
                case 2:
                    txtImgen.setText("");
                    txtImgen.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/botella.jpg")));
                    break;
                case 3:
                    txtImgen.setText("");
                    txtImgen.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/carton.jpg")));
                    break;
            }
            if(random>=5){
                txtImgen.setText("");
                txtImgen.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/sin_imagen.jpg")));
            }
        }else{
            txtproducto.setText("");
            txtproducto.setText("Elemento no valido");
            txtImgen.setText("");
            txtImgen.setIcon(new ImageIcon(getClass().getResource("/IMAGENES/otro.jpg")));
            txtprecio.setText("");
            txtprecio.setText("-----");
            JOptionPane.showMessageDialog(null, "Elemento no valido");
        }
        
    }
    
    //metodo para agregar el producto a la tabla de recibo
    void agregar(){
        if(txtproducto.getText().equals("Nombre del producto") || txtproducto.getText().equals("Elemento no valido") || txtImgen.getText().equals("Foto") || txtprecio.getText().equals("")){
            if(txtproducto.getText().equals("Elemento no valido")){
                JOptionPane.showMessageDialog(null, "Elemento no valido");
            }else{
                JOptionPane.showMessageDialog(null, "Campo de textos vacios");
            }
        }else{    
            m = (DefaultTableModel) tablaRecibo.getModel();
            
            int id_pro = random;
            String des_pro = txtproducto.getText();
            double prec_pro = precio;
            boolean t=false;
            int pos=0;
            double subtotal;
            
            for(int i=0; i<tablaRecibo.getRowCount(); i++){
                if(id_pro==Integer.parseInt(tablaRecibo.getValueAt(i, 0).toString())){
                    t=true;
                    switch (id_pro) {
                        case 1:
                            pos=i;
                            break;
                        case 2:
                            pos=i;
                            break;
                        case 3:
                            pos=i;
                            break;
                    }
                }
            }
            
            Object[] o = new Object[4];
            
            if(t!=true){
                o[0] = id_pro;
                o[1] = des_pro;
                o[2] = 1;
                o[3] = prec_pro;
                
                m.addRow(o);
                tablaRecibo.setModel(m);
            }else{
                switch (id_pro) {
                    case 1:
                        cant_lata++;
                        subtotal=cant_lata*prec_pro;
                        tablaRecibo.setValueAt(cant_lata, pos, 2);
                        tablaRecibo.setValueAt(subtotal, pos, 3);
                        break;
                    case 2:
                        cant_botella++;
                        subtotal=cant_botella*prec_pro;
                        tablaRecibo.setValueAt(cant_botella, pos, 2);
                        tablaRecibo.setValueAt(subtotal, pos, 3);
                        break;
                    case 3:
                        cant_carton++;
                        subtotal=cant_carton*prec_pro;
                        tablaRecibo.setValueAt(cant_carton, pos, 2);
                        tablaRecibo.setValueAt(subtotal, pos, 3);
                        break;
                }
            }
            monto += prec_pro;
            txtmonto.setText("$. " + monto + "0");
     
            txtproducto.setText("Nombre del producto");
            txtImgen.setText("");
            txtImgen.setText("Foto");
            txtImgen.setIcon(null);
            txtprecio.setText("");
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

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnsalir = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        btngenerar = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtproducto = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtImgen = new javax.swing.JLabel();
        btningresarp = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRecibo = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        txtmonto = new javax.swing.JLabel();
        btncontinuar = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtprecio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1200, 720));
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
        jLabel1.setText("Ingrese Producto");

        btngenerar.setBackground(new java.awt.Color(31, 95, 91));
        btngenerar.setMaximumSize(new java.awt.Dimension(200, 50));
        btngenerar.setMinimumSize(new java.awt.Dimension(200, 50));
        btngenerar.setRequestFocusEnabled(false);
        btngenerar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngenerarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btngenerarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btngenerarMouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SF Pro", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Generar");

        javax.swing.GroupLayout btngenerarLayout = new javax.swing.GroupLayout(btngenerar);
        btngenerar.setLayout(btngenerarLayout);
        btngenerarLayout.setHorizontalGroup(
            btngenerarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btngenerarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );
        btngenerarLayout.setVerticalGroup(
            btngenerarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btngenerarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtproducto.setFont(new java.awt.Font("SF Pro", 1, 18)); // NOI18N
        txtproducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtproducto.setText("Nombre del producto");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(6, 55, 58)));

        txtImgen.setFont(new java.awt.Font("SF Pro", 1, 14)); // NOI18N
        txtImgen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtImgen.setText("Foto");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtImgen, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtImgen, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        btningresarp.setBackground(new java.awt.Color(31, 95, 91));
        btningresarp.setMaximumSize(new java.awt.Dimension(200, 50));
        btningresarp.setMinimumSize(new java.awt.Dimension(200, 50));
        btningresarp.setRequestFocusEnabled(false);
        btningresarp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btningresarpMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btningresarpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btningresarpMouseExited(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SF Pro", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Agregar Producto");

        javax.swing.GroupLayout btningresarpLayout = new javax.swing.GroupLayout(btningresarp);
        btningresarp.setLayout(btningresarpLayout);
        btningresarpLayout.setHorizontalGroup(
            btningresarpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btningresarpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );
        btningresarpLayout.setVerticalGroup(
            btningresarpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btningresarpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

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
        tablaRecibo.setToolTipText("");
        tablaRecibo.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tablaRecibo);
        if (tablaRecibo.getColumnModel().getColumnCount() > 0) {
            tablaRecibo.getColumnModel().getColumn(0).setPreferredWidth(10);
            tablaRecibo.getColumnModel().getColumn(1).setPreferredWidth(130);
            tablaRecibo.getColumnModel().getColumn(2).setPreferredWidth(10);
            tablaRecibo.getColumnModel().getColumn(3).setPreferredWidth(15);
        }

        jLabel13.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(6, 55, 58));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Total:");

        txtmonto.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        txtmonto.setForeground(new java.awt.Color(6, 55, 58));
        txtmonto.setText("Monto");

        btncontinuar.setBackground(new java.awt.Color(31, 95, 91));
        btncontinuar.setMaximumSize(new java.awt.Dimension(200, 50));
        btncontinuar.setMinimumSize(new java.awt.Dimension(200, 50));
        btncontinuar.setRequestFocusEnabled(false);
        btncontinuar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btncontinuarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btncontinuarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btncontinuarMouseExited(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("SF Pro", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Continuar");

        javax.swing.GroupLayout btncontinuarLayout = new javax.swing.GroupLayout(btncontinuar);
        btncontinuar.setLayout(btncontinuarLayout);
        btncontinuarLayout.setHorizontalGroup(
            btncontinuarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btncontinuarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                .addContainerGap())
        );
        btncontinuarLayout.setVerticalGroup(
            btncontinuarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btncontinuarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("SF Pro Text", 1, 14)); // NOI18N
        jLabel2.setText("Costo:");

        txtprecio.setFont(new java.awt.Font("SF Pro", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btningresarp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
                                        .addComponent(btngenerar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtproducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(45, 45, 45))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(235, 235, 235)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(292, 292, 292)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btncontinuar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btngenerar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(58, 58, 58)
                                .addComponent(txtproducto)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(108, 108, 108)
                                .addComponent(btningresarp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncontinuar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btngenerarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngenerarMouseClicked
        // TODO add your handling code here:
        //System.out.println("" + generarRandom());
        mostrarProducto();
    }//GEN-LAST:event_btngenerarMouseClicked

    private void btngenerarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngenerarMouseEntered
        // TODO add your handling code here:
        btngenerar.setBackground(new Color(6, 26, 35));
    }//GEN-LAST:event_btngenerarMouseEntered

    private void btngenerarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngenerarMouseExited
        // TODO add your handling code here:
        btngenerar.setBackground(new Color(31,95,91));
    }//GEN-LAST:event_btngenerarMouseExited

    private void btningresarpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btningresarpMouseClicked
        // TODO add your handling code here:
        agregar();
    }//GEN-LAST:event_btningresarpMouseClicked

    private void btningresarpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btningresarpMouseEntered
        // TODO add your handling code here:
        btningresarp.setBackground(new Color(6, 26, 35));
    }//GEN-LAST:event_btningresarpMouseEntered

    private void btningresarpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btningresarpMouseExited
        // TODO add your handling code here:
        btningresarp.setBackground(new Color(31,95,91));
    }//GEN-LAST:event_btningresarpMouseExited

    private void btncontinuarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncontinuarMouseClicked
        // TODO add your handling code here:
        m1 = tablaRecibo.getModel();
        filas = tablaRecibo.getRowCount();
        if(tablaRecibo.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "Tabla vacia");
        }else{
            recibo_cliente recibo_cliente = new recibo_cliente();
            recibo_cliente.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btncontinuarMouseClicked

    private void btncontinuarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncontinuarMouseEntered
        // TODO add your handling code here:
        btncontinuar.setBackground(new Color(6, 26, 35));
    }//GEN-LAST:event_btncontinuarMouseEntered

    private void btncontinuarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncontinuarMouseExited
        // TODO add your handling code here:
        btncontinuar.setBackground(new Color(31,95,91));
    }//GEN-LAST:event_btncontinuarMouseExited

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
            java.util.logging.Logger.getLogger(home_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new home_cliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btncontinuar;
    private javax.swing.JPanel btngenerar;
    private javax.swing.JPanel btningresarp;
    private javax.swing.JPanel btnsalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tablaRecibo;
    private javax.swing.JLabel txtImgen;
    private javax.swing.JLabel txtmonto;
    private javax.swing.JLabel txtprecio;
    private javax.swing.JLabel txtproducto;
    // End of variables declaration//GEN-END:variables
}
