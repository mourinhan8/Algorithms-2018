@file:Suppress("UNUSED_PARAMETER")

package lesson6

import java.io.File

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    val m = second.length
    val n = first.length
    val sb = StringBuilder(maxOf(m, n))
    val num = Array(m + 1) { IntArray(n + 1) }
    for (i in 0..m) {
        for (j in 0..n) {
            if (i == 0 || j == 0)
                num[i][j] = 0
            else if (second[i - 1] == first[j - 1])
                num[i][j] = num[i - 1][j - 1] + 1
            else num[i][j] = maxOf(num[i - 1][j], num[i][j - 1])
        }
    }
    var index = num[m][n]
    if (index == 0) return ""
    var i = m
    var j = n
    while (i > 0 && j > 0) {
        when {
            second[i - 1] == first[j - 1] -> {
                sb.append(second[i - 1])
                i--
                j--
                index--
            }
            num[i - 1][j] > num[i][j - 1] -> i--
            else -> j--
        }
    }
    return sb.reverse().toString()
}
//Трудоемкость алгоритм - O(m * n)
//Ресурсоемкость - O(m * n)

/**
 * Наибольшая возрастающая подпоследовательность
 * Средняя
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    TODO()
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Сложная
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    val file = File(inputName).readLines()
    val number = mutableListOf<MutableList<Int>>()
    for (line in file) {
        val sub = line.split(" ").filter { it != "" }
        val subNumber = mutableListOf<Int>()
        for (i in sub) subNumber.add(i.toInt())
        number.add(subNumber)
    }
    val m = number.size
    val n = number[0].size
    for (i in 0 until m) {
        for (j in 0 until n) {
            if (i == 0 && j == 0) continue
            if (i == 0 && j != 0) number[i][j] += number[i][j - 1]
            if (i != 0 && j == 0) number[i][j] += number[i - 1][j]
            if (i != 0 && j != 0) number[i][j] += minOf(number[i][j - 1], number[i - 1][j], number[i - 1][j - 1])
        }
    }
    return number[m - 1][n - 1]
}
//Трудоемкость алгоритм - O(m * n)
//Ресурсоемкость - O(m * n)

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5