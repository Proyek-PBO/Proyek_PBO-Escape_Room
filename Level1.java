package pbo.Level1;

import pbo.projek.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Level1 extends javax.swing.JFrame implements Methods{
    
/**
 *
 * @author Valentino Rocky Atmojo - 221116996
 */
    
    private Random rd = new Random();
    private Select_Level sl = new Select_Level();
    private Setting setting;
    private String username;
    private Inventory i = new Inventory(username);
    private int[] coord_y = new int[]{140, 270, 405, 535};
    private Map<javax.swing.JLabel,Boolean> have = new HashMap<>();
    
    private ArrayList tmp = new ArrayList();
    private boolean click = false, kayu = false, pakupalu = false, obeng = false, tangga = false, kertas = false, korek = false;
    private boolean put_tangga = false, open_ventilasi = false, open_brankas = false, light_lilin = false, burn_kertas = false;
    private String[] hint = new String[]{
        "Ambil kayu yang ada dibawah meja",
        "Ambil palu dan paku yang ada diatas meja",
        "Gabungkan kayu dengan palu dan paku",
        "Letakkan tangga dibawah ventilasi",
        "Ambil obeng yang ada diatas meja",
        "Buka ventilasi dengan obeng",
        "Ambil kertas yang ada di lorong ventilasi",
        "Mainkan game pada tv dan pecahkan puzzlenya",
        "Buka brankas dari petunjuk di game",
        "Ambil korek api yang ada di brankas",
        "Hidupkan lilin dengan korek api",
        "Bakar kertas dengan lilin",
        "Baca tulisan rahasia yang ada di kertas, kemudian buka pintu"
    };
    
//===================================================================================================================================================
    // Soal pass
    private String soal_pass;
    private long comb_ke;
    
    private String toString(ArrayList<String> angka){
        String s = "";
        for(int i = 0; i < angka.size(); i++){
            s += angka.get(i);
        }
        return s;
    }
    private int persempit_pencarian(long k, long f, int x){
        if (f*x < k) return persempit_pencarian(k, f, x+1);
        else return x;
    }
    private void Cari(long k, ArrayList<String> num_list, ArrayList<String> angka, ArrayList<Long> f){
        if (num_list.size() != 1){
            int m = persempit_pencarian(k, f.get(num_list.size()-2), 1) - 1;
            angka.add(num_list.get(m));
            num_list.remove(m);
            k -= m*f.get(num_list.size()-1);
            Cari(k, num_list, angka, f);
        }else{
            angka.add(num_list.get(0));
        }
    }
    private long Faktorial(int n, ArrayList<Long> f){
        if (n == 0){ return 1;
        }else{
            long x = n*Faktorial(n-1, f);
            f.add(x);
            return x;
        }
    }
    private String getPermutation(int n) {
        ArrayList<String> angka = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        String[] alfabet = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A",
        "B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
        ArrayList<Integer> simpan = new ArrayList<>();
        for(int i = 0; i < n; i++){
            int x = rd.nextInt(alfabet.length);
            if (!simpan.contains(x)) {
                list.add(alfabet[x]);
                simpan.add(x);
            }else{
                i--;
            }
        }
        soal_pass = toString(list);
        ArrayList<Long> f = new ArrayList<>();
        Faktorial(n-1, f);
        comb_ke = rd.nextLong(f.get(n-2)*n)+1;

        Cari(comb_ke, list, angka, f);
        return toString(angka);
    }
    private String kunci_pass = getPermutation(rd.nextInt(11)+10);
    
    private Game game = new Game(soal_pass, comb_ke);
//====================================================================================================================================================
    // Soal pin
    private String[] warna = new String[]{"merah", "jingga", "kuning", "hijau", "biru", "nila", "ungu"};
    private String[] musim = new String[]{"bunga bermekaran", "matahari terik", "daun berguguran", "salju turun"};
    private String[] bulan = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    private String[] zodiak = new String[]{"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagitarius", "Capicorn", "Aquarius", "Pisces"};
    
    private int w = rd.nextInt(warna.length);
    private int m = rd.nextInt(musim.length);
    private int b = (m == 0 ? (rd.nextInt(3)+2) : m == 1 ? rd.nextInt(3)+5 : m == 2 ? rd.nextInt(3)+8 : (rd.nextInt(3)+11)%12);
    private int z = rd.nextInt(zodiak.length);
    
    private String kunci_pin = Integer.toString((b+1)%10) + (((z+1) >= 10) ? Integer.toString(z+1) : ("0" + Integer.toString(z+1))) + Integer.toString((w+1)*100 + (m+1)*10 + (b+1)/10);
//====================================================================================================================================================
    // Timer
    private Timer timer;
    private int second, minute;
    private String ddSecond, ddMinute;
    private DecimalFormat dFormat = new DecimalFormat("00");
    
    @Override
    public void normalTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                second++;
                ddSecond = dFormat.format(second);
                ddMinute = dFormat.format(minute);
                Timer.setText(ddMinute + " : " + ddSecond);

                if (second == 60) {
                    second = 0;
                    minute++;
                    ddSecond = dFormat.format(second);
                    ddMinute = dFormat.format(minute);
                    Timer.setText(ddMinute + " : " + ddSecond);
                }
            }
        });
    }
//=====================================================================================================================================================    
    // Music
    private Clip clip;
    private Clip main;
    
    @Override
    public void Playmusic(String musiclocation) {
        try {
            File musicPath = new File(musiclocation);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("Can't find file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//=====================================================================================================================================================
    // Methods
    @Override
    public void Select(javax.swing.JLabel curr){
        if (curr.getName().equals("kayu")) {
            kayu = true;
        }else if(curr.getName().equals("pakupalu")){
            pakupalu = true;
        }else if (curr.getName().equals("obeng")) {
            obeng = true;
        }else if (curr.getName().equals("tangga")) {
            tangga = true;
        }else if (curr.getName().equals("korek")) {
            korek = true;
        }else if (curr.getName().equals("kertas")) {
            kertas = true;
        }
    }
    
    @Override
    public void Unselect(javax.swing.JLabel curr){
        if (curr.getName().equals("kayu")) {
            kayu = false;
        }else if(curr.getName().equals("pakupalu")){
            pakupalu = false;
        }else if (curr.getName().equals("obeng")) {
            obeng = false;
        }else if (curr.getName().equals("tangga")) {
            tangga = false;
        }else if (curr.getName().equals("korek")) {
            korek = false;
        }else if (curr.getName().equals("kertas")) {
            kertas = false;
        }
    }
    
    @Override
    public void Unselect_All(){
        kayu = false;
        pakupalu = false;
        obeng = false;
        tangga = false;
        korek = false;
        kertas = false;
    }
    
    @Override
    public void move(javax.swing.JLabel l){
        l.setVisible(true);
        tmp.add(l);
        click = true;
    }
    
    @Override
    public void stay(javax.swing.JLabel l){
        l.setVisible(false);
        tmp.clear();
        click = false;
    }
    
    private void new_Set(){
        for (int j = 0; j < 4; j++) {
            if (i.get(j) != null) {
                if (i.get(j) == Kayu) {
                    i_kayu.setLocation(1250, coord_y[j]);
                }else if (i.get(j) == Paku_Palu) {
                    i_paku_palu.setLocation(1250, coord_y[j]);
                }else if (i.get(j) == Obeng) {
                    i_obeng.setLocation(1250, coord_y[j]);
                }else if (i.get(j) == Tangga) {
                    i_tangga.setLocation(1250, coord_y[j]);
                }else if (i.get(j) == Korek) {
                    i_korek.setLocation(1250, coord_y[j]);
                }else if (i.get(j) == Kertas) {
                    i_kertas.setLocation(1250, coord_y[j]);
                }else if (i.get(j) == i_kertas_gosong) {
                    i_kertas_gosong.setLocation(1250, coord_y[j]);
                }
            }
        }
    }
    
    private void make_tangga(int x, javax.swing.JLabel z){
        i_tangga.setVisible(true);
        i.add(Tangga);
        have.put(Tangga, true);
        i.remove(x);
        i.remove(z);
        i_kayu.setVisible(false);
        i_paku_palu.setVisible(false);
        new_Set();
    }
    
    @Override
    public void Hint() {
        if (!cek(Kayu)) {
            JOptionPane.showMessageDialog(this, hint[0], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!cek(Paku_Palu)) {
            JOptionPane.showMessageDialog(this, hint[1], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!cek(Tangga)) {
            JOptionPane.showMessageDialog(this, hint[2], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!put_tangga) {
            JOptionPane.showMessageDialog(this, hint[3], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!cek(Obeng)) {
            JOptionPane.showMessageDialog(this, hint[4], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!open_ventilasi) {
            JOptionPane.showMessageDialog(this, hint[5], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!cek(Kertas)) {
            JOptionPane.showMessageDialog(this, hint[6], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!game.getWin()) {
            JOptionPane.showMessageDialog(this, hint[7], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!open_brankas) {
            JOptionPane.showMessageDialog(this, hint[8], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!cek(Korek)) {
            JOptionPane.showMessageDialog(this, hint[9], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!light_lilin) {
            JOptionPane.showMessageDialog(this, hint[10], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else if (!burn_kertas) {
            JOptionPane.showMessageDialog(this, hint[11], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, hint[12], "Hint", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private boolean cek(javax.swing.JLabel item){
        for (Map.Entry<JLabel, Boolean> entry : have.entrySet()) {
            JLabel key = entry.getKey();
            Boolean value = entry.getValue();
            if (key == item) {
                return value;
            }
        }
        return false;
    }
    
    /**
     * Creates new form Level1
     */
    public Level1() {
        initComponents();
        Playmusic("Music\\Level1.wav");
    }
    
    public Level1(String username, Select_Level sl, Clip main, Setting setting) {
        initComponents();
        this.sl = sl;
        this.main = main;
        this.setting = setting;
        this.username = username;
        if (setting.isPlaying_music()) {
            Playmusic("Music\\Level1.wav");
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

        Pin = new javax.swing.JFrame();
        Ok = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        Tombol0 = new javax.swing.JButton();
        Tombol9 = new javax.swing.JButton();
        Tombol8 = new javax.swing.JButton();
        Tombol7 = new javax.swing.JButton();
        Tombol6 = new javax.swing.JButton();
        Tombol5 = new javax.swing.JButton();
        Tombol4 = new javax.swing.JButton();
        Tombol3 = new javax.swing.JButton();
        Tombol2 = new javax.swing.JButton();
        Tombol1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Password = new javax.swing.JFrame();
        jTextField1 = new javax.swing.JTextField();
        Enter = new javax.swing.JLabel();
        Keyboard = new javax.swing.JLabel();
        Tulisan_rahasia = new javax.swing.JFrame();
        jLabel7 = new javax.swing.JLabel();
        Sajak1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Sajak2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Sajak3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Sajak4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();
        papan_penunjuk_1 = new javax.swing.JLabel();
        hint_3 = new javax.swing.JLabel();
        hint_2 = new javax.swing.JLabel();
        hint_2.setVisible(false);
        hint_1 = new javax.swing.JLabel();
        hint_1.setVisible(false);
        hint_0 = new javax.swing.JLabel();
        hint_0.setVisible(false);
        Inventory_click1 = new javax.swing.JLabel();
        Inventory_click1.setVisible(false);
        Inventory_click2 = new javax.swing.JLabel();
        Inventory_click2.setVisible(false);
        Inventory_click3 = new javax.swing.JLabel();
        Inventory_click3.setVisible(false);
        Inventory_click4 = new javax.swing.JLabel();
        Inventory_click4.setVisible(false);
        i_tangga = new javax.swing.JLabel();
        i_tangga.setVisible(false);
        i_paku_palu = new javax.swing.JLabel();
        i_paku_palu.setVisible(false);
        i_kayu = new javax.swing.JLabel();
        i_kayu.setVisible(false);
        i_obeng = new javax.swing.JLabel();
        i_obeng.setVisible(false);
        i_korek = new javax.swing.JLabel();
        i_kertas = new javax.swing.JLabel();
        i_kertas_gosong = new javax.swing.JLabel();
        Invetory_box1 = new javax.swing.JLabel();
        Invetory_box2 = new javax.swing.JLabel();
        Invetory_box3 = new javax.swing.JLabel();
        Invetory_box4 = new javax.swing.JLabel();
        Inventory_backgound = new javax.swing.JLabel();
        Sandi = new javax.swing.JLabel();
        Ventilasi = new javax.swing.JLabel();
        Kertas = new javax.swing.JLabel();
        Kertas.setVisible(false);
        Lorong_ventilasi = new javax.swing.JLabel();
        Lorong_ventilasi.setVisible(false);
        Tangga = new javax.swing.JLabel();
        Tangga.setVisible(false);
        place_tangga = new javax.swing.JLabel();
        Ventilasi_buka = new javax.swing.JLabel();
        Ventilasi_buka.setVisible(false);
        Kayu = new javax.swing.JLabel();
        Paku_Palu = new javax.swing.JLabel();
        Obeng = new javax.swing.JLabel();
        Lilin_mati = new javax.swing.JLabel();
        Lilin_hidup = new javax.swing.JLabel();
        Lilin_hidup.setVisible(false);
        Meja = new javax.swing.JLabel();
        Perkakas = new javax.swing.JLabel();
        Tv = new javax.swing.JLabel();
        Brankas_tutup = new javax.swing.JLabel();
        Korek = new javax.swing.JLabel();
        Korek.setVisible(false);
        Brankas_buka = new javax.swing.JLabel();
        Brankas_buka.setVisible(false);
        Pintu = new javax.swing.JLabel();
        Kaleng_cat = new javax.swing.JLabel();
        Lampu = new javax.swing.JLabel();
        Timer = new javax.swing.JLabel();
        Pigura = new javax.swing.JLabel();
        Papan_lukis = new javax.swing.JLabel();
        Palet = new javax.swing.JLabel();
        Botol_cat = new javax.swing.JLabel();
        Cat = new javax.swing.JLabel();
        Cat2 = new javax.swing.JLabel();
        Rak = new javax.swing.JLabel();
        Cat3 = new javax.swing.JLabel();
        Graviti = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        Latar_belakang = new javax.swing.JLabel();

        Pin.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Pin.setTitle("Pin");
        Pin.setLocation(new java.awt.Point(500, 300));
        Pin.setMinimumSize(new java.awt.Dimension(300, 400));
        Pin.setResizable(false);
        Pin.setSize(new java.awt.Dimension(284, 369));
        Pin.getContentPane().setLayout(null);

        Ok.setFont(new java.awt.Font("Stencil", 0, 12)); // NOI18N
        Ok.setText("OK");
        Ok.setPreferredSize(new java.awt.Dimension(50, 50));
        Ok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OkMouseClicked(evt);
            }
        });
        Pin.getContentPane().add(Ok);
        Ok.setBounds(49, 294, 50, 50);

        Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/delete.png"))); // NOI18N
        Delete.setPreferredSize(new java.awt.Dimension(50, 50));
        Delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DeleteMouseClicked(evt);
            }
        });
        Pin.getContentPane().add(Delete);
        Delete.setBounds(185, 294, 50, 50);

        Tombol0.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        Tombol0.setText("0");
        Tombol0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tombol0.setPreferredSize(new java.awt.Dimension(50, 50));
        Tombol0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tombol0ActionPerformed(evt);
            }
        });
        Pin.getContentPane().add(Tombol0);
        Tombol0.setBounds(117, 294, 50, 50);

        Tombol9.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        Tombol9.setText("9");
        Tombol9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tombol9.setPreferredSize(new java.awt.Dimension(50, 50));
        Tombol9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tombol9ActionPerformed(evt);
            }
        });
        Pin.getContentPane().add(Tombol9);
        Tombol9.setBounds(185, 226, 50, 50);

        Tombol8.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        Tombol8.setText("8");
        Tombol8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tombol8.setPreferredSize(new java.awt.Dimension(50, 50));
        Tombol8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tombol8ActionPerformed(evt);
            }
        });
        Pin.getContentPane().add(Tombol8);
        Tombol8.setBounds(117, 226, 50, 50);

        Tombol7.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        Tombol7.setText("7");
        Tombol7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tombol7.setPreferredSize(new java.awt.Dimension(50, 50));
        Tombol7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tombol7ActionPerformed(evt);
            }
        });
        Pin.getContentPane().add(Tombol7);
        Tombol7.setBounds(49, 226, 50, 50);

        Tombol6.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        Tombol6.setText("6");
        Tombol6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tombol6.setPreferredSize(new java.awt.Dimension(50, 50));
        Tombol6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tombol6ActionPerformed(evt);
            }
        });
        Pin.getContentPane().add(Tombol6);
        Tombol6.setBounds(185, 158, 50, 50);

        Tombol5.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        Tombol5.setText("5");
        Tombol5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tombol5.setPreferredSize(new java.awt.Dimension(50, 50));
        Tombol5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tombol5ActionPerformed(evt);
            }
        });
        Pin.getContentPane().add(Tombol5);
        Tombol5.setBounds(117, 158, 50, 50);

        Tombol4.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        Tombol4.setText("4");
        Tombol4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tombol4.setPreferredSize(new java.awt.Dimension(50, 50));
        Tombol4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tombol4ActionPerformed(evt);
            }
        });
        Pin.getContentPane().add(Tombol4);
        Tombol4.setBounds(49, 158, 50, 50);

        Tombol3.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        Tombol3.setText("3");
        Tombol3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tombol3.setPreferredSize(new java.awt.Dimension(50, 50));
        Tombol3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tombol3ActionPerformed(evt);
            }
        });
        Pin.getContentPane().add(Tombol3);
        Tombol3.setBounds(185, 90, 50, 50);

        Tombol2.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        Tombol2.setText("2");
        Tombol2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tombol2.setPreferredSize(new java.awt.Dimension(50, 50));
        Tombol2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tombol2ActionPerformed(evt);
            }
        });
        Pin.getContentPane().add(Tombol2);
        Tombol2.setBounds(117, 90, 50, 50);

        Tombol1.setFont(new java.awt.Font("Stencil", 0, 18)); // NOI18N
        Tombol1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tombol1.setLabel("1");
        Tombol1.setPreferredSize(new java.awt.Dimension(50, 50));
        Tombol1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tombol1ActionPerformed(evt);
            }
        });
        Pin.getContentPane().add(Tombol1);
        Tombol1.setBounds(49, 90, 50, 50);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(31, 55));

        jLabel1.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        Pin.getContentPane().add(jPanel1);
        jPanel1.setBounds(24, 25, 31, 55);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(31, 55));

        jLabel2.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Pin.getContentPane().add(jPanel2);
        jPanel2.setBounds(65, 25, 31, 55);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(31, 55));

        jLabel3.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        Pin.getContentPane().add(jPanel3);
        jPanel3.setBounds(106, 25, 31, 55);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(31, 55));

        jLabel4.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        Pin.getContentPane().add(jPanel4);
        jPanel4.setBounds(147, 25, 31, 55);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(31, 55));

        jLabel5.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Pin.getContentPane().add(jPanel5);
        jPanel5.setBounds(188, 25, 31, 55);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(31, 55));

        jLabel6.setFont(new java.awt.Font("Stencil", 0, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Pin.getContentPane().add(jPanel6);
        jPanel6.setBounds(229, 25, 31, 55);

        Password.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Password.setTitle("Password");
        Password.setLocation(new java.awt.Point(350, 300));
        Password.setMinimumSize(new java.awt.Dimension(765, 288));
        Password.setResizable(false);
        Password.getContentPane().setLayout(null);
        Password.getContentPane().add(jTextField1);
        jTextField1.setBounds(10, 10, 730, 40);

        Enter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EnterMouseClicked(evt);
            }
        });
        Password.getContentPane().add(Enter);
        Enter.setBounds(640, 119, 105, 40);

        Keyboard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Keyboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/keyboard.png"))); // NOI18N
        Password.getContentPane().add(Keyboard);
        Keyboard.setBounds(0, 70, 750, 180);

        Tulisan_rahasia.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Tulisan_rahasia.setTitle("Tulisan Rahasia");
        Tulisan_rahasia.setLocation(new java.awt.Point(650, 250));
        Tulisan_rahasia.setMinimumSize(new java.awt.Dimension(311, 409));
        Tulisan_rahasia.setResizable(false);
        Tulisan_rahasia.setSize(new java.awt.Dimension(301, 374));
        Tulisan_rahasia.getContentPane().setLayout(null);

        jLabel7.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 51));
        jLabel7.setText("Warna");
        Tulisan_rahasia.getContentPane().add(jLabel7);
        jLabel7.setBounds(20, 30, 60, 20);

        Sajak1.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        Sajak1.setForeground(new java.awt.Color(255, 255, 51));
        Sajak1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Sajak1.setText(warna[w]);
        Tulisan_rahasia.getContentPane().add(Sajak1);
        Sajak1.setBounds(90, 30, 65, 20);

        jLabel8.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 51));
        jLabel8.setText("pada busur dilangit,");
        Tulisan_rahasia.getContentPane().add(jLabel8);
        jLabel8.setBounds(100, 50, 180, 20);

        jLabel9.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 51));
        jLabel9.setText("pada musim");
        Tulisan_rahasia.getContentPane().add(jLabel9);
        jLabel9.setBounds(40, 80, 110, 20);

        Sajak2.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        Sajak2.setForeground(new java.awt.Color(255, 255, 51));
        Sajak2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Sajak2.setText(musim[m]);
        Tulisan_rahasia.getContentPane().add(Sajak2);
        Sajak2.setBounds(90, 105, 180, 20);

        jLabel10.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 51));
        jLabel10.setText("di bulan");
        Tulisan_rahasia.getContentPane().add(jLabel10);
        jLabel10.setBounds(60, 130, 70, 20);

        Sajak3.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        Sajak3.setForeground(new java.awt.Color(255, 255, 51));
        Sajak3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Sajak3.setText(bulan[b] + ".");
        Tulisan_rahasia.getContentPane().add(Sajak3);
        Sajak3.setBounds(140, 130, 110, 20);

        jLabel11.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 51));
        jLabel11.setText("Kehidupan baru");
        Tulisan_rahasia.getContentPane().add(jLabel11);
        jLabel11.setBounds(30, 190, 150, 20);

        jLabel12.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 51));
        jLabel12.setText("dengan zodiak");
        Tulisan_rahasia.getContentPane().add(jLabel12);
        jLabel12.setBounds(130, 210, 130, 20);

        Sajak4.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        Sajak4.setForeground(new java.awt.Color(255, 255, 51));
        Sajak4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Sajak4.setText(zodiak[z]);
        Tulisan_rahasia.getContentPane().add(Sajak4);
        Sajak4.setBounds(70, 230, 100, 20);

        jLabel13.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 51));
        jLabel13.setText("memasuki dunia ini,");
        Tulisan_rahasia.getContentPane().add(jLabel13);
        jLabel13.setBounds(20, 250, 170, 20);

        jLabel14.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 51));
        jLabel14.setText("diterangi indahnya");
        Tulisan_rahasia.getContentPane().add(jLabel14);
        jLabel14.setBounds(110, 270, 170, 20);

        jLabel16.setFont(new java.awt.Font("Script MT Bold", 1, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 51));
        jLabel16.setText("cahaya bulan purnama");
        Tulisan_rahasia.getContentPane().add(jLabel16);
        jLabel16.setBounds(50, 290, 200, 20);

        jLabel15.setFont(new java.awt.Font("Script MT Bold", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 51));
        jLabel15.setText("dimana bulan berputar terhadap bumi.");
        Tulisan_rahasia.getContentPane().add(jLabel15);
        jLabel15.setBounds(30, 315, 240, 20);

        Background.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/background_tulisan_rahasia.png"))); // NOI18N
        Tulisan_rahasia.getContentPane().add(Background);
        Background.setBounds(0, 0, 301, 374);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Lavel 1");
        setLocation(new java.awt.Point(0, 0));
        setMinimumSize(new java.awt.Dimension(1400, 789));
        setResizable(false);
        setSize(new java.awt.Dimension(1400, 789));
        getContentPane().setLayout(null);

        papan_penunjuk_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/papan gantung level 1.png"))); // NOI18N
        getContentPane().add(papan_penunjuk_1);
        papan_penunjuk_1.setBounds(1220, 0, 160, 85);

        hint_3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/Hint 3.png"))); // NOI18N
        hint_3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hint_3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hint_3MouseClicked(evt);
            }
        });
        getContentPane().add(hint_3);
        hint_3.setBounds(1250, 650, 100, 80);

        hint_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/Hint 2.png"))); // NOI18N
        hint_2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hint_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hint_2MouseClicked(evt);
            }
        });
        getContentPane().add(hint_2);
        hint_2.setBounds(1250, 650, 100, 80);

        hint_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/Hint 1.png"))); // NOI18N
        hint_1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hint_1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hint_1MouseClicked(evt);
            }
        });
        getContentPane().add(hint_1);
        hint_1.setBounds(1250, 650, 100, 80);

        hint_0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/Hint 0.png"))); // NOI18N
        hint_0.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hint_0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hint_0MouseClicked(evt);
            }
        });
        getContentPane().add(hint_0);
        hint_0.setBounds(1250, 650, 100, 80);

        Inventory_click1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/inventory click.png"))); // NOI18N
        Inventory_click1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Inventory_click1MouseClicked(evt);
            }
        });
        getContentPane().add(Inventory_click1);
        Inventory_click1.setBounds(1227, 120, 147, 121);

        Inventory_click2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/inventory click.png"))); // NOI18N
        Inventory_click2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Inventory_click2MouseClicked(evt);
            }
        });
        getContentPane().add(Inventory_click2);
        Inventory_click2.setBounds(1227, 249, 147, 121);

        Inventory_click3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/inventory click.png"))); // NOI18N
        Inventory_click3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Inventory_click3MouseClicked(evt);
            }
        });
        getContentPane().add(Inventory_click3);
        Inventory_click3.setBounds(1227, 378, 147, 121);

        Inventory_click4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/inventory click.png"))); // NOI18N
        Inventory_click4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Inventory_click4MouseClicked(evt);
            }
        });
        getContentPane().add(Inventory_click4);
        Inventory_click4.setBounds(1227, 507, 147, 121);

        i_tangga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/i_tangga.png"))); // NOI18N
        getContentPane().add(i_tangga);
        i_tangga.setBounds(1420, 760, 100, 80);

        i_paku_palu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/i_palu & paku.png"))); // NOI18N
        getContentPane().add(i_paku_palu);
        i_paku_palu.setBounds(1420, 760, 100, 80);

        i_kayu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/i_kayu.png"))); // NOI18N
        getContentPane().add(i_kayu);
        i_kayu.setBounds(1420, 760, 100, 80);

        i_obeng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/i_obeng.png"))); // NOI18N
        getContentPane().add(i_obeng);
        i_obeng.setBounds(1410, 760, 100, 80);

        i_korek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/i_korek api.png"))); // NOI18N
        getContentPane().add(i_korek);
        i_korek.setBounds(1420, 760, 100, 80);

        i_kertas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/i_kertas.png"))); // NOI18N
        getContentPane().add(i_kertas);
        i_kertas.setBounds(1420, 760, 100, 80);

        i_kertas_gosong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/i_kertas_gosong.png"))); // NOI18N
        i_kertas_gosong.setName("kertas_gosong"); // NOI18N
        getContentPane().add(i_kertas_gosong);
        i_kertas_gosong.setBounds(1420, 760, 100, 80);

        Invetory_box1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/inventory box.png"))); // NOI18N
        Invetory_box1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Invetory_box1MouseClicked(evt);
            }
        });
        getContentPane().add(Invetory_box1);
        Invetory_box1.setBounds(1228, 121, 145, 119);

        Invetory_box2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/inventory box.png"))); // NOI18N
        Invetory_box2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Invetory_box2MouseClicked(evt);
            }
        });
        getContentPane().add(Invetory_box2);
        Invetory_box2.setBounds(1228, 250, 145, 119);

        Invetory_box3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/inventory box.png"))); // NOI18N
        Invetory_box3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Invetory_box3MouseClicked(evt);
            }
        });
        getContentPane().add(Invetory_box3);
        Invetory_box3.setBounds(1228, 379, 145, 119);

        Invetory_box4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/inventory box.png"))); // NOI18N
        Invetory_box4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Invetory_box4MouseClicked(evt);
            }
        });
        getContentPane().add(Invetory_box4);
        Invetory_box4.setBounds(1228, 508, 145, 119);

        Inventory_backgound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/projek/Image/Inventory_background.png"))); // NOI18N
        getContentPane().add(Inventory_backgound);
        Inventory_backgound.setBounds(1200, 0, 200, 750);

        Sandi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/password.png"))); // NOI18N
        Sandi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SandiMouseClicked(evt);
            }
        });
        getContentPane().add(Sandi);
        Sandi.setBounds(550, 410, 20, 40);

        Ventilasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/ventilasi.png"))); // NOI18N
        Ventilasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VentilasiMouseClicked(evt);
            }
        });
        getContentPane().add(Ventilasi);
        Ventilasi.setBounds(110, 90, 90, 90);

        Kertas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/kertas.png"))); // NOI18N
        Kertas.setName("kertas"); // NOI18N
        Kertas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                KertasMouseClicked(evt);
            }
        });
        getContentPane().add(Kertas);
        Kertas.setBounds(140, 130, 10, 20);

        Lorong_ventilasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/lorong_ventilasi.png"))); // NOI18N
        getContentPane().add(Lorong_ventilasi);
        Lorong_ventilasi.setBounds(110, 90, 90, 90);

        Tangga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/tangga.png"))); // NOI18N
        Tangga.setName("tangga"); // NOI18N
        getContentPane().add(Tangga);
        Tangga.setBounds(130, 190, 200, 450);

        place_tangga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                place_tanggaMouseClicked(evt);
            }
        });
        getContentPane().add(place_tangga);
        place_tangga.setBounds(130, 190, 200, 450);

        Ventilasi_buka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/pintu_ventilasi.png"))); // NOI18N
        getContentPane().add(Ventilasi_buka);
        Ventilasi_buka.setBounds(250, 510, 90, 90);

        Kayu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/kayu.png"))); // NOI18N
        Kayu.setText("jLabel1");
        Kayu.setName("kayu"); // NOI18N
        Kayu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                KayuMouseClicked(evt);
            }
        });
        getContentPane().add(Kayu);
        Kayu.setBounds(1000, 670, 100, 60);

        Paku_Palu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/palu & paku.png"))); // NOI18N
        Paku_Palu.setName("pakupalu"); // NOI18N
        Paku_Palu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Paku_PaluMouseClicked(evt);
            }
        });
        getContentPane().add(Paku_Palu);
        Paku_Palu.setBounds(1110, 580, 50, 50);

        Obeng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/obeng.png"))); // NOI18N
        Obeng.setName("obeng"); // NOI18N
        Obeng.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ObengMouseClicked(evt);
            }
        });
        getContentPane().add(Obeng);
        Obeng.setBounds(990, 540, 70, 30);

        Lilin_mati.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/lilin_mati.png"))); // NOI18N
        Lilin_mati.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Lilin_matiMouseClicked(evt);
            }
        });
        getContentPane().add(Lilin_mati);
        Lilin_mati.setBounds(1060, 540, 30, 40);

        Lilin_hidup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/lilin_hidup.png"))); // NOI18N
        Lilin_hidup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Lilin_hidupMouseClicked(evt);
            }
        });
        getContentPane().add(Lilin_hidup);
        Lilin_hidup.setBounds(1060, 530, 30, 50);

        Meja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/meja.png"))); // NOI18N
        getContentPane().add(Meja);
        Meja.setBounds(930, 530, 270, 230);

        Perkakas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/perkakas.png"))); // NOI18N
        getContentPane().add(Perkakas);
        Perkakas.setBounds(880, 630, 100, 60);

        Tv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/tv.png"))); // NOI18N
        Tv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TvMouseClicked(evt);
            }
        });
        getContentPane().add(Tv);
        Tv.setBounds(860, 450, 160, 190);

        Brankas_tutup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/brankas tertutup.png"))); // NOI18N
        Brankas_tutup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Brankas_tutupMouseClicked(evt);
            }
        });
        getContentPane().add(Brankas_tutup);
        Brankas_tutup.setBounds(360, 430, 160, 160);

        Korek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/korek api.png"))); // NOI18N
        Korek.setText("jLabel1");
        Korek.setName("korek"); // NOI18N
        Korek.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                KorekMouseClicked(evt);
            }
        });
        getContentPane().add(Korek);
        Korek.setBounds(470, 488, 15, 20);

        Brankas_buka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/brankas terbuka.png"))); // NOI18N
        getContentPane().add(Brankas_buka);
        Brankas_buka.setBounds(360, 430, 160, 165);

        Pintu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/pintu.png"))); // NOI18N
        getContentPane().add(Pintu);
        Pintu.setBounds(530, 270, 150, 300);

        Kaleng_cat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/cat.png"))); // NOI18N
        getContentPane().add(Kaleng_cat);
        Kaleng_cat.setBounds(720, 530, 120, 100);

        Lampu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/lampu.png"))); // NOI18N
        getContentPane().add(Lampu);
        Lampu.setBounds(540, -20, 110, 160);

        Timer.setFont(new java.awt.Font("Playbill", 1, 36)); // NOI18N
        Timer.setForeground(new java.awt.Color(204, 255, 255));
        Timer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(Timer);
        Timer.setBounds(530, 190, 150, 50);
        Timer.setText("00 : 00");
        second =0;
        minute =0;
        normalTimer();
        timer.start();

        Pigura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/pigura.png"))); // NOI18N
        getContentPane().add(Pigura);
        Pigura.setBounds(310, 220, 160, 110);

        Papan_lukis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/papan lukis.png"))); // NOI18N
        getContentPane().add(Papan_lukis);
        Papan_lukis.setBounds(50, 440, 140, 310);

        Palet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/palet.png"))); // NOI18N
        getContentPane().add(Palet);
        Palet.setBounds(240, 660, 80, 31);

        Botol_cat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/botol cat.png"))); // NOI18N
        getContentPane().add(Botol_cat);
        Botol_cat.setBounds(200, 700, 90, 36);

        Cat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/tumpahan cat.png"))); // NOI18N
        getContentPane().add(Cat);
        Cat.setBounds(370, 610, 490, 120);

        Cat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/cipratan cat.png"))); // NOI18N
        Cat2.setText("jLabel17");
        getContentPane().add(Cat2);
        Cat2.setBounds(40, 250, 210, 190);

        Rak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/rak.png"))); // NOI18N
        getContentPane().add(Rak);
        Rak.setBounds(960, 290, 200, 60);

        Cat3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/cipratan cat2.png"))); // NOI18N
        getContentPane().add(Cat3);
        Cat3.setBounds(990, 260, 150, 240);

        Graviti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/garviti.png"))); // NOI18N
        getContentPane().add(Graviti);
        Graviti.setBounds(750, 230, 150, 270);

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/stiker.png"))); // NOI18N
        getContentPane().add(jLabel17);
        jLabel17.setBounds(260, 30, 680, 95);

        Latar_belakang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pbo/Level1/image/Latar Belakang.jpg"))); // NOI18N
        getContentPane().add(Latar_belakang);
        Latar_belakang.setBounds(0, 0, 1200, 750);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hint_3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hint_3MouseClicked
        hint_3.setVisible(false);
        hint_2.setVisible(true);
        Hint();
    }//GEN-LAST:event_hint_3MouseClicked

    private void hint_2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hint_2MouseClicked
        hint_2.setVisible(false);
        hint_1.setVisible(true);
        Hint();
    }//GEN-LAST:event_hint_2MouseClicked

    private void hint_1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hint_1MouseClicked
        hint_1.setVisible(false);
        hint_0.setVisible(true);
        Hint();
    }//GEN-LAST:event_hint_1MouseClicked

    private void hint_0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hint_0MouseClicked
        JOptionPane.showMessageDialog(this, "Hint sudah habis", "Hint", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_hint_0MouseClicked

    private void SandiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SandiMouseClicked
        Pin.setVisible(true);
    }//GEN-LAST:event_SandiMouseClicked

    private void KayuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KayuMouseClicked
        if(i.size() < 3){
            Kayu.setVisible(false);
            i.add(Kayu);
            have.put(Kayu, true);
            i_kayu.setVisible(true);
            i_kayu.setLocation(1250, coord_y[i.size()]);
        }
    }//GEN-LAST:event_KayuMouseClicked

    private void Paku_PaluMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Paku_PaluMouseClicked
        if(i.size() < 3){
            Paku_Palu.setVisible(false);
            i.add(Paku_Palu);
            have.put(Paku_Palu, true);
            i_paku_palu.setVisible(true);
            i_paku_palu.setLocation(1250, coord_y[i.size()]);
        }
    }//GEN-LAST:event_Paku_PaluMouseClicked

    private void ObengMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ObengMouseClicked
        if(i.size() < 3){
            Obeng.setVisible(false);
            i.add(Obeng);
            have.put(Obeng, true);
            i_obeng.setVisible(true);
            i_obeng.setLocation(1250, coord_y[i.size()]);
        }
    }//GEN-LAST:event_ObengMouseClicked

    private void Invetory_box1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Invetory_box1MouseClicked
        if(i.size() >= 0){
            if (click) {
                javax.swing.JLabel temp = (javax.swing.JLabel)tmp.remove(0);
                temp.setVisible(false);
                click = false;
            }
            if (kayu && i.get(0) == Paku_Palu) {
                make_tangga(0, Kayu);
            }else if (pakupalu && i.get(0) == Kayu) {
                make_tangga(0, Paku_Palu);
            }else{
                move(Inventory_click1);
                Unselect_All();
                if(i.get(0) instanceof javax.swing.JLabel){
                    javax.swing.JLabel curr = (javax.swing.JLabel) i.get(0);
                    Select(curr);
                }
            }
            
            try{
                if (((javax.swing.JLabel)i.get(0)).getName().equals("kertas_gosong")) {
                    Tulisan_rahasia.setVisible(true);
                    stay(Inventory_click1);
                }
            }catch(Exception e){
            }
        }
    }//GEN-LAST:event_Invetory_box1MouseClicked

    private void Invetory_box2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Invetory_box2MouseClicked
        if(i.size() >= 1){
            if (click) {
                javax.swing.JLabel temp = (javax.swing.JLabel)tmp.remove(0);
                temp.setVisible(false);
                click = false;
            }
            if (kayu && i.get(1) == Paku_Palu) {
                make_tangga(1, Kayu);
            }else if (pakupalu && i.get(1) == Kayu) {
                make_tangga(1, Paku_Palu);
            }else{
                move(Inventory_click2);
                Unselect_All();
                if(i.get(1) instanceof javax.swing.JLabel){
                    javax.swing.JLabel curr = (javax.swing.JLabel) i.get(1);
                    Select(curr);
                }
            }
            
            try{
                if (((javax.swing.JLabel)i.get(1)).getName().equals("kertas_gosong")) {
                    Tulisan_rahasia.setVisible(true);
                    stay(Inventory_click2);
                }
            }catch(Exception e){
            }
        }
    }//GEN-LAST:event_Invetory_box2MouseClicked

    private void Invetory_box3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Invetory_box3MouseClicked
        if(i.size() >= 2){
            if (click) {
                javax.swing.JLabel temp = (javax.swing.JLabel)tmp.remove(0);
                temp.setVisible(false);
                click = false;
            }
            if (kayu && i.get(2) == Paku_Palu) {
                make_tangga(2, Kayu);
            }else if (pakupalu && i.get(2) == Kayu) {
                make_tangga(2, Paku_Palu);
            }else{
                move(Inventory_click3);
                Unselect_All();
                if(i.get(2) instanceof javax.swing.JLabel){
                    javax.swing.JLabel curr = (javax.swing.JLabel) i.get(2);
                    Select(curr);
                }
            }
            
            try{
                if (((javax.swing.JLabel)i.get(2)).getName().equals("kertas_gosong")) {
                    Tulisan_rahasia.setVisible(true);
                    stay(Inventory_click3);
                }
            }catch(Exception e){
            }
        }
    }//GEN-LAST:event_Invetory_box3MouseClicked

    private void Invetory_box4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Invetory_box4MouseClicked
        if(i.size() >= 3){
            if (click) {
                javax.swing.JLabel temp = (javax.swing.JLabel)tmp.remove(0);
                temp.setVisible(false);
                click = false;
            }
            if (kayu && i.get(3) == Paku_Palu) {
                make_tangga(3, Kayu);
            }else if (pakupalu && i.get(3) == Kayu) {
                make_tangga(3, Paku_Palu);
            }else{
                move(Inventory_click4);
                Unselect_All();
                if(i.get(3) instanceof javax.swing.JLabel){
                    javax.swing.JLabel curr = (javax.swing.JLabel) i.get(3);
                    Select(curr);
                }
            }
            
            try{
                if (((javax.swing.JLabel)i.get(3)).getName().equals("kertas_gosong")) {
                    Tulisan_rahasia.setVisible(true);
                    stay(Inventory_click4);
                }
            }catch(Exception e){
            }
        }
    }//GEN-LAST:event_Invetory_box4MouseClicked

    private void Inventory_click1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventory_click1MouseClicked
        stay(Inventory_click1);
        
        javax.swing.JLabel curr = (javax.swing.JLabel) i.get(0);
        Unselect(curr);
    }//GEN-LAST:event_Inventory_click1MouseClicked

    private void Inventory_click2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventory_click2MouseClicked
        stay(Inventory_click2);
        
        javax.swing.JLabel curr = (javax.swing.JLabel) i.get(1);
        Unselect(curr);
    }//GEN-LAST:event_Inventory_click2MouseClicked

    private void Inventory_click3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventory_click3MouseClicked
        stay(Inventory_click3);
        
        javax.swing.JLabel curr = (javax.swing.JLabel) i.get(2);
        Unselect(curr);
    }//GEN-LAST:event_Inventory_click3MouseClicked

    private void Inventory_click4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventory_click4MouseClicked
        stay(Inventory_click4);
        
        javax.swing.JLabel curr = (javax.swing.JLabel) i.get(3);
        Unselect(curr);
    }//GEN-LAST:event_Inventory_click4MouseClicked

    private void VentilasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VentilasiMouseClicked
        if (obeng && put_tangga) {
            Ventilasi.setVisible(false);
            i_obeng.setVisible(false);
            i.remove(Obeng);
            new_Set();
            Ventilasi_buka.setVisible(true);
            Lorong_ventilasi.setVisible(true);
            Kertas.setVisible(true);
            open_ventilasi = true;
            
            javax.swing.JLabel temp = (javax.swing.JLabel)tmp.remove(0);
            temp.setVisible(false);
            click = false;
        }
    }//GEN-LAST:event_VentilasiMouseClicked

    private void place_tanggaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_place_tanggaMouseClicked
        if (tangga) {
            Tangga.setVisible(true);
            i_tangga.setVisible(false);
            i.remove(Tangga);
            new_Set();
            put_tangga = true;
            
            javax.swing.JLabel temp = (javax.swing.JLabel)tmp.remove(0);
            temp.setVisible(false);
            click = false;
        }
    }//GEN-LAST:event_place_tanggaMouseClicked

    private void TvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TvMouseClicked
        if (game.getWin()) {
            JOptionPane.showMessageDialog(this, (soal_pass + ", Kombinasi ke-" + comb_ke), "Quetion", JOptionPane.QUESTION_MESSAGE);
        }else{
            game = new Game(soal_pass, comb_ke);
            game.setVisible(true);
        }
    }//GEN-LAST:event_TvMouseClicked

    private void KorekMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KorekMouseClicked
        if (i.size() < 3) {
            i.add(Korek);
            Korek.setVisible(false);
            i_korek.setVisible(true);
            have.put(Korek, true);
            i_korek.setLocation(1250, coord_y[i.size()]);
        }
    }//GEN-LAST:event_KorekMouseClicked

    private void Brankas_tutupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Brankas_tutupMouseClicked
        Password.setVisible(true);
    }//GEN-LAST:event_Brankas_tutupMouseClicked

    private void Lilin_matiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Lilin_matiMouseClicked
        if (korek) {
            Lilin_mati.setVisible(false);
            Lilin_hidup.setVisible(true);
            light_lilin = true;
            i_korek.setVisible(false);
            i.remove(Korek);
            new_Set();
            
            javax.swing.JLabel temp = (javax.swing.JLabel)tmp.remove(0);
            temp.setVisible(false);
            click = false;
        }
    }//GEN-LAST:event_Lilin_matiMouseClicked

    private void KertasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KertasMouseClicked
        if (i.size() < 3) {
            i.add(Kertas);
            Kertas.setVisible(false);
            i_kertas.setVisible(true);
            have.put(Kertas, true);
            i_kertas.setLocation(1250, coord_y[i.size()]);
        }
    }//GEN-LAST:event_KertasMouseClicked

    private void Lilin_hidupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Lilin_hidupMouseClicked
        if (kertas) {
            i_kertas.setVisible(false);
            i.remove(Kertas);
            new_Set();
            i_kertas_gosong.setVisible(true);
            burn_kertas = true;
            i.add(i_kertas_gosong);
            i_kertas_gosong.setLocation(1250, coord_y[i.size()]);
            
            javax.swing.JLabel temp = (javax.swing.JLabel)tmp.remove(0);
            temp.setVisible(false);
            click = false;
        }
    }//GEN-LAST:event_Lilin_hidupMouseClicked

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
    // Pin
    private int c = 0;
    public void Munculkan(String n){
        switch(c){
            case 0: jLabel1.setText(n); c++; break;
            case 1: jLabel2.setText(n); c++; break;
            case 2: jLabel3.setText(n); c++; break;
            case 3: jLabel4.setText(n); c++; break;
            case 4: jLabel5.setText(n); c++; break;
            case 5: jLabel6.setText(n); c++; break;
        }
    }
    
    public void Hapus(){
        switch(c){
            case 1: jLabel1.setText(""); c--; break;
            case 2: jLabel2.setText(""); c--; break;
            case 3: jLabel3.setText(""); c--; break;
            case 4: jLabel4.setText(""); c--; break;
            case 5: jLabel5.setText(""); c--; break;
            case 6: jLabel6.setText(""); c--; break;
        }
    }
    
    public void cek(){
        String key = jLabel1.getText() + jLabel2.getText() + jLabel3.getText() + jLabel4.getText() + jLabel5.getText() + jLabel6.getText();
        if (key.equals(kunci_pin)) {
            Pin.setVisible(false);
            timer.stop();
            Highscore hs = new Highscore(1, username, minute*60+second);
            this.setVisible(false);
            if (clip != null) {
                clip.stop();
            }
            sl.setVisible(true);
            hs.setVisible(true);
            if (setting.isPlaying_music()) {
                main.start();
                main.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }else{
            jLabel1.setText("");
            jLabel2.setText("");
            jLabel3.setText("");
            jLabel4.setText("");
            jLabel5.setText("");
            jLabel6.setText("");
            c = 0;
        }
    }
    
    private void Tombol0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tombol0ActionPerformed
        Munculkan("0");
    }//GEN-LAST:event_Tombol0ActionPerformed

    private void OkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OkMouseClicked
        cek();
    }//GEN-LAST:event_OkMouseClicked

    private void Tombol9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tombol9ActionPerformed
        Munculkan("9");
    }//GEN-LAST:event_Tombol9ActionPerformed

    private void DeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteMouseClicked
        Hapus();
    }//GEN-LAST:event_DeleteMouseClicked

    private void Tombol8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tombol8ActionPerformed
        Munculkan("8");
    }//GEN-LAST:event_Tombol8ActionPerformed

    private void Tombol7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tombol7ActionPerformed
        Munculkan("7");
    }//GEN-LAST:event_Tombol7ActionPerformed

    private void Tombol6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tombol6ActionPerformed
        Munculkan("6");
    }//GEN-LAST:event_Tombol6ActionPerformed

    private void Tombol5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tombol5ActionPerformed
        Munculkan("5");
    }//GEN-LAST:event_Tombol5ActionPerformed

    private void Tombol4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tombol4ActionPerformed
        Munculkan("4");
    }//GEN-LAST:event_Tombol4ActionPerformed

    private void Tombol3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tombol3ActionPerformed
        Munculkan("3");
    }//GEN-LAST:event_Tombol3ActionPerformed

    private void Tombol2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tombol2ActionPerformed
        Munculkan("2");
    }//GEN-LAST:event_Tombol2ActionPerformed

    private void Tombol1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tombol1ActionPerformed
        Munculkan("1");
    }//GEN-LAST:event_Tombol1ActionPerformed
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Password
    private void EnterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EnterMouseClicked
        if (jTextField1.getText().equals(kunci_pass)) {
            Brankas_buka.setVisible(true);
            open_brankas = true;
            Korek.setVisible(true);
            Brankas_tutup.setVisible(false);
            Password.setVisible(false);
        }else{
            jTextField1.setText("");
        }
    }//GEN-LAST:event_EnterMouseClicked
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    
    
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
            java.util.logging.Logger.getLogger(Level1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Level1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Level1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Level1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Level1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JLabel Botol_cat;
    private javax.swing.JLabel Brankas_buka;
    private javax.swing.JLabel Brankas_tutup;
    private javax.swing.JLabel Cat;
    private javax.swing.JLabel Cat2;
    private javax.swing.JLabel Cat3;
    private javax.swing.JButton Delete;
    private javax.swing.JLabel Enter;
    private javax.swing.JLabel Graviti;
    private javax.swing.JLabel Inventory_backgound;
    private javax.swing.JLabel Inventory_click1;
    private javax.swing.JLabel Inventory_click2;
    private javax.swing.JLabel Inventory_click3;
    private javax.swing.JLabel Inventory_click4;
    private javax.swing.JLabel Invetory_box1;
    private javax.swing.JLabel Invetory_box2;
    private javax.swing.JLabel Invetory_box3;
    private javax.swing.JLabel Invetory_box4;
    private javax.swing.JLabel Kaleng_cat;
    private javax.swing.JLabel Kayu;
    private javax.swing.JLabel Kertas;
    private javax.swing.JLabel Keyboard;
    private javax.swing.JLabel Korek;
    private javax.swing.JLabel Lampu;
    private javax.swing.JLabel Latar_belakang;
    private javax.swing.JLabel Lilin_hidup;
    private javax.swing.JLabel Lilin_mati;
    private javax.swing.JLabel Lorong_ventilasi;
    private javax.swing.JLabel Meja;
    private javax.swing.JLabel Obeng;
    private javax.swing.JButton Ok;
    private javax.swing.JLabel Paku_Palu;
    private javax.swing.JLabel Palet;
    private javax.swing.JLabel Papan_lukis;
    private javax.swing.JFrame Password;
    private javax.swing.JLabel Perkakas;
    private javax.swing.JLabel Pigura;
    private javax.swing.JFrame Pin;
    private javax.swing.JLabel Pintu;
    private javax.swing.JLabel Rak;
    private javax.swing.JLabel Sajak1;
    private javax.swing.JLabel Sajak2;
    private javax.swing.JLabel Sajak3;
    private javax.swing.JLabel Sajak4;
    private javax.swing.JLabel Sandi;
    private javax.swing.JLabel Tangga;
    private javax.swing.JLabel Timer;
    private javax.swing.JButton Tombol0;
    private javax.swing.JButton Tombol1;
    private javax.swing.JButton Tombol2;
    private javax.swing.JButton Tombol3;
    private javax.swing.JButton Tombol4;
    private javax.swing.JButton Tombol5;
    private javax.swing.JButton Tombol6;
    private javax.swing.JButton Tombol7;
    private javax.swing.JButton Tombol8;
    private javax.swing.JButton Tombol9;
    private javax.swing.JFrame Tulisan_rahasia;
    private javax.swing.JLabel Tv;
    private javax.swing.JLabel Ventilasi;
    private javax.swing.JLabel Ventilasi_buka;
    private javax.swing.JLabel hint_0;
    private javax.swing.JLabel hint_1;
    private javax.swing.JLabel hint_2;
    private javax.swing.JLabel hint_3;
    private javax.swing.JLabel i_kayu;
    private javax.swing.JLabel i_kertas;
    private javax.swing.JLabel i_kertas_gosong;
    private javax.swing.JLabel i_korek;
    private javax.swing.JLabel i_obeng;
    private javax.swing.JLabel i_paku_palu;
    private javax.swing.JLabel i_tangga;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel papan_penunjuk_1;
    private javax.swing.JLabel place_tangga;
    // End of variables declaration//GEN-END:variables
}
