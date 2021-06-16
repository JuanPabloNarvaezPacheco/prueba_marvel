package com.ob.marvelapp.ui.fragments.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ob.marvelapp.databinding.FragmentDetailHeroBinding
import com.ob.marvelapp.extensions.*
import com.ob.marvelapp.ui.adapters.HeroDetailListAdapter
import com.ob.marvelapp.ui.model.UIHero
import com.ob.marvelapp.ui.screenstates.detail.HeroDetailState
import com.ob.marvelapp.ui.viewmodels.detail.HeroDetailViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HeroDetailFragment : Fragment() {

  private lateinit var binding: FragmentDetailHeroBinding
  private val args: HeroDetailFragmentArgs by navArgs()

  @Inject
  lateinit var viewModel: HeroDetailViewModel

  override fun onAttach(context: Context) {
    super.onAttach(context)

    application.component.heroDetailSubComponent.create().inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentDetailHeroBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    configureStates()
    viewModel.getHero(args.heroId)
  }

  private fun configureStates() {
    viewModel.heroState.observe(viewLifecycleOwner, { state ->
      when (state) {
        is HeroDetailState.Error -> Log.e("ERROR", "Error loading hero")
        is HeroDetailState.ShowItem -> setupView(state.item)
        is HeroDetailState.IsLoading -> Log.d("LOADING", "Loading hero")
      }
    })
  }

  private fun setupView(hero: UIHero) {
    with(binding) {
      val storiesAdapter = HeroDetailListAdapter()
      val comicsAdapter = HeroDetailListAdapter()
      imgHero.loadImageUrl(hero.thumbnail)
      txtHeroName.text = hero.name
      txtHeroDescription.text = hero.description
      comicsRecyclerView.adapter = comicsAdapter
      storiesRecyclerView.adapter = storiesAdapter
      comicsAdapter.submitList(hero.comics)
      storiesAdapter.submitList(hero.stories)
    }
  }
}