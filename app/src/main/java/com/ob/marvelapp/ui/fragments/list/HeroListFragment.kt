package com.ob.marvelapp.ui.fragments.list

import android.content.Context
import android.os.*
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.ob.marvelapp.databinding.FragmentListHeroBinding
import com.ob.marvelapp.extensions.application
import com.ob.marvelapp.ui.adapters.HeroListAdapter
import com.ob.marvelapp.ui.events.EventObserver
import com.ob.marvelapp.ui.model.UIHero
import com.ob.marvelapp.ui.screenstates.list.HeroListState
import com.ob.marvelapp.ui.screenstates.navigation.NavigationEvent
import com.ob.marvelapp.ui.viewmodels.list.HeroListViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HeroListFragment : Fragment() {

  @Inject
  lateinit var viewModel: HeroListViewModel

  private lateinit var binding: FragmentListHeroBinding
  private lateinit var adapter: HeroListAdapter
  private lateinit var layoutManager: LinearLayoutManager

  private var state: Parcelable? = null

  override fun onAttach(context: Context) {
    super.onAttach(context)

    application.component.heroListSubComponent.create(state).inject(this)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentListHeroBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupView()
    configureStates()
    prepareForEvents()
  }

  override fun onPause() {
    super.onPause()
    viewModel.state = layoutManager.onSaveInstanceState()
  }

  private fun configureStates() {
    viewModel.heroesStateList.observe(viewLifecycleOwner, { state ->
      when (state) {
        is HeroListState.Error -> Log.e("Error", state.toString())
        is HeroListState.ShowItems -> adapter.submitList(state.heroList)
        is HeroListState.IsLoading -> binding.progress.isVisible = state.isLoading
      }
    })
  }

  private fun prepareForEvents() {
    viewModel.navigation.observe(viewLifecycleOwner, EventObserver { event ->
      when (event) {
        is NavigationEvent.ToHeroDetail -> findNavController().navigate(HeroListFragmentDirections.actionToDetail(event.arg),
          FragmentNavigatorExtras())
      }
    })
  }

  override fun onResume() {
    super.onResume()
    viewModel.getHeroes()
  }

  private fun setupView() {
    adapter = HeroListAdapter(object : HeroListAdapter.OnItemClickListener {
      override fun onItemClick(uiHero: UIHero) {
        viewModel.navigateToDetail(NavigationEvent.ToHeroDetail(uiHero.id))
      }
    })
    layoutManager = LinearLayoutManager(context)
    binding.heroRecyclerView.layoutManager = layoutManager
    binding.heroRecyclerView.adapter = adapter
    viewModel.state?.let { layoutManager.onRestoreInstanceState(it) }
  }
}