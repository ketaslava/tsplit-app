package com.ktvincco.tsplit.domain


class KeyboardInput(val inputText: String? = null, val action: String? = null,
    val amount: Int? = null)


class Button (val previewText: String? = null, val previewImage: String? = null,
              val inputText: String? = null, val action: String? = null,
              val longPressAction: String? = null, val amount: Int? = null,
              val referenceLayerName: String? = null, val stackIndicator: Boolean = false,
              val shiftIndicator: Boolean = false, val gestureName: String? = null,
              val overlayLayer: String? = null, val gestureIndicator: Boolean = false,
              val isLockReferenceLayerOnCompleteClick: Boolean = false,
              val isUnlockLayerOnCompleteClick: Boolean = false)

class Layer (val name: String, val buttons: List<Button?>) {

    fun getButtonByPosition(position: Int): Button? {
        if (position > buttons.size - 1 || position < 0) return null
        return buttons[position]
    }
}


class Stack (private val layers: List<Layer>) {

    fun getLayerByName(name: String): Layer? {
        for (layer in layers) {
            if (layer.name == name) {
                return layer
            }
        }
        return null
    }

}


val keyboardStack = Stack(listOf<Layer>(

    // Latin

    Layer("latin1",listOf<Button>(

        // Row 1
        Button(previewText = "I", inputText = "i", referenceLayerName = "latin2"),
        Button(previewText = "N", inputText = "n", referenceLayerName = "latin2"),
        Button(previewText = "Y", inputText = "y", referenceLayerName = "latin2"),
        Button(previewText = "W", inputText = "w", referenceLayerName = "latin2"),
        Button(previewText = "S", inputText = "s", referenceLayerName = "latin2"),
        Button(previewText = "R", inputText = "r", referenceLayerName = "latin2"),

        // Row 2
        Button(previewText = "A", inputText = "a", referenceLayerName = "latin2"),
        Button(previewText = "T", inputText = "t", referenceLayerName = "latin2"),
        Button(previewText = "L", inputText = "l", referenceLayerName = "latin2"),
        Button(previewText = "U", inputText = "u", referenceLayerName = "latin2"),
        Button(previewText = "E", inputText = "e", referenceLayerName = "latin2"),
        Button(previewText = "O", inputText = "o", referenceLayerName = "latin2"),

        // Row 3
        Button(previewText = "SH", referenceLayerName = "latinCapital1", isLockReferenceLayerOnCompleteClick = true),
        Button(previewText = "H", inputText = "h", referenceLayerName = "latin2"),
        Button(previewText = "C", inputText = "c", referenceLayerName = "latin2"),
        Button(previewText = "M", inputText = "m", referenceLayerName = "latin2"),
        Button(previewText = "D", inputText = "d", referenceLayerName = "latin2"),
        Button(previewText = "SH", referenceLayerName = "latinCapital1", isLockReferenceLayerOnCompleteClick = true),

        // Row 4
        Button(previewText = "1", inputText = "1", referenceLayerName = "numpad1", overlayLayer = "specialSymbolsSetDragSelector1", gestureIndicator = true),
        Button(previewText = "SP", inputText = " ", referenceLayerName = "latin2"),
        Button(previewText = "G", inputText = "g", referenceLayerName = "latin2"),
        Button(previewText = "F", inputText = "f", referenceLayerName = "latin2"),
        Button(previewText = "SP", inputText = " ", referenceLayerName = "latin2"),
        Button(previewText = "DE", action = "deleteCharacterFromTheLeft", gestureName = "deleteMultipleCharactersFromTheLeft", longPressAction = "deleteMultipleCharactersFromTheLeft", gestureIndicator = true),

        )),
    Layer("latin2",listOf<Button>(

        // Row 1
        Button(previewText = "_", inputText = "_"),
        Button(previewText = "V", inputText = "v"),
        Button(previewText = "J", inputText = "j"),
        Button(previewText = "Z", inputText = "z"),
        Button(previewText = "B", inputText = "b"),
        Button(previewText = ":", inputText = ":"),

        // Row 2
        Button(previewText = "?", inputText = "?"),
        Button(previewText = "P", inputText = "p"),
        Button(previewText = "X", inputText = "x"),
        Button(previewText = "Q", inputText = "q"),
        Button(previewText = "K", inputText = "k"),
        Button(previewText = "!", inputText = "!"),

        // Row 3
        Button(previewText = "XX", isUnlockLayerOnCompleteClick = true),
        Button(previewText = ".", inputText = "."),
        Button(previewText = "'", inputText = "'"),
        Button(previewText = "\"", inputText = "\""),
        Button(previewText = ",", inputText = ","),
        Button(previewText = "XX", isUnlockLayerOnCompleteClick = true),

        // Row 4
        Button(previewText = "SC", referenceLayerName = "scriptSelector", stackIndicator = true),
        Button(previewText = "->", action = "moveCursor", amount = 1, gestureName = "moveCursor", gestureIndicator = true),
        Button(previewText = "(", inputText = "("),
        Button(previewText = ")", inputText = ")"),
        Button(previewText = "<-", action = "moveCursor", amount = -1, gestureName = "moveCursor", gestureIndicator = true),
        Button(previewText = "EN", action = "enter"),
    )),
    Layer("latinCapital1",listOf<Button>(

        // Row 1
        Button(previewText = "I", inputText = "I", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "N", inputText = "N", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "Y", inputText = "Y", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "W", inputText = "W", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "S", inputText = "S", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "R", inputText = "R", referenceLayerName = "latinCapital2", shiftIndicator = true),

        // Row 2
        Button(previewText = "A", inputText = "A", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "T", inputText = "T", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "L", inputText = "L", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "U", inputText = "U", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "E", inputText = "E", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "O", inputText = "O", referenceLayerName = "latinCapital2", shiftIndicator = true),

        // Row 3
        Button(previewText = "XX", referenceLayerName = "latinCapital1", isUnlockLayerOnCompleteClick = true),
        Button(previewText = "H", inputText = "H", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "C", inputText = "C", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "M", inputText = "M", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "D", inputText = "D", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "XX", referenceLayerName = "latinCapital1", isUnlockLayerOnCompleteClick = true),

        // Row 4
        Button(previewText = "Ø", inputText = "Ø", referenceLayerName = "specialLettersCapital1", overlayLayer = "specialSymbolsSetDragSelectorCapital1", gestureIndicator = true),
        Button(previewText = "SP", inputText = " ", referenceLayerName = "latinCapital2"),
        Button(previewText = "G", inputText = "G", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "F", inputText = "F", referenceLayerName = "latinCapital2", shiftIndicator = true),
        Button(previewText = "SP", inputText = " ", referenceLayerName = "latinCapital2"),
        Button(previewText = "\\N", inputText = "\n"),

        )),
    Layer("latinCapital2",listOf<Button>(

        // Row 1
        Button(previewText = "/", inputText = "/", shiftIndicator = true),
        Button(previewText = "V", inputText = "V", shiftIndicator = true),
        Button(previewText = "J", inputText = "J", shiftIndicator = true),
        Button(previewText = "Z", inputText = "Z", shiftIndicator = true),
        Button(previewText = "B", inputText = "B", shiftIndicator = true),
        Button(previewText = ";", inputText = ";", shiftIndicator = true),

        // Row 2
        Button(previewText = "*", inputText = "*", shiftIndicator = true),
        Button(previewText = "P", inputText = "P", shiftIndicator = true),
        Button(previewText = "X", inputText = "X", shiftIndicator = true),
        Button(previewText = "Q", inputText = "Q", shiftIndicator = true),
        Button(previewText = "K", inputText = "K", shiftIndicator = true),
        Button(previewText = "$", inputText = "$", shiftIndicator = true),

        // Row 3
        Button(previewText = "XX", isUnlockLayerOnCompleteClick = true),
        Button(previewText = "-", inputText = "-", shiftIndicator = true),
        Button(previewText = "{", inputText = "{", shiftIndicator = true),
        Button(previewText = "}", inputText = "}", shiftIndicator = true),
        Button(previewText = "—", inputText = "—", shiftIndicator = true),
        Button(previewText = "XX", isUnlockLayerOnCompleteClick = true),

        // Row 4
        Button(previewText = "SC", referenceLayerName = "scriptSelector", stackIndicator = true),
        Button(previewText = "->", action = "moveCursor", amount = 1, gestureName = "moveCursor", gestureIndicator = true),
        Button(previewText = "[", inputText = "[", shiftIndicator = true),
        Button(previewText = "]", inputText = "]", shiftIndicator = true),
        Button(previewText = "<-", action = "moveCursor", amount = -1, gestureName = "moveCursor", gestureIndicator = true),
        Button(previewText = "EN", action = "enter"),

    )),

    // Cyrillic

    Layer("cyrillic1",listOf<Button>(

        // Row 1
        Button(previewText = "И", inputText = "и", referenceLayerName = "cyrillic2"),
        Button(previewText = "Н", inputText = "н", referenceLayerName = "cyrillic2"),
        Button(previewText = "Я", inputText = "я", referenceLayerName = "cyrillic2"),
        Button(previewText = "В", inputText = "в", referenceLayerName = "cyrillic2"),
        Button(previewText = "С", inputText = "с", referenceLayerName = "cyrillic2"),
        Button(previewText = "Р", inputText = "р", referenceLayerName = "cyrillic2"),

        // Row 2
        Button(previewText = "А", inputText = "а", referenceLayerName = "cyrillic2"),
        Button(previewText = "Т", inputText = "т", referenceLayerName = "cyrillic2"),
        Button(previewText = "Л", inputText = "л", referenceLayerName = "cyrillic2"),
        Button(previewText = "У", inputText = "у", referenceLayerName = "cyrillic2"),
        Button(previewText = "Е", inputText = "е", referenceLayerName = "cyrillic2"),
        Button(previewText = "О", inputText = "о", referenceLayerName = "cyrillic2"),

        // Row 3
        Button(previewText = "SH", referenceLayerName = "cyrillicCapital1", isLockReferenceLayerOnCompleteClick = true),
        Button(previewText = "Ь", inputText = "ь", referenceLayerName = "cyrillic2"),
        Button(previewText = "К", inputText = "к", referenceLayerName = "cyrillic2"),
        Button(previewText = "М", inputText = "м", referenceLayerName = "cyrillic2"),
        Button(previewText = "D", inputText = "d", referenceLayerName = "cyrillic2"),
        Button(previewText = "SH", referenceLayerName = "cyrillicCapital1", isLockReferenceLayerOnCompleteClick = true),

        // Row 4
        Button(previewText = "1", inputText = "1", referenceLayerName = "numpad1", overlayLayer = "specialSymbolsSetDragSelector1", gestureIndicator = true),
        Button(previewText = "SP", inputText = " ", referenceLayerName = "cyrillic2"),
        Button(previewText = "Г", inputText = "г", referenceLayerName = "cyrillic2"),
        Button(previewText = "П", inputText = "п", referenceLayerName = "cyrillic2"),
        Button(previewText = "SP", inputText = " ", referenceLayerName = "cyrillic2"),
        Button(previewText = "DE", action = "deleteCharacterFromTheLeft", gestureName = "deleteMultipleCharactersFromTheLeft", longPressAction = "deleteMultipleCharactersFromTheLeft", gestureIndicator = true),

        )),
    Layer("cyrillic2",listOf<Button>(

        // Row 1
        Button(previewText = "I", inputText = "i"),
        Button(previewText = "Ю", inputText = "ю"),
        Button(previewText = "Ж", inputText = "ж"),
        Button(previewText = "З", inputText = "з"),
        Button(previewText = "Б", inputText = "б"),
        Button(previewText = "Ц", inputText = "ц"),

        // Row 2
        Button(previewText = "Ы", inputText = "ы"),
        Button(previewText = "Ч", inputText = "ч"),
        Button(previewText = "Х", inputText = "х"),
        Button(previewText = "Й", inputText = "й"),
        Button(previewText = "Ш", inputText = "ш"),
        Button(previewText = "Щ", inputText = "щ"),

        // Row 3
        Button(previewText = "XX", isUnlockLayerOnCompleteClick = true),
        Button(previewText = ".", inputText = "."),
        Button(previewText = "Ф", inputText = "ф"),
        Button(previewText = "Э", inputText = "э"),
        Button(previewText = ",", inputText = ","),
        Button(previewText = "XX", isUnlockLayerOnCompleteClick = true),

        // Row 4
        Button(previewText = "SC", referenceLayerName = "scriptSelector", stackIndicator = true),
        Button(previewText = "->", action = "moveCursor", amount = 1, gestureName = "moveCursor", gestureIndicator = true),
        Button(previewText = "Ё", inputText = "ё"),
        Button(previewText = "Ъ", inputText = "ъ"),
        Button(previewText = "<-", action = "moveCursor", amount = -1, gestureName = "moveCursor", gestureIndicator = true),
        Button(previewText = "EN", action = "enter"),
    )),
    Layer("cyrillicCapital1",listOf<Button>(

        // Row 1
        Button(previewText = "И", inputText = "И", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "Н", inputText = "Н", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "Я", inputText = "Я", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "В", inputText = "В", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "С", inputText = "С", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "Р", inputText = "Р", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),

        // Row 2
        Button(previewText = "А", inputText = "А", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "Т", inputText = "Т", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "Л", inputText = "Л", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "У", inputText = "У", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "Е", inputText = "Е", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "О", inputText = "О", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),

        // Row 3
        Button(previewText = "XX", referenceLayerName = "cyrillicCapital1", isUnlockLayerOnCompleteClick = true),
        Button(previewText = "Ь", inputText = "Ь", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "К", inputText = "К", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "М", inputText = "М", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "D", inputText = "D", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "XX", referenceLayerName = "cyrillicCapital1", isUnlockLayerOnCompleteClick = true),

        // Row 4
        Button(previewText = "Ø", inputText = "Ø", referenceLayerName = "specialLettersCapital1", overlayLayer = "specialSymbolsSetDragSelector2", gestureIndicator = true),
        Button(previewText = "SP", inputText = " ", referenceLayerName = "cyrillicCapital2"),
        Button(previewText = "Г", inputText = "Г", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "П", inputText = "П", referenceLayerName = "cyrillicCapital2", shiftIndicator = true),
        Button(previewText = "SP", inputText = " ", referenceLayerName = "cyrillicCapital2"),
        Button(previewText = "\\N", inputText = "\n"),

        )),
    Layer("cyrillicCapital2",listOf<Button>(

        // Row 1
        Button(previewText = "I", inputText = "I", shiftIndicator = true),
        Button(previewText = "Ю", inputText = "Ю", shiftIndicator = true),
        Button(previewText = "Ж", inputText = "Ж", shiftIndicator = true),
        Button(previewText = "З", inputText = "З", shiftIndicator = true),
        Button(previewText = "Б", inputText = "Б", shiftIndicator = true),
        Button(previewText = "Ц", inputText = "Ц", shiftIndicator = true),

        // Row 2
        Button(previewText = "Ы", inputText = "Ы", shiftIndicator = true),
        Button(previewText = "Ч", inputText = "Ч", shiftIndicator = true),
        Button(previewText = "Х", inputText = "Х", shiftIndicator = true),
        Button(previewText = "Й", inputText = "Й", shiftIndicator = true),
        Button(previewText = "Ш", inputText = "Ш", shiftIndicator = true),
        Button(previewText = "Щ", inputText = "Щ", shiftIndicator = true),

        // Row 3
        Button(previewText = "XX", isUnlockLayerOnCompleteClick = true),
        Button(previewText = ".", inputText = ".", shiftIndicator = true),
        Button(previewText = "Ф", inputText = "Ф", shiftIndicator = true),
        Button(previewText = "Э", inputText = "Э", shiftIndicator = true),
        Button(previewText = ",", inputText = ",", shiftIndicator = true),
        Button(previewText = "XX", isUnlockLayerOnCompleteClick = true),

        // Row 4
        Button(previewText = "SC", referenceLayerName = "scriptSelector", stackIndicator = true),
        Button(previewText = "->", action = "moveCursor", amount = 1, gestureName = "moveCursor", gestureIndicator = true),
        Button(previewText = "Ё", inputText = "Ё", shiftIndicator = true),
        Button(previewText = "Ъ", inputText = "Ъ", shiftIndicator = true),
        Button(previewText = "<-", action = "moveCursor", amount = -1, gestureName = "moveCursor", gestureIndicator = true),
        Button(previewText = "EN", action = "enter"),

        )),

    // Special

    Layer("scriptSelector",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(),
        Button(),
        Button(previewText = "LA", action = "changeScriptToLatin"),
        Button(),

        // Row 2
        Button(),
        Button(),
        Button(),
        Button(),
        Button(previewText = "КИ", action = "changeScriptToCyrillic"),
        Button(),

        // Row 3
        Button(),
        Button(),
        Button(),
        Button(),
        Button(), //Button(previewText = "أ ر", action = "changeScriptToArabic"),
        Button(),

        // Row 4
        Button(),
        Button(),
        Button(),
        Button(),
        Button(),
        Button(),
    )),
    Layer("specialSymbolsSetDragSelector1",listOf<Button?>(

        // Row 1
        Button(previewText = "★", inputText = "★", referenceLayerName = "specialSymbols3", overlayLayer = "specialSymbolsSetDragSelector1"),
        Button(previewText = "✿", inputText = "✿", referenceLayerName = "specialSymbols5", overlayLayer = "specialSymbolsSetDragSelector1"),
        null,
        null,
        null,
        null,

        // Row 2
        Button(previewText = "~", inputText = "~", referenceLayerName = "specialSymbols2", overlayLayer = "specialSymbolsSetDragSelector1"),
        Button(previewText = "∑", inputText = "∑", referenceLayerName = "specialSymbols4", overlayLayer = "specialSymbolsSetDragSelector1"),
        null,
        null,
        null,
        null,

        // Row 3
        Button(previewText = "@", inputText = "@", referenceLayerName = "specialSymbols1", overlayLayer = "specialSymbolsSetDragSelector1"),
        Button(previewText = "Ø", inputText = "Ø", referenceLayerName = "specialLetters1", overlayLayer = "specialSymbolsSetDragSelector1"),
        null,
        null,
        null,
        null,

        // Row 4
        Button(previewText = "1", inputText = "1", referenceLayerName = "numpad1", overlayLayer = "specialSymbolsSetDragSelector1"),
        Button(previewText = "[ ]", inputText = "[]", referenceLayerName = "brackets1", overlayLayer = "specialSymbolsSetDragSelector1"),
        null,
        null,
        null,
    )),
    Layer("specialSymbolsSetDragSelector2",listOf<Button?>(

        // Row 1
        Button(overlayLayer = "specialSymbolsSetDragSelector2"),
        Button(overlayLayer = "specialSymbolsSetDragSelector2"),
        null,
        null,
        null,
        null,

        // Row 2
        Button(overlayLayer = "specialSymbolsSetDragSelector2"),
        Button(overlayLayer = "specialSymbolsSetDragSelector2"),
        null,
        null,
        null,
        null,

        // Row 3
        Button(overlayLayer = "specialSymbolsSetDragSelector2"),
        Button(overlayLayer = "specialSymbolsSetDragSelector2"),
        null,
        null,
        null,
        null,

        // Row 4
        Button(previewText = "Ø", inputText = "Ø", referenceLayerName = "specialLettersCapital1", overlayLayer = "specialSymbolsSetDragSelector2"),
        null,
        null,
        null,
        null,
        null,
    )),

    // Numpad

    Layer("numpad1",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(previewText = "+", inputText = "+", referenceLayerName = "numpad2"),
        Button(previewText = "1", inputText = "1", referenceLayerName = "numpad2", stackIndicator = true),
        Button(previewText = "2", inputText = "2", referenceLayerName = "numpad2", stackIndicator = true),
        Button(previewText = "3", inputText = "3", referenceLayerName = "numpad2", stackIndicator = true),

        // Row 2
        Button(),
        Button(),
        Button(previewText = "-", inputText = "-", referenceLayerName = "numpad2"),
        Button(previewText = "4", inputText = "4", referenceLayerName = "numpad2", stackIndicator = true),
        Button(previewText = "5", inputText = "5", referenceLayerName = "numpad2", stackIndicator = true),
        Button(previewText = "6", inputText = "6", referenceLayerName = "numpad2", stackIndicator = true),

        // Row 3
        Button(),
        Button(),
        Button(previewText = "/", inputText = "/", referenceLayerName = "numpad2"),
        Button(previewText = "7", inputText = "7", referenceLayerName = "numpad2", stackIndicator = true),
        Button(previewText = "8", inputText = "8", referenceLayerName = "numpad2", stackIndicator = true),
        Button(previewText = "9", inputText = "9", referenceLayerName = "numpad2", stackIndicator = true),

        // Row 4
        Button(),
        Button(),
        Button(previewText = "=", inputText = "=", referenceLayerName = "numpad2"),
        Button(previewText = "*", inputText = "*", referenceLayerName = "numpad2"),
        Button(previewText = "0", inputText = "0", referenceLayerName = "numpad2", stackIndicator = true),
        Button(previewText = "#", inputText = "#", referenceLayerName = "numpad2"),
    )),

    Layer("numpad2",listOf<Button>(

        // Row 1
        Button(previewText = "(", inputText = "("),
        Button(previewText = ")", inputText = ")"),
        Button(previewText = "±", inputText = "±"),
        Button(previewText = "¹", inputText = "¹"),
        Button(previewText = "²", inputText = "²"),
        Button(previewText = "³", inputText = "³"),

        // Row 2
        Button(previewText = ".", inputText = "."),
        Button(previewText = ",", inputText = ","),
        Button(previewText = "%", inputText = "%"),
        Button(previewText = "⁴", inputText = "⁴"),
        Button(previewText = "⁵", inputText = "⁵"),
        Button(previewText = "⁶", inputText = "⁶"),

        // Row 3
        Button(previewText = "!", inputText = "!"),
        Button(previewText = "f", inputText = "f"),
        Button(previewText = "÷", inputText = "÷"),
        Button(previewText = "⁷", inputText = "⁷"),
        Button(previewText = "⁸", inputText = "⁸"),
        Button(previewText = "⁹", inputText = "⁹"),

        // Row 4
        Button(previewText = "π", inputText = "π"),
        Button(previewText = "∞", inputText = "∞"),
        Button(previewText = "√", inputText = "√"),
        Button(previewText = "ⁿ", inputText = "ⁿ"),
        Button(previewText = "⁰", inputText = "⁰"),
        Button(previewText = "°", inputText = "°"),

        )),

    // Brackets

    Layer("brackets1",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(previewText = "[", inputText = "["),
        Button(previewText = "]", inputText = "]"),
        Button(previewText = "{", inputText = "{"),
        Button(previewText = "}", inputText = "}"),

        // Row 2
        Button(),
        Button(),
        Button(previewText = "(", inputText = "("),
        Button(previewText = ")", inputText = ")"),
        Button(previewText = "<", inputText = "<"),
        Button(previewText = ">", inputText = ">"),

        // Row 3
        Button(),
        Button(),
        Button(previewText = "\"", inputText = "\""),
        Button(previewText = "'", inputText = "'"),
        Button(previewText = "‹", inputText = "‹"),
        Button(previewText = "›", inputText = "›"),

        // Row 4
        Button(),
        Button(),
        Button(previewText = "«", inputText = "«"),
        Button(previewText = "»", inputText = "»"),
        Button(previewText = "‚", inputText = "‚"),
        Button(previewText = "‘", inputText = "‘"),
    )),

    // Special letters

    Layer("specialLetters1",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(previewText = "ø", inputText = "ø", referenceLayerName = "variantsOfO1", stackIndicator = true),
        Button(previewText = "æ", inputText = "æ", referenceLayerName = "variantsOfA1", stackIndicator = true),
        Button(previewText = "œ", inputText = "œ", referenceLayerName = "variantsOfO1", stackIndicator = true),
        Button(previewText = "Ω", inputText = "Ω"),

        // Row 2
        Button(),
        Button(),
        Button(previewText = "Π", inputText = "Π"),
        Button(previewText = "ā", inputText = "ā", referenceLayerName = "variantsOfA1", stackIndicator = true),
        Button(previewText = "ō", inputText = "ō", referenceLayerName = "variantsOfO1", stackIndicator = true),
        Button(previewText = "ē", inputText = "ē", referenceLayerName = "variantsOfE1", stackIndicator = true),

        // Row 3
        Button(),
        Button(),
        Button(previewText = "π", inputText = "π"),
        Button(previewText = "ū", inputText = "ū", referenceLayerName = "variantsOfU1", stackIndicator = true),
        Button(previewText = "ī", inputText = "ī", referenceLayerName = "variantsOfI1", stackIndicator = true),
        Button(previewText = "ñ", inputText = "ñ"),

        // Row 4
        Button(),
        Button(),
        Button(previewText = "μ", inputText = "μ"),
        Button(previewText = "ß", inputText = "ß"),
        Button(previewText = "ç", inputText = "ç"),
        Button(previewText = "", inputText = ""),
    )),

    Layer("specialLettersCapital1",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(previewText = "Ø", inputText = "Ø", referenceLayerName = "variantsOfOCapital1", stackIndicator = true),
        Button(previewText = "Æ", inputText = "Æ", referenceLayerName = "variantsOfACapital1", stackIndicator = true),
        Button(previewText = "Œ", inputText = "Œ", referenceLayerName = "variantsOfOCapital1", stackIndicator = true),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(),
        Button(),
        Button(previewText = "", inputText = ""),
        Button(previewText = "Ã", inputText = "Ã", referenceLayerName = "variantsOfACapital1", stackIndicator = true),
        Button(previewText = "Ō", inputText = "Ō", referenceLayerName = "variantsOfOCapital1", stackIndicator = true),
        Button(previewText = "Ē", inputText = "Ē", referenceLayerName = "variantsOfECapital1", stackIndicator = true),

        // Row 3
        Button(),
        Button(),
        Button(previewText = "", inputText = ""),
        Button(previewText = "Ū", inputText = "Ū", referenceLayerName = "variantsOfUCapital1", stackIndicator = true),
        Button(previewText = "Ī", inputText = "Ī", referenceLayerName = "variantsOfICapital1", stackIndicator = true),
        Button(previewText = "Ñ", inputText = "Ñ"),

        // Row 4
        Button(),
        Button(),
        Button(previewText = "", inputText = ""),
        Button(previewText = "ẞ", inputText = "ẞ"),
        Button(previewText = "Ç", inputText = "Ç"),
        Button(previewText = "", inputText = ""),
    )),

    Layer("currencies1",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(previewText = "", inputText = ""),
        Button(previewText = "$", inputText = "$"),
        Button(previewText = "£", inputText = "£"),
        Button(previewText = "¢", inputText = "¢"),

        // Row 2
        Button(),
        Button(),
        Button(previewText = "", inputText = ""),
        Button(previewText = "₹", inputText = "₹"),
        Button(previewText = "¥", inputText = "¥"),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(),
        Button(),
        Button(previewText = "", inputText = ""),
        Button(previewText = "₱", inputText = "₱"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(),
        Button(),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),

    Layer("variantsOfA1",listOf<Button>(

        // Row 1
        Button(previewText = "æ", inputText = "æ"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(previewText = "ã", inputText = "ã"),
        Button(previewText = "ā", inputText = "ā"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(previewText = "à", inputText = "à"),
        Button(previewText = "á", inputText = "á"),
        Button(previewText = "å", inputText = "å"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(previewText = "â", inputText = "â"),
        Button(previewText = "ä", inputText = "ä"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),
    Layer("variantsOfACapital1",listOf<Button>(

        // Row 1
        Button(previewText = "Æ", inputText = "Æ"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(previewText = "Ã", inputText = "Ã"),
        Button(previewText = "Ā", inputText = "Ā"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(previewText = "À", inputText = "À"),
        Button(previewText = "Á", inputText = "Á"),
        Button(previewText = "Å", inputText = "Å"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(previewText = "Â", inputText = "Â"),
        Button(previewText = "Ä", inputText = "Ä"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),
    Layer("variantsOfE1",listOf<Button>(

        // Row 1
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(previewText = "", inputText = ""),
        Button(previewText = "ē", inputText = "ē"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(previewText = "è", inputText = "è"),
        Button(previewText = "é", inputText = "é"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(previewText = "ê", inputText = "ê"),
        Button(previewText = "ë", inputText = "ë"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),
    Layer("variantsOfECapital1",listOf<Button>(

        // Row 1
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(previewText = "", inputText = ""),
        Button(previewText = "Ē", inputText = "Ē"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(previewText = "È", inputText = "È"),
        Button(previewText = "É", inputText = "É"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(previewText = "Ê", inputText = "Ê"),
        Button(previewText = "Ë", inputText = "Ë"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),
    Layer("variantsOfO1",listOf<Button>(

        // Row 1
        Button(previewText = "œ", inputText = "œ"),
        Button(previewText = "ø", inputText = "ø"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(previewText = "", inputText = ""),
        Button(previewText = "ō", inputText = "ō"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(previewText = "ò", inputText = "ò"),
        Button(previewText = "ó", inputText = "ó"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(previewText = "ô", inputText = "ô"),
        Button(previewText = "ö", inputText = "ö"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),
    Layer("variantsOfOCapital1",listOf<Button>(

        // Row 1
        Button(previewText = "Œ", inputText = "Œ"),
        Button(previewText = "Ø", inputText = "Ø"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(previewText = "", inputText = ""),
        Button(previewText = "Ō", inputText = "Ō"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(previewText = "Ò", inputText = "Ò"),
        Button(previewText = "Ó", inputText = "Ó"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(previewText = "Ô", inputText = "Ô"),
        Button(previewText = "Ö", inputText = "Ö"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),
    Layer("variantsOfU1",listOf<Button>(

        // Row 1
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(previewText = "", inputText = ""),
        Button(previewText = "ū", inputText = "ū"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(previewText = "ù", inputText = "ù"),
        Button(previewText = "ú", inputText = "ú"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(previewText = "û", inputText = "û"),
        Button(previewText = "ü", inputText = "ü"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),
    Layer("variantsOfUCapital1",listOf<Button>(

        // Row 1
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(previewText = "", inputText = ""),
        Button(previewText = "Ū", inputText = "Ū"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(previewText = "Ù", inputText = "Ù"),
        Button(previewText = "Ú", inputText = "Ú"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(previewText = "Û", inputText = "Û"),
        Button(previewText = "Ü", inputText = "Ü"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),
    Layer("variantsOfI1",listOf<Button>(

        // Row 1
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(previewText = "", inputText = ""),
        Button(previewText = "ī", inputText = "ī"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(previewText = "ì", inputText = "ì"),
        Button(previewText = "í", inputText = "í"),
        Button(previewText = "i", inputText = "i"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(previewText = "î", inputText = "î"),
        Button(previewText = "ï", inputText = "ï"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),
    Layer("variantsOfICapital1",listOf<Button>(

        // Row 1
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 2
        Button(previewText = "", inputText = ""),
        Button(previewText = "Ī", inputText = "Ī"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 3
        Button(previewText = "Ì", inputText = "Ì"),
        Button(previewText = "Í", inputText = "Í"),
        Button(previewText = "I", inputText = "I"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),

        // Row 4
        Button(previewText = "Î", inputText = "Î"),
        Button(previewText = "Ï", inputText = "Ï"),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
        Button(previewText = "", inputText = ""),
    )),

    // Special symbols

    Layer("specialSymbols1",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(previewText = "@", inputText = "@"),
        Button(previewText = "$", inputText = "$", referenceLayerName = "currencies1", stackIndicator = true),
        Button(previewText = "&", inputText = "&"),
        Button(previewText = "•", inputText = "•"),

        // Row 2
        Button(),
        Button(),
        Button(previewText = "!", inputText = "!"),
        Button(previewText = "?", inputText = "?"),
        Button(previewText = "/", inputText = "/"),
        Button(previewText = ":/", inputText = "https://"),

        // Row 3
        Button(),
        Button(),
        Button(previewText = ",", inputText = ","),
        Button(previewText = ".", inputText = "."),
        Button(previewText = ":", inputText = ":"),
        Button(previewText = ";", inputText = ";"),

        // Row 4
        Button(),
        Button(),
        Button(previewText = "✓", inputText = "✓"),
        Button(previewText = "×", inputText = "×"),
        Button(previewText = "\\", inputText = "\\"),
        Button(previewText = "=", inputText = "="),
    )),
    Layer("specialSymbols2",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(previewText = "~", inputText = "~"),
        Button(previewText = "§", inputText = "§"),
        Button(previewText = "№", inputText = "№"),
        Button(previewText = "·", inputText = "·"),

        // Row 2
        Button(),
        Button(),
        Button(previewText = "¡", inputText = "¡"),
        Button(previewText = "¿", inputText = "¿"),
        Button(previewText = "‽", inputText = "‽"),
        Button(previewText = "±", inputText = "±"),

        // Row 3
        Button(),
        Button(),
        Button(previewText = "←", inputText = "←"),
        Button(previewText = "→", inputText = "→"),
        Button(previewText = "%", inputText = "%"),
        Button(previewText = "_", inputText = "_"),

        // Row 4
        Button(),
        Button(),
        Button(previewText = "↑", inputText = "↑"),
        Button(previewText = "↓", inputText = "↓"),
        Button(previewText = "∞", inputText = "∞"),
        Button(previewText = "≈", inputText = "≈"),
    )),
    Layer("specialSymbols3",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(previewText = "★", inputText = "★"),
        Button(previewText = "¶", inputText = "¶"),
        Button(previewText = "♪", inputText = "♪"),
        Button(previewText = "°", inputText = "°"),

        // Row 2
        Button(),
        Button(),
        Button(previewText = "^", inputText = "^"),
        Button(previewText = "∅", inputText = "∅"),
        Button(previewText = "√", inputText = "√"),
        Button(previewText = "|", inputText = "|"),

        // Row 3
        Button(),
        Button(),
        Button(previewText = "Ⓤ", inputText = "Ⓤ"),
        Button(previewText = "™", inputText = "™"),
        Button(previewText = "÷", inputText = "÷"),
        Button(previewText = "—", inputText = "—"),

        // Row 4
        Button(),
        Button(),
        Button(previewText = "©", inputText = "©"),
        Button(previewText = "®", inputText = "®"),
        Button(previewText = "ت", inputText = "ت"),
        Button(previewText = "≠", inputText = "≠"),
    )),
    Layer("specialSymbols4",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(previewText = "╱", inputText = "╱"),
        Button(previewText = "╲", inputText = "╲"),
        Button(previewText = "╳", inputText = "╳"),
        Button(previewText = "`", inputText = "`"),

        // Row 2
        Button(),
        Button(),
        Button(previewText = "ᯥ", inputText = "ᯥ"),
        Button(previewText = "∑", inputText = "∑"),
        Button(previewText = "∆", inputText = "∆"),
        Button(previewText = "∇", inputText = "∇"),

        // Row 3
        Button(),
        Button(),
        Button(previewText = "✦", inputText = "✦"),
        Button(previewText = "•", inputText = "•"),
        Button(previewText = "ⁿ", inputText = "ⁿ"),
        Button(previewText = "–", inputText = "–"),

        // Row 4
        Button(),
        Button(),
        Button(previewText = "☀", inputText = "☀"),
        Button(previewText = "☁", inputText = "☁"),
        Button(previewText = "‰", inputText = "‰"),
        Button(previewText = "℅", inputText = "℅"),

    )),
    Layer("specialSymbols5",listOf<Button>(

        // Row 1
        Button(),
        Button(),
        Button(previewText = "✿", inputText = "✿"),
        Button(previewText = "❄", inputText = "❄"),
        Button(previewText = "‣", inputText = "‣"),
        Button(previewText = "⇄", inputText = "⇄"),

        // Row 2
        Button(),
        Button(),
        Button(previewText = "⚡", inputText = "⚡"),
        Button(previewText = "☮", inputText = "☮"),
        Button(previewText = "⚒", inputText = "⚒"),
        Button(previewText = "☭", inputText = "☭"),

        // Row 3
        Button(),
        Button(),
        Button(previewText = "●", inputText = "●"),
        Button(previewText = "▲", inputText = "▲"),
        Button(previewText = "▼", inputText = "▼"),
        Button(previewText = "◼", inputText = "◼"),

        // Row 4
        Button(),
        Button(),
        Button(previewText = "☽", inputText = "☽"),
        Button(previewText = "☾", inputText = "☾"),
        Button(previewText = "ツ", inputText = "ツ"),
        Button(previewText = "☄", inputText = "☄"),
    )),
))