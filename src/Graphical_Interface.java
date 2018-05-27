import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graphical_Interface extends JFrame implements ActionListener
{
    private JButton save, load, designate;
    private JTextArea load_text, designate_text;
    List array_list;
    boolean flag_array_word;
    String save_words;

    public Graphical_Interface()
    {
        array_list = new ArrayList(0);
        setTitle("wyznaczanie ilosc slow");
        setSize(800,800);
        setLayout(null);

        load = new JButton("load");
        designate = new JButton("designate");
        save = new JButton("save");
        load.setBounds(25, 10, 100, 30);
        designate.setBounds(325, 10, 130, 30);
        save.setBounds(655, 10, 100, 30);
        add(load);
        add(designate);
        add(save);
        load.addActionListener(this);
        designate.addActionListener(this);
        save.addActionListener(this);

        load_text = new JTextArea();
        designate_text = new JTextArea();
        JScrollPane sP1 = new JScrollPane(load_text);
        JScrollPane sP2 = new JScrollPane(designate_text);
        sP1.setBounds(25,50,300,600);
        sP2.setBounds(455, 50, 300, 600);
        add(sP1);
        add(sP2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object z = e.getSource();
        if (z == load)
        {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                try
                {
                    load_text.setText("");
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNext()){
                        load_text.append(scanner.nextLine() + "\n");
                    }

                } catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
        else if (z == designate)
        {
            if (load_text.getText().length() == 0)
            {
                System.out.println("brak słow");
            }

            String[] list = load_text.getText().replace("\n"," ").split(" ");

            for (int i = 0; i < list.length; i++){
                flag_array_word = true;
                if (!list[i].equals(""))
                {
                    int k = 1;
                    for(int j = i + 1; j < list.length; j++)
                    {
                        if (list[i].equals(list[j]))
                        {
                            k++;
                        }
                    }
                    for (int j = 0; j < array_list.size(); j++)
                    {
                        Words temp = (Words) array_list.get(j);
                        if (temp.getName().equals(list[i]))
                        {
                            flag_array_word = false;
                        }
                    }
                    if (flag_array_word)
                    {
                        array_list.add(new Words(list[i],k));
                    }

                }
            }
            disp();

        }
        else if (z == save)
        {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                try
                {
                    PrintWriter pw = new PrintWriter(file);
                    Scanner scanner = new Scanner(save_words);
                    while (scanner.hasNext())
                    {
                        pw.println(scanner.nextLine() + "\n");
                    }
                    pw.close();
                }
                catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }
    public void disp()
    {
        save_words = "";
        while (!array_list.isEmpty())
        {
            Words temp1 = (Words) array_list.get(0);
            save_words = save_words + temp1.getName() + " = " + temp1.getNumber() + "\n";
            array_list.remove(0);
        }
        designate_text.setText(save_words);
    }
}
