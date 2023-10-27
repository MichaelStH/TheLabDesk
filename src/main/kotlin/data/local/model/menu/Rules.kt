package data.local.model.menu

import core.log.Timber
import kotools.types.collection.NotEmptySet
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/*
Toolbar = Set<Menu>
Menu = Label + NotEmptySet<MenuOption>
MenuOption = Label + (Action | NotEmptySet<MenuOption>)
ActionableOption = Label + Action
Label = NotBlankString
Action = () -> Unit
 */

private fun main() {
    MenuToolbar(
        Menu(
            label = "File".toNotBlankString().getOrThrow(),
            RunnableMenuOption("New Window".toNotBlankString().getOrThrow()) {
                Timber.d("onClick() : New Window")
            },
            RunnableMenuOption("Exit".toNotBlankString().getOrThrow()) {
                Timber.d("onClick(): Exit")
            }
        ),
        Menu(
            label = "Help".toNotBlankString().getOrThrow(),
            RunnableMenuOption("About".toNotBlankString().getOrThrow()) {
                Timber.d("Click on 'About'.")
            }
        )
    )
}

fun MenuToolbar(vararg menus: Menu): MenuToolbar {
    TODO()
}

interface MenuToolbar {
    val menus: Set<Menu>
}

sealed interface MenuOption {
    val label: NotBlankString
}

fun Menu(
    label: NotBlankString,
    option: MenuOption,
    vararg otherOptions: MenuOption
): Menu {
    TODO()
}

interface Menu : MenuOption {
    val options: NotEmptySet<MenuOption>
}

fun RunnableMenuOption(
    label: NotBlankString,
    action: () -> Unit
): RunnableMenuOption {
    TODO()
}

// 2 actionable options are equals if there have the same label.
interface RunnableMenuOption : MenuOption {
    operator fun invoke()
}
