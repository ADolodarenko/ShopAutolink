package ru.flc.service.shopautolink.view;

public final class Constants
{
    //Exceptions of any kind
	public static final String EXCPT_RESOURCE_MANAGER_EMPTY = "Resource manager is empty.";
	public static final String EXCPT_TABLE_EDITOR_FACTORY_EMPTY = "Table cell editor factory is empty.";
	public static final String EXCPT_TABLE_RENDERER_FACTORY_EMPTY = "Table cell renderer factory is empty.";
    public static final String EXCPT_VALUE_TYPE_WRONG = "Wrong value type.";
	
	//Exceptions for Parameter class
	public static final String EXCPT_PARAM_KEY_EMPTY = "Parameter key is empty.";
	public static final String EXCPT_PARAM_VALUE_EMPTY = "Parameter value is empty.";
	public static final String EXCPT_PARAM_VALUE_WRONG = "Parameter value %s is wrong.";
	public static final String EXCPT_PARAM_TYPE_EMPTY = "Parameter type is empty.";
	
	//Exceptions for the FileSource hierarchy
	public static final String EXCPT_FILE_SETTINGS_EMPTY = "File settings are empty.";
	public static final String EXCPT_FILE_SETTINGS_WRONG = "Wrong file settings.";
	
	//Exceptions for access objects
	public static final String EXCPT_FILE_SOURCE_EMPTY = "Empty file source.";
	public static final String EXCPT_PACK_SIZE_WRONG = "Wrong pack size.";
	
	//Exceptions for workers
	public static final String EXCPT_FILE_OBJECT_EMPTY = "File access object is empty.";
	public static final String EXCPT_DATA_OBJECT_EMPTY = "Data access object is empty.";
	
	//Titles for workers
	public static final String KEY_LOADER_SUCCESS = "LoaderSuccess";
	public static final String KEY_LOADER_FAILURE = "LoaderFailure";
	public static final String KEY_LOADER_CANCELLED = "LoaderCancelled";
	public static final String KEY_LOADER_PACK_LOADED = "LoaderPackLoaded";
	public static final String KEY_PROCESSOR_SUCCESS = "ProcessorSuccess";
	public static final String KEY_PROCESSOR_FAILURE = "ProcessorFailure";
	public static final String KEY_PROCESSOR_CANCELLED = "ProcessorCancelled";
	
	//Messages
	public static final String MESS_ROWS_AFFECTED = "(%n row(s) affected)";
	
	//Titles for main frame
	public static final String KEY_MAIN_FRAME = "MainFrame";
	public static final String KEY_LOG_PANEL = "LogPanel";
	public static final String KEY_BUTTON_LOAD = "LoadButton";
	public static final String KEY_BUTTON_LINK = "LinkButton";
	public static final String KEY_BUTTON_SETTINGS = "SettingsButton";
	
	public static final String KEY_PARAM_TITLE_NAME = "ColumnParamName";
	public static final String KEY_PARAM_TITLE_VALUE = "ColumnParamValue";
	
	//For Settings hierarchy
	public static final String KEY_PARAM_APP_LOCALE = "ApplicationLocale";
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
    
	//Icons
	public static final String ICON_NAME_LINKING = "linking32.png";
	public static final String ICON_NAME_LOADING = "loading_mod_green.gif";
	public static final String ICON_NAME_UPLOAD = "button-upload.png";
	public static final String ICON_NAME_LINK = "button-link.png";
	public static final String ICON_NAME_SETTINGS = "button-settings.png";
	public static final String ICON_NAME_USA = "american_16.png";
	public static final String ICON_NAME_RUS = "russian_16.png";

	private Constants(){}
}
