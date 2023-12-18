package cn.edu.ncu.liuzehao.moviet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cn.edu.ncu.liuzehao.moviet.bridge.NavigationHelper
import cn.edu.ncu.liuzehao.moviet.bridge.ReactModel
import cn.edu.ncu.liuzehao.moviet.bridge.YourBridgeImpl
import cn.edu.ncu.liuzehao.moviet.ui.theme.MovietTheme
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel

val navControllers = mutableListOf<NavHostController>()

@Route(path = "/compose/movies")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        ARouter.getInstance().inject(this)

        super.onCreate(savedInstanceState)
        setContent {
            NativePage()
        }
    }
}

@Composable
fun NativePage() {
    val navController = rememberNavController()
    NavigationHelper.add("homePage", navController)
    NavHost(navController = navController, startDestination = "homePage") {
        composable("homePage") {
            MovietTheme {
                var movieType by remember { mutableStateOf(0) }
                Column {
                    Head()
                    HeadButtons(
                        romance = { movieType = 0 },
                        action = { movieType = 1 },
                        comedy = { movieType = 2 },
                        scifi = { movieType = 3 },
                        modifier = Modifier
                            .padding(horizontal = 14.dp)
                            .height(41.dp)
                            .width(101.dp),
                    )
                    MovieList(
                        movies = when (movieType) {
                            0 -> Data.getRomanticItems()
                            1 -> Data.getActionItems()
                            2 -> Data.getComedyItems()
                            3 -> Data.getScifictionItems()
                            else -> Data.getRomanticItems()
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun Head() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        }
        Text(
            text = "天堂电影院",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif,
            )
        )
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TempView() {
    MovietTheme {
        var movieType by remember { mutableStateOf(0) }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            Column {
                Head()
                HeadButtons(
                    romance = { movieType = 0 },
                    action = { movieType = 1 },
                    comedy = { movieType = 2 },
                    scifi = { movieType = 3 },
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .height(41.dp)
                        .width(101.dp),
                )
                MovieList(
                    movies = when (movieType) {
                        0 -> Data.getRomanticItems()
                        1 -> Data.getActionItems()
                        2 -> Data.getComedyItems()
                        3 -> Data.getScifictionItems()
                        else -> Data.getRomanticItems()
                    }
                )
            }
        }
    }
}

/**
 * Head buttons
 *
 * @param romance
 * @param action
 * @param comedy
 * @param modifier
 * @receiver
 * @receiver
 * @receiver
 */
@Composable
fun HeadButtons(
    romance: () -> Unit,
    action: () -> Unit,
    comedy: () -> Unit,
    scifi: () -> Unit,
    modifier: Modifier
) {
    val topButtonColor = Color(0xff3d3bee)
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 27.dp)
    ) {
        Button(
            onClick = romance,
            modifier = modifier,
            elevation = ButtonDefaults.elevation(30.dp, 40.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = topButtonColor,
                contentColor = Color.White
            )
        ) {
            Text(text = "浪漫")
        }
        Button(
            onClick = action,
            modifier = modifier,
            elevation = ButtonDefaults.elevation(30.dp, 40.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = topButtonColor,
                contentColor = Color.White
            )
        ) {
            Text(text = "动作")
        }
        Button(
            onClick = comedy,
            modifier = modifier,
            elevation = ButtonDefaults.elevation(30.dp, 40.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = topButtonColor,
                contentColor = Color.White
            )
        ) {
            Text(text = "喜剧")
        }
        Button(
            onClick = scifi,
            modifier = modifier,
            elevation = ButtonDefaults.elevation(30.dp, 40.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = topButtonColor,
                contentColor = Color.White
            )
        ) {
            Text(text = "科幻")
        }
    }
}


@Composable
fun MovieItem(imageId: Int, movieTitle: String, movieClasses: List<String>, score: Double) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .background(Color.Transparent),
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(120.dp)
                .background(Color.Transparent)
                .clickable {
                    YourBridgeImpl.instance!!.fire(
                        "onRefresh",
                        movieTitle,
                        object : MethodChannel.Result {
                            override fun success(result: Any?) {
                                Toast
                                    .makeText(context, result as String, Toast.LENGTH_LONG)
                                    .show()
                            }

                            override fun error(
                                errorCode: String,
                                errorMessage: String?,
                                errorDetails: Any?
                            ) {
                                Toast
                                    .makeText(context, errorMessage, Toast.LENGTH_LONG)
                                    .show()
                            }

                            override fun notImplemented() {
                                TODO("Not yet implemented")
                            }
                        })
                    if (score > 9.0) {
                        context.startActivity(
                            FlutterActivity
                                .withCachedEngine("main")
                                .build(context)
                        )
                    } else {
                        Log.d("goToNative", "${NavigationHelper.model}")
                        if (NavigationHelper.model) {
                            context.startActivity(
                                Intent(
                                    context,
                                    ReactNativeActivity::class.java
                                )
                            )
                        }
                    }
                }
        ) {
            Row(
                modifier = Modifier
                    .width(382.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                // 用来给图片占位置
                Surface(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .width(111.dp),
                ) { }
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(130.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = movieTitle,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = movieClasses.joinToString("|"),
                        style = TextStyle(
                            fontWeight = FontWeight.Light,
                            color = Color.LightGray
                        ),
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text(
                            text = "ME: ", style = TextStyle(
                                color = Color.LightGray
                            )
                        )
                        Text(text = score.toString())
                    }
                }
            }
        }
        Box {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = null,
                modifier = Modifier
                    .width(111.dp)
                    .height(140.dp)
                    .padding(10.dp)
                    .clip(
                        RoundedCornerShape(CornerSize(15))
                    )
            )
        }
    }
}


@Composable
fun MovieList(movies: List<Movie>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(movies) {
            Surface(
                modifier = Modifier
                    .padding(vertical = 9.dp)
                    .shadow(
                        elevation = 50.dp,
                    ),
                color = Color.Transparent
//                shape = RoundedCornerShape(40f)
            ) {
                MovieItem(
                    imageId = it.imageId,
                    movieTitle = it.movieTitle,
                    movieClasses = it.movieClasses,
                    score = it.score
                )
            }
        }
    }
}
