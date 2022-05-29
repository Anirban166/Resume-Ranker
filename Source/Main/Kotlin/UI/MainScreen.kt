package ui
import java.io.File
import androidx.compose.material.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

@Composable
fun MainScreen(keywords: String, updateKeywords: (String) -> Unit, directory: String, updateDirectory: (File?) -> Unit,
outputDirectory: String, updateOutputDirectory: (File?) -> Unit, onButtonClick: () -> Unit, textAreaText: String) 
{
    Surface(modifier = Modifier.fillMaxSize())
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally)
        {
            KeywordTextField(keywords, updateKeywords)
            DirectoryTextField(directory, updateDirectory, "Directory", "Pick source directory")
            DirectoryTextField(outputDirectory, updateOutputDirectory, "Output Directory", "Pick output directory")
            Button(onClick = onButtonClick, enabled = keywords.isNotEmpty() && outputDirectory.isNotEmpty() && directory.isNotEmpty(), modifier = Modifier.padding(5.dp))
            {
                Text("Display Results")
            }
            TextArea(textAreaText)
        }
    }
}

@Composable
fun KeywordTextField(keywords: String, updateKeywords: (String) -> Unit)
{
    Surface(Modifier.fillMaxWidth().defaultMinSize(minHeight = 50.dp))
    {
        TextField(value = keywords, onValueChange = { updateKeywords(it) },
            placeholder = { Text("Keywords") }, label = { Text(text = "Enter keywords") },
            singleLine = true, leadingIcon =
            {
                Icon(painter = painterResource("keyword-icon.png"), "ThumbUp")
            }
        )
    }
}

@Composable
fun DirectoryTextField(dir: String, update: (File?) -> Unit, placeholderText: String, labelText: String)
{
    Surface(Modifier.fillMaxWidth().defaultMinSize(minHeight = 50.dp))
    {
        TextField(value = dir, onValueChange = { }, placeholder = { Text(placeholderText) },
            label = { Text(text = labelText) }, singleLine = true, leadingIcon =
            {
                Icon(painter = painterResource("path-icon.png"), null)
            },
            trailingIcon =
            {
                IconButton(onClick =
                {
                    val dir = fileChooser()
                    update(dir)
                }, enabled = true)
                {
                    Icon(painter = painterResource("folder-icon.png"), contentDescription = "FilePickerIcon")
                }
            }
        )
    }
}

@Composable
fun TextArea(text: String)
{
    val scrollState = rememberScrollState(0)
    LaunchedEffect(scrollState.maxValue)
    {
        scrollState.animateScrollTo(scrollState.maxValue)
    }
    Surface(modifier = Modifier.fillMaxSize().padding(5.dp))
    {
        TextField(value = text, onValueChange = {}, modifier = Modifier.fillMaxSize().verticalScroll(scrollState))
    }
}