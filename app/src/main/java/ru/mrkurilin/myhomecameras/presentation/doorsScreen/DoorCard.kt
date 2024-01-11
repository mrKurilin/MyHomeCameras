package ru.mrkurilin.myhomecameras.presentation.doorsScreen

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import ru.mrkurilin.myhomecameras.R

@Composable
fun DoorCard(
    doorUiModel: DoorUiModel,
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
        val (photo, playButton, favoriteImage, name, lock) = createRefs()

        if (doorUiModel.snapshot.isNotBlank()) {
            AsyncImage(
                model = doorUiModel.snapshot,
                contentScale = ContentScale.FillBounds,
                contentDescription = "Translated description of what the image contains",
                modifier = Modifier
                    .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                    .constrainAs(photo) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(name.top)
                        width = Dimension.matchParent
                    }
            )
        }


        Text(
            text = doorUiModel.name,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .constrainAs(name) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }

        )

        if (doorUiModel.snapshot.isNotBlank()) {
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

        if (doorUiModel.favorites) {
            Image(
                painterResource(R.drawable.star_svgrepo_com),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Yellow),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp)
                    .constrainAs(favoriteImage) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(lock.start)
                    }
            )
        }

        Image(
            painterResource(R.drawable.lock_svgrepo_com),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.Blue),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(24.dp)
                .constrainAs(lock) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Preview
@Composable
fun DoorCardFavoritePreview() {
    DoorCard(
        DoorUiModel(
            "Name",
            room = "Room",
            id = 0,
            favorites = true,
            snapshot = "sa"
        )
    )
}

@Preview
@Composable
fun DoorCardUnFavoritePreview() {
    DoorCard(
        DoorUiModel(
            "Name",
            room = "Room",
            id = 0,
            favorites = false,
            snapshot = "sa"
        )
    )
}