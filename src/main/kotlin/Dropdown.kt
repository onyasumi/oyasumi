import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow


@Suppress("unused")
class DropdownItem<T>(val value: T, val label: String = value.toString()) {

    override fun toString(): String {
        return label
    }

}


@Composable
@JvmName("DropdownTDropdownItem")
@Suppress("unused")
fun <T> Dropdown(
    items: List<DropdownItem<T>>,
    selectedItem: MutableStateFlow<DropdownItem<T>>,
    modifier: Modifier = Modifier.padding(20.dp),
    onValueChange: (DropdownItem<T>) -> Unit = {},
    label: String = "",
    showArrow: Boolean = true)
{

    val selected by selectedItem.collectAsState()

    Dropdown(
        items = items,
        selectedLabel = MutableStateFlow(selected.label),
        modifier = modifier,
        onValueChange = {
            selectedItem.value = it
            onValueChange(it)
        },
        label = label,
        showArrow = showArrow
    )

}


@Composable
@JvmName("DropdownT")
@Suppress("unused")
fun <T> Dropdown(
    items: List<DropdownItem<T>>,
    selectedValue: MutableStateFlow<T>,
    modifier: Modifier = Modifier.padding(20.dp),
    onValueChange: (DropdownItem<T>) -> Unit = {},
    label: String = "",
    showArrow: Boolean = true)
{

    val selected by selectedValue.collectAsState()

    Dropdown(
        items = items,
        selectedLabel = MutableStateFlow(selected.toString()),
        modifier = modifier,
        onValueChange = {
            selectedValue.value = it.value
            onValueChange(it)
        },
        label = label,
        showArrow = showArrow
    )

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
@JvmName("DropdownTString")
@Suppress("unused")
fun <T> Dropdown(
    items: List<DropdownItem<T>>,
    selectedLabel: MutableStateFlow<String>,
    modifier: Modifier = Modifier.padding(20.dp),
    onValueChange: (DropdownItem<T>) -> Unit = {},
    label: String = "",
    showArrow: Boolean = true)
{

    var expanded by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    val selected by selectedLabel.collectAsState("")

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = modifier
        ) {
            OutlinedTextField(
                value = selected,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(label) },
                readOnly = true,
                onValueChange = {},
                isError = error,
                trailingIcon = { if(showArrow) ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    error = (selected == "")
                    expanded = false
                }
            ) {
                items.forEach {
                    DropdownMenuItem(
                        onClick = {
                            selectedLabel.value = it.label
                            onValueChange(it)
                            error = false
                            expanded = false
                        }
                    ) {
                        Text(it.label)
                    }
                }
            }
        }
    }


}


@Preview
@Composable
@Suppress("unused")
fun DropdownPreview() {
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {

        val selected: MutableStateFlow<String> = remember { MutableStateFlow("") }
        val selectedState by selected.collectAsState("")

        Column {
            Dropdown(
                items = listOf(
                    DropdownItem("Tokyo"),
                    DropdownItem("Shinjuku"),
                    DropdownItem("Shinagawa"),
                    DropdownItem("Ikebukuro")
                ),
                selectedLabel = selected,
                label = "Select a station"
            )

            Text(
                text = "You selected: ${if(selectedState == "") "Nothing" else selectedState}",
                color = Color.DarkGray,
                fontSize = 16.sp,
                modifier = Modifier.padding(25.dp)
            )
        }

    }
}