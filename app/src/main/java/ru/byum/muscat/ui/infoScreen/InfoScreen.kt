package ru.byum.muscat.ui.infoScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.byum.muscat.R


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoScreen (modifier: Modifier = Modifier, navController: NavController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier.width(96.dp), onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Filled.ArrowBack,
                modifier = Modifier.size(128.dp),
                contentDescription = "Back"
            )
        }
    }

    Column(
        modifier
            .fillMaxSize()
            .padding(top = 25.dp),
        //verticalArrangement = Arrangement.Center,
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier
                .fillMaxWidth()
                .padding(),
            //horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier.padding()) {


                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            Column() {
                    Text(
                        text = buildAnnotatedString {
                            append(
                                AnnotatedString("Search", spanStyle = SpanStyle(Color(133, 8, 8),
                                    fontWeight = FontWeight.Bold)),
                            )

                            append(" - поиск информации об исполнителе или альбоме.\n" +
                                    "С помощью меню справа выберите параметр поиска: Artist или Release. Введите\n" +
                            "запрос и нажмите ввод.\n" +
                            "С помощью кнопки + можно добавлять элементы в созданные ранее папки.")
                        },
                        color = Color(0, 12, 120), fontSize = 20.sp,
                        style = androidx.compose.ui.text.TextStyle(fontStyle = FontStyle.Italic),
                        //modifier = Modifier.padding(2.dp)
                    )
            }
        }

        Row(
            modifier
                .fillMaxWidth()
                .padding(),
            //horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier.padding()) {


                Icon(
                    painter = painterResource(id = R.drawable.folder_icon),
                    contentDescription = "Folders"
                )
            }
            Column() {

                Text(
                    text = buildAnnotatedString {
                        append(
                            AnnotatedString("Folders", spanStyle = SpanStyle(Color(133, 8, 8),
                                fontWeight = FontWeight.Bold)),
                        )

                        append(" - список персональных папок.\n" +
                                "Чтобы создать папку, нажмите +, введите название папки,\n" +
                                "выберите тип папки Artist или Release и нажмите 'Создать папку'.\n" +
                                "Чтобы удалить папку, нажмите значок корзины справа от ее названия.")
                    },
                    color = Color(0, 12, 120), fontSize = 20.sp,
                    style = androidx.compose.ui.text.TextStyle(fontStyle = FontStyle.Italic),
                    //modifier = Modifier.padding(2.dp)
                )
            }

        }


        Row(
            modifier
                .fillMaxWidth()
                .padding(),
            //horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier.padding()) {
                Icon(
                    painter = painterResource(id = R.drawable.stats_icon),
                    contentDescription = "Stats"
                )
            }
            Column() {

                Text(
                    text = buildAnnotatedString {
                        append(
                            AnnotatedString("Stats", spanStyle = SpanStyle(Color(133, 8, 8),
                                fontWeight = FontWeight.Bold)),
                        )

                        append(" - статистика рейтинга и папок пользователя.\n"
                                )
                    },
                    color = Color(0, 12, 120), fontSize = 20.sp,
                    style = androidx.compose.ui.text.TextStyle(fontStyle = FontStyle.Italic),
                    //modifier = Modifier.padding(2.dp)
                )
            }
        }


        Row(
            modifier
                .fillMaxWidth()
                .padding(),
            //horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier.padding()) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = "Info"
                )
            }
            Column() {

                Text(
                    text = buildAnnotatedString {
                        append(
                            AnnotatedString("Info", spanStyle = SpanStyle(Color(133, 8, 8),
                                fontWeight = FontWeight.Bold)),
                        )

                        append(" - общая информация для пользователя.")
                    },
                    color = Color(0, 12, 120), fontSize = 20.sp,
                    style = androidx.compose.ui.text.TextStyle(fontStyle = FontStyle.Italic),
                    //modifier = Modifier.padding(2.dp)
                )
            }
        }
    }
}


