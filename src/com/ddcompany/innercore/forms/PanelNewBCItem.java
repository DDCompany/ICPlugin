package com.ddcompany.innercore.forms;

import com.ddcompany.innercore.project.structure.buildConfig.BCType;

import javax.swing.*;

public class PanelNewBCItem {
    private JComboBox<BCType> comboBoxType;
    private JTextField fieldDir;
    private JTextField fieldTargetFile;
    private JLabel labelTargetSrc;
    private JPanel area;

    public PanelNewBCItem() {
        for (BCType api : BCType.values())
            this.comboBoxType.addItem(api);

        comboBoxType.addActionListener(e -> {
            if (comboBoxType.getSelectedItem() != BCType.SRC) {
                fieldTargetFile.setVisible(false);
                labelTargetSrc.setVisible(false);
            } else {
                fieldTargetFile.setVisible(true);
                labelTargetSrc.setVisible(true);
            }
        });
    }

    public BCType getType() {
        return (BCType) this.comboBoxType.getSelectedItem();
    }

    public String getDir() {
        return this.fieldDir.getText();
    }

    public String getTargetFile() {
        return this.fieldTargetFile.getText();
    }

    public JPanel getArea() {
        return this.area;
    }
}
