package ru.flc.service.shopautolink;

import ru.flc.service.shopautolink.view.AutolinkFrame;

import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        new Main();
    }

    private static void setLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public Main()
    {
        EventQueue.invokeLater(() ->
        {
            setLookAndFeel();

            JFrame mainFrame = new AutolinkFrame();
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);

        });
    }
}

