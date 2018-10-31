package ru.flc.service.shopautolink;

import org.dav.service.util.ResourceManager;

import javax.swing.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

public class SAResourceManager implements ResourceManager
{
    private static SAResourceManager instance;

    private static final ClassLoader loader = SAResourceManager.class.getClassLoader();

    public static final Locale RUS_LOCALE = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
    public static final Locale ENG_LOCALE = new Locale.Builder().setLanguage("en").setRegion("US").build();

    public static SAResourceManager getInstance()
    {
        if (instance == null)
            instance = new SAResourceManager();

        return instance;
    }

    private Locale currentLocale;
    private ResourceBundle bundle;

    private SAResourceManager()
    {
        setCurrentLocale(ENG_LOCALE);
    }

    @Override
    public Locale getCurrentLocale()
    {
        return currentLocale;
    }

    @Override
    public void setCurrentLocale(Locale locale)
    {
        this.currentLocale = locale;

        bundle = ResourceBundle.getBundle("ShopAutolinkStrings", this.currentLocale);
    }

    public void switchCurrentLocale()
    {
        if (getCurrentLocale() == ENG_LOCALE)
            setCurrentLocale(RUS_LOCALE);
        else
            setCurrentLocale(ENG_LOCALE);
    }

    @Override
    public ResourceBundle getBundle()
    {
        return bundle;
    }

    @Override
    public File getConfig()
    {
        File result = null;
        String fullJarPath = null;
        
        try
        {
            fullJarPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        }
        catch (URISyntaxException e){}
    
        if (fullJarPath != null)
        {
            String fullConfPath = Paths.get(fullJarPath).getParent().toAbsolutePath() + File.separator + "shop_autolink.conf";
            
            result = new File(fullConfPath);
        }
        
        return result;
    }

    @Override
    public ImageIcon getImageIcon(String name)
    {
        URL url = loader.getResource(name);

        if (url == null)
            return new ImageIcon();
        else
            return new ImageIcon(url);
    }
}
