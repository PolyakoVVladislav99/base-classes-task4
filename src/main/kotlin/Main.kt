import kotlin.math.sqrt

data class Point(val x: Double, val y: Double)

class Triangle(val a: Point, val b: Point, val c: Point) {

    private fun distance(p1: Point, p2: Point): Double {
        return sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y))
    }

    fun isDegenerate(): Boolean {
        val ab = distance(a, b)
        val bc = distance(b, c)
        val ca = distance(c, a)
        return (ab + bc <= ca || ab + ca <= bc || bc + ca <= ab)
    }

    fun circumcenter(): Point {
        val d = 2 * (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y))
        if (d == 0.0) throw IllegalArgumentException("Треугольник вырожденный, невозможно построить описанную окружность.")

        val ux = ((a.x * a.x + a.y * a.y) * (b.y - c.y) +
                (b.x * b.x + b.y * b.y) * (c.y - a.y) +
                (c.x * c.x + c.y * c.y) * (a.y - b.y)) / d
        val uy = ((a.x * a.x + a.y * a.y) * (c.x - b.x) +
                (b.x * b.x + b.y * b.y) * (a.x - c.x) +
                (c.x * c.x + c.y * c.y) * (b.x - a.x)) / d
        return Point(ux, uy)
    }

    fun circumradius(): Double {
        val ab = distance(a, b)
        val bc = distance(b, c)
        val ca = distance(c, a)
        val s = (ab + bc + ca) / 2
        val area = sqrt(s * (s - ab) * (s - bc) * (s - ca))
        return (ab * bc * ca) / (4 * area)
    }
}

fun main() {
    try {
        println("Введите координаты вершины A (x y):")
        val (ax, ay) = readLine()!!.split(" ").map { it.toDoubleOrNull() ?: throw IllegalArgumentException("Некорректный ввод координат.") }
        println("Введите координаты вершины B (x y):")
        val (bx, by) = readLine()!!.split(" ").map { it.toDoubleOrNull() ?: throw IllegalArgumentException("Некорректный ввод координат.") }
        println("Введите координаты вершины C (x y):")
        val (cx, cy) = readLine()!!.split(" ").map { it.toDoubleOrNull() ?: throw IllegalArgumentException("Некорректный ввод координат.") }

        val triangle = Triangle(Point(ax, ay), Point(bx, by), Point(cx, cy))

        if (triangle.isDegenerate()) {
            println("Треугольник вырожденный, описанная окружность невозможна.")
            return
        }

        val center = triangle.circumcenter()
        val radius = triangle.circumradius()

        println("Координаты центра описанной окружности: (${center.x}, ${center.y})")
        println("Радиус описанной окружности: $radius")

    } catch (e: Exception) {
        println("Ошибка: ${e.message}. Убедитесь, что вводите данные корректно.")
    }
}
