package shop_server;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class shop_main {

    private void check_name(JTextField bName) throws nameException, NullException {
        String sName = bName.getText();
        if (sName.contains("Client Name") ||
                sName.contains("Eqipment Name") ||
                sName.contains("Phone Number") ||
                sName.contains("Date (Begin and End)") ||
                sName.contains("Price (Rub)")) throw new nameException();
        if (sName.length() == 0) throw new NullException();
    }

    private void check_customer(JTextField bName) throws NullPointerException {
        String s = bName.getText();
        if (s.length() == 0) throw new NullPointerException();
    }


    private static class MyException extends Exception {
        public MyException() {
            super("You have noo entered the equipment!");
        }
    }

    private void checkName(JTextField bName) throws MyException, NullPointerException {
        String sName = bName.getText();
        if (sName.equals("Eqipment Name")) throw new MyException();
        if (sName.length() == 0) throw new MyException();
    }

    private void check_remove(boolean flag) throws deleteException {
        if (!flag) throw new deleteException();
    }


    public void show() {


        try {
            // Создание парсера документа
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создание пустого документа
            Document doc = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


        // Создание окна
        // Объявления графических компонентов
        JFrame bookList = new JFrame("Eqipment List");
        bookList.setSize(900, 300);
        bookList.setLocation(100, 100);
        bookList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание кнопок и прикрепление иконок
        JButton open_txt = new JButton("Open TXT");
        JButton save_xml = new JButton("Save XML");
        JButton save_csv = new JButton("Save CSV");
        JButton save_txt = new JButton("Save TXT");
        JButton pdf = new JButton("Save PDF");
        JButton add = new JButton("Add Product");
        JButton delete = new JButton("Delete Product");
        JButton delay = new JButton("Contract delay");
        JButton periodSales = new JButton("Period Sales");
        JButton sort = new JButton("Make Sort");
        JButton finance = new JButton("Fin. Report");
        JButton filter = new JButton("Filter For Status");
        //JButton update = new JButton("Update");


        //JButton search = new JButton("Search in Name");

        // Настройка подсказок для кнопок
        save_csv.setToolTipText("Save as CSV");
        save_xml.setToolTipText("Save My List");
        pdf.setToolTipText("Create A PDF");
        add.setToolTipText("Edit List");
        open_txt.setToolTipText("Open Table in TXT");
        delete.setToolTipText("Delete Any Product");
        delay.setToolTipText("Contract Delay");
        periodSales.setToolTipText("Sales for the period");
        sort.setToolTipText("Make a Sort");
        finance.setToolTipText("Make a Finance Report");
        filter.setToolTipText("Filter For Status");
        save_txt.setToolTipText("Save TXT");


        // Добавление кнопок на панель инструментов
        JToolBar toolBar = new JToolBar("Панель инструментов");
        toolBar.add(open_txt);
        toolBar.add(save_txt);
        toolBar.add(save_csv);
        toolBar.add(save_xml);
        toolBar.add(pdf);
        toolBar.add(add);
        toolBar.add(delete);
        toolBar.add(delay);
        toolBar.add(periodSales);
        toolBar.add(sort);
        toolBar.add(finance);
        toolBar.add(filter);



        // Размещение панели инструментов
        bookList.setLayout(new BorderLayout());
        bookList.add(toolBar, BorderLayout.NORTH);

        // Создание таблицы с данными
        String[] columns = {"Client Name", "Equipment", "Count", "Price (for one point)", "Status", "Email / Phone Number", "Date of Begin and End"};

        String[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable main_equipment = new JTable(model);
        JScrollPane scroll = new JScrollPane(main_equipment);

        // Размещение таблицы с данными
        bookList.add(scroll, BorderLayout.CENTER);

        // Подготовка компонентов поиска
        String[] name_set = {"Peskov", "Mendeleev", "Atarov", "Vesnin", "Knyazev", "Lekin", "Maltsev", "Koltsev"};

        JComboBox<String> names = new JComboBox<>(name_set);
        //JTextField bookName = new JTextField("Eqipment Name");
        JButton search = new JButton("Search");
        // Добавление компонентов на панель
        JPanel filterPanel = new JPanel();
        filterPanel.add(names);
        //filterPanel.add(bookName);
        filterPanel.add(search);

        // Размещение  панели поиска внизу окна
        bookList.add(filterPanel, BorderLayout.SOUTH);

        // Визуализация экранной формы
        bookList.setVisible(true);

        //filter.addActionListener (new ActionListener()
        //{
        //public void actionPerformed (ActionEvent event)
        //{
        //JOptionPane.showMessageDialog (bookList, "Check Search");
        //}
        //});


        save_csv.addActionListener(event -> {

            FileDialog save12 = new FileDialog(bookList, "Сохранение данных CSV", FileDialog.SAVE);
            save12.setFile("*.csv");
            save12.setVisible(true);
            String fileName = save12.getDirectory() + save12.getFile();
            String[] newName = save12.getFile().split("\\.");

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                for (int i = -1; i < model.getRowCount(); i++) {
                    for (int j = 0; j < 7; j++) {
                        if (i == -1) {
                            writer.write(" ");
                        } else {
                            System.out.println(model.getValueAt(i, j) + "; ");

                            writer.write(model.getValueAt(i, j) + "; ");
                        }
                    }
                    writer.write("\n");
                }

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        save_xml.addActionListener(event -> {
            FileDialog save12 = new FileDialog(bookList, "Сохранение данных", FileDialog.SAVE);
            save12.setFile("*.xml");
            save12.setVisible(true);
            String fileName = save12.getDirectory() + save12.getFile();
            String[] newName = save12.getFile().split("\\.");

            try {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = builder.newDocument();
                Node booklist = doc.createElement("booklist");
                doc.appendChild(booklist);
                for (int i = 0; i < model.getRowCount(); i++) {
                    Element book = doc.createElement("book");
                    booklist.appendChild(book);
                    book.setAttribute("one", (String) model.getValueAt(i, 0));
                    book.setAttribute("two", (String) model.getValueAt(i, 1));
                    book.setAttribute("three", (String) model.getValueAt(i, 2));
                    book.setAttribute("four", (String) model.getValueAt(i, 3));
                    book.setAttribute("five", (String) model.getValueAt(i, 4));
                    book.setAttribute("six", (String) model.getValueAt(i, 5));
                    book.setAttribute("seven", (String) model.getValueAt(i, 6));
                }

                Transformer trans = TransformerFactory.newInstance().newTransformer();
                FileWriter fw = new FileWriter(fileName);
                trans.transform(new DOMSource(doc), new StreamResult(fw));
            } catch (IOException | TransformerException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        });

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addProduct = new JFrame("Add New Product");
                addProduct.setSize(1000, 80);
                addProduct.setLocation(200, 200);


                JButton addProductBut = new JButton("Commit");

                JTextField Client_Name = new JTextField("Client Name");
                JTextField Product_Name = new JTextField("Eqipment Name");

                JSpinner in_stock = new JSpinner();
                in_stock.setBounds(0, 100, 50, 40);

                JComboBox<String> status = new JComboBox<>(new String[]{"yes", "no"});

                JTextField phone = new JTextField("Phone Number");
                JTextField date = new JTextField("Date (Begin and End)");

                JTextField price = new JTextField("Price (Rub)");

                JPanel addPanel = new JPanel();
                addPanel.add(Client_Name);
                addPanel.add(Product_Name);
                addPanel.add(in_stock);
                addPanel.add(price);
                addPanel.add(status);
                addPanel.add(phone);
                addPanel.add(date);
                addPanel.add(addProductBut).setBackground(Color.BLACK);

                addProduct.add(addPanel, BorderLayout.CENTER);

                addProductBut.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            check_name(Product_Name);
                            check_name(Client_Name);
                            check_name(phone);
                            check_name(date);
                            check_name(price);
                            check_customer(Client_Name);
                            //check_stock(in_stock);
                            //check_price(price);
                            model.addRow(new String[]{
                                    Client_Name.getText(),
                                    Product_Name.getText(),
                                    in_stock.getValue().toString(),
                                    price.getText(),
                                    Objects.requireNonNull(status.getSelectedItem()).toString(),
                                    phone.getText(),
                                    date.getText()});
                        } catch (NullPointerException null_ex) {
                            JOptionPane.showMessageDialog(bookList, null_ex.toString());
                        } catch (nameException | NullException name_exc) {
                            JOptionPane.showMessageDialog(null, name_exc.getMessage());
                        }

                    }
                });

                addProduct.setVisible(true);
            }
        });

        pdf.addActionListener(event -> {
            FileDialog save1 = new FileDialog(bookList, "Сохранение данных", FileDialog.SAVE);
            save1.setFile("*.pdf");
            save1.setVisible(true);
            String fileName = save1.getDirectory() + save1.getFile();
            String[] newName = save1.getFile().split("\\.");

            com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4, 5, 5, 5, 5);
            PdfPTable table = new PdfPTable(7);
            try {

                PdfWriter.getInstance(document, new FileOutputStream(fileName));
                table.addCell(new PdfPCell(new Phrase("Client Name"))).setBackgroundColor(BaseColor.GREEN);
                table.addCell(new PdfPCell(new Phrase("Equipment"))).setBackgroundColor(BaseColor.GREEN);
                table.addCell(new PdfPCell(new Phrase("Count"))).setBackgroundColor(BaseColor.GREEN);
                table.addCell(new PdfPCell(new Phrase("Price (for one point)"))).setBackgroundColor(BaseColor.GREEN);
                table.addCell(new PdfPCell(new Phrase("Status"))).setBackgroundColor(BaseColor.GREEN);
                table.addCell(new PdfPCell(new Phrase("Email / Phone Number"))).setBackgroundColor(BaseColor.GREEN);
                table.addCell(new PdfPCell(new Phrase("Date"))).setBackgroundColor(BaseColor.GREEN);


                for (int i = 0; i < model.getRowCount(); i++) {
                    table.addCell(new Phrase((String) model.getValueAt(i, 0)));
                    table.addCell(new Phrase((String) model.getValueAt(i, 1)));
                    table.addCell(new Phrase((String) model.getValueAt(i, 2)));
                    table.addCell(new Phrase((String) model.getValueAt(i, 3)));
                    table.addCell(new Phrase((String) model.getValueAt(i, 4)));
                    table.addCell(new Phrase((String) model.getValueAt(i, 5)));
                    table.addCell(new Phrase((String) model.getValueAt(i, 6)));

                }
                document.open();
                document.add(table);
                document.close();


            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }

        });

        open_txt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    FileDialog open = new FileDialog(bookList, "Загрузка данных", FileDialog.LOAD);
                    open.setFile("*.txt");
                    open.setVisible(true);
                    String file1 = open.getDirectory() + open.getFile();
                    BufferedReader reader = new BufferedReader(new FileReader(file1));
                    int rows = model.getRowCount();
                    for (int i = 0; i < rows; i++) model.removeRow(0); // Очистка таблицы
                    String mtitle;
                    int i = 1;
                    do {
                        mtitle = reader.readLine();
                        if (mtitle != null) {
                            String a1 = reader.readLine();
                            String a2 = reader.readLine();
                            String a3 = reader.readLine();
                            String a4 = reader.readLine();
                            String a5 = reader.readLine();
                            String a6 = reader.readLine();

                            model.addRow(new String[]{mtitle, a1, a2, a3, a4, a5, a6}); // Запись строки в таблицу
                            i++;
                        }
                    } while (mtitle != null);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //JButton delete_but = new JButton("Commit Delete");
                System.out.println(main_equipment.getSelectedRow());
                if (model.getRowCount() > 0) {
                    if (main_equipment.getSelectedRow() != -1) {
                        try {
                            model.removeRow(main_equipment.convertRowIndexToModel(main_equipment.getSelectedRow()));
                            JOptionPane.showMessageDialog(bookList, "Delete Succesful!");

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Enter the row!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter the row!");
                    }

                }

            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame searchName = new JFrame("Found Name");
                    searchName.setSize(500, 300);
                    searchName.setLocation(300, 300);

                    searchName.setLayout(new BorderLayout());

                    String[] column = {"Client Name", "Equipment", "Count", "Price (for one point)", "Status", "Email / Phone Number", "Date of Begin and End"};
                    String[][] data = {};
                    DefaultTableModel modelFound = new DefaultTableModel(data, column);
                    JTable findName = new JTable(modelFound);
                    JScrollPane scroll = new JScrollPane(findName);

                    searchName.add(scroll, BorderLayout.CENTER);

                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (Objects.equals((String) model.getValueAt(i, 0), (String) names.getSelectedItem())) {
                            modelFound.addRow(new String[]{
                                    (String) model.getValueAt(i, 0),
                                    (String) model.getValueAt(i, 1),
                                    (String) model.getValueAt(i, 2),
                                    (String) model.getValueAt(i, 3),
                                    (String) model.getValueAt(i, 4),
                                    (String) model.getValueAt(i, 5),
                                    (String) model.getValueAt(i, 6)});
                        }
                    }

                    searchName.setVisible(true);

                } catch (HeadlessException ex) {
                    ex.printStackTrace();
                }
            }
        });

        delay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame delayContract = new JFrame("Delay Contract");
                    delayContract.setSize(500, 300);
                    delayContract.setLocation(300, 300);

                    delayContract.setLayout(new BorderLayout());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date today = sdf.parse("2022-06-8");


                    String[] column = {"Client Name", "Equipment", "Count", "Price (for one point)", "Status", "Email / Phone Number", "Date of Begin and End", "Delay"};
                    String[][] data = {};
                    DefaultTableModel modelDelay = new DefaultTableModel(data, column);
                    JTable Delay = new JTable(modelDelay);
                    JScrollPane scroll = new JScrollPane(Delay);


                    for (int i = 0; i < model.getRowCount(); i++) {

                        String contract = null;
                        Date date = sdf.parse((String) model.getValueAt(i, 6));
                        Calendar cal1 = Calendar.getInstance();
                        Calendar cal2 = Calendar.getInstance();
                        cal1.setTime(today);
                        cal2.setTime(date);

                        if (cal1.after(cal2)) {
                            contract = "Contract overdue";
                            System.out.println("Today After Date");
                        }

                        if (cal1.before(cal2)) {
                            contract = "Contract not overdue";
                            System.out.println("Today Before Date");
                        }

                        if (cal1.equals(cal2)) {
                            contract = "Contract overdue today";
                            System.out.println("Today = Date");
                        }

                        modelDelay.addRow(new String[]{
                                (String) model.getValueAt(i, 0),
                                (String) model.getValueAt(i, 1),
                                (String) model.getValueAt(i, 2),
                                (String) model.getValueAt(i, 3),
                                (String) model.getValueAt(i, 4),
                                (String) model.getValueAt(i, 5),
                                (String) model.getValueAt(i, 6),
                                (String) contract});

                    }


                    delayContract.add(scroll, BorderLayout.CENTER);

                    delayContract.setVisible(true);

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        periodSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    JFrame period = new JFrame("Sales For Period");
                    period.setSize(600, 80);
                    period.setLocation(200, 200);

                    JButton showSales = new JButton("Open");

                    JTextField date1 = new JTextField("Date From");
                    JTextField date2 = new JTextField("Date To");

                    JPanel panSales = new JPanel();
                    panSales.add(date1);
                    panSales.add(date2);
                    panSales.add(showSales).setBackground(Color.BLACK);
                    period.add(panSales, BorderLayout.CENTER);

                    showSales.addActionListener(e1 -> {
                        try {

                            JFrame period_new = new JFrame("Sales For Period");
                            period_new.setSize(600, 80);
                            period_new.setLocation(200, 200);


                            String[] column = {"Client Name", "Equipment", "Count", "Price (for one point)", "Status", "Email / Phone Number", "Date of Begin and End"};
                            String[][] data1 = {};
                            DefaultTableModel modelSales = new DefaultTableModel(data1, column);
                            JTable findName = new JTable(modelSales);
                            JScrollPane scroll1 = new JScrollPane(findName);

                            period_new.add(scroll1, BorderLayout.CENTER);

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date11 = sdf.parse(date1.getText());
                            Date date22 = sdf.parse(date2.getText());
                            Calendar cal1 = Calendar.getInstance();
                            Calendar cal2 = Calendar.getInstance();
                            cal1.setTime(date11);
                            cal2.setTime(date22);

                            for (int i = 0; i < model.getRowCount(); i++) {
                                Date date = sdf.parse((String) model.getValueAt(i, 6));
                                Calendar cal3 = Calendar.getInstance();
                                cal3.setTime(date);

                                if ((cal3.after(cal1) || cal3.equals(cal1)) && (cal3.before(cal2) || cal3.equals(cal2))) {
                                    modelSales.addRow(new String[]{
                                            (String) model.getValueAt(i, 0),
                                            (String) model.getValueAt(i, 1),
                                            (String) model.getValueAt(i, 2),
                                            (String) model.getValueAt(i, 3),

                                            (String) model.getValueAt(i, 4),
                                            (String) model.getValueAt(i, 5),
                                            (String) model.getValueAt(i, 6)});
                                }
                                period_new.setVisible(true);
                            }

                        } catch (ParseException ex) {
                            ex.printStackTrace();

                        }

                    });
                    period.setVisible(true);
                } catch (HeadlessException ex) {
                    ex.printStackTrace();
                }
            }

        });

        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame sorted = new JFrame("Make a Sort!");
                    sorted.setSize(600, 80);
                    sorted.setLocation(200, 200);

                    JComboBox<String> choice = new JComboBox<>(new String[]{"Client Name", "Equipment Name", "Price", "Count", "Date"});
                    JButton sortedButton = new JButton("Sort");

                    JPanel sortPanel = new JPanel();

                    sortPanel.add(sortedButton);
                    sortPanel.add(choice);

                    sorted.add(sortPanel, BorderLayout.CENTER);

                    sortedButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                String new_choice = (String) choice.getSelectedItem();
                                if (new_choice.contains("Client Name")) {
                                    for (int i = 0; i < model.getRowCount() - 1; i++) {
                                        for (int j = i + 1; j < model.getRowCount(); j++) {
                                            String obj1 = (String) model.getValueAt(i, 0);
                                            String obj2 = (String) model.getValueAt(j, 0);
                                            if (obj1.compareTo(obj2) > 0) {
                                                model.moveRow(j, j, i);
                                            }

                                        }
                                    }

                                }

                                if (new_choice.contains("Equipment Name")) {
                                    for (int i = 0; i < model.getRowCount() - 1; i++) {
                                        for (int j = i + 1; j < model.getRowCount(); j++) {
                                            String obj1 = (String) model.getValueAt(i, 1);
                                            String obj2 = (String) model.getValueAt(j, 1);
                                            if (obj1.compareTo(obj2) > 0) {
                                                model.moveRow(j, j, i);
                                            }

                                        }
                                    }

                                }

                                if (new_choice.contains("Price")) {
                                    for (int i = 0; i < model.getRowCount() - 1; i++) {
                                        for (int j = i + 1; j < model.getRowCount(); j++) {
                                            String obj1 = (String) model.getValueAt(i, 3);
                                            String obj2 = (String) model.getValueAt(j, 3);
                                            if (obj1.compareTo(obj2) > 0) {
                                                model.moveRow(j, j, i);
                                            }

                                        }
                                    }

                                }

                                if (new_choice.contains("Count")) {
                                    for (int i = 0; i < model.getRowCount() - 1; i++) {
                                        for (int j = i + 1; j < model.getRowCount(); j++) {
                                            String obj1 = (String) model.getValueAt(i, 2);
                                            String obj2 = (String) model.getValueAt(j, 2);
                                            if (obj1.compareTo(obj2) > 0) {
                                                model.moveRow(j, j, i);
                                            }

                                        }
                                    }

                                }

                                if (new_choice.contains("Date")) {
                                    for (int i = 0; i < model.getRowCount() - 1; i++) {
                                        for (int j = i + 1; j < model.getRowCount(); j++) {
                                            String obj1 = (String) model.getValueAt(i, 6);
                                            String obj2 = (String) model.getValueAt(j, 6);
                                            if (obj1.compareTo(obj2) > 0) {
                                                model.moveRow(j, j, i);
                                            }

                                        }
                                    }

                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    sorted.setVisible(true);
                } catch (HeadlessException ex) {
                    ex.printStackTrace();
                }
            }
        });

        finance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    JFrame finance = new JFrame("Financial Report");
                    finance.setSize(500, 300);
                    finance.setLocation(300, 300);

                    finance.setLayout(new BorderLayout());

                    String[] column = {"Client Name", "Sum of Money"};
                    String[][] data = {};
                    DefaultTableModel modelFin = new DefaultTableModel(data, column);
                    JTable findName = new JTable(modelFin);
                    JScrollPane scroll = new JScrollPane(findName);

                    finance.add(scroll, BorderLayout.CENTER);


                    String[][] fin = new String[model.getRowCount()][2];
                    for (int i = 0; i < model.getRowCount(); i++) {
                        fin[i][0] = (String) model.getValueAt(i, 0);
                        fin[i][1] = String.valueOf(Integer.parseInt(String.valueOf(model.getValueAt(i, 3))) * Integer.parseInt(String.valueOf(model.getValueAt(i, 2))));
                        modelFin.addRow(new String[]{fin[i][0], fin[i][1]});
                        System.out.println(fin[i][0]);
                        System.out.println(fin[i][1]);
                    }

                    finance.setVisible(true);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });

        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame filter = new JFrame("Filter");
                    filter.setSize(500, 300);
                    filter.setLocation(300, 300);

                    filter.setLayout(new BorderLayout());

                    String[] column = {"Client Name", "Equipment", "Count", "Price (for one point)", "Status", "Email / Phone Number", "Date of Begin and End"};
                    String[][] data = {};
                    DefaultTableModel modelFilter = new DefaultTableModel(data, column);
                    JTable findName = new JTable(modelFilter);
                    JScrollPane scroll = new JScrollPane(findName);

                    filter.add(scroll, BorderLayout.CENTER);

                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 4).equals("yes")) {
                            modelFilter.addRow(new String[]{
                                    (String) model.getValueAt(i, 0),
                                    (String) model.getValueAt(i, 1),
                                    (String) model.getValueAt(i, 2),
                                    (String) model.getValueAt(i, 3),

                                    (String) model.getValueAt(i, 4),
                                    (String) model.getValueAt(i, 5),
                                    (String) model.getValueAt(i, 6)});
                        }

                    }

                    filter.setVisible(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        save_txt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    if (model.getRowCount() != 0){
                        FileWriter writer = new FileWriter("table.txt", false);
                        for (int i = 0; i < model.getRowCount(); i++){
                            for (int j = 0; i < model.getColumnCount(); j++){
                                writer.write((String) model.getValueAt(i,j));
                            }

                        }
                        writer.flush();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Enter the row!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {

        // Создание и отображение экранной формы
        new shop_main().show();
    }

}