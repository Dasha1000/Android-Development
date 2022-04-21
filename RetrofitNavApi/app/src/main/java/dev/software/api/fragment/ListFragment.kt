package dev.software.api.fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import androidx.recyclerview.widget.LinearLayoutManager
import dev.software.api.R
import dev.software.api.adapter.ListCharactersAdapter
import dev.software.api.addPaginationScrollListener
import dev.software.api.addSpaceDecoration
import dev.software.api.databinding.FragmentListBinding
import dev.software.api.model.Item
import dev.software.api.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = requireNotNull(_binding)

    private val adapter by lazy {
        ListCharactersAdapter{
            findNavController().navigate(ListFragmentDirections.toDetails(it.id))
        }
    }

    private var currentlyRequestedList: Call<List<Item.Characters>>? = null
    private var isLoading = false
    private var curPage = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentListBinding.inflate(inflater, container,false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCharacter()

        with(binding) {

            swipeRefresh.setOnRefreshListener {
                adapter.submitList(emptyList())
                curPage = 0
                loadCharacter {
                    swipeRefresh.isRefreshing = false
                }
            }

            val layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager

            recyclerView.addSpaceDecoration(SPACE_SIZE)
            recyclerView.addPaginationScrollListener(layoutManager, ITEM_COUNT){
                loadCharacter()
            }
        }
    }

    private fun loadCharacter(onLoadCharacterFinished: () -> Unit = {}){

        if (isLoading) return

        isLoading = true

        val loadingFinishedCallback = {
            isLoading = false
            onLoadCharacterFinished()
        }

        val curList = adapter.currentList.toList()
        currentlyRequestedList = ApiService.api.getCharacters(PAGE_SIZE, curPage * PAGE_SIZE)
        isLoading = true
        currentlyRequestedList?.enqueue(object : Callback<List<Item.Characters>> {

            override fun onResponse(
                call: Call<List<Item.Characters>>,
                response: Response<List<Item.Characters>>
            ) {
                if (response.isSuccessful) {
                    val character = response.body() ?: return
                    if (curList.size == PAGE_SIZE) {
                        adapter.submitList(curList + character + Item.Loading)
                    }
                    else
                        adapter.submitList(curList.dropLast(1) + character + Item.Loading)
                    curPage += 1
                }
                else {
                    Toast.makeText(context, "The data was not received ", Toast.LENGTH_SHORT)
                }

                currentlyRequestedList = null
                loadingFinishedCallback()
            }

            override fun onFailure(call: Call<List<Item.Characters>>, t: Throwable) {
                if(call.isCanceled) {
                    Toast.makeText(context, "The data was not received ", Toast.LENGTH_SHORT)
                }
                currentlyRequestedList = null
                loadingFinishedCallback()

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentlyRequestedList?.cancel()
        _binding = null
    }

    companion object {
        private const val SPACE_SIZE = 50
        private const val ITEM_COUNT = 5
        private const val PAGE_SIZE = 5
    }
}
