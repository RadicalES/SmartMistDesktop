/*
 * Copyright (C) 2012-2015 Radical Electronic Systems, South Africa
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.radicales.sm100.apps;

import com.radicales.sm100.device.*;
import java.awt.Cursor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

/**
 * Smart Mist GUI Applications
 * Provides a Swing JFrame application to manage a Smart Mist Controller.
 *
 * @author
 * Jan Zwiegers,
 * <a href="mailto:jan@radicalsystems.co.za">jan@radicalsystems.co.za</a>,
 * <a href="http://www.radicalsystems.co.za">www.radicalsystems.co.za</a>
 *
 * @version
 * <b>1.0 01/11/2014</b><br>
 * Original release.
 */
public class SmartMist extends javax.swing.JFrame implements Sm100Event, SetupXMLEvent {

    private Properties gAppProperties;
    private SetupXML gSetup;
    private Sm100 gSm100;
    private javax.swing.JTextField[] tfBudgets = new javax.swing.JTextField[12];


    /**
     * Creates new form Sm100
     */
    public SmartMist() {
        initComponents();
        loadProperties();
        loadSettings();
        initFields();
        gSm100 = null;
    }

    private void loadProperties() {
        String SettingsPath = this.getClass().getClassLoader().getResource("").getPath();
        gAppProperties = new Properties();

        try {
            try (FileInputStream in = new FileInputStream(SettingsPath + "SmartMist.properties")) {
                gAppProperties.load(in);
                in.close();
            }
        }
        catch(FileNotFoundException fnfe) {
            System.out.println("SmartMist.properties file not found");
        }
        catch(IOException ioe) {
            System.out.println("SmartMist.properties IO exception");
        }

        String sns = gAppProperties.getProperty("SystemNumber");
        if(sns != null) {
            //jcbbSystem.setSelectedItem(sns);
        }

        String sas = gAppProperties.getProperty("SourceAddress");
        if(sas != null) {
           // jtfSource.setText(sas);
        }
    }

    private void loadSettings() {
       String SettingsPath = this.getClass().getClassLoader().getResource("").getPath();
       gSetup = new SetupXML(SettingsPath + "SmartMist.xml", this);
       gSetup.parse();
    }

    private void initFields() {

        // load into array
        tfBudgets[0] = tfBudgetJan;
        tfBudgets[1] = tfBudgetFeb;
        tfBudgets[2] = tfBudgetMar;
        tfBudgets[3] = tfBudgetApr;
        tfBudgets[4] = tfBudgetMay;
        tfBudgets[5] = tfBudgetJun;
        tfBudgets[6] = tfBudgetJul;
        tfBudgets[7] = tfBudgetAug;
        tfBudgets[8] = tfBudgetSep;
        tfBudgets[9] = tfBudgetOct;
        tfBudgets[10] = tfBudgetNov;
        tfBudgets[11] = tfBudgetDec;

        // set values
        for(javax.swing.JTextField tf : tfBudgets) {
            tf.setText("100");
        }

        // clear program, start times and sequence lists.
        DefaultListModel lm = new DefaultListModel();
        lvPrograms.setModel(lm);
        lm = new DefaultListModel();
        lvStartTimes.setModel(lm);
        lm = new DefaultListModel();
        lvSequences.setModel(lm);
//        tfProgName.setText("");

        // load from settings object

    }

    private Sm100 getSelectedDevice( String Header ) {
        int i = lvDevices.getSelectedIndex();

        if(i < 0) {
            JOptionPane.showMessageDialog(this, "No device selected", Header, JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        return (Sm100)lvDevices.getModel().getElementAt(i);
    }

    private void updateStatusBar( String Device ) {
        lblStatus.setText(Device);
        invalidate();
    }

    private void Connect() {

        if(gSm100 == null) {
           gSm100 = getSelectedDevice("Connect");
           if(gSm100 != null) {
               //gSm100.registerEventListener(this);
               if(!gSm100.start()) {
                   JOptionPane.showMessageDialog(this, "Failed to start connection", "Connect", JOptionPane.ERROR_MESSAGE);
               }
           }
        }
        else {
            JOptionPane.showMessageDialog(this, "A connection is already active. Please disconnect first and then reconnect.", "Connect", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void Disconnect() {
        if(gSm100 != null) {
            gSm100.stop();
            //gSm100.removeEventListener(this);
            gSm100 = null;
        }
    }

    private void updateProgramDays( Sm100ProgramDays ProgDays ) {
        cbSunday.setSelected(ProgDays.Sundays);
        cbMonday.setSelected(ProgDays.Mondays);
        cbTuesday.setSelected(ProgDays.Tuesdays);
        cbWednesday.setSelected(ProgDays.Wednesdays);
        cbThursday.setSelected(ProgDays.Thursdays);
        cbFriday.setSelected(ProgDays.Fridays);
        cbSaturday.setSelected(ProgDays.Saturdays);
        cbOdd.setSelected(ProgDays.OddDays);
        cbOdd31.setSelected(ProgDays.Odd31Days);
        cbEven.setSelected(ProgDays.EvenDays);
        cbDayCycle.setSelected(ProgDays.DayCycle);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBarMain = new javax.swing.JToolBar();
        btnSaveSetup = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jTabbedPaneMain = new javax.swing.JTabbedPane();
        jPanelDevices = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lvDevices = new javax.swing.JList();
        jPanel15 = new javax.swing.JPanel();
        btnAddDevice = new javax.swing.JButton();
        btnRemoveDevice = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        tfDeviceDescription = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        tfDeviceLocation = new javax.swing.JTextField();
        tfIpAddress = new javax.swing.JFormattedTextField( new IPAddressFormatter());
        jPanelZones = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lvZones = new javax.swing.JList();
        jPanel14 = new javax.swing.JPanel();
        btnAddZone = new javax.swing.JButton();
        btnRemoveZone = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfZoneName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfZoneOffDelay = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cbbZoneChannel = new javax.swing.JComboBox();
        jLabel23 = new javax.swing.JLabel();
        cbPumpChannel = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jPanel20 = new javax.swing.JPanel();
        cbCycleJan = new javax.swing.JCheckBox();
        cbCycleFeb = new javax.swing.JCheckBox();
        cbCycleMarch = new javax.swing.JCheckBox();
        cbCycleApril = new javax.swing.JCheckBox();
        cbCycleMay = new javax.swing.JCheckBox();
        cbCycleJune = new javax.swing.JCheckBox();
        cbCycleJuly = new javax.swing.JCheckBox();
        cbCycleAug = new javax.swing.JCheckBox();
        cbCycleSep = new javax.swing.JCheckBox();
        cbCycleOct = new javax.swing.JCheckBox();
        cbCycleNov = new javax.swing.JCheckBox();
        cbCycleDec = new javax.swing.JCheckBox();
        jPanelPrograms = new javax.swing.JPanel();
        jPanelProgramsContainer = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lvPrograms = new javax.swing.JList();
        jPanel9 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        btnProgMinus = new javax.swing.JButton();
        btnProgStart = new javax.swing.JButton();
        btnProgStop = new javax.swing.JButton();
        jTabbedPaneProgramConfig = new javax.swing.JTabbedPane();
        jPanelStartTime = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lvStartTimes = new javax.swing.JList();
        jPanel21 = new javax.swing.JPanel();
        btnProgAddTime = new javax.swing.JButton();
        btnProgRemoveTime = new javax.swing.JButton();
        jPanelSequences = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lvSequences = new javax.swing.JList();
        jPanel6 = new javax.swing.JPanel();
        btnProgAddSeq = new javax.swing.JButton();
        btnProgRemoveSeq = new javax.swing.JButton();
        jPanelWaterBudget = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jCheckBox10 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        tfBudgetJan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tfBudgetFeb = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tfBudgetMar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        tfBudgetApr = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tfBudgetMay = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfBudgetJun = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tfBudgetJul = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tfBudgetAug = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tfBudgetSep = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        tfBudgetOct = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        tfBudgetNov = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        tfBudgetDec = new javax.swing.JTextField();
        jPanelCycle = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        cbAuto = new javax.swing.JCheckBox();
        jPanel23 = new javax.swing.JPanel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        cbSunday = new javax.swing.JCheckBox();
        cbMonday = new javax.swing.JCheckBox();
        cbTuesday = new javax.swing.JCheckBox();
        cbWednesday = new javax.swing.JCheckBox();
        cbThursday = new javax.swing.JCheckBox();
        cbFriday = new javax.swing.JCheckBox();
        cbSaturday = new javax.swing.JCheckBox();
        cbEven = new javax.swing.JCheckBox();
        cbOdd = new javax.swing.JCheckBox();
        cbOdd31 = new javax.swing.JCheckBox();
        cbDayCycle = new javax.swing.JCheckBox();
        tfDayCycle = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        cbStartInputA = new javax.swing.JCheckBox();
        cbStopInputA = new javax.swing.JCheckBox();
        cbStartInputB = new javax.swing.JCheckBox();
        cbStopInputB = new javax.swing.JCheckBox();
        cbStartInputC = new javax.swing.JCheckBox();
        cbStopInputC = new javax.swing.JCheckBox();
        cbRainSensorCancels = new javax.swing.JCheckBox();
        cbRainSensorDelays = new javax.swing.JCheckBox();
        jPanelCloud = new javax.swing.JPanel();
        cbTempBudget = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        tfMonActProg = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        tfMonActZone = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        tfMonActZoneChan = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        tfMonTimeLeft = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        cbMonInput0 = new javax.swing.JCheckBox();
        jLabel31 = new javax.swing.JLabel();
        cbMonInput1 = new javax.swing.JCheckBox();
        jLabel32 = new javax.swing.JLabel();
        cbMonInput2 = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        cbMonInput3 = new javax.swing.JCheckBox();
        jPanel19 = new javax.swing.JPanel();
        cbChan0 = new javax.swing.JCheckBox();
        cbChan1 = new javax.swing.JCheckBox();
        cbChan2 = new javax.swing.JCheckBox();
        cbChan3 = new javax.swing.JCheckBox();
        jPanel22 = new javax.swing.JPanel();
        cbChan4 = new javax.swing.JCheckBox();
        cbChan5 = new javax.swing.JCheckBox();
        cbChan6 = new javax.swing.JCheckBox();
        cbChan7 = new javax.swing.JCheckBox();
        jPanel18 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jCheckBox7 = new javax.swing.JCheckBox();
        jLabel36 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jPanelStatus = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Smart Mist - V1.0");
        setPreferredSize(new java.awt.Dimension(900, 800));
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

        jToolBarMain.setBackground(new java.awt.Color(255, 255, 255));
        jToolBarMain.setFloatable(false);
        jToolBarMain.setRollover(true);
        jToolBarMain.setAlignmentY(0.5F);
        jToolBarMain.setOpaque(false);
        jToolBarMain.setPreferredSize(new java.awt.Dimension(900, 68));

        btnSaveSetup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/save.png"))); // NOI18N
        btnSaveSetup.setActionCommand("save");
        btnSaveSetup.setFocusable(false);
        btnSaveSetup.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveSetup.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveSetup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarActionHandler(evt);
            }
        });
        jToolBarMain.add(btnSaveSetup);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/connect.png"))); // NOI18N
        jButton2.setToolTipText("Connect to device");
        jButton2.setActionCommand("connect");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarActionHandler(evt);
            }
        });
        jToolBarMain.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/discon.png"))); // NOI18N
        jButton3.setToolTipText("Disconnect from device");
        jButton3.setActionCommand("disconnect");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarActionHandler(evt);
            }
        });
        jToolBarMain.add(jButton3);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/search.png"))); // NOI18N
        jButton6.setToolTipText("Browse device");
        jButton6.setActionCommand("browse");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarActionHandler(evt);
            }
        });
        jToolBarMain.add(jButton6);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/upload.png"))); // NOI18N
        jButton4.setToolTipText("Upload setup to device");
        jButton4.setActionCommand("upload");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarActionHandler(evt);
            }
        });
        jToolBarMain.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/download.png"))); // NOI18N
        jButton5.setToolTipText("Download setup from device");
        jButton5.setActionCommand("download");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarActionHandler(evt);
            }
        });
        jToolBarMain.add(jButton5);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/settings.png"))); // NOI18N
        jButton7.setToolTipText("Program settings");
        jButton7.setActionCommand("settings");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarActionHandler(evt);
            }
        });
        jToolBarMain.add(jButton7);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/clock.png"))); // NOI18N
        jButton8.setToolTipText("Synchronize Date / Time");
        jButton8.setActionCommand("settimedate");
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toolbarActionHandler(evt);
            }
        });
        jToolBarMain.add(jButton8);

        getContentPane().add(jToolBarMain, java.awt.BorderLayout.NORTH);

        jTabbedPaneMain.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTabbedPaneMain.setPreferredSize(new java.awt.Dimension(900, 640));
        jTabbedPaneMain.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneMainStateChanged(evt);
            }
        });

        jPanelDevices.setPreferredSize(new java.awt.Dimension(1410, 600));

        jPanel10.setPreferredSize(new java.awt.Dimension(1200, 600));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jPanel4.setPreferredSize(new java.awt.Dimension(220, 580));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane4.setPreferredSize(new java.awt.Dimension(140, 530));

        lvDevices.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lvDevices.setPreferredSize(new java.awt.Dimension(40, 80));
        lvDevices.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lvDevicesHandler(evt);
            }
        });
        jScrollPane4.setViewportView(lvDevices);

        jPanel4.add(jScrollPane4);

        jPanel15.setPreferredSize(new java.awt.Dimension(110, 50));
        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        btnAddDevice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/plus_blue32.png"))); // NOI18N
        btnAddDevice.setPreferredSize(new java.awt.Dimension(42, 42));
        btnAddDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDeviceActionPerformed(evt);
            }
        });
        jPanel15.add(btnAddDevice);

        btnRemoveDevice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/minus_blue32.png"))); // NOI18N
        btnRemoveDevice.setPreferredSize(new java.awt.Dimension(42, 42));
        jPanel15.add(btnRemoveDevice);

        jPanel4.add(jPanel15);

        jPanel10.add(jPanel4);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel11.setPreferredSize(new java.awt.Dimension(504, 580));

        jLabel4.setText("IP Address");

        jLabel24.setText("Description");

        tfDeviceDescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfDeviceDescriptionActionPerformed(evt);
            }
        });

        jLabel25.setText("Location");

        tfDeviceLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfDeviceLocationActionPerformed(evt);
            }
        });

        tfIpAddress.setText("0.0.0.0");
        tfIpAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIpAddressActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfDeviceDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                    .addComponent(tfDeviceLocation)
                    .addComponent(tfIpAddress))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfIpAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(tfDeviceDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfDeviceLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addContainerGap(473, Short.MAX_VALUE))
        );

        jPanel10.add(jPanel11);

        javax.swing.GroupLayout jPanelDevicesLayout = new javax.swing.GroupLayout(jPanelDevices);
        jPanelDevices.setLayout(jPanelDevicesLayout);
        jPanelDevicesLayout.setHorizontalGroup(
            jPanelDevicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDevicesLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 882, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelDevicesLayout.setVerticalGroup(
            jPanelDevicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDevicesLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Devices", jPanelDevices);

        jPanelZones.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanelZones.setPreferredSize(new java.awt.Dimension(1410, 600));

        jPanel2.setPreferredSize(new java.awt.Dimension(1200, 600));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jPanel13.setPreferredSize(new java.awt.Dimension(220, 580));
        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 530));

        lvZones.setPreferredSize(new java.awt.Dimension(40, 80));
        lvZones.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lvZoneSelectionChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lvZones);

        jPanel13.add(jScrollPane1);

        jPanel14.setPreferredSize(new java.awt.Dimension(200, 50));
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        btnAddZone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/plus_blue32.png"))); // NOI18N
        btnAddZone.setPreferredSize(new java.awt.Dimension(42, 42));
        btnAddZone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddZoneActionPerformed(evt);
            }
        });
        jPanel14.add(btnAddZone);

        btnRemoveZone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/minus_blue32.png"))); // NOI18N
        btnRemoveZone.setPreferredSize(new java.awt.Dimension(42, 42));
        btnRemoveZone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveZoneActionPerformed(evt);
            }
        });
        jPanel14.add(btnRemoveZone);

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/play32.png"))); // NOI18N
        jButton10.setPreferredSize(new java.awt.Dimension(42, 42));
        jPanel14.add(jButton10);

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/stop32.png"))); // NOI18N
        jButton11.setPreferredSize(new java.awt.Dimension(42, 42));
        jPanel14.add(jButton11);

        jPanel13.add(jPanel14);

        jPanel2.add(jPanel13);

        jPanel1.setPreferredSize(new java.awt.Dimension(400, 580));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(300, 220));
        jPanel5.setLayout(new java.awt.GridLayout(6, 2, 10, 4));

        jLabel2.setText("Zone Name");
        jPanel5.add(jLabel2);

        tfZoneName.setText("Zone A");
        jPanel5.add(tfZoneName);

        jLabel3.setText("Off delay");
        jPanel5.add(jLabel3);

        tfZoneOffDelay.setText("10");
        jPanel5.add(tfZoneOffDelay);

        jLabel1.setText("Channel");
        jPanel5.add(jLabel1);

        cbbZoneChannel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "#0 Undefined", "#1", "#2", "#3", "#4", "#5", "#6", "#7", "#8" }));
        jPanel5.add(cbbZoneChannel);

        jLabel23.setText("Pump Channel");
        jPanel5.add(jLabel23);

        cbPumpChannel.setText("Enabled");
        jPanel5.add(cbPumpChannel);

        jLabel7.setText("Wind Sensitive");
        jPanel5.add(jLabel7);

        jCheckBox1.setText("Enabled");
        jPanel5.add(jCheckBox1);

        jLabel22.setText("Temperature Sensitive");
        jPanel5.add(jLabel22);

        jCheckBox2.setText("Enabled");
        jPanel5.add(jCheckBox2);

        jPanel1.add(jPanel5);

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Months")));
        jPanel20.setPreferredSize(new java.awt.Dimension(300, 220));
        jPanel20.setLayout(new java.awt.GridLayout(6, 2));

        cbCycleJan.setText("January");
        jPanel20.add(cbCycleJan);

        cbCycleFeb.setText("February");
        jPanel20.add(cbCycleFeb);

        cbCycleMarch.setText("March");
        jPanel20.add(cbCycleMarch);

        cbCycleApril.setText("April");
        jPanel20.add(cbCycleApril);

        cbCycleMay.setText("May");
        jPanel20.add(cbCycleMay);

        cbCycleJune.setText("June");
        jPanel20.add(cbCycleJune);

        cbCycleJuly.setText("July");
        jPanel20.add(cbCycleJuly);

        cbCycleAug.setText("August");
        jPanel20.add(cbCycleAug);

        cbCycleSep.setText("September");
        jPanel20.add(cbCycleSep);

        cbCycleOct.setText("October");
        jPanel20.add(cbCycleOct);

        cbCycleNov.setText("November");
        jPanel20.add(cbCycleNov);

        cbCycleDec.setText("December");
        jPanel20.add(cbCycleDec);

        jPanel1.add(jPanel20);

        jPanel2.add(jPanel1);

        javax.swing.GroupLayout jPanelZonesLayout = new javax.swing.GroupLayout(jPanelZones);
        jPanelZones.setLayout(jPanelZonesLayout);
        jPanelZonesLayout.setHorizontalGroup(
            jPanelZonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelZonesLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 885, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelZonesLayout.setVerticalGroup(
            jPanelZonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelZonesLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 59, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Zones", jPanelZones);

        jPanelPrograms.setPreferredSize(new java.awt.Dimension(1410, 600));

        jPanelProgramsContainer.setPreferredSize(new java.awt.Dimension(1400, 600));
        jPanelProgramsContainer.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                jPanelProgramsContainerAncestorResized(evt);
            }
        });
        jPanelProgramsContainer.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jPanel3.setMaximumSize(new java.awt.Dimension(220, 65534));
        jPanel3.setPreferredSize(new java.awt.Dimension(220, 580));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(140, 600));

        lvPrograms.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lvProgramSelectionChanged(evt);
            }
        });
        jScrollPane2.setViewportView(lvPrograms);

        jPanel3.add(jScrollPane2);

        jPanel9.setPreferredSize(new java.awt.Dimension(220, 50));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/plus_blue32.png"))); // NOI18N
        jButton9.setToolTipText("Add a new program");
        jButton9.setPreferredSize(new java.awt.Dimension(42, 42));
        jPanel9.add(jButton9);

        btnProgMinus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/minus_blue32.png"))); // NOI18N
        btnProgMinus.setToolTipText("Remove a program");
        btnProgMinus.setActionCommand("remove");
        btnProgMinus.setPreferredSize(new java.awt.Dimension(42, 42));
        jPanel9.add(btnProgMinus);

        btnProgStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/play32.png"))); // NOI18N
        btnProgStart.setToolTipText("Run a selected program");
        btnProgStart.setActionCommand("start");
        btnProgStart.setPreferredSize(new java.awt.Dimension(42, 42));
        btnProgStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProgramsHandler(evt);
            }
        });
        jPanel9.add(btnProgStart);

        btnProgStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/stop32.png"))); // NOI18N
        btnProgStop.setToolTipText("Stop any running program");
        btnProgStop.setActionCommand("stop");
        btnProgStop.setPreferredSize(new java.awt.Dimension(42, 42));
        jPanel9.add(btnProgStop);

        jPanel3.add(jPanel9);

        jPanelProgramsContainer.add(jPanel3);

        jTabbedPaneProgramConfig.setBorder(javax.swing.BorderFactory.createTitledBorder("Configuration"));
        jTabbedPaneProgramConfig.setPreferredSize(new java.awt.Dimension(653, 580));

        jPanelStartTime.setLayout(new javax.swing.BoxLayout(jPanelStartTime, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane3.setPreferredSize(new java.awt.Dimension(35, 530));

        lvStartTimes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(lvStartTimes);

        jPanelStartTime.add(jScrollPane3);

        jPanel21.setPreferredSize(new java.awt.Dimension(636, 50));
        jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        btnProgAddTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/plus_blue32.png"))); // NOI18N
        btnProgAddTime.setToolTipText("Add a program start time");
        btnProgAddTime.setActionCommand("addtime");
        btnProgAddTime.setPreferredSize(new java.awt.Dimension(42, 42));
        btnProgAddTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProgAddTimeActionPerformed(evt);
            }
        });
        jPanel21.add(btnProgAddTime);

        btnProgRemoveTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/minus_blue32.png"))); // NOI18N
        btnProgRemoveTime.setToolTipText("Remove a program start time");
        btnProgRemoveTime.setActionCommand("removetime");
        btnProgRemoveTime.setPreferredSize(new java.awt.Dimension(42, 42));
        btnProgRemoveTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProgRemoveTimeActionPerformed(evt);
            }
        });
        jPanel21.add(btnProgRemoveTime);

        jPanelStartTime.add(jPanel21);

        jTabbedPaneProgramConfig.addTab("Start Times", jPanelStartTime);

        jPanelSequences.setLayout(new javax.swing.BoxLayout(jPanelSequences, javax.swing.BoxLayout.PAGE_AXIS));

        jScrollPane5.setPreferredSize(new java.awt.Dimension(35, 530));

        lvSequences.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(lvSequences);

        jPanelSequences.add(jScrollPane5);

        jPanel6.setPreferredSize(new java.awt.Dimension(349, 50));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        btnProgAddSeq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/plus_blue32.png"))); // NOI18N
        btnProgAddSeq.setToolTipText("Add a new program sequence");
        btnProgAddSeq.setActionCommand("addseq");
        btnProgAddSeq.setPreferredSize(new java.awt.Dimension(42, 42));
        jPanel6.add(btnProgAddSeq);

        btnProgRemoveSeq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/radicales/sm100/apps/resources/minus_blue32.png"))); // NOI18N
        btnProgRemoveSeq.setToolTipText("Remove a program sequence");
        btnProgRemoveSeq.setActionCommand("removeseq");
        btnProgRemoveSeq.setPreferredSize(new java.awt.Dimension(42, 42));
        jPanel6.add(btnProgRemoveSeq);

        jPanelSequences.add(jPanel6);

        jTabbedPaneProgramConfig.addTab("Sequences", jPanelSequences);

        jPanelWaterBudget.setLayout(new java.awt.GridLayout(13, 2, 4, 4));

        jLabel5.setText("Status");
        jPanelWaterBudget.add(jLabel5);

        jCheckBox10.setText("Enabled");
        jPanelWaterBudget.add(jCheckBox10);

        jLabel8.setText("January");
        jPanelWaterBudget.add(jLabel8);

        tfBudgetJan.setText("jTextField4");
        jPanelWaterBudget.add(tfBudgetJan);

        jLabel9.setText("February");
        jPanelWaterBudget.add(jLabel9);

        tfBudgetFeb.setText("jTextField5");
        jPanelWaterBudget.add(tfBudgetFeb);

        jLabel10.setText("March");
        jPanelWaterBudget.add(jLabel10);

        tfBudgetMar.setText("jTextField6");
        jPanelWaterBudget.add(tfBudgetMar);

        jLabel11.setText("April");
        jPanelWaterBudget.add(jLabel11);

        tfBudgetApr.setText("jTextField7");
        jPanelWaterBudget.add(tfBudgetApr);

        jLabel12.setText("May");
        jPanelWaterBudget.add(jLabel12);

        tfBudgetMay.setText("jTextField8");
        jPanelWaterBudget.add(tfBudgetMay);

        jLabel13.setText("June");
        jPanelWaterBudget.add(jLabel13);

        tfBudgetJun.setText("jTextField9");
        jPanelWaterBudget.add(tfBudgetJun);

        jLabel14.setText("July");
        jPanelWaterBudget.add(jLabel14);

        tfBudgetJul.setText("jTextField10");
        jPanelWaterBudget.add(tfBudgetJul);

        jLabel15.setText("August");
        jPanelWaterBudget.add(jLabel15);

        tfBudgetAug.setText("jTextField11");
        jPanelWaterBudget.add(tfBudgetAug);

        jLabel16.setText("September");
        jPanelWaterBudget.add(jLabel16);

        tfBudgetSep.setText("jTextField12");
        jPanelWaterBudget.add(tfBudgetSep);

        jLabel17.setText("October");
        jPanelWaterBudget.add(jLabel17);

        tfBudgetOct.setText("jTextField13");
        jPanelWaterBudget.add(tfBudgetOct);

        jLabel18.setText("November");
        jPanelWaterBudget.add(jLabel18);

        tfBudgetNov.setText("jTextField14");
        jPanelWaterBudget.add(tfBudgetNov);

        jLabel19.setText("December");
        jPanelWaterBudget.add(jLabel19);

        tfBudgetDec.setText("jTextField15");
        jPanelWaterBudget.add(tfBudgetDec);

        jTabbedPaneProgramConfig.addTab("Water Budget", jPanelWaterBudget);

        jPanelCycle.setLayout(new javax.swing.BoxLayout(jPanelCycle, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel12.setMinimumSize(new java.awt.Dimension(105, 40));
        jPanel12.setPreferredSize(new java.awt.Dimension(624, 20));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 10, 5);
        flowLayout1.setAlignOnBaseline(true);
        jPanel12.setLayout(flowLayout1);

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder("Mode"));
        jPanel24.setPreferredSize(new java.awt.Dimension(180, 80));
        jPanel24.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        cbAuto.setText("Auto");
        jPanel24.add(cbAuto);

        jPanel12.add(jPanel24);

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Cloud Rain"));
        jPanel23.setPreferredSize(new java.awt.Dimension(260, 80));
        jPanel23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jCheckBox3.setText("Delays");
        jPanel23.add(jCheckBox3);

        jCheckBox4.setText("Cancels");
        jPanel23.add(jCheckBox4);

        jPanel12.add(jPanel23);

        jPanelCycle.add(jPanel12);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Days"));
        jPanel7.setPreferredSize(new java.awt.Dimension(373, 180));
        jPanel7.setLayout(new java.awt.GridLayout(6, 2));

        cbSunday.setText("Sunday");
        jPanel7.add(cbSunday);

        cbMonday.setText("Monday");
        jPanel7.add(cbMonday);

        cbTuesday.setText("Tuesday");
        jPanel7.add(cbTuesday);

        cbWednesday.setText("Wednesday");
        jPanel7.add(cbWednesday);

        cbThursday.setText("Thursday");
        jPanel7.add(cbThursday);

        cbFriday.setText("Friday");
        jPanel7.add(cbFriday);

        cbSaturday.setText("Saturday");
        jPanel7.add(cbSaturday);

        cbEven.setText("Even");
        jPanel7.add(cbEven);

        cbOdd.setText("Odd");
        cbOdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOddActionPerformed(evt);
            }
        });
        jPanel7.add(cbOdd);

        cbOdd31.setText("Odd 31");
        jPanel7.add(cbOdd31);

        cbDayCycle.setText("Day Cycle");
        jPanel7.add(cbDayCycle);

        tfDayCycle.setText("0");
        jPanel7.add(tfDayCycle);

        jPanelCycle.add(jPanel7);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Inputs"));
        jPanel8.setPreferredSize(new java.awt.Dimension(185, 120));
        jPanel8.setLayout(new java.awt.GridLayout(4, 2));

        cbStartInputA.setText("Input A Start");
        jPanel8.add(cbStartInputA);

        cbStopInputA.setText("Input A Stop");
        jPanel8.add(cbStopInputA);

        cbStartInputB.setText("Input B Start");
        jPanel8.add(cbStartInputB);

        cbStopInputB.setText("Input B Stop");
        jPanel8.add(cbStopInputB);

        cbStartInputC.setText("Input C Start");
        jPanel8.add(cbStartInputC);

        cbStopInputC.setText("Input C Stop");
        jPanel8.add(cbStopInputC);

        cbRainSensorCancels.setText("Rain Sensor Cancels");
        jPanel8.add(cbRainSensorCancels);

        cbRainSensorDelays.setText("Rain Sensor Delays");
        jPanel8.add(cbRainSensorDelays);

        jPanelCycle.add(jPanel8);

        jTabbedPaneProgramConfig.addTab("Cycle Settings", jPanelCycle);

        cbTempBudget.setText("Enabled");

        jLabel20.setText("High Limit");

        jTextField1.setText("35 Deg C");

        jTextField2.setText("150");

        jLabel21.setText("Low Limit");

        jTextField3.setText("10 Deg C");

        jTextField4.setText("50");

        javax.swing.GroupLayout jPanelCloudLayout = new javax.swing.GroupLayout(jPanelCloud);
        jPanelCloud.setLayout(jPanelCloudLayout);
        jPanelCloudLayout.setHorizontalGroup(
            jPanelCloudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCloudLayout.createSequentialGroup()
                .addComponent(cbTempBudget)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelCloudLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCloudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCloudLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(14, 14, 14)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCloudLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(445, Short.MAX_VALUE))
        );
        jPanelCloudLayout.setVerticalGroup(
            jPanelCloudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCloudLayout.createSequentialGroup()
                .addComponent(cbTempBudget)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCloudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCloudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 458, Short.MAX_VALUE))
        );

        jTabbedPaneProgramConfig.addTab("Cloud", jPanelCloud);

        jPanelProgramsContainer.add(jTabbedPaneProgramConfig);

        javax.swing.GroupLayout jPanelProgramsLayout = new javax.swing.GroupLayout(jPanelPrograms);
        jPanelPrograms.setLayout(jPanelProgramsLayout);
        jPanelProgramsLayout.setHorizontalGroup(
            jPanelProgramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProgramsLayout.createSequentialGroup()
                .addComponent(jPanelProgramsContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 885, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelProgramsLayout.setVerticalGroup(
            jPanelProgramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProgramsLayout.createSequentialGroup()
                .addComponent(jPanelProgramsContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 48, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Programs", jPanelPrograms);

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("Device"));
        jPanel17.setPreferredSize(new java.awt.Dimension(140, 230));
        jPanel17.setLayout(new java.awt.GridLayout(10, 2, 5, 5));

        jLabel26.setText("Date & Time");
        jPanel17.add(jLabel26);

        jTextField5.setText("jTextField5");
        jPanel17.add(jTextField5);

        jLabel27.setText("Active Program");
        jPanel17.add(jLabel27);

        tfMonActProg.setText("None");
        jPanel17.add(tfMonActProg);

        jLabel28.setText("Active Zone");
        jPanel17.add(jLabel28);

        tfMonActZone.setText("None");
        jPanel17.add(tfMonActZone);

        jLabel34.setText("Ouput Channel");
        jPanel17.add(jLabel34);

        tfMonActZoneChan.setText("None");
        jPanel17.add(tfMonActZoneChan);

        jLabel29.setText("Time Left");
        jPanel17.add(jLabel29);

        tfMonTimeLeft.setText("None");
        jPanel17.add(tfMonTimeLeft);

        jLabel30.setText("Input A");
        jPanel17.add(jLabel30);

        cbMonInput0.setText("On");
        jPanel17.add(cbMonInput0);

        jLabel31.setText("Input B");
        jPanel17.add(jLabel31);

        cbMonInput1.setText("On");
        jPanel17.add(cbMonInput1);

        jLabel32.setText("Input C");
        jPanel17.add(jLabel32);

        cbMonInput2.setText("On");
        jPanel17.add(cbMonInput2);

        jLabel33.setText("Rain Sensor");
        jPanel17.add(jLabel33);

        cbMonInput3.setText("On");
        jPanel17.add(cbMonInput3);

        cbChan0.setText("#1");
        jPanel19.add(cbChan0);

        cbChan1.setText("#2");
        jPanel19.add(cbChan1);

        cbChan2.setText("#3");
        jPanel19.add(cbChan2);

        cbChan3.setText("#4");
        jPanel19.add(cbChan3);

        jPanel17.add(jPanel19);

        cbChan4.setText("#5");
        jPanel22.add(cbChan4);

        cbChan5.setText("#6");
        jPanel22.add(cbChan5);

        cbChan6.setText("#7");
        jPanel22.add(cbChan6);

        cbChan7.setText("#8");
        jPanel22.add(cbChan7);

        jPanel17.add(jPanel22);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Cloud"));
        jPanel18.setPreferredSize(new java.awt.Dimension(140, 138));
        jPanel18.setLayout(new java.awt.GridLayout(6, 2, 5, 5));

        jLabel35.setText("Rain Delayed");
        jPanel18.add(jLabel35);

        jCheckBox7.setText("Active");
        jPanel18.add(jCheckBox7);

        jLabel36.setText("Wind Speed");
        jPanel18.add(jLabel36);

        jTextField10.setText("jTextField10");
        jPanel18.add(jTextField10);

        jLabel37.setText("Temperature");
        jPanel18.add(jLabel37);

        jTextField11.setText("jTextField11");
        jPanel18.add(jTextField11);

        jLabel38.setText("Last Sync");
        jPanel18.add(jLabel38);

        jTextField12.setText("jTextField12");
        jPanel18.add(jTextField12);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(210, Short.MAX_VALUE))
        );

        jTabbedPaneMain.addTab("Monitor", jPanel16);

        getContentPane().add(jTabbedPaneMain, java.awt.BorderLayout.WEST);
        jTabbedPaneMain.getAccessibleContext().setAccessibleName("Devices");

        jPanelStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelStatus.setFocusable(false);
        jPanelStatus.setPreferredSize(new java.awt.Dimension(600, 32));

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStatus.setText("Not Connected");

        lblTime.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblTime.setText("Unknown");
        lblTime.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanelStatusLayout = new javax.swing.GroupLayout(jPanelStatus);
        jPanelStatus.setLayout(jPanelStatusLayout);
        jPanelStatusLayout.setHorizontalGroup(
            jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatusLayout.createSequentialGroup()
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelStatusLayout.setVerticalGroup(
            jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatusLayout.createSequentialGroup()
                .addGroup(jPanelStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        getContentPane().add(jPanelStatus, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void toolbarActionHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toolbarActionHandler

        String cmd = evt.getActionCommand();
        Cursor d = getCursor();
        Cursor w = new Cursor(Cursor.WAIT_CURSOR);
        setCursor(w);

        if(cmd.matches("connect")) {
            Connect();
        }
        else if(cmd.matches("disconnect")) {
            Disconnect();
        }
        else if(cmd.matches("download")) {
            gSm100.syncDownloadPrograms();
        }
        else if(cmd.matches("upload")) {
            gSm100.syncUploadPrograms();
        }
        else if(cmd.matches("settimedate")) {
            Date date = new Date();
            gSm100.setDateTime(date);
            gSm100.getDateTime();
        }
        else if(cmd.matches("save")) {
           // String SettingsPath = this.getClass().getClassLoader().getResource("").getPath();
            if(!gSetup.save()) {
                JOptionPane.showMessageDialog(this, "Failed to save setup file!", "Save File", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Unknown command: " + cmd, "Toolbar", JOptionPane.ERROR_MESSAGE);
        }

        setCursor(d);
    }//GEN-LAST:event_toolbarActionHandler

    private void lvZoneSelectionChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lvZoneSelectionChanged
        int i = lvZones.getSelectedIndex();

        if(i < 0) {
            return;
        }

        Sm100Zone z = (Sm100Zone)lvZones.getModel().getElementAt(i);

        if(z != null) {
            tfZoneName.setText(z.getName());
            cbbZoneChannel.setSelectedIndex(z.getIndex());
            tfZoneOffDelay.setText(Integer.toString(z.getOffDelay()));
        }
        else {
            tfZoneName.setText("Error");
            cbbZoneChannel.setSelectedIndex(0);
            tfZoneOffDelay.setText("10");
        }
    }//GEN-LAST:event_lvZoneSelectionChanged

    private void lvProgramSelectionChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lvProgramSelectionChanged
        int i = lvPrograms.getSelectedIndex();

        if(i < 0) {
            return;
        }

        Sm100Program p = (Sm100Program)lvPrograms.getModel().getElementAt(i);

        if(p != null) {
//            tfProgName.setText(p.getName());
            int[] wb = p.getWaterBudget();
            for(i=0; i<wb.length; i++) {
                tfBudgets[i].setText(Integer.toString(wb[i]));
            }

            lvStartTimes.removeAll();
            DefaultListModel lmm = new DefaultListModel();
            List<StartTime> sts = p.getStartTimesList();
            for(StartTime st : sts) {
                lmm.addElement(st);
            }
            lvStartTimes.setModel(lmm);
            lvStartTimes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            lvSequences.removeAll();
            DefaultListModel lmmm = new DefaultListModel();
            List<Sequence> seq = p.getSequenceList();
            for(Sequence s : seq) {
                lmmm.addElement(s.toString());
            }
            lvSequences.setModel(lmmm);
            lvSequences.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            updateProgramDays(p.getProgramDays());
            cbAuto.setSelected(p.isAuto());
            cbStartInputA.setSelected(p.isTriggerA());
            cbStartInputB.setSelected(p.isTriggerB());
            cbRainSensorCancels.setSelected(p.isRainSensor());
           // cbStop.setSelected(p.isStopInput());
        }
        else {
            // clear info
        }
    }//GEN-LAST:event_lvProgramSelectionChanged

    private void ProgramsHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProgramsHandler
        String cmd = evt.getActionCommand();
        int i = lvPrograms.getSelectedIndex();
        Sm100Program n = (Sm100Program)lvPrograms.getModel().getElementAt(i);
        if(cmd.matches("start")) {
            gSm100.startProgram(n.getName());
        }
        else if(cmd.matches("stop")) {
            gSm100.stopProgram(n.getName());
        }
    }//GEN-LAST:event_ProgramsHandler

    private void cbOddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbOddActionPerformed

    private void btnRemoveZoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveZoneActionPerformed
        int i = lvZones.getSelectedIndex();
         Sm100Zone z = (Sm100Zone)lvZones.getModel().getElementAt(i);
         if(z != null) {
             gSm100.removeZone(z);
         }
    }//GEN-LAST:event_btnRemoveZoneActionPerformed

    private void btnAddZoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddZoneActionPerformed

        if(gSm100 == null) {
            JOptionPane.showMessageDialog(this, "Not connected!", "Add Zone", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            gSm100.addNextZone();
        } catch (Sm100Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to add new zone: " + ex.getMessage(), "Add Zone", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAddZoneActionPerformed

    private void lvDevicesHandler(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lvDevicesHandler
        int i = lvDevices.getSelectedIndex();
        if(i < 0) {
            return;
        }

        Sm100 sm = (Sm100)lvDevices.getModel().getElementAt(i);
        if(sm != null) {
            tfIpAddress.setText(sm.getIpAddress());
            tfDeviceDescription.setText(sm.getDescription());
            tfDeviceLocation.setText(sm.getLocation());

            // update Zones list
            DefaultListModel lm = new DefaultListModel();
            for(Sm100Zone z : sm.getZoneList()) {
                lm.addElement(z);
            }
            lvZones.setModel(lm);
            lvZones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lvZones.setSelectedIndex(0);

            // update programs list
            lm = new DefaultListModel();
            for(Sm100Program p : sm.getProgramsList()) {
                lm.addElement(p);
            }
            lvPrograms.setModel(lm);
            lvPrograms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lvPrograms.setSelectedIndex(0);
        }
    }//GEN-LAST:event_lvDevicesHandler

    private void jPanelProgramsContainerAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jPanelProgramsContainerAncestorResized

       // pack();
    }//GEN-LAST:event_jPanelProgramsContainerAncestorResized

    private void btnAddDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDeviceActionPerformed
        NewDeviceDialog dlg = new NewDeviceDialog((java.awt.Frame)this.getOwner(), true);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);

        if(dlg.getStatus() == NewDeviceDialog.STATUS_OK) {
            Sm100 sm = new Sm100(dlg.getDeviceName(), dlg.getIpAddress(), dlg.getPort(), true);
            gSetup.addDevice(sm);
        }

    }//GEN-LAST:event_btnAddDeviceActionPerformed

    private void tfDeviceDescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfDeviceDescriptionActionPerformed
        int i = lvDevices.getSelectedIndex();
        Sm100 sm = (Sm100)lvDevices.getModel().getElementAt(i);
        if(sm != null) {
            sm.setDescription(tfDeviceDescription.getText());
        }
    }//GEN-LAST:event_tfDeviceDescriptionActionPerformed

    private void tfIpAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIpAddressActionPerformed
        int i = lvDevices.getSelectedIndex();
        Sm100 sm = (Sm100)lvDevices.getModel().getElementAt(i);
        if(sm != null) {
            sm.setIpAddress(tfIpAddress.getText());
        }
    }//GEN-LAST:event_tfIpAddressActionPerformed

    private void tfDeviceLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfDeviceLocationActionPerformed
        int i = lvDevices.getSelectedIndex();
        Sm100 sm = (Sm100)lvDevices.getModel().getElementAt(i);
        if(sm != null) {
            sm.setLocation(tfDeviceLocation.getText());
        }
    }//GEN-LAST:event_tfDeviceLocationActionPerformed

    private void jTabbedPaneMainStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneMainStateChanged
        javax.swing.JTabbedPane tp = (javax.swing.JTabbedPane) evt.getSource();
        int index = tp.getSelectedIndex();
        if(index == 3) {
            if(gSm100 != null) {
                System.out.println("Starting monitoring");
                gSm100.startMonitor();
            }
        }
        else {
            if(gSm100 != null) {
                System.out.println("Stopping monitoring");
                gSm100.stopMonitor();
            }
        }
    }//GEN-LAST:event_jTabbedPaneMainStateChanged

    private void btnProgAddTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProgAddTimeActionPerformed
        int pi = lvPrograms.getSelectedIndex();
        if(pi == -1) {
            JOptionPane.showMessageDialog(this, "Please select a program", "Add Start Time", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Sm100Program p = (Sm100Program)lvPrograms.getModel().getElementAt(pi);
        SetStartTimeDialog dlg = new SetStartTimeDialog((java.awt.Frame)this.getOwner(), true);
        dlg.setLocationRelativeTo(this);
        dlg.setTitle("Add Start Time");
        dlg.setVisible(true);

        if(dlg.getStatus() == SetStartTimeDialog.STATUS_OK) {
            StartTime st = dlg.getStartTime();
            try {
                p.addStartTime(st);
            }
            catch(Sm100ProgramException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Add Start Time", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnProgAddTimeActionPerformed

    private void btnProgRemoveTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProgRemoveTimeActionPerformed
        int pi = lvPrograms.getSelectedIndex();
        int si = lvStartTimes.getSelectedIndex();
        if(pi == -1) {
            JOptionPane.showMessageDialog(this, "Please select a program", "Remove Start Time", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(si == -1) {
            JOptionPane.showMessageDialog(this, "Please select a start time", "Remove Start Time", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Sm100Program p = (Sm100Program)lvPrograms.getModel().getElementAt(pi);
        StartTime s = (StartTime)lvStartTimes.getModel().getElementAt(si);
        try {
            p.removeStartTime(s);
        }
        catch(Sm100ProgramException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Add Start Time", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnProgRemoveTimeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SmartMist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SmartMist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SmartMist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SmartMist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SmartMist().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDevice;
    private javax.swing.JButton btnAddZone;
    private javax.swing.JButton btnProgAddSeq;
    private javax.swing.JButton btnProgAddTime;
    private javax.swing.JButton btnProgMinus;
    private javax.swing.JButton btnProgRemoveSeq;
    private javax.swing.JButton btnProgRemoveTime;
    private javax.swing.JButton btnProgStart;
    private javax.swing.JButton btnProgStop;
    private javax.swing.JButton btnRemoveDevice;
    private javax.swing.JButton btnRemoveZone;
    private javax.swing.JButton btnSaveSetup;
    private javax.swing.JCheckBox cbAuto;
    private javax.swing.JCheckBox cbChan0;
    private javax.swing.JCheckBox cbChan1;
    private javax.swing.JCheckBox cbChan2;
    private javax.swing.JCheckBox cbChan3;
    private javax.swing.JCheckBox cbChan4;
    private javax.swing.JCheckBox cbChan5;
    private javax.swing.JCheckBox cbChan6;
    private javax.swing.JCheckBox cbChan7;
    private javax.swing.JCheckBox cbCycleApril;
    private javax.swing.JCheckBox cbCycleAug;
    private javax.swing.JCheckBox cbCycleDec;
    private javax.swing.JCheckBox cbCycleFeb;
    private javax.swing.JCheckBox cbCycleJan;
    private javax.swing.JCheckBox cbCycleJuly;
    private javax.swing.JCheckBox cbCycleJune;
    private javax.swing.JCheckBox cbCycleMarch;
    private javax.swing.JCheckBox cbCycleMay;
    private javax.swing.JCheckBox cbCycleNov;
    private javax.swing.JCheckBox cbCycleOct;
    private javax.swing.JCheckBox cbCycleSep;
    private javax.swing.JCheckBox cbDayCycle;
    private javax.swing.JCheckBox cbEven;
    private javax.swing.JCheckBox cbFriday;
    private javax.swing.JCheckBox cbMonInput0;
    private javax.swing.JCheckBox cbMonInput1;
    private javax.swing.JCheckBox cbMonInput2;
    private javax.swing.JCheckBox cbMonInput3;
    private javax.swing.JCheckBox cbMonday;
    private javax.swing.JCheckBox cbOdd;
    private javax.swing.JCheckBox cbOdd31;
    private javax.swing.JCheckBox cbPumpChannel;
    private javax.swing.JCheckBox cbRainSensorCancels;
    private javax.swing.JCheckBox cbRainSensorDelays;
    private javax.swing.JCheckBox cbSaturday;
    private javax.swing.JCheckBox cbStartInputA;
    private javax.swing.JCheckBox cbStartInputB;
    private javax.swing.JCheckBox cbStartInputC;
    private javax.swing.JCheckBox cbStopInputA;
    private javax.swing.JCheckBox cbStopInputB;
    private javax.swing.JCheckBox cbStopInputC;
    private javax.swing.JCheckBox cbSunday;
    private javax.swing.JCheckBox cbTempBudget;
    private javax.swing.JCheckBox cbThursday;
    private javax.swing.JCheckBox cbTuesday;
    private javax.swing.JCheckBox cbWednesday;
    private javax.swing.JComboBox cbbZoneChannel;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelCloud;
    private javax.swing.JPanel jPanelCycle;
    private javax.swing.JPanel jPanelDevices;
    private javax.swing.JPanel jPanelPrograms;
    private javax.swing.JPanel jPanelProgramsContainer;
    private javax.swing.JPanel jPanelSequences;
    private javax.swing.JPanel jPanelStartTime;
    private javax.swing.JPanel jPanelStatus;
    private javax.swing.JPanel jPanelWaterBudget;
    private javax.swing.JPanel jPanelZones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPaneMain;
    private javax.swing.JTabbedPane jTabbedPaneProgramConfig;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JToolBar jToolBarMain;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTime;
    private javax.swing.JList lvDevices;
    private javax.swing.JList lvPrograms;
    private javax.swing.JList lvSequences;
    private javax.swing.JList lvStartTimes;
    private javax.swing.JList lvZones;
    private javax.swing.JTextField tfBudgetApr;
    private javax.swing.JTextField tfBudgetAug;
    private javax.swing.JTextField tfBudgetDec;
    private javax.swing.JTextField tfBudgetFeb;
    private javax.swing.JTextField tfBudgetJan;
    private javax.swing.JTextField tfBudgetJul;
    private javax.swing.JTextField tfBudgetJun;
    private javax.swing.JTextField tfBudgetMar;
    private javax.swing.JTextField tfBudgetMay;
    private javax.swing.JTextField tfBudgetNov;
    private javax.swing.JTextField tfBudgetOct;
    private javax.swing.JTextField tfBudgetSep;
    private javax.swing.JTextField tfDayCycle;
    private javax.swing.JTextField tfDeviceDescription;
    private javax.swing.JTextField tfDeviceLocation;
    private javax.swing.JFormattedTextField tfIpAddress;
    private javax.swing.JTextField tfMonActProg;
    private javax.swing.JTextField tfMonActZone;
    private javax.swing.JTextField tfMonActZoneChan;
    private javax.swing.JTextField tfMonTimeLeft;
    private javax.swing.JTextField tfZoneName;
    private javax.swing.JTextField tfZoneOffDelay;
    // End of variables declaration//GEN-END:variables

    @Override
    public void eventStatus(String Message) {
        updateStatusBar(Message);
    }

    @Override
    public void eventProgramList(String[] Names) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eventProgramConfig(Sm100Program Program) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eventProgramsUpdate(List<Sm100Program> Programs) {
        DefaultListModel lm = new DefaultListModel();
        for(Sm100Program p : Programs) {
            lm.addElement(p);
        }
        lvPrograms.setModel(lm);
        lvPrograms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lvPrograms.setSelectedIndex(0);
    }

    @Override
    public void eventZonesUpdate(List<Sm100Zone> Zones) {
        DefaultListModel lm = new DefaultListModel();
        for(Sm100Zone z : Zones) {
            lm.addElement(z);
        }
        lvZones.setModel(lm);
        lvZones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lvZones.setSelectedIndex(0);
    }

    @Override
    public void eventInformation(String Name, String Family, String Revision, int Channels) {
        System.out.println("eventInformation GUI - " + Name + "Channel " + Integer.toString(Channels));


        lvZones.removeAll();
        lvPrograms.removeAll();
    }

    @Override
    public void eventDataTime(Date Time) {
       //Calendar cal = Calendar.getInstance();
       //cal.setTime(Time);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd MMM yyyy");

        lblTime.setText("Device Time: " + sdf.format(Time));
    }

    @Override
    public void eventDeviceListChanged(List<Sm100> list) {
        DefaultListModel lm = new DefaultListModel();
        for(Sm100 sm : list) {
            sm.removeEventListener(this);
            sm.registerEventListener(this);
            lm.addElement(sm);
        }
        lvDevices.setModel(lm);
    }

    @Override
    public void eventZoneStatusUpdate(List<Sm100Zone> Zones) {
        for(Sm100Zone z : Zones) {
            switch(z.getChannel()) {
            case 0:
                cbChan0.setSelected(z.getStatus());
                break;
            case 1:
                cbChan1.setSelected(z.getStatus());
                break;
            case 2:
                cbChan2.setSelected(z.getStatus());
                break;
            case 3:
                cbChan3.setSelected(z.getStatus());
                break;
             case 4:
                cbChan4.setSelected(z.getStatus());
                break;
            case 5:
                cbChan5.setSelected(z.getStatus());
                break;
             case 6:
                cbChan6.setSelected(z.getStatus());
                break;
            case 7:
                cbChan7.setSelected(z.getStatus());
                break;
            }


        }
    }

    @Override
    public void eventActiveProgram(boolean Active, Sm100Program Program, Sm100Zone Zone, String Status, int RunTime, int TimeToRun) {

        if(Active) {
            tfMonActProg.setText(Program.getName());
            tfMonActZone.setText(Zone.getName());
            tfMonActZoneChan.setText("#" + Integer.toString(Zone.getChannel()));
            int mins = TimeToRun / 60;
            int secs = TimeToRun % 60;
            tfMonTimeLeft.setText(String.format("%d:%02d", mins, secs));
        }
        else {
            tfMonActProg.setText("In Active");
            tfMonActZone.setText("In Active");
            tfMonActZoneChan.setText("In Active");
            tfMonTimeLeft.setText("In Active");
        }

    }

    @Override
    public void eventInputs(boolean[] Status) {
        cbMonInput0.setSelected(Status[0]);
        cbMonInput1.setSelected(Status[1]);
        cbMonInput2.setSelected(Status[2]);
        cbMonInput3.setSelected(Status[3]);
    }

    @Override
    public void eventUploadComplete() {
        JOptionPane.showMessageDialog(this, "Upload completed successfully.", "Upload", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void eventProgramStartTimesUpdate(Sm100Program Program, List<StartTime> StartTimesList) {
        int pi = lvPrograms.getSelectedIndex();

        System.out.println("eventProgramStartTimesUpdate");

        if(pi == -1) {
            return;
        }

        Sm100Program p = (Sm100Program)lvPrograms.getModel().getElementAt(pi);
        if(p == Program) {
            lvStartTimes.removeAll();
            DefaultListModel lmm = new DefaultListModel();
            for(StartTime st : StartTimesList) {
                lmm.addElement(st);
            }
            lvStartTimes.setModel(lmm);
            lvStartTimes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }

}
