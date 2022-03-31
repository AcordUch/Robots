package localization;

import java.util.ListResourceBundle;

import static localizer.LocalizationKey.*;

public class Localization_ru extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {CONFIG_MENU_NAME.value(), "Настройки"},
                {EXIT_BUTTON_NAME.value(), "Выход"},

                {VIEW_MODE_MENU_NAME.value(), "Режим Отображения"},
                {UNIVERSAL_SCHEME_NAME.value(), "Универсальная схема"},
                {SYSTEM_SCHEME_NAME.value(), "Системная схема"},

                {SWITCHER_NAME.value(), "Вкл / Выкл лог"},
                {TEST_LOG_BUTTON_NAME.value(), "Сообщение в лог"},

                {GAME_WINDOW_NAME.value(), "Меню игры"},

                {CLOSING_FRAME_TITLE.value(), "Подтверждение"},
                {CLOSING_FRAME_MESSAGE.value(), "Закрыть окно?"},
                {CLOSING_FRAME_OPTION_YES.value(), "Да"},
                {CLOSING_FRAME_OPTION_NO.value(), "Нет"},

                {TEST_MENU_NAME.value(), "Тесты"}
        };
    }
}