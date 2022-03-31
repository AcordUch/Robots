package gui;

import localizer.LocalizationKey;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class CloseFrame {
    public static void closeApp(Component frame, ResourceBundle bundle){
        if(showDialogBox(frame, bundle) == 0){
            frame.setVisible(false);
            System.exit(0);
        }
    }

    public static void closeInternalFrame(JInternalFrame frame, ResourceBundle bundle){
        if(showDialogBox(frame, bundle) == 0){
            frame.setVisible(false);
            frame.dispose();
        }
    }

    private static int showDialogBox(Component frame, ResourceBundle bundle){
        Object[] option = new Object[] {
                bundle.getString(LocalizationKey.CLOSING_FRAME_OPTION_YES.value()),
                bundle.getString(LocalizationKey.CLOSING_FRAME_OPTION_NO.value())
        };
        return JOptionPane.showOptionDialog(
                frame,
                bundle.getString(LocalizationKey.CLOSING_FRAME_MESSAGE.value()),
                bundle.getString(LocalizationKey.CLOSING_FRAME_TITLE.value()),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                option,
                option[0]
        );
    }
}
