package localizer;

public enum LocalizationKey {
    CONFIG_MENU_NAME("configMenuName"), EXIT_BUTTON_NAME("exitButtonName"),

    VIEW_MODE_MENU_NAME("viewModeMenuName"), UNIVERSAL_SCHEME_NAME("universalSchemeName"),
    SYSTEM_SCHEME_NAME("systemSchemeName"),

    SWITCHER_NAME("switcherName"), TEST_LOG_BUTTON_NAME("testLogButtonName"),

    GAME_WINDOW_NAME("gameWindowName"),

    CLOSING_FRAME_TITLE("closingFrameTitle"), CLOSING_FRAME_MESSAGE("closingFrameMessage"),
    CLOSING_FRAME_OPTION_YES("closingFrameOptionYes"), CLOSING_FRAME_OPTION_NO("closingFrameOptionNo"),

    TEST_MENU_NAME("testMenuName");

    private final String value;

    LocalizationKey(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }
}
