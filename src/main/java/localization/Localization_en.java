package localization;

import java.util.ListResourceBundle;

import static localizer.LocalizationKey.*;

public class Localization_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {CONFIG_MENU_NAME.value(), "Settings"},
                {EXIT_BUTTON_NAME.value(), "Exit"},

                {VIEW_MODE_MENU_NAME.value(), "View Modes"},
                {UNIVERSAL_SCHEME_NAME.value(), "Universal scheme"},
                {SYSTEM_SCHEME_NAME.value(), "System scheme"},

                {SWITCHER_NAME.value(), "Turn on/off log menu"},
                {TEST_LOG_BUTTON_NAME.value(), "Message in log"},

                {GAME_WINDOW_NAME.value(), "Game window"},

                {CLOSING_FRAME_TITLE.value(), "Confirmation"},
                {CLOSING_FRAME_MESSAGE.value(), "Close the window?"},
                {CLOSING_FRAME_OPTION_YES.value(), "Yes"},
                {CLOSING_FRAME_OPTION_NO.value(), "No"},

                {TEST_MENU_NAME.value(), "Tests"}
        };
    }
}
