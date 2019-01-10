package com.david.prestoq

import android.view.ViewGroup
import org.junit.Assert
import org.junit.Test
import java.lang.IllegalArgumentException

class ManagerSpecialAdapterTest {

    @Test(expected = IllegalArgumentException::class)
    fun itemWidthGreaterThanMaxCanvasUnits() {
        val layoutParams = ViewGroup.MarginLayoutParams(100, 100)
        ManagerSpecialAdapter.getWidth(1024, 17, 16, layoutParams)
    }

    @Test
    fun itemWidthEqualToMaxCanvasUnits() {
        val layoutParams = ViewGroup.MarginLayoutParams(100, 100)
        val width = ManagerSpecialAdapter.getWidth(1024, 16, 16, layoutParams)
        Assert.assertEquals(1024, width)
    }

    @Test
    fun itemWidthWithLeftRightMargins() {
        val layoutParams = ViewGroup.MarginLayoutParams(100, 100)
        layoutParams.rightMargin = 16
        layoutParams.leftMargin = 16
        val width = ManagerSpecialAdapter.getWidth(1024, 16, 16, layoutParams)
        Assert.assertEquals(992, width)
    }

    @Test
    fun itemHeightWithTopBottomMargins() {
        val layoutParams = ViewGroup.MarginLayoutParams(100, 100)
        layoutParams.topMargin = 16
        layoutParams.bottomMargin = 16
        val height = ManagerSpecialAdapter.getHeight(1024, 16, 16, layoutParams)
        Assert.assertEquals(992, height)
    }

    @Test
    fun itemHeightIsNotCappedByMaxCanvasUnits() {
        val layoutParams = ViewGroup.MarginLayoutParams(100, 100)
        val height = ManagerSpecialAdapter.getHeight(1024, 17, 16, layoutParams)
        Assert.assertEquals(1024 * 17 / 16, height)
    }
}