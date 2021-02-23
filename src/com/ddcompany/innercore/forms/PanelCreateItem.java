package com.ddcompany.innercore.forms;

import com.ddcompany.innercore.mod.ICItemType;

import javax.swing.*;

public class PanelCreateItem {
    private JPanel area;
    private JTextField fieldID;
    private JTextField fieldName;
    private JSlider sliderStackSize;
    private JLabel labelStackSize;
    private JTextField fieldBurnTime;
    private JCheckBox checkRenderAsTool;
    private JCheckBox checkGlint;
    private JCheckBox checkClickableOnLiquid;
    private JComboBox comboTypes;
    private JTextField fieldSaturation;
    private JLabel labelSaturation;

    public PanelCreateItem() {
        sliderStackSize.addChangeListener(e -> labelStackSize.setText(String.valueOf(sliderStackSize.getValue())));
        comboTypes.addActionListener(e -> {
            ICItemType item = (ICItemType) comboTypes.getSelectedItem();

            if (item != null) {
                switch (item) {
                    case SIMPLE:
                        labelSaturation.setVisible(false);
                        fieldSaturation.setVisible(false);
                        break;
                    case FOOD:
                        labelSaturation.setVisible(true);
                        fieldSaturation.setVisible(true);
                }
            }
        });
    }

    public JPanel getArea() {
        return area;
    }

    public String getName() {
        return fieldName.getText();
    }

    public String getID() {
        return fieldID.getText();
    }

    public int getStackSize() {
        return sliderStackSize.getValue();
    }

    public String getBurnTime() {
        return fieldBurnTime.getText();
    }

    public String getSaturation() {
        return fieldSaturation.getText();
    }

    public boolean isRenderAsTool() {
        return checkRenderAsTool.isSelected();
    }

    public boolean isClickableOnLiquid() {
        return checkClickableOnLiquid.isSelected();
    }

    public boolean isGlint() {
        return checkGlint.isSelected();
    }

    public JComboBox getComboTypes() {
        return comboTypes;
    }

    public JTextField getFieldID() {
        return fieldID;
    }
}
