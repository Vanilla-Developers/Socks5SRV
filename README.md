# Socks5SRV

![RELEASES](https://github.com/Vanilla-Developers/Socks5SRV/releases)


---

## Socks5SRV (English)

A simple and robust Minecraft plugin that forces all outgoing Java HTTP(S) connections to use a SOCKS5 proxy. Ideal for routing traffic from plugins like DiscordSRV through a specific network interface or proxy server.

### Features

- **Global Proxy:** Sets a system-wide SOCKS5 proxy for the entire JVM, affecting all plugins.
- **Easy Configuration:** Configure host, port, and credentials in a simple `config.yml`.
- **Lightweight:** No commands, no unnecessary features. Just set it and forget it.
- **Auto-start:** The plugin will not start if a default, unconfigured `config.yml` is detected.

### Installation

1.  Download the latest `.jar` file from the [Releases](https://github.com/<ВАШ_ЛОГИН>/<ВАШ_РЕПОЗИТОРИЙ>/releases) page.
2.  Place the `Socks5SRV.jar` file into your server's `plugins/` directory.
3.  Start your server once to generate the `plugins/Socks5SRV/config.yml` file.
4.  Stop the server.

### Configuration

Open the `plugins/Socks5SRV/config.yml` file and enter your SOCKS5 proxy details.

```yaml
proxy:
  # --- IMPORTANT ---
  # PLEASE CHANGE THESE VALUES. The plugin will not start with default values.
  host: "127.0.0.1"
  port: 1080
  username: "" # Leave empty for no authentication
  password: "" # Leave empty for no authentication

settings:
  # Language for plugin messages (en, ru).
  lang: "en"
```

After configuring, start your server again. The proxy will be applied globally.

---

## Socks5SRV (Русский)

Простой и надежный плагин для Minecraft, который заставляет все исходящие Java HTTP(S) соединения использовать SOCKS5 прокси. Идеально подходит для маршрутизации трафика от плагинов, таких как DiscordSRV, через определенный сетевой интерфейс или прокси-сервер.

### Особенности

- **Глобальный прокси:** Устанавливает системный SOCKS5 прокси для всей JVM, что влияет на все плагины.
- **Простая настройка:** Настройте хост, порт и данные для аутентификации в простом файле `config.yml`.
- **Легковесность:** Никаких команд и лишних функций. Просто настройте и забудьте.
- **Защита от автозапуска:** Плагин не запустится, если обнаружит стандартную, ненастроенную конфигурацию.

### Установка

1.  Скачайте последнюю версию `.jar` файла со страницы [Релизов](https://github.com/<ВАШ_ЛОГИН>/<ВАШ_РЕПОЗИТОРИЙ>/releases).
2.  Поместите файл `Socks5SRV.jar` в папку `plugins/` вашего сервера.
3.  Запустите сервер один раз, чтобы сгенерировался файл `plugins/Socks5SRV/config.yml`.
4.  Остановите сервер.

### Настройка

Откройте файл `plugins/Socks5SRV/config.yml` и введите данные вашего SOCKS5 прокси.

```yaml
proxy:
  # --- ВАЖНО ---
  # ПОЖАЛУЙСТА, ИЗМЕНИТЕ ЭТИ ЗНАЧЕНИЯ. Плагин не запустится со значениями по умолчанию.
  host: "127.0.0.1"
  port: 1080
  username: "" # Оставьте пустым, если аутентификация не требуется
  password: "" # Оставьте пустым, если аутентификация не требуется

settings:
  # Язык сообщений плагина (en, ru).
  lang: "ru"
```

После настройки снова запустите сервер. Прокси будет применен глобально.
