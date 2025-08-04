package com.damir00109.socks5SRV;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.nio.file.Files;
import java.util.logging.Level;

public class Socks5SRV extends JavaPlugin {

    private String proxyHost;
    private int proxyPort;
    private String proxyUser;
    private String proxyPass;
    private FileConfiguration langConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadLanguageFile();

        if (isDefaultConfig()) {
            log(getMessage("config-not-set"));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (loadProxyConfig()) {
            setGlobalProxy();
            log(getMessage("plugin-enabled").replace("%version%", getPluginMeta().getVersion()));
            log(getMessage("proxy-applied"));
        } else {
            log(getMessage("proxy-load-fail"));
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        log(getMessage("plugin-disabled").replace("%version%", getPluginMeta().getVersion()));
    }

    private void loadLanguageFile() {
        String lang = getConfig().getString("settings.lang", "en");
        File langFile = new File(getDataFolder(), "lang/" + lang + ".yml");
        if (!langFile.exists()) {
            try {
                File langDir = new File(getDataFolder(), "lang");
                if (!langDir.exists() && !langDir.mkdirs()) {
                    log(getMessage("lang-folder-error"));
                    return;
                }
                saveResource("lang/en.yml", false);
                saveResource("lang/ru.yml", false);

                File enFile = new File(getDataFolder(), "lang/en.yml");
                File ruFile = new File(getDataFolder(), "lang/ru.yml");
                if(enFile.exists()) Files.move(enFile.toPath(), new File(langDir, "en.yml").toPath());
                if(ruFile.exists()) Files.move(ruFile.toPath(), new File(langDir, "ru.yml").toPath());

                log(getMessage("lang-file-created").replace("%lang%", lang));
            } catch (Exception e) {
                log(getMessage("lang-file-error").replace("%error%", e.getMessage()));
            }
        }

        langConfig = YamlConfiguration.loadConfiguration(langFile);

        try (InputStream defLangStream = getResource("lang/" + lang + ".yml")) {
            if (defLangStream != null) {
                langConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defLangStream)));
            }
        } catch (IOException e) {
            log(getMessage("lang-load-error", "en").replace("%lang%", lang).replace("%error%", e.getMessage()));
        }

    }
    
    private String getMessage(String key, String lang) {
        String message = langConfig.getString(key);
        if (message == null) {
             try (InputStream defLangStream = getResource("lang/" + lang + ".yml")) {
                if (defLangStream != null) {
                    FileConfiguration defLangConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defLangStream));
                     return defLangConfig.getString(key, "Missing translation key: " + key);
                }
            } catch (IOException e) {
                 // ignore
            }
            return "Missing translation key: " + key;
        }
        return message;
    }

    private String getMessage(String key) {
        return getMessage(key, getConfig().getString("settings.lang", "en"));
    }

    private boolean isDefaultConfig() {
        FileConfiguration config = getConfig();
        return "127.0.0.1".equals(config.getString("proxy.host")) &&
               1080 == config.getInt("proxy.port") &&
               "discordsrv".equals(config.getString("proxy.username")) &&
               "123456789".equals(config.getString("proxy.password"));
    }

    private boolean loadProxyConfig() {
        try {
            reloadConfig();
            FileConfiguration config = getConfig();
            proxyHost = config.getString("proxy.host", "");
            proxyPort = config.getInt("proxy.port", 0);
            proxyUser = config.getString("proxy.username", "");
            proxyPass = config.getString("proxy.password", "");

            return !proxyHost.isEmpty() && proxyPort != 0;
        } catch (Exception e) {
            log(getMessage("config-load-error").replace("%error%", e.getMessage()));
            return false;
        }
    }

    private void setGlobalProxy() {
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("socksProxyHost", proxyHost);
        System.setProperty("socksProxyPort", String.valueOf(proxyPort));

        if (proxyUser != null && !proxyUser.isEmpty() && proxyPass != null && !proxyPass.isEmpty()) {
            System.setProperty("java.net.socks.username", proxyUser);
            System.setProperty("java.net.socks.password", proxyPass);

            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(proxyUser, proxyPass.toCharArray());
                }
            });
            log(getMessage("auth-enabled"));
        } else {
            Authenticator.setDefault(null);
            log(getMessage("no-auth"));
        }
    }

    private void log(String message) {
        getLogger().log(Level.INFO, message);
    }
}
