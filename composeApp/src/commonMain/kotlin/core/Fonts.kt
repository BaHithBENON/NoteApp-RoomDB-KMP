package core

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import noteapproomdb.composeapp.generated.resources.*
import noteapproomdb.composeapp.generated.resources.Res
import noteapproomdb.composeapp.generated.resources.SpaceMono_Bold
import noteapproomdb.composeapp.generated.resources.SpaceMono_Italic
import org.jetbrains.compose.resources.Font


@Composable
fun SpaceMonoFamily() = FontFamily(
    Font(Res.font.SpaceMono_Bold, FontWeight.Bold),
    Font(Res.font.SpaceMono_Italic, FontWeight.Light),
    Font(Res.font.SpaceMono_Regular, FontWeight.Normal)
)


@Composable
fun PoppinsFamily() = FontFamily(
    Font(Res.font.Poppins_Bold, style = FontStyle.Italic, weight = FontWeight.Bold),
    Font(Res.font.Poppins_Italic, style = FontStyle.Italic, weight = FontWeight.Light),
    Font(Res.font.Poppins_Light, style = FontStyle.Normal, weight = FontWeight.SemiBold),
    Font(Res.font.Poppins_Medium, style = FontStyle.Normal, weight = FontWeight.Medium),
    Font(Res.font.Poppins_Regular, style = FontStyle.Normal, weight = FontWeight.Normal)
)

