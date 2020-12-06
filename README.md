# ThemeChanger

This is a library for changing themes as in Telegram or Vk.

![](https://media.giphy.com/media/j5ndt9Uhr27D1VLu5R/giphy.gif)
![](https://media.giphy.com/media/UQgXKAcUT3yHHB773v/giphy.gif)

## Usage

```java
btnChange.setOnClickListener { view ->
            changeTheme(view = view, duration = 1000) {
                if (checkTheme()) updateTheme(false)
                else updateTheme(true)
            }
        }
```
