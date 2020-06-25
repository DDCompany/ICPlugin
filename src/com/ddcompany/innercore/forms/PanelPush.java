package com.ddcompany.innercore.forms;

import com.ddcompany.innercore.ICService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PanelPush {
    private JList<JadbDevice> listDevices;
    private JPanel area;
    private JTextField fieldDir;
    private JCheckBox checkRunIC;
    private JTextField fieldPath;
    private JCheckBox checkPushToRoot;
    private JList<String> listBlackList;
    private JButton btnAddBlack;
    private JButton btnRemBlack;

    private DefaultListModel<String> modelBlackList = new DefaultListModel<>();

    PanelPush(Project project, VirtualFile file) throws IOException, JadbException {
        this.listDevices.setCellRenderer(new DevicesCellRenderer());
        this.listDevices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listBlackList.setModel(this.modelBlackList);
        this.listBlackList.setCellRenderer(new BlackListCellRenderer(project));
        this.listBlackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if (project.getBasePath() != null) {
            String cutStr = file.getPath().replaceFirst(project.getBasePath(), "");
            this.fieldPath.setText(cutStr.isEmpty() ? "/" : cutStr);
        }

        this.btnAddBlack.addActionListener(e -> {
            String path =
                    Messages.showInputDialog("Enter Path: ", "Input Dialog", Messages.getQuestionIcon());

            if (path != null && !path.isEmpty()) {
                path = (project.getBasePath() + File.separator + path).replace("\\", "/");
                if (!this.modelBlackList.contains(path)) {
                    this.modelBlackList.addElement(path);
                }
            }
        });

        this.btnRemBlack.addActionListener(e -> {
            int index = this.listBlackList.getSelectedIndex();
            if (index > -1)
                this.modelBlackList.remove(index);
        });

        this.loadDevices();
        this.setPreviousData(project);
    }

    private void setPreviousData(Project project) {
        ICService service = ICService.Companion.get(project);
        this.fieldDir.setText(service.getDir());
        this.checkRunIC.setSelected(service.getMustRunApp());
        this.checkPushToRoot.setSelected(service.getMustPushToRoot());
        service.getPushBlackList().forEach(s -> {
            this.modelBlackList.addElement(s);
        });

        String serial = service.getSerial();
        if (this.listDevices.getModel().getSize() == 1) {
            this.listDevices.setSelectedIndex(0);
        } else if (!serial.isEmpty()) {
            ListModel<JadbDevice> model = this.listDevices.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                if (model.getElementAt(i).getSerial().equals(serial)) {
                    this.listDevices.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void loadDevices() throws IOException, JadbException {
        JadbConnection jadbConnection = new JadbConnection();
        this.listDevices.setListData(jadbConnection.getDevices().toArray(new JadbDevice[0]));
    }

    JPanel getArea() {
        return this.area;
    }

    JList<JadbDevice> getListDevices() {
        return this.listDevices;
    }

    ArrayList<String> getBlackList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < this.modelBlackList.getSize(); i++)
            list.add(this.modelBlackList.getElementAt(i));

        return list;
    }

    String getDir() {
        return this.fieldDir.getText();
    }

    boolean mustRunApp() {
        return this.checkRunIC.isSelected();
    }

    boolean mustPushToRoot() {
        return this.checkPushToRoot.isSelected();
    }

    private class DevicesCellRenderer extends JLabel implements ListCellRenderer<JadbDevice> {
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

    private class BlackListCellRenderer extends JLabel implements ListCellRenderer<String> {
        private final Project project;

        BlackListCellRenderer(Project project) {
            this.project = project;
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            this.setText(value.replaceFirst(Objects.requireNonNull(project.getBasePath()), ""));

            if (isSelected) {
                this.setBackground(JBColor.BLUE);
                this.setForeground(JBColor.RED);
            }

            return this;
        }
    }
}
