package dev.software.evaluate


//    PARSER RULES
//    expression : plusMinus* EOF
//    plusMinus: mulDiv ( ( '+' | '-' ) mulDiv )*
//    mulDiv : factor ( ( '*' | '/' ) factor )*
//    factor : NUMBER | '(' expression ')'



fun main() {
    //val expression: String = "105 - 99 + 3* 5 /4 " //9.75
    //val expression: String = "105 - 99 + 3* (14 - 2* (19 - 3)) /4 " //-7.5
    val expression: String = "33*9-5*(6+5*4)/3 " //253.66666666666666
    //val expression = "11*8-5* (2-3)"//93
    val eval = Evaluate()
    val lexemeList = eval.parsing(expression)
    val utilityLexemes = UtilityLexemes(lexemeList)
    println(eval.express(utilityLexemes))
}

enum class Type {
    MINUS, PLUS, DIVISION, MULTIPLICATION, NUMBER, L_BRACKET, R_BRACKET, EOF
}


data class Lexeme(var type: Type, var value: String){

}

// helper class for working with the list of lexemes
class UtilityLexemes(var lex: List<Lexeme>) {
    var pos = 0
        private set

    fun next(): Lexeme = lex[pos++]

    fun back() = pos--
}


internal class Evaluate() {
    // break the expression into lexemes
    fun parsing(expression: String): List<Lexeme> {
        val lex: MutableList<Lexeme> = mutableListOf()
        var pos = 0
        while (pos < expression.length) {
            var currentLexeme = expression[pos]
            when (currentLexeme) {
                '(' -> {
                    lex.add(Lexeme(Type.L_BRACKET, currentLexeme.toString()))
                    pos++
                }
                ')' -> {
                    lex.add(Lexeme(Type.R_BRACKET, currentLexeme.toString()))
                    pos++
                }
                '+' -> {
                    lex.add(Lexeme(Type.PLUS, currentLexeme.toString()))
                    pos++
                }
                '-' -> {
                    lex.add(Lexeme(Type.MINUS, currentLexeme.toString()))
                    pos++
                }
                '*' -> {
                    lex.add(Lexeme(Type.MULTIPLICATION, currentLexeme.toString()))
                    pos++
                }
                '/' -> {
                    lex.add(Lexeme(Type.DIVISION, currentLexeme.toString()))
                    pos++
                }
                in '0'..'9' -> {
                    val sb = StringBuilder()
                    do {
                        sb.append(currentLexeme)
                        pos++
                        if (pos >= expression.length) {
                            break
                        }
                        currentLexeme = expression[pos]
                    } while (currentLexeme in '0'..'9')
                    lex.add(Lexeme(Type.NUMBER, sb.toString()))
                }
                else -> {
                    if (currentLexeme != ' ') {
                        throw RuntimeException("Unexpected symbol: $currentLexeme")
                    }
                    pos++
                }
            }
        }
        lex.add(Lexeme(Type.EOF, ""))
        return lex
    }

    fun express(lex: UtilityLexemes): Double {
        val lexeme = lex.next()
        if (lexeme.type == Type.EOF)
            return 0.0
        else {
            lex.back()
            return plusMinus(lex)
        }
    }

    private fun plusMinus(lex: UtilityLexemes): Double {
        var value = mulDiv(lex)
        while (true) {
            val lexeme = lex.next()
            when (lexeme.type) {
                Type.PLUS -> value += mulDiv(lex)
                Type.MINUS -> value -= mulDiv(lex)
                Type.EOF, Type.R_BRACKET -> {
                    lex.back()
                    return value
                }
                else -> throw RuntimeException( "Unexpected token: ${lexeme.value} at position: ${lex.pos}")
            }
        }
    }

    private fun mulDiv(lex: UtilityLexemes): Double {
        var value = factor(lex)
        while (true) {
            val lexeme = lex.next()
            when (lexeme.type) {
                Type.MULTIPLICATION -> value *= factor(lex)
                Type.DIVISION -> value /= factor(lex)
                Type.EOF, Type.R_BRACKET, Type.PLUS, Type.MINUS,  -> {
                    lex.back()
                    return value
                }
                else -> throw RuntimeException( "Unexpected token: ${lexeme.value} at position: ${lex.pos}")
            }
        }
    }


    private fun factor(lex: UtilityLexemes): Double {
        var lexeme = lex.next()
        when (lexeme.type) {
            Type.NUMBER -> return lexeme.value.toDouble()
            Type.L_BRACKET -> {
                val value = express(lex)
                lexeme = lex.next()
                if (lexeme.type != Type.R_BRACKET) { // forgot about the right bracket
                    throw RuntimeException( "Unexpected token: ${lexeme.value} at position: ${lex.pos}")
                }
                return value
            }
            else -> throw RuntimeException("Unexpected token: ${lexeme.value} at position: ${lex.pos}")
        }
    }
}

