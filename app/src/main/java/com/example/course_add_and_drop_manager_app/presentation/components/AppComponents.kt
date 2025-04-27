package com.example.course_add_and_drop_manager_app.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.course_add_and_drop_manager_app.R
import com.example.course_add_and_drop_manager_app.ui.theme.colorGray

import com.example.course_add_and_drop_manager_app.ui.theme.colorHeading
import com.example.course_add_and_drop_manager_app.ui.theme.colorPrimary
import com.example.course_add_and_drop_manager_app.ui.theme.colorSecondary
import com.example.course_add_and_drop_manager_app.ui.theme.colorText
import com.example.course_add_and_drop_manager_app.ui.theme.white
import org.w3c.dom.Text


@Composable
fun HeadingTextComponent(value:String){

    Text(
        text=value,
        modifier=Modifier
            .fillMaxWidth()
            .heightIn(min = 20.dp),
        style=TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal,
            color =colorHeading,
            textAlign = TextAlign.Center

        )
    )
}
@Composable
fun HeadingHomeTextComponent(value:String){

    Text(
        text=value,
        modifier=Modifier
            .fillMaxWidth()
            .heightIn(min = 20.dp),
        style=TextStyle(
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color =colorHeading,
            textAlign = TextAlign.Center

        )
    )
}
@Composable
fun NormalTextComponent(value:String){
    Text(
        text=value,
        modifier=Modifier.fillMaxWidth(),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        )
    )

}
@Composable
fun TextFieldPhotoComponent(labelValue: String,painterResource: Painter,contentDescription:String) {
    val textValue = remember { mutableStateOf("") }
    val localFocusManager= LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 8.dp),
        value = textValue.value,
        onValueChange = { textValue.value = it },
        label = { Text(text = labelValue,
            style= TextStyle(color=colorGray)) },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = colorPrimary,
            unfocusedIndicatorColor = Color.White,
            focusedLabelColor = colorPrimary,
            cursorColor = colorPrimary,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        singleLine = true,
        keyboardActions= KeyboardActions{
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        leadingIcon = {
            Icon(
                painter = painterResource,   // Replace 'ic_profile' with your actual icon file name
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp)  // Adjust size as needed
            )
        },


    )
}
@Composable
fun TextFieldComponent(labelValue: String,painterResource: Painter,contentDescription:String) {
    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 8.dp),
        value = textValue.value,
        onValueChange = { textValue.value = it },
        label = { Text(text = labelValue,
            style= TextStyle(color=colorGray)) },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = colorPrimary,
            unfocusedIndicatorColor = Color.White,
            focusedLabelColor = colorPrimary,
            cursorColor = colorPrimary,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(
                painter = painterResource,   // Replace 'ic_profile' with your actual icon file name
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp)  // Adjust size as needed
            )
        },


        )
}
@Composable
fun PasswordTextFieldComponent(labelValue: String,painterResource: Painter,contentDescription:String) {
    val textValue = remember { mutableStateOf("") }
    val passwordVisible=remember{ mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 8.dp),
        value = textValue.value,
        onValueChange = { textValue.value = it },
        label = {
            Text(
                text = labelValue,
                style = TextStyle(color = colorGray)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = colorPrimary,
            unfocusedIndicatorColor = Color.White,
            focusedLabelColor = colorPrimary,
            cursorColor = colorPrimary,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(
                painter = painterResource,   // Replace 'ic_profile' with your actual icon file name
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp)  // Adjust size as needed
            )
        },
        trailingIcon = {
            val iconImage=if(passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            var description = if(passwordVisible.value){
               stringResource(id=R.string.hide_password)

            }else{
                stringResource(id=R.string.show_password)
            }
            IconButton(onClick = {passwordVisible.value=!passwordVisible.value}) {
                Icon(
                    imageVector = iconImage,
                    contentDescription=description
                )
            }

        },
        visualTransformation =if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}
@Composable
fun PasswordLoginTextFieldComponent(labelValue: String,painterResource: Painter,contentDescription:String) {
    val textValue = remember { mutableStateOf("") }
    val passwordVisible=remember{ mutableStateOf(false) }
    val localFocusManager= LocalFocusManager.current
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 8.dp),
        value = textValue.value,
        onValueChange = { textValue.value = it },
        label = {
            Text(
                text = labelValue,
                style = TextStyle(color = colorGray)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = colorPrimary,
            unfocusedIndicatorColor = Color.White,
            focusedLabelColor = colorPrimary,
            cursorColor = colorPrimary,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        keyboardActions= KeyboardActions{
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        leadingIcon = {
            Icon(
                painter = painterResource,   // Replace 'ic_profile' with your actual icon file name
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp)  // Adjust size as needed
            )
        },
        trailingIcon = {
            val iconImage=if(passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            var description = if(passwordVisible.value){
                stringResource(id=R.string.hide_password)

            }else{
                stringResource(id=R.string.show_password)
            }
            IconButton(onClick = {passwordVisible.value=!passwordVisible.value}) {
                Icon(
                    imageVector = iconImage,
                    contentDescription=description
                )
            }

        },
        visualTransformation =if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}
@Composable
fun CheckboxComponent(value:String,onTextSelected:(String)->Unit){
    Row(modifier=Modifier
        .fillMaxWidth()
        .heightIn(56.dp)
        .padding(horizontal = 25.dp),
        verticalAlignment=Alignment.CenterVertically){
        val checkedState=remember{ mutableStateOf(false) }
        Checkbox(checked=checkedState.value,
            onCheckedChange ={checkedState.value!=checkedState.value} )
        ClickableTextComponent(value=value,onTextSelected)
    }
}
@Composable
fun ClickableTextComponent(value:String,onTextSelected:(String)->Unit){
    val initialText="Agree with"
    val terms =" terms "
    val andText=" and "
    val conditions= " conditions "
    val annotatedString=buildAnnotatedString{
        append(initialText)
        withStyle(style= SpanStyle(color=colorPrimary)){
            pushStringAnnotation(tag=terms, annotation=terms)
            append(terms)
        }
        append(andText)
        withStyle(style= SpanStyle(color=colorPrimary)){
            pushStringAnnotation(tag=conditions, annotation=conditions)
            append(conditions)
        }

    }

     ClickableText(text=annotatedString,onClick={offset->
         annotatedString.getStringAnnotations(offset,offset)
             .firstOrNull()?.also{span->
                 Log.d(" ClickableTextComponent","{${span.item}}")
                 if((span.item ==  conditions) || (span.item == terms)){
                     onTextSelected(span.item)

                 }


             }


     })
}


@Composable
fun ButtonComponent(
    value: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(260.dp) // Optional: fixed width for nicer centering
                .height(50.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = colorPrimary),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White
                )
            )
        }
    }
}
@Composable
fun ClickableLoginTextComponent(onTextSelected: (String) -> Unit) {
    val initialText = "Already have an account? "
    val loginText = "Log In "

    val annotatedString = buildAnnotatedString {
//        append(initialText)
        withStyle(style = SpanStyle(color = colorPrimary, fontSize = 16.sp)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    // Using Column to align the text properly
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Centers the content horizontally
        verticalArrangement = Arrangement.Center, // Centers the content vertically
        modifier = Modifier.fillMaxWidth() // Makes the column fill the available space
    ) {
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(offset, offset)
                    .firstOrNull()?.also { span ->
                        Log.d("ClickableTextComponent", "{${span.item}}")
                        if (span.item == loginText) {
                            onTextSelected(span.item)
                        }
                    }
            }
        )
    }
}
@Composable
fun ClickableSignUpTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {

    val loginText = "Sign Up "

    val annotatedString = buildAnnotatedString {
//        append(initialText)
        withStyle(style = SpanStyle(color = colorPrimary, fontSize = 16.sp)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    // Using Column to align the text properly
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Centers the content horizontally
        verticalArrangement = Arrangement.Center, // Centers the content vertically
        modifier = Modifier.fillMaxWidth() // Makes the column fill the available space
    ) {
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(offset, offset)
                    .firstOrNull()?.also { span ->
                        Log.d("ClickableTextComponent", "{${span.item}}")
                        if (span.item == loginText) {
                            onTextSelected(span.item)
                        }
                    }
            }
        )
    }
}
@Composable
fun UnderLinedTextComponent(tryingToLogin: Boolean = true, onTextSelected: (String) -> Unit) {

    val loginText = "Forget your password? "

    val annotatedString = buildAnnotatedString {
//        append(initialText)
        withStyle(style = SpanStyle(color = colorPrimary, fontSize = 16.sp)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }

    // Using Column to align the text properly
    Column(
//        horizontalAlignment = Alignment.CenterHorizontally, // Centers the content horizontally
//        verticalArrangement = Arrangement.Center, // Centers the content vertically
        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp) // Makes the column fill the available space
    ) {
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(offset, offset)
                    .firstOrNull()?.also { span ->
                        Log.d("ClickableTextComponent", "{${span.item}}")
                        if (span.item == loginText) {
                            onTextSelected(span.item)
                        }
                    }
            }
        )
    }
}