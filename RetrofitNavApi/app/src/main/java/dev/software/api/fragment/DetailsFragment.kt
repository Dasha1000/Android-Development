package dev.software.api.fragment

import coil.load
import dev.software.api.databinding.FragmentDetailsBinding
import dev.software.api.model.CharacterDescription


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.software.api.adapter.ListCharactersAdapter
import dev.software.api.addPaginationScrollListener
import dev.software.api.addSpaceDecoration
import dev.software.api.databinding.FragmentListBinding
import dev.software.api.model.Item
import dev.software.api.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = requireNotNull(_binding)

    private var currentlyRequest: Call<List<CharacterDescription>>? = null

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDetailCharacter()

        with(binding){
            toolbar.setupWithNavController(findNavController())
        }
    }

    private fun loadDetailCharacter() {

        currentlyRequest = ApiService.api.characterDescription(args.id)
        currentlyRequest?.enqueue(object : Callback<List<CharacterDescription>> {
            override fun onResponse(
                call: Call<List<CharacterDescription>>,
                response: Response<List<CharacterDescription>>
            ) {
                if (response.isSuccessful) {

                    val characterDetail = response.body() ?: return
                    with(binding) {
                        textName.text = "Name: ${characterDetail[0].name}"
                        picture.load(characterDetail[0].url)
                        textBirthday.text = "Birthday: ${characterDetail[0].birthday}"
                        textNick.text = "Nick: ${characterDetail[0].nickname}"
                    }
                } else {
                    Toast.makeText(context, "The data was not received ", Toast.LENGTH_SHORT)
                }
            }

            override fun onFailure(call: Call<List<CharacterDescription>>, t: Throwable) {

                if (call.isCanceled) {
                    Toast.makeText(context, "The data was not received ", Toast.LENGTH_SHORT)
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentlyRequest?.cancel()
        _binding = null
    }

}

