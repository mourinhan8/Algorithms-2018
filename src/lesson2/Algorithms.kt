@file:Suppress("UNUSED_PARAMETER")

package lesson2

import java.io.File
import kotlin.math.sqrt

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    TODO()
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    var res = 0
    for (i in 2..menNumber) {
        res = (choiceInterval + res) % i
    }
    return res + 1
}
//Трудоемкость алгоритм - O(N) - N: menNumber
//Ресурсоемкость - O(1)

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
fun longestCommonSubstring(first: String, second: String): String {
    var sb = StringBuilder()
    val m = first.length
    val n = second.length
    var maxlen = 0
    var last = 0
    val num = Array(m) { IntArray(n) }
    for (i in 0 until m) {
        for (j in 0 until n) {
            if (first[i] == second[j]) {
                if (i == 0 || j == 0)
                    num[i][j] = 1
                else num[i][j] = 1 + num[i - 1][j - 1]
                if (num[i][j] > maxlen) {
                    maxlen = num[i][j]
                    val thisBegin = i - num[i][j] + 1
                    if (last == thisBegin)
                        sb.append(first[i])
                    else {
                        last = thisBegin
                        sb = StringBuilder()
                        sb.append(first.substring(last, i + 1))
                    }
                }
            }
        }
    }
    return sb.toString()
}
//Трудоемкость алгоритм - O(m*n)
//Ресурсоемкость - O(m*n)

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
fun calcPrimesNumber(limit: Int): Int {
    if (limit <= 1) return 0
    val arr = IntArray(limit + 1)
    for (i in 0..limit) arr[i] = 1
    arr[0] = 0
    arr[1] = 0
    for (i in 2..sqrt(limit.toDouble()).toInt()) {
        if (arr[i] == 1)
            for (j in 2..(limit / i)) arr[i * j] = 0
    }
    var count = 0
    for (i in arr)
        if (i == 1) count++
    return count
}
//Трудоемкость алгоритм - O(N logN) - N : limit
//Ресурсоемкость - O(N + 1)
/**
 * Балда
 * Сложная
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */

val list = mutableListOf<List<Char>>()

fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    val file = File(inputName).readLines()
    for (line in file) {
        val sub = line.split(" ").filter { it != "" }.map { it[0] }
        list.add(sub)
    }
    val setRes = mutableSetOf<String>()
    val map = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    for (i in 0 until list.size) {
        for (j in 0 until list[i].size) {
            val key = list[i][j]
            if (!map.containsKey(list[i][j])) {
                val s = mutableListOf(Pair(j, i))
                map[key] = s
            } else map.getValue(key).add(Pair(j, i))
        }
    }
    for (word in words) {
        val ch = word[0]
        if (map.containsKey(ch)) {
            for (i in map.getValue(ch)) {
                if (checkWord(i.first, i.second, word[1], word.substring(2))) {
                    setRes.add(word)
                    break
                }
            }
        }
    }
    return setRes
}

fun checkWord(x: Int, y: Int, c: Char, str: String): Boolean {
    val listMove = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
    for (i in listMove) {
        val k = x + i.first
        val l = y + i.second
        if (k >= 0 && l >= 0 && k < list[0].size && l < list.size) {
            if (list[l][k] == c) {
                if (str.isEmpty()) return true
                if (checkWord(k, l, str[0], str.substring(1))) return true
            }
        }
    }
    return false
}
//Трудоемкость алгоритм - O(m * n) - m: размер списка list, n: размер подсписка в списка list
//Ресурсоемкость - O(m * n) - m: размер списка list, n: размер подсписка в списка list