package com.allinx.english.presentation.test.finish.customview

/**
 * Интерфейс для взаимодействия с CustomView AnalyticalPieChart
 */
interface AnalyticalPieChartInterface {

    /**
     * Метод для добавления списка данных для отображения на графике.
     * @property list - список данных, тип которого мы можете поменять
     * на свою определенную модель.
     */
    fun setDataChart(list: List<Pair<Int, String>>)

    /**
     * Метод для активирования анимации прорисовки.
     */
    fun startAnimation()
}