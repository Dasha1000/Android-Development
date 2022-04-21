package dev.software.bing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import dev.software.bing.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {

    var resultList: ArrayList<String> = arrayListOf()

    private var _binding: FragmentCalculatorBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentCalculatorBinding.inflate(inflater, container, false)
            .also { _binding = it  }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            btnClear.setOnClickListener{
                inputTextView.text = ""
            }
            btnLeftBracket.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() +btnLeftBracket.text
            }
            btnRightBracket.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() +btnRightBracket.text
            }
            btnDivide.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() +btnDivide.text
            }
            btnSeven.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnSeven.text
            }
            btnEight.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnEight.text
            }
            btnNine.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnNine.text
            }
            btnMultiply.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnMultiply.text
            }
            btnFour.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnFour.text
            }
            btnFive.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnFive.text
            }
            btnSix.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnSix.text
            }
            btnMinus.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnMinus.text
            }
            btnOne.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnOne.text
            }
            btnTwo.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnTwo.text
            }
            btnThree.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnThree.text
            }
            btnPlus.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnPlus.text
            }
            btnZero.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + btnZero.text
            }
            btnDot.setOnClickListener{
                inputTextView.text = inputTextView.text.toString() + ","
            }
            btnDelete.setOnClickListener{
                if (inputTextView.text.isNotEmpty())
                    inputTextView.text = inputTextView.text.removeRange(inputTextView.text.lastIndex,inputTextView.text.lastIndex+1)
            }
            btnEqual.setOnClickListener{
                val eval = Evaluate()
                val expr = inputTextView.text.toString()
                val lexemeList = eval.parsing(expr)
                val utilityLexemes = UtilityLexemes(lexemeList)
                try{
                    var result = eval.express(utilityLexemes).toString()

                    if (result.contains("."))
                        result = result.replace('.',',')

                    inputTextView.text = result
                    resultList.add("$expr = $result")
                    println(resultList)
                } catch (ex : RuntimeException){
                    println(ex.message)
                    Toast.makeText(requireContext(), "Check your expression", Toast.LENGTH_SHORT).show()
                }
            }
            history.setOnClickListener {
                  pushFragment(resultList)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding
    }
}