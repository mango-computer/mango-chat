jQuery.scrollBar plugin
======

[![Author](https://img.shields.io/badge/author-falkm-blue.svg?style=flat-square)](https://falk-m.de)
[![Source Code](http://img.shields.io/badge/source-falkmueller/jQuery.scrollBar-blue.svg?style=flat-square)](https://github.com/falkmueller/jQuery.scrollBar)
[![Software License](https://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat-square)](LICENSE)
[![Website](https://img.shields.io/website-falk-falk/http/falk-m.de.svg)](https://falk-m.de)

a ligthwight jQuery scrollbar Plugin. Display the scrollbar cross-platform.
[Documentation and Demo](http://code.falk-m.de/scrollBar/).

![Screnshot](screenshot.jpg)

## Usage

### html Code

```html
 <div class="sb-container">This is a example</div>
```

### Init jQuery.scrollBox

(include scrollBar.css and scrollBar.js in your page before)
```html
 <script>$(".sb-container").scrollBox();</script>
```

When you dynamicly load content call this after them
```html
 <script>$(window).trigger("resize.scrollBox");</script>
```

## Tested

- Chrome (Pc, Android Tablet)
- Firefox (Pc, Android Tablet)
- Internet Explorer 11 (Pc)
- Safari (Mac Book, iPad)