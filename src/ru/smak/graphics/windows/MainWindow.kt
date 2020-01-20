package ru.smak.graphics.windows

import ru.smak.graphics.components.ExplicitFun
import ru.smak.graphics.components.GraphicsPanel
import ru.smak.graphics.components.ParametricFun
import ru.smak.graphics.convertation.CartesianScreenPlane
import ru.smak.graphics.painting.CartesianPainter
import ru.smak.graphics.painting.GridPainter
import ru.smak.graphics.painting.PanelPointer
import java.awt.Dimension
import javax.swing.GroupLayout
import javax.swing.JFrame
import javax.swing.WindowConstants
import kotlin.math.*


class MainWindow : JFrame("Экзамен: КТ, 3 курс") {

    /**
     * Блок оконных компонентов
     */
    private val mainPanel = GraphicsPanel()


    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        minimumSize = Dimension(480, 500)
        val gl = GroupLayout(contentPane)
        layout = gl
        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel, 450, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
        )
        gl.setHorizontalGroup(
            gl.createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel, 450, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
        )
        pack()
        val plane = CartesianScreenPlane(
            mainPanel.width,
            mainPanel.height,
            -3.5, 3.5,
            -3.5, 3.5
        )
        val cartesianP = CartesianPainter(plane)
        mainPanel.addPainter(cartesianP)
        val gridP = GridPainter(plane)
        mainPanel.addPainter(gridP, 0)
        val funk = { x:Double ->  abs(x) / abs(x+1) }
        val x = {t:Double -> cos(4*t)}
        val y = {t:Double -> sin(4*t)}
        val funcp = ExplicitFun(plane,funk)
        val funct = ParametricFun(plane,x,y,-10.0,10.0)
        mainPanel.addPainter(funct,0)
        mainPanel.addPainter(funcp,0)
        /**
         * Добавление указателя в позицию мыши
         */
        val pointer = PanelPointer(plane)
        mainPanel.mousePressedListeners.add { e -> e?.let { pointer.set(e.point, mainPanel.graphics) } }
        mainPanel.mouseDraggedListeners.add { e -> e?.let { pointer.move(e.point, mainPanel.graphics) } }
        mainPanel.mouseReleasedListeners.add { e -> e?.let { pointer.remove(mainPanel.graphics) } }
    }

    fun makeVisible() {
        isVisible = true
        repaint()
    }

}