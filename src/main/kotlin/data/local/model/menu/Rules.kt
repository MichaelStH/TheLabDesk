package data.local.model.menu

import core.log.Timber
import kotools.types.collection.NotEmptySet
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/*
Toolbar = Set<Menu>
MenuOption = Menu | RunnableMenuOption
Menu = MenuOptionLabel + NotEmptySet<MenuOption>
MenuLabel = "File" | "Help"
RunnableMenuOption = MenuOptionLabel + Action
RunnableMenuOptionLabel = "New Window" | "Exit" | "About"
Action = () -> Unit
 */

private fun main() {
    MenuToolbar(
        Menu(
            MenuOptionLabel.File,
            RunnableMenuOption(MenuOptionLabel.NewWindow) {
                Timber.d("onClick() : New Window")
            },
            RunnableMenuOption(MenuOptionLabel.Exit) {
                Timber.d("onClick(): Exit")
            }
        ),
        Menu(
            MenuOptionLabel.Help,
            RunnableMenuOption(MenuOptionLabel.About) {
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
    val label: MenuOptionLabel
}

enum class MenuOptionLabel(private val value: String) {
    About("About"),
    Exit("Exit"),
    File("File"),
    Help("Help"),
    NewWindow("New Window");

    fun toNotBlankString(): NotBlankString = value.toNotBlankString()
        .getOrNull()
        ?: error("Converting '$value' to NotBlankString shouldn't fail.")

    override fun toString(): String = value
}

fun Menu(
    label: MenuOptionLabel,
    option: MenuOption,
    vararg otherOptions: MenuOption
): Menu {
    TODO()
}

interface Menu : MenuOption {
    val options: NotEmptySet<MenuOption>
}

fun RunnableMenuOption(
    label: MenuOptionLabel,
    action: () -> Unit
): RunnableMenuOption {
    TODO()
}

// 2 actionable options are equals if there have the same label.
interface RunnableMenuOption : MenuOption {
    operator fun invoke()
}
