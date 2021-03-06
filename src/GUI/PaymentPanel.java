
package GUI;

import BL.Bill;
import BL.BillItem;
import BL.Doctor;
import BL.Enums.PAYMENT;
import BL.Hospital;
import BL.Patient;
import GUI.DBErrorDialog;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PaymentPanel extends javax.swing.JPanel {

    private Hospital hospitalManager;
    private DefaultListModel listModelPatients;
    private DefaultListModel listModelPaidBills;
    private DefaultListModel listModelUnpaidBills;
    private Bill currentBill;
    public PaymentPanel() {
        initComponents();
    }
    public PaymentPanel(Hospital hospitalManager) {
        initComponents();
        this.hospitalManager = hospitalManager;
        listModelPatients =new DefaultListModel();
        listModelPaidBills = new DefaultListModel();
        listModelUnpaidBills = new DefaultListModel();
        paymentPanel.setVisible(true);
        selectBtn.setVisible(false);
        selectedPatientLbl.setVisible(false);
        payBtn.setVisible(false);
        paidBtn.setVisible(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paymentBtnGroup = new javax.swing.ButtonGroup();
        patientSearchTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        patientSearchList = new javax.swing.JList<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        unpaidBillsList = new javax.swing.JList<>();
        payBillBtn = new javax.swing.JButton();
        creditRadio = new javax.swing.JRadioButton();
        cashRadio = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        paidBillsList = new javax.swing.JList<>();
        paidBtn = new javax.swing.JButton();
        selectBtn = new javax.swing.JButton();
        searchBtn = new javax.swing.JButton();
        selectedPatientLbl = new javax.swing.JLabel();
        paymentPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        billItemsTable = new javax.swing.JTable();
        priceLbl = new javax.swing.JLabel();
        payHideShowPanel = new javax.swing.JPanel();
        payBtn = new javax.swing.JButton();

        patientSearchTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientSearchTxtActionPerformed(evt);
            }
        });

        patientSearchList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        patientSearchList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                patientSearchListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(patientSearchList);

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        unpaidBillsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        unpaidBillsList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                unpaidBillsListPropertyChange(evt);
            }
        });
        unpaidBillsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                unpaidBillsListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(unpaidBillsList);

        payBillBtn.setText("Pay");
        payBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payBillBtnActionPerformed(evt);
            }
        });

        paymentBtnGroup.add(creditRadio);
        creditRadio.setSelected(true);
        creditRadio.setText("Credit Card");

        paymentBtnGroup.add(cashRadio);
        cashRadio.setText("Cash");

        jLabel2.setText("Payment options");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cashRadio)
                    .addComponent(creditRadio)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(payBillBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cashRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(creditRadio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(payBillBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Unpaid Bills", jPanel2);

        paidBillsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        paidBillsList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                paidBillsListPropertyChange(evt);
            }
        });
        paidBillsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                paidBillsListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(paidBillsList);

        paidBtn.setText("Details");
        paidBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paidBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paidBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paidBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Paid Bills", jPanel1);

        selectBtn.setText("Select");
        selectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectBtnActionPerformed(evt);
            }
        });

        searchBtn.setText("Search");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        selectedPatientLbl.setText("Patient not selected");

        jLabel1.setText("Total Price");

        billItemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product", "Amount", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        billItemsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(billItemsTable);
        if (billItemsTable.getColumnModel().getColumnCount() > 0) {
            billItemsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        }

        javax.swing.GroupLayout payHideShowPanelLayout = new javax.swing.GroupLayout(payHideShowPanel);
        payHideShowPanel.setLayout(payHideShowPanelLayout);
        payHideShowPanelLayout.setHorizontalGroup(
            payHideShowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        payHideShowPanelLayout.setVerticalGroup(
            payHideShowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
        );

        payBtn.setText("Payment");
        payBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paymentPanelLayout = new javax.swing.GroupLayout(paymentPanel);
        paymentPanel.setLayout(paymentPanelLayout);
        paymentPanelLayout.setHorizontalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addComponent(priceLbl)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addComponent(payHideShowPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(payBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        paymentPanelLayout.setVerticalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(priceLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(payHideShowPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(payBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 139, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(selectBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(patientSearchTxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBtn))
                    .addComponent(selectedPatientLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(paymentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addGap(108, 108, 108))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(patientSearchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectedPatientLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(paymentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void patientSearchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientSearchTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_patientSearchTxtActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
       String search = patientSearchTxt.getText();
    
       try {
            if(search!=null){
                if(search.trim().length()!= 0){
                  
                 //CLEAR DATA
                 listModelPaidBills.clear();
                 listModelUnpaidBills.clear();
                 currentBill = null;
                    
                ArrayList<Patient> searchPatients = hospitalManager.searchPatientByName(search);
                listModelPatients.clear();
                
                for (Patient patient : searchPatients) {
                listModelPatients.addElement(patient);
                }
                patientSearchList.setModel(listModelPatients);
                patientSearchList.setSelectedIndex(0);
                } 
            }
            
        } catch (SQLException ex) {
            new DBErrorDialog();
        }
  
    }//GEN-LAST:event_searchBtnActionPerformed

    private void selectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectBtnActionPerformed
       
        if (!patientSearchList.isSelectionEmpty()) {
           try {
               loadBills();
           } catch (SQLException ex) {
               //Logger.getLogger(PaymentPanel.class.getName()).log(Level.SEVERE, null, ex);
               new DBErrorDialog();
           }
        }
       
       
    }//GEN-LAST:event_selectBtnActionPerformed

    private void payBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payBtnActionPerformed
       if (!unpaidBillsList.isSelectionEmpty()){
           
           currentBill = (Bill)listModelUnpaidBills.getElementAt(unpaidBillsList.getSelectedIndex());
         
           
           DefaultTableModel model = (DefaultTableModel)billItemsTable.getModel();
           model.setRowCount(0);
           for (BillItem item : currentBill.getItems()) {
               model.addRow(new Object[]{item.getItemName(), item.getAmount(), item.getPrice()});
           }
           priceLbl.setText(String.format("%s $", String.valueOf(currentBill.getTotalPrice())));
           billItemsTable.setModel(model);
       }
    }//GEN-LAST:event_payBtnActionPerformed

    private void payBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payBillBtnActionPerformed
        Bill payBill = currentBill;
        if (currentBill==null){
            return;
        }
        int reply = JOptionPane.showConfirmDialog(null, "Pay " + String.valueOf(payBill.getTotalPrice()),
                "Confirm Payment", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION){
            PAYMENT paymentType;
                if (cashRadio.isSelected()) {
                    paymentType = PAYMENT.CASH;
                }
                else{
                    paymentType = PAYMENT.CREDIT_CARD;
                }
            try {
                hospitalManager.payBill(payBill.getAppointmentID(), paymentType);
                loadBills();
                
            } catch (SQLException ex) {
                //Logger.getLogger(PaymentPanel.class.getName()).log(Level.SEVERE, null, ex);
                new DBErrorDialog();
            }
            }
        
        
    }//GEN-LAST:event_payBillBtnActionPerformed

    private void patientSearchListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_patientSearchListValueChanged
        clearTableAndBill();
        if (!patientSearchList.isSelectionEmpty()) {
            try {
                loadBills();
            } catch (SQLException ex) {
                //Logger.getLogger(PaymentPanel.class.getName()).log(Level.SEVERE, null, ex);
                new DBErrorDialog();
            }
        }
    }//GEN-LAST:event_patientSearchListValueChanged

    private void paidBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paidBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paidBtnActionPerformed

    private void paidBillsListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_paidBillsListPropertyChange

    }//GEN-LAST:event_paidBillsListPropertyChange

    private void unpaidBillsListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_unpaidBillsListPropertyChange

    }//GEN-LAST:event_unpaidBillsListPropertyChange

    private void unpaidBillsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_unpaidBillsListValueChanged
        if (!unpaidBillsList.isSelectionEmpty()){

            currentBill = (Bill)listModelUnpaidBills.getElementAt(unpaidBillsList.getSelectedIndex());

            DefaultTableModel model = (DefaultTableModel)billItemsTable.getModel();
            model.setRowCount(0);
            for (BillItem item : currentBill.getItems()) {
                model.addRow(new Object[]{item.getItemName(), item.getAmount(), item.getPrice()});
            }
            priceLbl.setText(String.format("%s $", String.valueOf(currentBill.getTotalPrice())));
            billItemsTable.setModel(model);
        }
    }//GEN-LAST:event_unpaidBillsListValueChanged

    private void paidBillsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_paidBillsListValueChanged
        if (!paidBillsList.isSelectionEmpty()){

            currentBill = (Bill)listModelPaidBills.getElementAt(paidBillsList.getSelectedIndex());

            DefaultTableModel model = (DefaultTableModel)billItemsTable.getModel();
            model.setRowCount(0);
            for (BillItem item : currentBill.getItems()) {
                model.addRow(new Object[]{item.getItemName(), item.getAmount(), item.getPrice()});
            }
            priceLbl.setText(String.format("%s $", String.valueOf(currentBill.getTotalPrice())));
            billItemsTable.setModel(model);
        }
    }//GEN-LAST:event_paidBillsListValueChanged

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        
        
        if (jTabbedPane1.getSelectedIndex() == 0) {
            clearTableAndBill();
            
            if (listModelUnpaidBills != null) {
                unpaidBillsList.setSelectedIndex(0);
              if (unpaidBillsList.getSelectedIndex() != -1) {  
                  currentBill = (Bill)listModelUnpaidBills.getElementAt(unpaidBillsList.getSelectedIndex());
               loadTable();
              }
            }
            
        }
        if (jTabbedPane1.getSelectedIndex() == 1) {
            clearTableAndBill();
             if (listModelPaidBills != null){
            paidBillsList.setSelectedIndex(0);
                 if (paidBillsList.getSelectedIndex() != -1) {
                     currentBill = (Bill)listModelPaidBills.getElementAt(paidBillsList.getSelectedIndex());
                     loadTable();
                 }
            
             }
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable billItemsTable;
    private javax.swing.JRadioButton cashRadio;
    private javax.swing.JRadioButton creditRadio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> paidBillsList;
    private javax.swing.JButton paidBtn;
    private javax.swing.JList<String> patientSearchList;
    private javax.swing.JTextField patientSearchTxt;
    private javax.swing.JButton payBillBtn;
    private javax.swing.JButton payBtn;
    private javax.swing.JPanel payHideShowPanel;
    private javax.swing.ButtonGroup paymentBtnGroup;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JLabel priceLbl;
    private javax.swing.JButton searchBtn;
    private javax.swing.JButton selectBtn;
    private javax.swing.JLabel selectedPatientLbl;
    private javax.swing.JList<String> unpaidBillsList;
    // End of variables declaration//GEN-END:variables

    private void loadBills() throws SQLException {
        ArrayList<Bill> paidBills = new ArrayList<Bill>();
               ArrayList<Bill> unpaidBills = new ArrayList<Bill>();
               Patient patient = (Patient)listModelPatients.getElementAt(patientSearchList.getSelectedIndex());
               selectedPatientLbl.setText("SELECTED: " + patient.toString());
               
               //UNPAID
               unpaidBills = hospitalManager.getBillsByPatientID(patient.getID(), false);
               
               listModelUnpaidBills.clear();
               for (Bill unpaidBill : unpaidBills) {
                   listModelUnpaidBills.addElement(unpaidBill);
                   
               }
               unpaidBillsList.setModel(listModelUnpaidBills);
              unpaidBillsList.setSelectedIndex(0);
               //PAID
               paidBills = hospitalManager.getBillsByPatientID(patient.getID(), true);
               listModelPaidBills.clear();
               for (Bill paidBill : paidBills) {
                   listModelPaidBills.addElement(paidBill);
               }
               paidBillsList.setModel(listModelPaidBills);
               //paidBillsList.setSelectedIndex(0);
               
    }

    private void loadTable() {
        
            DefaultTableModel model = (DefaultTableModel)billItemsTable.getModel();
            model.setRowCount(0);
            for (BillItem item : currentBill.getItems()) {
                model.addRow(new Object[]{item.getItemName(), item.getAmount(), item.getPrice()});
            }
            priceLbl.setText(String.format("%s $", String.valueOf(currentBill.getTotalPrice())));
            billItemsTable.setModel(model);
    }

    private void clearTableAndBill() {
        currentBill = null;
            DefaultTableModel model =(DefaultTableModel)billItemsTable.getModel();
            model.setRowCount(0);
            priceLbl.setText("");
    }
}
