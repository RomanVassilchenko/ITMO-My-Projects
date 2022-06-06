package GUI;

import Dependency.Command;
import Dependency.Worker;
import NetInteraction.ClientEvents;
import NetInteraction.ClientManager;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.text.DateFormat.getDateInstance;
import static java.util.Map.Entry.comparingByValue;
import static java.util.TimeZone.SHORT;
import static javax.swing.JOptionPane.showMessageDialog;

public class TableForm {
    private JTable table1;
    private ClientEvents clientEvents;
    private ClientManager clientManager;
    private JFrame parent;
    private DefaultTableModel tableModel;
    private JFrame tableFrame;
    private JPanel tablePanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton sortButton;
    private JComboBox comboBox1;
    private JButton backButton;
    private JScrollPane scrollPane;
    private JButton updateButton;
    private JTextArea textArea1;
    private JButton updateTableButton;
    private JComboBox fieldsBox;
    private String language;
    private int maxCount = 0;
    private DateFormat df;
    private DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.US);
    ResourceBundle bundleRu = ResourceBundle
            .getBundle("resources.resource", new Locale("ru", "RU"));
    ResourceBundle bundleDe = ResourceBundle
            .getBundle("resources.resource", new Locale("de", "DE"));
    ResourceBundle bundleEs = ResourceBundle
            .getBundle("resources.resource", new Locale("es", "MX"));
    ResourceBundle bundleLt = ResourceBundle
            .getBundle("resources.resource", new Locale("lt", "LT"));

    public TableForm(JFrame parent, ClientEvents clientEvents, Object languageIn) {
        this.parent = parent;
        this.clientEvents = clientEvents;
        language = languageIn.toString();
        clientManager = clientEvents.getClientManager();
        tableFrame = new JFrame();
        tableFrame.setSize(1900, 800);
        tableFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
        $$$setupUI$$$();
        setupSelectedLanguages(language);
        tableFrame.add(tablePanel);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Вставка новой строки после выделенной
                Integer lastKey = clientManager.getCollectionForTable().getWorkerList().lastKey();
                Command command = new Command("insert " + lastKey);
                command.setKey(lastKey + 1);
                Insert insert = new Insert(clientEvents, command);
                try {
                    clientEvents.commandMode(new Command("getCollectionForTable"));
                    if (clientManager.getCollectionForTable().getWorkerList().get(lastKey + 1) != null) {
                        Worker worker = clientManager.getCollectionForTable().getWorkerList().get(lastKey + 1);
                        tableModel.insertRow(maxCount + 1, new String[]{String.valueOf(worker.getId()), worker.getName()
                                , String.valueOf(worker.getCoordinates().getX()), String.valueOf(worker.getCoordinates().getY())
                                , String.valueOf(worker.getCreationDate())
                                , String.valueOf(worker.getSalary())
                                , String.valueOf(worker.getPosition()), String.valueOf(worker.getStatus())
                                , String.valueOf(worker.getPerson().getBirthday())
                                , String.valueOf(worker.getPerson().getPassportID())
                                , String.valueOf(worker.getPerson().getHeight()), worker.getAuthor()});
                    }
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Номер выделенной строки
                int idx = table1.getSelectedRow();
                if (idx != -1) {
                    Integer key = Integer.valueOf(table1.getModel().getValueAt(idx, 0).toString());
                    Command command = new Command("remove_key " + key);
                    try {
                        clientEvents.commandMode(command);
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        clientEvents.commandMode(new Command("getCollectionForTable"));
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                    if (clientManager.getCollectionForTable().getWorkerList().get(key) == null) {
                        tableModel.removeRow(idx);
                        maxCount--;
                    }
                }

            }
        });
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sort();
            }

        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableFrame.dispose();
                parent.setVisible(true);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = table1.getSelectedRow();
                if (idx != -1) {
                    Integer key = Integer.valueOf(table1.getModel().getValueAt(idx, 0).toString());
                    switch (fieldsBox.getSelectedItem().toString()) {
                        case "coordinates":
                            Command upd1 = new Command("update " + key + " coordinates");
                            upd1.setId(key);
                            upd1.setElement(fieldsBox.getSelectedItem().toString());
                            UpdateCoordinates updateCoordinates = new UpdateCoordinates(clientEvents
                                    , textArea1, upd1, language);
                            break;
                        case "person":
                            Command upd2 = new Command("update " + key + " person");
                            upd2.setId(key);
                            upd2.setElement(fieldsBox.getSelectedItem().toString());
                            UpdatePerson updatePerson = new UpdatePerson(clientEvents
                                    , textArea1, upd2, language);
                            break;
                        default:
                            Command upd3 = new Command("update "
                                    + key + " " + fieldsBox.getSelectedItem().toString());
                            upd3.setId(key);
                            upd3.setElement(fieldsBox.getSelectedItem().toString());
                            Update update = new Update(clientEvents, textArea1, upd3, language);
                            break;
                    }
                }
                try {
                    clientEvents.commandMode(new Command("getCollectionForTable"));
                    initializeTable();
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        updateTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientEvents.commandMode(new Command("getCollectionForTable"));
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
                initializeTable();
            }
        });
    }


    public void initializeTable() {
        int count = -1;
        Object[] columnsHeader = new String[]{"id", "name", "cord_x", "cord_y", "creationDate", "salary", "position"
                , "status", "birthday", "passport", "height", "author"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);
        for (Map.Entry<Integer, Worker> entry : clientManager.getCollectionForTable().getWorkerList().entrySet()) {
            Worker worker = entry.getValue();
            try {
//                showMessageDialog(null, worker.toString());
                tableModel.insertRow(count + 1, new String[]{String.valueOf(worker.getId()), worker.getName()
                        , String.valueOf(worker.getCoordinates().getX()), String.valueOf(worker.getCoordinates().getY())
                        , df.format(inputFormat.parse(String.valueOf(worker.getCreationDate())))
                        , String.valueOf(worker.getSalary())
                        , String.valueOf(worker.getPosition()), String.valueOf(worker.getStatus())
                        , df.format(inputFormat.parse(String.valueOf(worker.getPerson().getBirthday())))
                        , String.valueOf(worker.getPerson().getPassportID())
                        , String.valueOf(worker.getPerson().getHeight()), worker.getAuthor()});
            } catch (ParseException e) {
                e.printStackTrace();
            }
            count++;
        }
        if (count > maxCount) {
            maxCount = count;
        }
        table1.setModel(tableModel);
    }

    public void setupSelectedLanguages(String language) {
        switch (language) {
            case "Russian":
                backButton.setText(bundleRu.getString("back"));
                sortButton.setText(bundleRu.getString("sort"));
                addButton.setText(bundleRu.getString("add"));
                removeButton.setText(bundleRu.getString("remove"));
                updateButton.setText(bundleRu.getString("update"));
                updateTableButton.setText(bundleRu.getString("updateTable"));
                break;
            case "Lithuanian":
                backButton.setText(bundleLt.getString("back"));
                sortButton.setText(bundleLt.getString("sort"));
                addButton.setText(bundleLt.getString("add"));
                removeButton.setText(bundleLt.getString("remove"));
                updateButton.setText(bundleLt.getString("update"));
                updateTableButton.setText(bundleLt.getString("updateTable"));
                break;
            case "German":
                backButton.setText(bundleDe.getString("back"));
                sortButton.setText(bundleDe.getString("sort"));
                addButton.setText(bundleDe.getString("add"));
                removeButton.setText(bundleDe.getString("remove"));
                updateButton.setText(bundleDe.getString("update"));
                updateTableButton.setText(bundleDe.getString("updateTable"));
                break;
            case "Spanish (Mexico)":
                backButton.setText(bundleEs.getString("back"));
                sortButton.setText(bundleEs.getString("sort"));
                addButton.setText(bundleEs.getString("add"));
                removeButton.setText(bundleEs.getString("remove"));
                updateButton.setText(bundleEs.getString("update"));
                updateTableButton.setText(bundleEs.getString("updateTable"));
                break;
        }
    }

    public void sort() {
        int count = -1;
        Object[] columnsHeader = new String[]{"id", "name", "cord_x", "cord_y", "creationDate", "salary", "position"
                , "status", "birthday", "passport", "height", "author"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);
        TreeMap<Integer, Worker> map = clientManager.getCollectionForTable().getWorkerList();
        LinkedHashMap<Worker, Worker> sortedMap = null;
        switch (comboBox1.getSelectedItem().toString()) {
            case "salary":
                sortedMap = map.entrySet()
                        .stream()
                        .sorted((e1, e2) -> e2.getValue().getSalary().compareTo(e1.getValue().getSalary()))
                        .collect(Collectors.toMap(Map.Entry::getValue,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                break;
            case "cord_x":
                sortedMap = map.entrySet()
                        .stream()
                        .sorted((e1, e2) -> e2.getValue().getCoordinates().getX()
                                .compareTo(Math.toIntExact(Long.valueOf(e1.getValue().getCoordinates().getX()))))
                        .collect(Collectors.toMap(Map.Entry::getValue,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                break;
            case "cord_y":
                sortedMap = map.entrySet()
                        .stream()
                        .sorted((e1, e2) -> e2.getValue().getCoordinates()
                                .getY().compareTo(e1.getValue().getCoordinates().getY()))
                        .collect(Collectors.toMap(Map.Entry::getValue,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                break;
            case "id":
                sortedMap = map.entrySet()
                        .stream()
                        .sorted((e1, e2) -> e2.getValue().getId().compareTo(e1.getValue().getId()))
                        .collect(Collectors.toMap(Map.Entry::getValue,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                break;
            case "height":
                sortedMap = map.entrySet()
                        .stream()
                        .sorted((e1, e2) -> Integer.valueOf(Math.round(e2.getValue().getPerson().getHeight()))
                                .compareTo(Math.round(e1.getValue().getPerson()
                                        .getHeight())))
                        .collect(Collectors.toMap(Map.Entry::getValue,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                break;
            case "creationDate":
                sortedMap = map.entrySet()
                        .stream()
                        .sorted((e1, e2) -> e2.getValue().getCreationDate()
                                .compareTo(e1.getValue().getCreationDate()))
                        .collect(Collectors.toMap(Map.Entry::getValue,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                break;
            case "birthday":
                sortedMap = map.entrySet()
                        .stream()
                        .sorted((e1, e2) -> e2.getValue().getPerson().getBirthday()
                                .compareTo(e1.getValue().getPerson().getBirthday()))
                        .collect(Collectors.toMap(Map.Entry::getValue,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                break;
            case "name":
                sortedMap = map.entrySet()
                        .stream()
                        .sorted((e1, e2) -> e2.getValue().getName()
                                .compareTo(e1.getValue().getName()))
                        .collect(Collectors.toMap(Map.Entry::getValue,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                break;
        }

        for (Map.Entry<Worker, Worker> entry : sortedMap.entrySet()) {
            Worker worker = entry.getKey();
            tableModel.insertRow(count + 1, new String[]{String.valueOf(worker.getId()), worker.getName()
                    , String.valueOf(worker.getCoordinates().getX()), String.valueOf(worker.getCoordinates().getY())
                    , String.valueOf(worker.getCreationDate())
                    , String.valueOf(worker.getSalary())
                    , String.valueOf(worker.getPosition()), String.valueOf(worker.getStatus())
                    , String.valueOf(worker.getPerson().getBirthday())
                    , String.valueOf(worker.getPerson().getPassportID())
                    , String.valueOf(worker.getPerson().getHeight()), worker.getAuthor()});
            count++;
        }
        table1.setModel(tableModel);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout(0, 0));
        tablePanel.setAutoscrolls(true);
        tablePanel.setPreferredSize(new Dimension(1600, 1000));
        tablePanel.setRequestFocusEnabled(true);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        tablePanel.add(panel1, BorderLayout.NORTH);
        backButton = new JButton();
        backButton.setText("Назад");
        panel1.add(backButton, BorderLayout.WEST);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setAutoscrolls(true);
        panel2.setOpaque(true);
        panel2.setPreferredSize(new Dimension(1850, 900));
        tablePanel.add(panel2, BorderLayout.CENTER);
        scrollPane = new JScrollPane();
        scrollPane.setAutoscrolls(true);
        scrollPane.setPreferredSize(new Dimension(1850, 400));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(scrollPane, gbc);
        table1.setPreferredSize(new Dimension(1850, 400));
        scrollPane.setViewportView(table1);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel3, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel3.add(panel4, BorderLayout.EAST);
        updateTableButton = new JButton();
        updateTableButton.setText("Обновить таблицу");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(updateTableButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel4.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel4.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel4.add(spacer3, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel5.setPreferredSize(new Dimension(800, 40));
        panel3.add(panel5, BorderLayout.CENTER);
        sortButton = new JButton();
        sortButton.setText("Сортировать");
        panel5.add(sortButton);
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("id");
        defaultComboBoxModel1.addElement("salary");
        defaultComboBoxModel1.addElement("cord_x");
        defaultComboBoxModel1.addElement("cord_y");
        defaultComboBoxModel1.addElement("height");
        defaultComboBoxModel1.addElement("name");
        defaultComboBoxModel1.addElement("birthday");
        defaultComboBoxModel1.addElement("creationDate");
        comboBox1.setModel(defaultComboBoxModel1);
        panel5.add(comboBox1);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridBagLayout());
        panel5.add(panel6);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel6.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel6.add(spacer5, gbc);
        updateButton = new JButton();
        updateButton.setText("Обновить");
        panel5.add(updateButton);
        fieldsBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("name");
        defaultComboBoxModel2.addElement("coordinates");
        defaultComboBoxModel2.addElement("salary");
        defaultComboBoxModel2.addElement("position");
        defaultComboBoxModel2.addElement("status");
        defaultComboBoxModel2.addElement("person");
        fieldsBox.setModel(defaultComboBoxModel2);
        panel5.add(fieldsBox);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridBagLayout());
        panel5.add(panel7);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(spacer7, gbc);
        addButton = new JButton();
        addButton.setText("Добавить");
        panel5.add(addButton);
        removeButton = new JButton();
        removeButton.setText("Удалить");
        panel5.add(removeButton);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridBagLayout());
        panel8.setPreferredSize(new Dimension(1000, 120));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel8, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(800, 120));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel8.add(scrollPane1, gbc);
        textArea1 = new JTextArea();
        textArea1.setPreferredSize(new Dimension(800, 200));
        scrollPane1.setViewportView(textArea1);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return tablePanel;
    }

    private void createUIComponents() {
        switch (language) {
            case "Russian":
                df = (DateFormat) bundleRu.getObject("date");
                break;
            case "Lithuanian":
                df = (DateFormat) bundleLt.getObject("date");
                break;
            case "German":
                df = (DateFormat) bundleDe.getObject("date");
                break;
            case "Spanish (Mexico)":
                df = (DateFormat) bundleEs.getObject("date");
                break;
        }
        int count = -1;
        Object[] columnsHeader = new String[]{"id", "name", "cord_x", "cord_y", "creationDate", "salary", "position"
                , "status", "birthday", "passport", "height", "author"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);
        for (Map.Entry<Integer, Worker> entry : clientManager.getCollectionForTable().getWorkerList().entrySet()) {
            Worker worker = entry.getValue();
            try {
                tableModel.insertRow(count + 1, new String[]{String.valueOf(worker.getId()), worker.getName()
                        , String.valueOf(worker.getCoordinates().getX()), String.valueOf(worker.getCoordinates().getY())
                        , df.format(inputFormat.parse(String.valueOf(worker.getCreationDate())))
                        , String.valueOf(worker.getSalary())
                        , String.valueOf(worker.getPosition()), String.valueOf(worker.getStatus())
                        , df.format(inputFormat.parse(String.valueOf(worker.getPerson().getBirthday())))
                        , String.valueOf(worker.getPerson().getPassportID())
                        , String.valueOf(worker.getPerson().getHeight()), worker.getAuthor()});
            } catch (ParseException e) {
                e.printStackTrace();
            }
            count++;
        }
        if (count > maxCount) {
            maxCount = count;
        }
        table1 = new JTable(tableModel);
        // TODO: place custom component creation code here
    }
}
