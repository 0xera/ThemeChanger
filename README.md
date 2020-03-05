# ThemeChanger

This is a library for changing themes as in Telegram or Vk.

![](https://media.giphy.com/media/j5ndt9Uhr27D1VLu5R/giphy.gif)

## Installation

```groovy
implementation 'io.github.0xera:themechanger:0.1'
```

## Usage

With the creation of acvitity install the theme according to what is stored in preferences.

```java
    if (checkTheme()) {
            setTheme(R.style.ThemeLight)
        } else {
            setTheme(R.style.ThemeDark)
        }

    setContentView(R.layout.activity_main)
```

Then hang the listener of touch events on the view and call ```prepareToChange()```, passing it the context of the activity and event. If ```prepareToChange()``` returns true change the theme value in preferences.

```java
        btnChange.setOnTouchListener { _, event ->
            val action = ThemeChanger.prepareToChange(this, event)
            if (action) {
                if (checkTheme())
                    updateTheme(false)
                else
                    updateTheme(true)
            }
            action
        }
```

## Also
This is an experimental library. I will be happy to receive recommendations.
## License

```
Copyright 2020 Aydarov A.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License
```
