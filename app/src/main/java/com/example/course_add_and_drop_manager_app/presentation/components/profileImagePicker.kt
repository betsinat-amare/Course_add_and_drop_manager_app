import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.course_add_and_drop_manager_app.R
import com.example.course_add_and_drop_manager_app.presentation.components.NormalTextComponent

@Composable
fun ProfileImagePlaceholder(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .clickable { onClick() }
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Default Profile Icon",
                tint = Color.White,
                modifier = Modifier.size(60.dp)

            )
        }
        Spacer(modifier=Modifier.height(8.dp))

        NormalTextComponent(
            value = stringResource(id = R.string.welcomef),

        )
    }
}
@Composable
fun ProfileImgPlaceholder(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
            modifier = Modifier
                .padding(end=25.dp, top=10.dp)
                .clickable { onClick() }
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.Gray),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Default Profile Icon",
                tint = Color.White,
                modifier = Modifier.size(60.dp)

            )


    }
}
