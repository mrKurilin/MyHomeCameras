package ru.mrkurilin.myhomecameras.presentation.camerasScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import ru.mrkurilin.myhomecameras.R
import ru.mrkurilin.myhomecameras.presentation.camerasScreen.model.CameraUiModel

@Composable
fun CameraCard(
    cameraUiModel: CameraUiModel,
) {
    ConstraintLayout(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        val (photo, playButton, recImage, favoriteImage, name) = createRefs()

        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(cameraUiModel.snapshot)
                .size(coil.size.Size.ORIGINAL)
                .build()
        )
        val painterState = painter.state

        Image(
            painter = painter,
            contentScale = ContentScale.FillBounds,
            contentDescription = "Translated description of what the image contains",
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                .constrainAs(photo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.matchParent
                }
        )

        if (painterState is AsyncImagePainter.State.Success) {
            Image(
                painterResource(R.drawable.baseline_play_circle_outline_24),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .constrainAs(playButton) {
                        top.linkTo(photo.top)
                        start.linkTo(photo.start)
                        end.linkTo(photo.end)
                        bottom.linkTo(photo.bottom)
                    }
            )
        }

        if (cameraUiModel.rec && painterState is AsyncImagePainter.State.Success) {
            Image(
                painterResource(R.drawable.rec_svgrepo_com),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Red),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(recImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .padding(8.dp)
                    .padding(bottom = 16.dp)
                    .size(24.dp)
            )
        }

        if (cameraUiModel.favorites) {
            Image(
                painterResource(R.drawable.star_svgrepo_com),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
                    .constrainAs(favoriteImage) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )
        }

        Text(
            text = cameraUiModel.name,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(name) {
                    top.linkTo(photo.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
    }
}

@Preview(
    apiLevel = 33
)
@Composable
fun CameraCardPreview() {
    CameraCard(
        CameraUiModel(
            0,
            true,
            "Camera 1",
            true,
            "ads",
            "https://serverspace.ru/wp-content/uploads/2019/06/backup-i-snapshot.png"
        )
    )
}