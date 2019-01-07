package com.ddcompany.innercore.forms;

import com.ddcompany.innercore.ICProjectService;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PanelAdbDeviceSelect {
    private JList<JadbDevice> listDevices;
    private JPanel panel;
    private JTextField textFieldModDir;
    private JCheckBox checkBoxRunInner;
    private JCheckBox checkBoxSmartPush;

    public PanelAdbDeviceSelect(Project project) {
        listDevices.setCellRenderer(new AdbDevicesCellRenderer());
        listDevices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ICProjectService innerProjectService = ICProjectService.get(project);
        textFieldModDir.setText(innerProjectService.getModDirectory());
        checkBoxRunInner.setSelected(innerProjectService.isRunInnerCore());
        checkBoxSmartPush.setSelected(innerProjectService.isSmartPush());

        try {
            JadbConnection jadbConnection = new JadbConnection();
            listDevices.setListData(jadbConnection.getDevices().toArray(new JadbDevice[jadbConnection.getDevices().size()]));
        } catch (IOException | JadbException e) {
            e.printStackTrace();
        }

        String device = innerProjectService.getDevice();

        if(!device.isEmpty()){
            for(int i = 0; i < listDevices.getModel().getSize(); i++){
                if(listDevices.getModel().getElementAt(i).getSerial().equals(device))
                    listDevices.setSelectedIndex(i);
            }
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public JList<JadbDevice> getList() {
        return listDevices;
    }

    public String getDirName() {
        return textFieldModDir.getText();
    }

    public boolean isStartInnerCore(){
        return checkBoxRunInner.isSelected();
    }

    public boolean isSmartPush(){
        return checkBoxSmartPush.isSelected();
    }

    private class AdbDevicesCellRenderer extends JLabel implements ListCellRenderer<JadbDevice> {
        @Override
        public Component getListCellRendererComponent(JList<? extends JadbDevice> list, JadbDevice value, int index, boolean isSelected, boolean cellHasFocus) {
            this.setText(value.getSerial());

            if(isSelected) {
                this.setBackground(JBColor.BLUE);
                this.setForeground(JBColor.RED);
            }

            return this;
        }
    }
}
