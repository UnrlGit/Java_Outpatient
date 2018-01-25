package GUI;

import BL.Doctor;
import BL.Hospital;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;


public class DoctorManagementJPanel extends javax.swing.JPanel{

    private Hospital hospitalManager;
    DefaultListModel listModel;
    public DoctorManagementJPanel() {
        
        
    }

    DoctorManagementJPanel(Hospital hospitalManager) {
        try {
            initComponents();
            this.hospitalManager = hospitalManager;
            listModel =new DefaultListModel();
            fillDoctorList(hospitalManager.getAllDoctors());
        } catch (SQLException ex) {
            new DBErrorDialog();
        }
    }
    private void fillDoctorList(ArrayList<Doctor> allDoctors) {
        listModel.clear();
        for (Doctor doctor : allDoctors) {
            listModel.addElement(doctor);
        }
        doctorJList.setModel(listModel);
        doctorJList.setSelectedIndex(0);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        doctorJList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        editInfoLbl = new javax.swing.JLabel();

        doctorJList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        doctorJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        doctorJList.setSelectedIndex(0);
        jScrollPane1.setViewportView(doctorJList);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("DOCTOR MANAGEMENT");

        addBtn.setText("Add new doctor");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        editBtn.setText("Edit selected doctor");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        deleteBtn.setText("Delete selected doctor");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(editInfoLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                            .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jLabel1)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(editInfoLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(275, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        try {
            String fullName = JOptionPane.showInputDialog("Enter full name for new doctor");
            if(fullName!=null){
                if(fullName.trim().length()!= 0){
                Doctor addDoctor = new Doctor(0, fullName);
                addDoctor.setID(0);
                hospitalManager.addDoctor(addDoctor);
                fillDoctorList(hospitalManager.getAllDoctors());
                }
                else if(fullName.trim().length()== 0){
                JOptionPane.showMessageDialog(null, "Name cannot be empty");
                }
            }
            
        } catch (SQLException ex) {
            new DBErrorDialog();
        }
        
    }//GEN-LAST:event_addBtnActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        try {
            Doctor editDoctor = (Doctor) listModel.getElementAt(doctorJList.getSelectedIndex());
            String fullName = JOptionPane.showInputDialog("Enter new full name for doctor: " + editDoctor);
            if(fullName!=null){
                if(fullName.trim().length()!= 0){
                editDoctor.setFullName(fullName);
                hospitalManager.updateDoctor(editDoctor);
                fillDoctorList(hospitalManager.getAllDoctors());
                }
                else if(fullName.trim().length()== 0){
                JOptionPane.showMessageDialog(null, "Name cannot be empty");
                }
            }
            
        } catch (SQLException ex) {
            //Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
            new DBErrorDialog();
        }
        
        
        
    }//GEN-LAST:event_editBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        Doctor deleteDoctor = (Doctor) listModel.getElementAt(doctorJList.getSelectedIndex());
        int reply = JOptionPane.showConfirmDialog(null, "Delete " + deleteDoctor, "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                hospitalManager.deleteDoctor(deleteDoctor.getID());
                fillDoctorList(hospitalManager.getAllDoctors());
            } catch (SQLException ex) {
               // Logger.getLogger(Reception.class.getName()).log(Level.SEVERE, null, ex);
                new DBErrorDialog("Deleting doctor with patients is disabled");
            }
        }
        
    }//GEN-LAST:event_deleteBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JList<String> doctorJList;
    private javax.swing.JButton editBtn;
    private javax.swing.JLabel editInfoLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    

    
}
