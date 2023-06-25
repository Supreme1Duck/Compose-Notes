package com.example.duck.fastnotes.features.core

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.MainTheme
import com.example.duck.fastnotes.utils.ViewUtils.noRippleClickable
import kotlinx.coroutines.launch

/**
 * Base compose bottom sheet wrapper for all sheets in application
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetUI(modalSheetState: ModalBottomSheetState, onDone: () -> Unit, onCancel: () -> Unit, content: @Composable () -> Unit = {}) {
    val coroutineScope = rememberCoroutineScope()

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch {
            modalSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier
            .padding(bottom = MainTheme.spacing.bottom)
            .fillMaxWidth(),
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetBackgroundColor = MainTheme.colors.primary,
        sheetContentColor = MainTheme.colors.primary,
        sheetElevation = 10.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(MainTheme.spacing.default)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        modifier = Modifier.noRippleClickable(onCancel),
                        text = stringResource(id = R.string.common_cancel)
                    )

                    Text(
                        modifier = Modifier.noRippleClickable(onDone),
                        text = stringResource(id = R.string.common_done)
                    )
                }

                content()
            }
        }
    ) {}
}