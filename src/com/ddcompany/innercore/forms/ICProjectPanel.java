package com.ddcompany.innercore.forms;

import com.ddcompany.innercore.project.structure.ICApi;
import com.ddcompany.innercore.project.structure.buildConfig.BCItem;
import com.ddcompany.innercore.project.structure.buildConfig.BCItemBuildDir;
import com.ddcompany.innercore.project.structure.buildConfig.BCItemRes;
import com.ddcompany.innercore.project.structure.buildConfig.BCType;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.CommonDataKeys;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ICProjectPanel {
    private JTextField fieldName;
    private JPanel area;
    private JTextArea areaDescription;
    private JTextField fieldVersion;
    private JTextField fieldAuthor;
    private JList<BCItem> listItems;
    private JButton btnNew;
    private JButton btnDel;
    private JTextField fieldLibsDir;
    private JComboBox<ICApi> comboBoxAPI;

    private DefaultListModel<BCItem> listModel = new DefaultListModel<>();

    public ICProjectPanel() {
        listModel.addElement(new BCItemBuildDir("dev/", "main.js"));
        listModel.addElement(new BCItemRes(BCType.TEXTURES, "res/"));
        listModel.addElement(new BCItemRes(BCType.GUI, "gui/"));
        listItems.setModel(listModel);

        for (ICApi value : ICApi.values())
            comboBoxAPI.addItem(value);

        btnNew.addActionListener(e -> new DialogNewBCItem(
                DataManager.getInstance().getDataContextFromFocus().getResult().getData(CommonDataKeys.PROJECT), listModel).show());

        btnDel.addActionListener(e -> {
            BCItem selectedValue = listItems.getSelectedValue();

            if (selectedValue != null)
                listModel.removeElement(selectedValue);
        });
    }

    public List<BCItem> getBcItems() {
        List<BCItem> list = new ArrayList<>();
        for (int i = 0; i < this.listModel.size(); i++)
            list.add(this.listModel.get(i));

        return list;
    }

    public JPanel getArea() {
        return this.area;
    }

    public String getName() {
        return this.fieldName.getText();
    }

    public String getDesc() {
        return this.areaDescription.getText();
    }

    public String getVersion() {
        return this.fieldVersion.getText();
    }

    public String getAuthor() {
        return this.fieldAuthor.getText();
    }

    public String getLibsDir() {
        return this.fieldLibsDir.getText();
    }

    public ICApi getApi() {
        return (ICApi) this.comboBoxAPI.getSelectedItem();
    }
}
