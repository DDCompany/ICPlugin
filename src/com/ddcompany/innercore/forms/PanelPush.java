package com.ddcompany.innercore.forms;

import com.ddcompany.innercore.ICService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PanelPush {
    private JList<JadbDevice> list;
    private JPanel area;
    private JTextField fieldDir;
    private JCheckBox checkRunIC;
    private JTextField fieldPath;
    private JCheckBox checkPushToRoot;

    PanelPush(Project project, VirtualFile file) throws IOException, JadbException {
        this.list.setCellRenderer(new AdbDevicesCellRenderer());
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (project.getBasePath() != null) {
            String cutStr = file.getPath().replaceFirst(project.getBasePath(), "");
            this.fieldPath.setText(cutStr.isEmpty() ? "/" : cutStr);
        }

        this.loadDevices();
        this.setPreviousData(project);
    }

    private void setPreviousData(Project project) {
        ICService service = ICService.Companion.get(project);
        this.fieldDir.setText(service.getDir());
        this.checkRunIC.setSelected(service.getMustRunIC());
        this.checkPushToRoot.setSelected(service.getMustPushToRoot());

        String serial = service.getSerial();
        if (this.list.getModel().getSize() == 1) {
            this.list.setSelectedIndex(0);
        } else if (!serial.isEmpty()) {
            ListModel<JadbDevice> model = this.list.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                if (model.getElementAt(i).getSerial().equals(serial)) {
                    this.list.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void loadDevices() throws IOException, JadbException {
        JadbConnection jadbConnection = new JadbConnection();
        this.list.setListData(jadbConnection.getDevices().toArray(new JadbDevice[0]));
    }

    JPanel getArea() {
        return this.area;
    }

    JList<JadbDevice> getList() {
        return this.list;
    }

    String getDir() {
        return this.fieldDir.getText();
    }

    boolean mustRunIC() {
        return this.checkRunIC.isSelected();
    }

    boolean mustPushToRoot() {
        return this.checkPushToRoot.isSelected();
    }

    private class AdbDevicesCellRenderer extends JLabel implements ListCellRenderer<JadbDevice> {
        @Override
        public Component getListCellRendererComponent(JList<? extends JadbDevice> list, JadbDevice value, int index, boolean isSelected, boolean cellHasFocus) {
            this.setText(value.getSerial());

            if (isSelected) {
                this.setBackground(JBColor.BLUE);
                this.setForeground(JBColor.RED);
            }

            return this;
        }
    }
}
