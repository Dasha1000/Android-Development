package dev.software.bing
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.software.bing.databinding.FragmentListBinding
import kotlin.collections.ArrayList


class ListFragment() : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var resultList: ArrayList<String>? = requireArguments().getStringArrayList(KEY)

        with(binding){
            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = resultList?.let { NotesAdapter(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding

    }

    companion object{

        private const val KEY = "res"

        fun getInstance(list: ArrayList<String>): ListFragment {
            return ListFragment().apply {

                arguments = bundleOf(
                        KEY to list
                )
            }

        }
    }
}