package gui;

import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class SymbolsPanel extends JPanel {
    MainWindow mainWindow;
    JTable table;
    JPanel tPanel = new JPanel();
    ArrayList<String[]> list;

    public SymbolsPanel(MainWindow m) {
        mainWindow = m;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("All available crypto symbols from around the world");
        this.add(title);

        JPanel pn = new JPanel();
        pn.add(new JLabel("Find Symbol"));

        JTextField jb = new JTextField(10);
        pn.add(jb);

        JButton btn = new JButton("Find");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findSymbole(jb.getText().toUpperCase());
            }
        });
        pn.add(btn);
        this.add(pn);
        createTable();
    }

    private void createTable() {
        tPanel.removeAll();
        String[] columnNames = {"Symbole", "Currency Name"};

        String jsonString = null;
        try {
            String path = new File("cryptocurrencies.json").getAbsolutePath();
            jsonString = Files.readString(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject(jsonString);
        Iterator<String> keys = obj.keys();
        list = new ArrayList<>();
        while (keys.hasNext()) {
            String key = keys.next();
            String[] a = {key, (String) obj.get(key)};
            list.add(a);
        }

        String[][] data = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            data[i] = list.get(i);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        table = new JTable(model);

        tPanel.add(table);
        tPanel.add(new JScrollPane(table));
        this.add(tPanel);
        this.repaint();
        mainWindow.repaint();
    }

    private void findSymbole(String curr) {
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        int row = 1;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i)[0].equals(curr)) {
                row = i;
            }

        JViewport viewport = (JViewport) table.getParent();

        Rectangle rect = table.getCellRect(row, 0, true);

        Point pt = viewport.getViewPosition();

        rect.setLocation(rect.x - pt.x, rect.y - pt.y);

        table.scrollRectToVisible(rect);
    }

}
