package ru.flc.service.shopautolink.view;

public final class Constants
{
    //Exceptions of any kind
	public static final String EXCPT_RESOURCE_MANAGER_EMPTY = "Resource manager is empty.";
    public static final String EXCPT_VALUE_TYPE_WRONG = "Wrong value type.";
	
	//For ParameterAlt class
	public static final String EXCPT_PARAM_KEY_EMPTY = "Parameter key is empty.";
	public static final String EXCPT_PARAM_VALUE_EMPTY = "Parameter value is empty.";
	public static final String EXCPT_PARAM_VALUE_WRONG = "Parameter value %s is wrong.";
	public static final String EXCPT_PARAM_TYPE_EMPTY = "Parameter type is empty.";

	public static final String KEY_PARAM_TITLE_NAME = "ColumnParamName";
	public static final String KEY_PARAM_TITLE_VALUE = "ColumnParamValue";
	
	//For Settings hierarchy
	public static final String KEY_PARAM_APP_LANGUAGE = "Language";
	public static final String KEY_PARAM_MAIN_WIN_X = "MainWindowX";
	public static final String KEY_PARAM_MAIN_WIN_Y = "MainWindowY";
	public static final String KEY_PARAM_MAIN_WIN_MAXIMIZED = "MainWindowMaximized";
	public static final String KEY_PARAM_MAIN_WIN_WIDTH = "MainWindowWidth";
	public static final String KEY_PARAM_MAIN_WIN_HEIGHT = "MainWindowHeight";
	public static final String KEY_PARAM_DB_DRIVER = "DatabaseDriver";
	public static final String KEY_PARAM_DB_CONN_PREF = "DatabaseConnectionPref";
	public static final String KEY_PARAM_DB_HOST = "DatabaseHost";
	public static final String KEY_PARAM_DB_PORT = "DatabasePort";
	public static final String KEY_PARAM_DB_CATALOG = "DatabaseCatalog";
	public static final String KEY_PARAM_DB_USER = "DatabaseUser";
	public static final String KEY_PARAM_DB_PASSWORD = "DatabasePassword";
	public static final String KEY_PARAM_DB_TABLE = "DatabaseTable";
	public static final String KEY_PARAM_DB_SP = "DatabaseStoredProcedure";
	public static final String KEY_PARAM_CHANNEL = "ChannelId";
	public static final String KEY_PARAM_PRICE_LIST = "PriceListId";
	public static final String KEY_PARAM_PACK_SIZE = "PacketSize";

	//For SettingsDialog
    public static final String KEY_SETTINGS_DIALOG = "SettingsDialog";
    public static final String KEY_BUTTON_OK = "OkButton";
    public static final String KEY_BUTTON_CANCEL = "CancelButton";

	private Constants(){}
}
