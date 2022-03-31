package gui;

import localizer.LocalizationKey;
import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;


public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final LogWindow logWindow;

    public MainApplicationFrame(ResourceBundle bundle) {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        logWindow = createLogWindow(bundle);

        addWindow(logWindow);
        addWindow(createGameWindow(bundle));

        setJMenuBar(generateMenuBar(bundle));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CloseFrame.closeApp(MainApplicationFrame.this, bundle);
            }
        });

        logWindow.setVisible(false);
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    protected LogWindow createLogWindow(ResourceBundle bundle) {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource(), bundle);

        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();

        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected GameWindow createGameWindow(ResourceBundle bundle) {
        GameWindow gameWindow = new GameWindow(bundle);

        gameWindow.setLocation(10, 10);
        gameWindow.setSize(400, 400);
        gameWindow.setMinimumSize(gameWindow.getSize());

        return gameWindow;
    }

    private JMenuBar generateMenuBar(ResourceBundle bundle) {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createConfigMenu(bundle));
        menuBar.add(createLogMenu(bundle));

        return menuBar;
    }

    private JMenu createConfigMenu(ResourceBundle bundle) {
        var configMenu = new JMenu(bundle.getString(LocalizationKey.CONFIG_MENU_NAME.value()));

        var exitButton = new JMenuItem(bundle.getString(LocalizationKey.EXIT_BUTTON_NAME.value()));
        exitButton.addActionListener(l -> CloseFrame.closeApp(this, bundle));

        configMenu.add(createLookAndFeelMenu(bundle));
        configMenu.add(exitButton);

        return configMenu;
    }

    private JMenu createLookAndFeelMenu(ResourceBundle bundle) {
        JMenu lookAndFeelMenu =
                new JMenu(bundle.getString(LocalizationKey.VIEW_MODE_MENU_NAME.value()));

        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        addSystemLookAndFeel(lookAndFeelMenu, bundle);
        addCrossplatformLookAndFeel(lookAndFeelMenu, bundle);

        return lookAndFeelMenu;
    }

    private void addSystemLookAndFeel(JMenu lookAndFeelMenu, ResourceBundle bundle) {
        JMenuItem systemLookAndFeel =
                new JMenuItem(bundle.getString(LocalizationKey.SYSTEM_SCHEME_NAME.value()), KeyEvent.VK_S);

        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });

        lookAndFeelMenu.add(systemLookAndFeel);
    }

    private void addCrossplatformLookAndFeel(JMenu lookAndFeelMenu, ResourceBundle bundle) {
        JMenuItem crossplatformLookAndFeel =
                new JMenuItem(bundle.getString(LocalizationKey.UNIVERSAL_SCHEME_NAME.value()), KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });

        lookAndFeelMenu.add(crossplatformLookAndFeel);
    }

    private JMenu createLogMenu(ResourceBundle bundle) {
        JMenu testMenu = new JMenu(bundle.getString(LocalizationKey.TEST_MENU_NAME.value()));
        testMenu.setMnemonic(KeyEvent.VK_T);

        JMenuItem addLogMessageItem = new JMenuItem(
                bundle.getString(LocalizationKey.TEST_MENU_NAME.value()),
                KeyEvent.VK_S
        );
        addLogMessageItem.addActionListener((event) -> Logger.debug("Новая строка"));

        JMenuItem switchLogMenuVisibleItem = new JMenuItem(bundle.getString(LocalizationKey.SWITCHER_NAME.value()));
        switchLogMenuVisibleItem.addActionListener((event) -> logWindow.setVisible(!logWindow.isVisible()));

        testMenu.add(switchLogMenuVisibleItem);
        testMenu.add(addLogMessageItem);

        return testMenu;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.out.println("crushed. setLookAndFeel");
        }
    }
}
